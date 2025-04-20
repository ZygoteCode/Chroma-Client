package net.minecraft.client.particle.chroma.module.world;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.RotationUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class ChestAura extends Module
{
	private BlockPos targetBlock;
	private net.minecraft.client.particle.chroma.utils.Timer timer;
	private BlockPos lastBlock;
	private ArrayList<BlockPos> pos = new ArrayList();
	
	public ChestAura()
	{
		super("ChestAura", 56, Category.WORLD);
		timer = new net.minecraft.client.particle.chroma.utils.Timer();
	}
	
	@Override
	public void setup()
	{	
		this.getSetManager().rSetting(new Setting(202, "HiveMC chests", "", this, false));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		timer.reset();
		targetBlock = null;
		lastBlock = null;
		pos.clear();
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (mc.currentScreen instanceof GuiChest)
		{
			return;
		}
		
		if (event.getState().equals(EventState.PRE))
		{
			if (targetBlock != null)
			{
				return;
			}
			
			for (int x = -4; x < 4; x++)
			{
				for (int y = 4; y > -4; y--)
				{
					for (int z = -4; z < 4; z++)
					{
						int xPos = (int) (mc.thePlayer.posX + x), yPos = (int) (mc.thePlayer.posY + y), zPos = (int) (mc.thePlayer.posZ + z);
						BlockPos bp = new BlockPos(xPos, yPos, zPos);
						
						boolean ya = true;
						
						for (BlockPos position: pos)
						{
							if (position.getX() == xPos && position.getY() == yPos && position.getZ() == zPos)
							{
								ya = false;
								break;
							}
						}
						
						if (!ya)
						{
							continue;
						}
						
						Block block = mc.theWorld.getBlockState(bp).getBlock();
						
						if (block == Blocks.chest)
						{
							float[] rotations = RotationUtil.getBlockRotations(bp);
							event.setYaw(rotations[0]);
							event.setPitch(rotations[1]);
							this.targetBlock = bp;
							timer.reset();
						}
						else if (block == Blocks.barrier)
						{
							if (this.getSetManager().getSettingById(202).getValBoolean())
							{
								float[] rotations = RotationUtil.getBlockRotations(bp);
								event.setYaw(rotations[0]);
								event.setPitch(rotations[1]);
								this.targetBlock = bp;
								timer.reset();
							}
						}
					}
				}
			}
		}
		else if (event.getState().equals(EventState.POST))
		{
			if (this.targetBlock != null && timer.hasReach(100D))
			{
				BlockPos bp = this.targetBlock;
				Block block = mc.theWorld.getBlockState(bp).getBlock();
                this.mc.thePlayer.swingItem();
                this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem(), bp, EnumFacing.DOWN, new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ));
				timer.reset();
				
				if (mc.currentScreen instanceof GuiChest)
				{
					pos.add(targetBlock);
				}
				
				this.targetBlock = null;
			}
		}
	}
}