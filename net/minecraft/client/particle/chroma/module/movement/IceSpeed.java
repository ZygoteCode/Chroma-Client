package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class IceSpeed extends Module
{
	public IceSpeed()
	{
		super("IceSpeed", 77, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("NCP");
		this.getSetManager().rSetting(new Setting(251, "Mode", "", this, "NCP", modes));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
		
		if (mc.thePlayer.isSneaking() || mc.thePlayer.isBlocking() || mc.thePlayer.moveForward == 0.0F)
		{
			return;
		}
		
		BlockPos bp = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
		
		if (mc.theWorld.getBlockState(bp).getBlock() instanceof BlockIce || mc.theWorld.getBlockState(bp).getBlock() instanceof BlockPackedIce)
		{
			if (this.getMode().equals("NCP"))
			{
				if (mc.gameSettings.keyBindForward.pressed)
				{
					mc.gameSettings.keyBindJump.pressed = false;
					
					if (mc.thePlayer.onGround)
					{
						mc.thePlayer.jump();
						mc.timer.timerSpeed = 1.0F;
						mc.thePlayer.motionX *= 1.15D;
						mc.thePlayer.motionZ *= 1.15D;
						mc.thePlayer.moveStrafing *= 2;
					}
					else
					{
						mc.thePlayer.jumpMovementFactor = 0.0265F;
					}
				}
			}
		}
	}
	
	@Override
	public void onToggle()
	{
        Blocks.ice.slipperiness = 0.98F;
        Blocks.packed_ice.slipperiness = 0.98F;
		super.onToggle();
	}
}