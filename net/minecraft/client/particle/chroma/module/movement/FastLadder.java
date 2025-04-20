package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockVine;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventApplyMotion;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.BlockUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastLadder extends Module
{
	public FastLadder()
	{
		super("FastLadder", 18, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("Vanilla");
		options.add("AAC 3.6.4");
		options.add("Teleport");
		options.add("General AAC");
		options.add("Old AAC");
		options.add("Latest AAC");
		this.getSetManager().rSetting(new Setting(18, "Mode", "The working mode of the FastLadder module.", this, "Vanilla", options));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		this.setSuffix(this.getModeColored());
		
		if (mc.thePlayer.isOnLadder() && mc.thePlayer.isCollidedHorizontally && e.getState() == EventState.POST)
		{
			if (this.getMode().equalsIgnoreCase("AAC 3.6.4"))
			{
				mc.thePlayer.setSprinting(true);
				mc.timer.timerSpeed = 1.03F;
				mc.thePlayer.motionY *= 1.43F;
			}
			else if (this.getMode().equals("Vanilla"))
			{
				mc.thePlayer.setSprinting(true);
				mc.thePlayer.motionY *= 2.3F;
			}
		}
		else
		{
			mc.timer.timerSpeed = 1.0F;
		}
	}
	
	@EventTarget
	public void onApplyMotion(EventApplyMotion event)
	{
		if (this.getMode().equals("Teleport"))
		{
			if (mc.thePlayer.isOnLadder() && mc.gameSettings.keyBindForward.isKeyDown())
			{
	            for (int i = (int) mc.thePlayer.posY; i < 256; i++)
	            {
	                final Block block = BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ));

	                if (!(block instanceof BlockLadder))
	                {
	                    final EnumFacing horizontalFacing = mc.thePlayer.getHorizontalFacing();
	                    double x = 0;
	                    double z = 0;
	                    
	                    switch(horizontalFacing)
	                    {
	                        case DOWN:
	                        case UP:
	                            break;
	                        case NORTH:
	                            z = -1;
	                            break;
	                        case EAST:
	                            x = +1;
	                            break;
	                        case SOUTH:
	                            z = +1;
	                            break;
	                        case WEST:
	                            x = -1;
	                            break;
	                    }

	                    mc.thePlayer.setPosition(mc.thePlayer.posX + x, i, mc.thePlayer.posZ + z);
	                    break;
	                }
	            }
			}
		}
		else if (this.getMode().equals("General AAC"))
		{
			if (mc.thePlayer.isCollidedHorizontally)
			{
	            final EnumFacing facing = mc.thePlayer.getHorizontalFacing();
	            double x = 0;
	            double z = 0;
	            
	            if (facing == EnumFacing.NORTH)
	                z = -0.99;
	            if (facing == EnumFacing.EAST)
	                x = +0.99;
	            if (facing == EnumFacing.SOUTH)
	                z = +0.99;
	            if (facing == EnumFacing.WEST)
	                x = -0.99;

	            final BlockPos blockPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
	            final Block block = BlockUtils.getBlock(blockPos);

	            if (block instanceof BlockLadder || block instanceof BlockVine)
	            {
	                event.setY(0.5);
	                mc.thePlayer.motionY = 0;
	            }
			}
		}
		else if (this.getMode().equals("Old AAC"))
		{
			if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isOnLadder())
			{
	            event.setY(0.1649D);
	            mc.thePlayer.motionY = 0;
			}
		}
		else if (this.getMode().equals("Latest AAC"))
		{
            if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isOnLadder())
            {
            	event.setY(0.1699D);
                mc.thePlayer.motionY = 0;
            }
		}
	}
}