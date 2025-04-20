package net.minecraft.client.particle.chroma.module.world;

import net.minecraft.block.Block;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.module.combat.AutoArmor;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.BlockData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Tower extends Module
{
	private BlockPos pos;
	private BlockData data;
	private net.minecraft.client.particle.chroma.utils.Timer timer;
	
	public Tower()
	{
		super("Tower", 28, Category.WORLD);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(81, "Delay", "Delay of Tower.", this, 200, 1, 1000, true));
		this.getSetManager().rSetting(new Setting(82, "Fast tower", "", this, false));
		this.getSetManager().rSetting(new Setting(83, "Silent placing", "", this, false));
		this.getSetManager().rSetting(new Setting(84, "Silent swing", "", this, false));
		this.getSetManager().rSetting(new Setting(85, "Timer boost", "", this, 1.0, 1.0, 5.0, false));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		data = null; 
		timer = new net.minecraft.client.particle.chroma.utils.Timer();
		pos = null;
		mc.timer.timerSpeed = 1.0F;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate e)
	{
		double delay = this.getSetManager().getSettingById(81).getValueD();
		pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
		
		int newSlot = -1;
		
		if (this.getSetManager().getSettingById(83).getValBoolean())
		{
	        for (int i = 0; i < 9; ++i)
	        {
	            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
	            if (AutoArmor.isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemBlock) || !Block.getBlockFromItem(stack.getItem()).getDefaultState().getBlock().isFullBlock()) continue;
	            newSlot = i;
	            break;
	        }
	        
			if (newSlot == -1)
			{
				return;
			}
		}
		
		int oldSlot = mc.thePlayer.inventory.currentItem;
		
		if (this.getSetManager().getSettingById(83).getValBoolean())
		{
			mc.thePlayer.inventory.currentItem = newSlot;
		}
		
		if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock && mc.theWorld.getBlockState(pos) != null && mc.theWorld.getBlockState(pos).getBlock() == Blocks.air && mc.gameSettings.keyBindJump.pressed)
		{
			mc.timer.timerSpeed = (float) this.getSetManager().getSettingById(85).getValueD();
			
			if (timer.hasReach(delay))
			{
				data = getBlock(pos);
				BlockPos pos1 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.1D, mc.thePlayer.posZ);
				
				if(data != null && mc.theWorld.getBlockState(pos1).getBlock() == Blocks.air)
				{
					mc.thePlayer.setSprinting(false);
					
					if (this.getSetManager().getSettingById(82).getValBoolean())
					{
						mc.thePlayer.motionY = 0.4D;
					}
					
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), new BlockPos(mc.thePlayer).down(2), EnumFacing.UP, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
					
					if (!this.getSetManager().getSettingById(84).getValBoolean())
					{
						mc.thePlayer.swingItem();
					}
				}
				
				timer.reset();
			}		
		}
		
		if (this.getSetManager().getSettingById(83).getValBoolean())
		{
			mc.thePlayer.inventory.currentItem = oldSlot;
		}
	}
	
	@EventTarget
	public void onSentPacket(EventSentPacket e)
	{
		if (e.getPacket() instanceof C03PacketPlayer && (e.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook || e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook))
		{
			if (mc.thePlayer.getCurrentEquippedItem() != null && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock))
			{
				return;
			}
			
			e.setCancelled(true);
			
			if (e.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook)
			{
				C03PacketPlayer.C05PacketPlayerLook packet = (C03PacketPlayer.C05PacketPlayerLook) e.getPacket();
				packet.setPitch(90.0F);
				packet.setYaw(2.5019379F);
				mc.thePlayer.sendQueue.addToSendQueueBypass(packet);
			}
			else if (e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook)
			{
				C03PacketPlayer.C06PacketPlayerPosLook packet = (C03PacketPlayer.C06PacketPlayerPosLook) e.getPacket();
				packet.setPitch(90.0F);
				packet.setYaw(2.5019379F);
				mc.thePlayer.sendQueue.addToSendQueueBypass(packet);
			}
		}
	}
	
	public BlockData getBlock(BlockPos pos)
	{
		if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air)
		{
			return new BlockData(EnumFacing.UP, pos.add(0, -1, 0));
		}
		
		return null;
	}
}