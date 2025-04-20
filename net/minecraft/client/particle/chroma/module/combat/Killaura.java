package net.minecraft.client.particle.chroma.module.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventEntityReach;
import net.minecraft.client.particle.chroma.event.events.EventSendChatMessage;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.CombatUtil;
import net.minecraft.client.particle.chroma.utils.RaycastUtils;
import net.minecraft.client.particle.chroma.utils.RotationUtil;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Killaura extends Module
{
    private EntityLivingBase target;
    private Timer timer;
    private int tick;
    private EntityLivingBase last;
    private Timer smoothTimer;
	
	public Killaura()
	{
		super("Killaura", 21, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		timer = new Timer();
		smoothTimer = new Timer();
		this.getSetManager().rSetting(new Setting(29, "Delay", "", this, 100.0D, 1.0D, 1000.0D, true));
		this.getSetManager().rSetting(new Setting(30, "Reach", "", this, 3.62D, 1.0D, 10.0D, false));
		this.getSetManager().rSetting(new Setting(34, "Raycast", "", this, false));
		this.getSetManager().rSetting(new Setting(24, "Dead entities", "", this, false));
		this.getSetManager().rSetting(new Setting(25, "Animals", "", this, true));
		this.getSetManager().rSetting(new Setting(26, "Players", "", this, true));
		this.getSetManager().rSetting(new Setting(27, "Mobs", "", this, true));
		this.getSetManager().rSetting(new Setting(28, "Invisibles", "", this, false));
		this.getSetManager().rSetting(new Setting(62, "AutoBlock", "", this, false));
		this.getSetManager().rSetting(new Setting(218, "AutoSword", "", this, false));
		this.getSetManager().rSetting(new Setting(77, "Block rate", "", this, 60.0D, 1.0D, 100.0D, true));
		this.getSetManager().rSetting(new Setting(40, "Through walls", "", this, false));
		this.getSetManager().rSetting(new Setting(209, "Smooth", "", this, false));
		this.getSetManager().rSetting(new Setting(217, "Smooth speed", "", this, 10.0D, 1.0D, 10.0D, true));
		this.getSetManager().rSetting(new Setting(230, "Random rotations", "", this, true));
		this.getSetManager().rSetting(new Setting(231, "Random delay", "", this, true));
		this.getSetManager().rSetting(new Setting(283, "Spawn disable", "", this, true));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		timer.reset();
		smoothTimer.reset();
		target = null;
		tick = 10;
		last = null;
		super.onToggle();
	}
	
	@EventTarget
	public void onSendChatMessage(EventSendChatMessage event)
	{
		if (!this.getSetManager().getSettingById(283).getValBoolean())
		{
			return;
		}
		
		if (event.getMessage().toLowerCase().replace(" ", "").equals("/spawn") || event.getMessage().toLowerCase().replace(" ", "").startsWith("/warp"))
		{
			this.setToggled(false);
		}
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (this.getSetManager().getSettingById(283).getValBoolean() && mc.thePlayer.isDead)
		{
			this.setToggled(false);
			return;
		}
		
		if (event.getState().equals(EventState.PRE))
		{
			int valids = 0;
			
	        for (final Entity e : mc.theWorld.loadedEntityList)
	        {
	            if (e instanceof EntityLivingBase)
	            {
	                if (!isEntityValid(e))
	                {
	                    continue;
	                }
	                
	                valids++;           
	                target = (EntityLivingBase) e;
	            }
	        }
	        
            this.setSuffix(" §7" + String.valueOf(valids));
            
            if (!isEntityValid(target))
            {
                return;
            }
            
            if (this.getSetManager().getSettingById(209).getValBoolean())
            {
            	if (last != target)
                {
                	last = target;
                	tick = this.getSetManager().getSettingById(217).getValueI();
                }
                
                if (tick > 0 && tick <= 10)
                {
                	tick--;
                	
                	if (tick == 0)
                	{
                		smoothTimer.reset();
                	}
                }
            }
				
               final float[] rotations = CombatUtil.getRotations(target); 
               Random rnd = new Random();
               
	            if (this.getSetManager().getSettingById(209).getValBoolean())
	            {
	            	if (tick <= 0)
		            {
	            		event.setYaw((rotations[0] + (float) (this.getSetManager().getSettingById(230).getValBoolean() ? ((rnd.nextInt(12) + 2)) : 0)));
			            event.setPitch((rotations[1] - (float) (this.getSetManager().getSettingById(230).getValBoolean() ? ((rnd.nextInt(6) + 2)) : 0)));
		            }
		            else
		            {
		            	event.setYaw(((rotations[0] + (float) (this.getSetManager().getSettingById(230).getValBoolean() ? ((rnd.nextInt(12) + 2)) : 0))) / tick);
			            event.setPitch(((rotations[1] - (float) (this.getSetManager().getSettingById(230).getValBoolean() ? ((rnd.nextInt(6) + 2)) : 0))) / tick);
		            }
	            }
	            else
	            {
	            	event.setYaw((rotations[0] + (float) (this.getSetManager().getSettingById(230).getValBoolean() ? ((rnd.nextInt(12) + 2)) : 0)));
		            event.setPitch((rotations[1] - (float) (this.getSetManager().getSettingById(230).getValBoolean() ? ((rnd.nextInt(6) + 2)) : 0)));
	            }
	            
	            if (this.getSetManager().getSettingById(34).getValBoolean())
	            {
	            	target = (EntityLivingBase) RaycastUtils.raycastEntity(this.getSetManager().getSettingById(30).getValueD(), event.getYaw(), event.getPitch());
	            	
	                if (!isEntityValid(target))
	                {
	                    return;
	                }
	            }
				
	            if (this.getSetManager().getSettingById(62).getValBoolean())
	            {
	                Random rnd1 = new Random();
	                
	            	if (rnd1.nextInt(100) < this.getSetManager().getSettingById(77).getValueD())
	            	{
	            		if (mc.thePlayer.getCurrentEquippedItem() != null)
	            		{
	            			if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)
	            			{
	    		                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
	    		                
	    		                if (mc.getChroma().getModuleManager().getModuleByID(27).isToggled() && this.getSetManager().getSettingById(63).getValBoolean())
	    		                {
	    		                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	    		                }	
	            			}
	            		}
	            	}
	            }
		}
	}
	
	@EventTarget
	public void onSuperUpdate(EventSuperUpdate event)
	{
		if (!isEntityValid(target))
		{
			return;
		}
		
		if (this.getSetManager().getSettingById(209).getValBoolean())
		{
			if (!(tick == 0 && smoothTimer.hasReach(50D)))
			{
				return;
			}
		}
		
		Random rnd = new Random();
		setSlot();
		
        if (this.timer.hasReach((float)(this.getSetManager().getSettingById(29).getValueD() + (this.getSetManager().getSettingById(231).getValBoolean() ? ((rnd.nextInt(25) + 40)) : 0))))
        {
            mc.thePlayer.swingItem();
            
            if (mc.getChroma().getModuleManager().getModuleByID(26).isToggled())
            {
            	if (this.getSetManager().getSettingById(61).getValString().equals("Packet"))
                {
                	if ((rnd.nextInt(100) < 70.0D))
                    {
                    	Criticals.doCritical();
                    }
                }
                else if (this.getSetManager().getSettingById(61).getValString().equals("Jump"))
                {
                	if (mc.thePlayer.onGround)
                	{
                		mc.thePlayer.jump();
                	}
                }
            }
            
            if (mc.getChroma().getModuleManager().getModuleByID(76).isToggled())
            {
            	if (target.hurtTime < this.getSetManager().getSettingById(310).getValueI())
            	{
            		SuperKnockback.doSuperKnockback();
            	}
            }
            
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));                
            this.timer.reset();
            target = null;
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
		
		if (!entity.isEntityAlive() && !this.getSetManager().getSettingById(24).getValBoolean())
		{
			return false;
		}
		
		if (entity instanceof EntityAnimal && !this.getSetManager().getSettingById(25).getValBoolean())
		{
			return false;
		}
		
		if (entity instanceof EntityPlayer && !this.getSetManager().getSettingById(26).getValBoolean())
		{
			return false;
		}
		
		if (entity instanceof EntityMob && !this.getSetManager().getSettingById(27).getValBoolean())
		{
			return false;
		}
		
		if (entity.isInvisible() && !this.getSetManager().getSettingById(28).getValBoolean())
		{
			return false;
		}
		
		if (FriendsManager.isFriend(entity.getName()))
		{
			return false;
		}
		
		if (mc.thePlayer.getDistanceToEntity(entity) > this.getSetManager().getSettingById(30).getValueD())
		{
			return false;
		}
		
		if (!this.getSetManager().getSettingById(40).getValBoolean())
		{
			if (!mc.thePlayer.canEntityBeSeen(entity))
			{
				return false;
			}
		}
		
		return true;
	}
	
    public void setSlot()
    {
    	if (!this.getSetManager().getSettingById(218).getValBoolean())
    	{
    		return;
    	}
    	
        float bestDamage = 0.0f;
        int bestSlot = -1;
        
        for (int i = 0; i < 9; ++i)
        {
            if (isSlotEmpty(i)) continue;
            
            Item item = mc.thePlayer.inventory.getStackInSlot(i).getItem();
            float damage = 0.0f;
            
            if (item instanceof ItemSword)
            {
                damage = ((ItemSword)item).attackDamage;
            }
            else if (item instanceof ItemTool)
            {
                damage = ((ItemTool)item).damageVsEntity;
            }
            
            if (!(damage > bestDamage)) continue;
            
            bestDamage = damage;
            bestSlot = i;
        }
        
        if (bestSlot == -1)
        {
            return;
        }

        if (mc.thePlayer.inventory.currentItem != bestSlot)
        {
        	mc.thePlayer.inventory.currentItem = bestSlot;
        }
    }
    
    public static boolean isSlotEmpty(int slot)
    {
        return mc.thePlayer.inventory.getStackInSlot(slot) == null;
    }
}