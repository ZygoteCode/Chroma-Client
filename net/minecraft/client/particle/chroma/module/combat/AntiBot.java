package net.minecraft.client.particle.chroma.module.combat;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventReceivePacket;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.util.BlockPos;

public class AntiBot extends Module
{
	public AntiBot()
	{
		super("AntiBot", 24, Category.WORLD);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(41, "No was on ground", "", this, false));
		this.getSetManager().rSetting(new Setting(42, "Invisible", "", this, false));
		this.getSetManager().rSetting(new Setting(43, "No in tablist", "", this, false));
		this.getSetManager().rSetting(new Setting(44, "Sleeping players", "", this, false));
		this.getSetManager().rSetting(new Setting(45, "Lower ticks existed", "", this, false));
		this.getSetManager().rSetting(new Setting(46, "Minimum ticks existed", "", this, 0, 1, 125, true));
		this.getSetManager().rSetting(new Setting(47, "Strange entity id", "", this, false));
		this.getSetManager().rSetting(new Setting(48, "Mineplex methods", "", this, false));
		this.getSetManager().rSetting(new Setting(49, "Strange positions", "", this, false));
		this.getSetManager().rSetting(new Setting(50, "New spawned entities", "", this, false));
		this.getSetManager().rSetting(new Setting(51, "Non player entities", "", this, false));
		this.getSetManager().rSetting(new Setting(52, "Hypixel methods", "", this, false));
		this.getSetManager().rSetting(new Setting(53, "No armor", "", this, false));
		this.getSetManager().rSetting(new Setting(54, "Has armor", "", this, false));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		for (Entity en: mc.theWorld.loadedEntityList)
		{
			if (en instanceof EntityLivingBase && en != mc.thePlayer)
			{
				boolean isBot = false;
				EntityLivingBase entity = (EntityLivingBase) en;
				
				if (entity instanceof EntityPlayer && this.getSetManager().getSettingById(43).getValBoolean())
				{
					EntityPlayer p2;
					
			        Iterator<EntityPlayer> iterator = GuiPlayerTabOverlay.getPlayerList().iterator();
			        do
			        {
			            if (iterator.hasNext()) continue;
			            isBot = false;
			        }
			        while ((p2 = iterator.next()) == null || !entity.getUniqueID().equals(p2.getUniqueID()) || entity.getEntityId() == p2.getEntityId());
			        isBot = true;
				}
				
				if (!entity.wasOnGround && this.getSetManager().getSettingById(41).getValBoolean())
				{
					isBot = true;
				}
				
				if ((entity.isInvisible() || entity.isInvisibleToPlayer(mc.thePlayer)) && this.getSetManager().getSettingById(42).getValBoolean())
				{
					isBot = true;
				}
				
				if (entity.isPlayerSleeping() && this.getSetManager().getSettingById(44).getValBoolean())
				{
					isBot = true;
				}
				
				if (entity.ticksExisted < mc.thePlayer.ticksExisted && this.getSetManager().getSettingById(45).getValBoolean())
				{
					isBot = true;
				}
				
				if (entity.ticksExisted < this.getSetManager().getSettingById(46).getValueI())
				{
					isBot = true;
				}
				
				if (entity.getEntityId() >= 1000000000 && this.getSetManager().getSettingById(47).getValBoolean())
				{
					isBot = true;
				}
				
				if (this.getSetManager().getSettingById(48).getValBoolean() && entity instanceof EntityPlayer && !isReal((EntityPlayer) entity))
				{
					isBot = true;
				}
				
				if (this.getSetManager().getSettingById(49).getValBoolean() && entity.motionY == 0.0 && !entity.isCollidedVertically && entity.onGround && entity.posY % 1.0 != 0.0 && entity.posY % 0.5 != 0.0 && !mc.getCurrentServerData().serverIP.contains("mineplex"))
				{
					isBot = true;
				}
				
				if (this.getSetManager().getSettingById(48).getValBoolean() && entity.motionY == 0.0 && !entity.isCollidedVertically && !entity.onGround && entity.posY % 1.0 != 0.0 && entity.posY % 0.5 != 0.0 && mc.getCurrentServerData().serverIP.contains("mineplex"))
				{
					isBot = true;
				}
				
				if (!(entity instanceof EntityPlayer) && this.getSetManager().getSettingById(51).getValBoolean())
				{
					isBot = true;
				}
				
				if (this.getSetManager().getSettingById(52).getValBoolean() && entity.isInvisible() && entity.ticksExisted > 105)
				{
					isBot = true;
				}
				
				if (this.getSetManager().getSettingById(48).getValBoolean() && entity instanceof EntityPlayer)
				{
					EntityPlayer ep = (EntityPlayer) entity;
					
					if (ep.ticksExisted < 2 && ep.getHealth() < 20 && ep.getHealth() > 0)
					{
						isBot = true;
					}
				}
				
				if (this.getSetManager().getSettingById(52).getValBoolean() && entity instanceof EntityPlayer)
				{
					EntityPlayer ep = (EntityPlayer) entity;
					
					if (ep.getDisplayName().getFormattedText().contains("\u00A7r") && !GuiPlayerTabOverlay.getPlayerList().contains(ep) && ep.isInvisible())
					{
						isBot = true;
					}
				}
				
				if (this.getSetManager().getSettingById(53).getValBoolean() && entity instanceof EntityPlayer && !hasArmor(((EntityPlayer) entity)))
				{
					isBot = true;
				}
				
				if (this.getSetManager().getSettingById(54).getValBoolean() && entity instanceof EntityPlayer && hasArmor(((EntityPlayer) entity)))
				{
					isBot = true;
				}
				
				if (isBot)
				{
					mc.theWorld.removeEntityFromWorld(entity.getEntityId());
				}
			}
		}
	}
	
	public boolean hasArmor(EntityPlayer player)
	{
		if (player.inventory.armorInventory.length <= 0)
		{
			return false;
		}
		
		return true;
	}
	
    public boolean isReal(EntityPlayer player)
    {
        for (NetworkPlayerInfo npi : mc.thePlayer.sendQueue.getPlayerInfoMap())
        {
            if (npi == null || npi.getGameProfile() == null || player.getGameProfile() == null || !npi.getGameProfile().getId().toString().equals(player.getGameProfile().getId().toString()) || player.getEntityId() > 1000000000 || player.getName().startsWith("\u00a7c"))
            {
                continue;
            }

            return true;
        }

        return false;
    }
	
	@EventTarget
	public void onReceivePacket(EventReceivePacket e)
	{
        if (e.getPacket() instanceof S0CPacketSpawnPlayer && this.getSetManager().getSettingById(48).getValBoolean())
        {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) e.getPacket();
            int entityX = packet.getX() / 32;
            int entityY = packet.getY() / 32;
            int entityZ = packet.getZ() / 32;
            
			double diffX = mc.thePlayer.posX - entityX;
			double diffY = mc.thePlayer.posY - entityY;
			double diffZ = mc.thePlayer.posZ - entityZ;
			
			double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

            if ((mc.thePlayer.ticksExisted != 0 && entityY > mc.thePlayer.posY && mc.thePlayer.getDistance((double) entityX, (double) entityY, (double) entityZ) < 20D && !mc.theWorld.getSpawnPoint().equals(new BlockPos(entityX, mc.theWorld.getSpawnPoint().getY(), entityZ))) || ((dist <= 17.0D) && (entityX != mc.thePlayer.posX) && (entityY != mc.thePlayer.posY) && (entityZ != mc.thePlayer.posZ)))
            {
                e.setCancelled(true);
            }
        }
        else if (e.getPacket() instanceof S0CPacketSpawnPlayer && this.getSetManager().getSettingById(50).getValBoolean())
        {
        	e.setCancelled(true);
        }
	}
}