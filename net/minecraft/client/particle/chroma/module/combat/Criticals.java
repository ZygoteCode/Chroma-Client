package net.minecraft.client.particle.chroma.module.combat;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventAttack;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module
{
	public Criticals()
	{
		super("Criticals", 26, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("Packet");
		options.add("Jump");
		Chroma.getSetManager().rSetting(new Setting(61, "Mode", "The working mode of the Criticals module.", this, "Packet", options));
		super.setup();
	}
	
	@EventTarget
	public void onAttackEntity(EventAttack e)
	{
		if (e.getType() == 0 && e.getState() == EventState.PRE)
		{
			doCritical();
		}
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		this.setSuffix(this.getModeColored());
	}
	
	public static void doCritical()
	{
		if (!mc.thePlayer.onGround)
		{
			return;
		}
		
		if (mc.thePlayer.isInWater() || mc.thePlayer.isInLava())
		{
			return;
		}
		
		if (!mc.getChroma().getModuleManager().getModuleByID(26).isToggled())
		{
			return;
		}
		
		String mode = mc.getChroma().getModuleManager().getModuleByID(26).getMode();
		
        if (mode.equalsIgnoreCase("Packet"))
        {
        	double posX = mc.thePlayer.posX;
            double posY = mc.thePlayer.posY;
            double posZ = mc.thePlayer.posZ;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 0.0625, posZ, true));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 1.1E-5, posZ, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, false));
        }
        else if (mode.equalsIgnoreCase("Jump"))
        {
        	mc.gameSettings.keyBindJump.pressed = false;
        	mc.thePlayer.jump();
        }
	}
}