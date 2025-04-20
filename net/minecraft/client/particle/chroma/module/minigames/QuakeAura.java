package net.minecraft.client.particle.chroma.module.minigames;

import java.util.Random;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.CombatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class QuakeAura extends Module
{
	private Entity target;
	private net.minecraft.client.particle.chroma.utils.Timer timer;
	
	public QuakeAura()
	{
		super("QuakeAura", 86, Category.MINIGAMES);
	}
	
	@Override
	public void setup()
	{
		timer = new net.minecraft.client.particle.chroma.utils.Timer();
		this.getSetManager().rSetting(new Setting(281, "Delay", "", this, 70.0D, 20.0D, 1000.0D, true));
		this.getSetManager().rSetting(new Setting(282, "Reach", "", this, 16.0D, 3.0D, 25.0D, true));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		timer.reset();
		target = null;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{					
	        for (final Entity e : mc.theWorld.loadedEntityList)
	        {
	            if (e instanceof EntityLivingBase)
	            {
	                if (!isEntityValid(e))
	                {
	                    continue;
	                }
	                
	                target = (EntityLivingBase) e;
	            }
	        }
            
            if (!isEntityValid(target))
            {
                return;
            }
            
    		if (mc.thePlayer.getCurrentEquippedItem() != null)
    		{      
                final float[] rotations = CombatUtil.getRotations(target); 
                event.setYaw(rotations[0]);
    	        event.setPitch(rotations[1]);     
    		}
		}
		else if (event.getState().equals(EventState.POST))
		{
			if (!timer.hasReach(this.getSetManager().getSettingById(281).getValueD()))
			{
				return;
			}
			
			if (!isEntityValid(target))
			{
				return;
			}
			
			timer.reset();
			mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
			mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
		}
	}
	
	public boolean isEntityValid(Entity entity)
	{
		if (entity == null)
		{
			return false;
		}
		
		if (entity == mc.thePlayer)
		{
			return false;
		}
		
		if (!(entity instanceof EntityPlayer))
		{
			return false;
		}
		
		if (FriendsManager.isFriend(entity.getName()))
		{
			return false;
		}
		
		if (mc.thePlayer.getDistanceToEntity(entity) > this.getSetManager().getSettingById(282).getValueD())
		{
			return false;
		}
		
		if (!mc.thePlayer.canEntityBeSeen(entity))
		{
			return false;
		}
		
		return true;
	}
}