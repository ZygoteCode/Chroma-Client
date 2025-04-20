package net.minecraft.client.particle.chroma.module.combat;

import java.util.Random;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.CombatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class AimAssist extends Module
{
	public AimAssist()
	{
		super("AimAssist", 79, Category.COMBAT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
			Entity target = null;
			
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
				
            final float[] rotations = CombatUtil.getRotations(target); 
            Random rnd = new Random();
            
            event.setYaw((rotations[0]) + ((rnd.nextInt(12)) + 2));
	        event.setPitch((rotations[1]) - ((rnd.nextInt(6)) + 2));
	        
	        mc.thePlayer.rotationYaw = rotations[0];
	        mc.thePlayer.rotationPitch = rotations[1];
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
		
		if (mc.thePlayer.getDistanceToEntity(entity) > 4.0D)
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