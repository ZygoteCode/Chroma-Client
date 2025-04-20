package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.block.BlockStairs;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.util.BlockPos;

public class FastStairs extends Module
{
	public FastStairs()
	{
		super("FastStairs", 74, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("AAC 3.2.2");
		this.getSetManager().rSetting(new Setting(220, "Mode", "The working mode of the Speed module.", this, "AAC 3.2.2", options));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		mc.timer.timerSpeed = 1.0F;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
		
		if (mc.thePlayer.isSneaking() || mc.thePlayer.isBlocking() || mc.thePlayer.moveForward == 0.0F)
		{
			return;
		}
		
		BlockPos bp = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
		
		if (!(mc.theWorld.getBlockState(bp).getBlock() instanceof BlockStairs))
		{
			return;
		}
		
		if (mc.thePlayer.motionY != -0.0784000015258789)
		{
			return;
		}
		
		if (mc.thePlayer.motionY > 0.0)
		{
			return;
		}
		
		if (this.getMode().equals("AAC 3.2.2"))
		{
			mc.thePlayer.motionX *= 1.7D;
			mc.thePlayer.motionZ *= 1.7D;
		}
	}
}