package net.minecraft.client.particle.chroma.module.minigames;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.client.particle.chroma.utils.RotationUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedFucker extends Module
{
	private BlockPos targetBlock;
	private net.minecraft.client.particle.chroma.utils.Timer timer;
	
	public BedFucker()
	{
		super("BedFucker", 55, Category.MINIGAMES);
		timer = new net.minecraft.client.particle.chroma.utils.Timer();
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(291, "Reach", "", this, 4.0, 1.0, 7.0, true));
		this.getSetManager().rSetting(new Setting(292, "Delay", "", this, 100, 50, 500, true));
	}
	
	@Override
	public void onToggle()
	{
		timer.reset();
		targetBlock = null;
		super.onToggle();
	}
	
	@EventTarget
	public void onPreUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
			if (targetBlock != null)
			{
				return;
			}
			
			int reach = this.getSetManager().getSettingById(291).getValueI();
			
			for (int x = -reach; x < reach; x++)
			{
				for (int y = reach; y > -reach; y--)
				{
					for (int z = -reach; z < reach; z++)
					{
						int xPos = (int) (mc.thePlayer.posX + x), yPos = (int) (mc.thePlayer.posY + y), zPos = (int) (mc.thePlayer.posZ + z);
						BlockPos bp = new BlockPos(xPos, yPos, zPos);
						Block block = mc.theWorld.getBlockState(bp).getBlock();
						
						if (block == Blocks.bed || block == Blocks.dragon_egg || block == Blocks.cake)
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
		else if (event.getState().equals(EventState.POST))
		{
			if (this.targetBlock != null && timer.hasReach(this.getSetManager().getSettingById(292).getValueD()))
			{
				BlockPos bp = this.targetBlock;
				Block block = mc.theWorld.getBlockState(bp).getBlock();
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bp, EnumFacing.NORTH));
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bp, EnumFacing.NORTH));
				this.targetBlock = null;
				timer.reset();
			}
		}
	}
}