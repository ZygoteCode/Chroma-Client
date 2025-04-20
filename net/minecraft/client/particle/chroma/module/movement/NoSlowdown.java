package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdown extends Module
{
	private boolean wasOnGround;
	
	public NoSlowdown()
	{
		super("NoSlowdown", 27, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(63, "Item", "", this, true));
		this.getSetManager().rSetting(new Setting(64, "Soulsand", "", this, true));
		this.getSetManager().rSetting(new Setting(65, "Slime", "", this, true));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		this.wasOnGround = false;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        if (mc.thePlayer.isBlocking() && (mc.thePlayer.motionX != 0.0 || mc.thePlayer.motionZ != 0.0) && this.wasOnGround && mc.thePlayer.isUsingItem() && this.getSetManager().getSettingById(63).getValBoolean())
        {
            if (event.getState().equals(EventState.PRE))
            {
            	mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
            else if (event.getState().equals(EventState.POST))
            {
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            }
        }
        
        this.wasOnGround = mc.thePlayer.onGround;
	}
}