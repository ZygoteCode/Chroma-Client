package net.minecraft.client.particle.chroma.module.combat;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventEntityVelocity;
import net.minecraft.client.particle.chroma.event.events.EventPushWater;
import net.minecraft.client.particle.chroma.event.events.EventReceivePacket;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module
{
	public Velocity()
	{
		super("Velocity", "Cancels the velocity of the player.", Keyboard.KEY_NONE, Category.COMBAT, false, 25);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("NCP");
		modes.add("AAC");
		modes.add("AAC (1)");
		modes.add("AAC (2)");
		modes.add("AAC (3)");
		modes.add("AAC (4)");
		modes.add("AAC (5)");
		modes.add("Global");
		this.addSetting(new Setting(55, "Mode", "The mode of the Velocity module.", this, "NCP", modes));
		this.addSetting(new Setting(56, "Jump", "When the velocity finishes, the player will jump.", this, false));
		this.addSetting(new Setting(57, "Horizontal percent", "The percent of the horizontal effect.", this, 0, 0, 100, true));
		this.addSetting(new Setting(58, "Vertical percent", "The percent of the horizontal effect.", this, 0, 0, 100, true));
		this.addSetting(new Setting(59, "Water push", "Cancel water pushing.", this, false));
		this.addSetting(new Setting(60, "Explosions", "Cancel explosions.", this, false));
		super.setup();
	}
	
	@EventTarget
	public void onEntityVelocity(EventEntityVelocity e)
	{
		if (e.getEntity() == mc.thePlayer)
		{
			if (this.getMode().equalsIgnoreCase("NCP"))
			{
				double horizontal = this.getSetManager().getSettingById(57).getValueD();
				double vertical = this.getSetManager().getSettingById(58).getValueD();
				
				if (horizontal == 0.0 && vertical == 0.0)
				{
					e.setCancelled(true);
				}
				else if (horizontal == 0.0)
				{
					e.setX(0);
					e.setZ(0);
				}
				else if (vertical == 0.0)
				{
					e.setY(0);
				}
				else
				{
					e.setX((e.getX() / 100) * horizontal);
					e.setY((e.getY() / 100) * vertical);
					e.setZ((e.getZ() / 100) * horizontal);
				}
			}
			
			if (this.getSetManager().getSettingById(56).getValBoolean())
			{
				mc.thePlayer.jump();
			}
		}
	}
	
	@EventTarget
	public void onPushWater(EventPushWater e)
	{
		if (e.getPlayer() == mc.thePlayer && this.getSetManager().getSettingById(59).getValBoolean())
		{
			e.setCancelled(true);
		}
	}
	
	@EventTarget
	public void onExplosion(EventReceivePacket e)
	{
		if (e.getPacket() instanceof S27PacketExplosion && this.getSetManager().getSettingById(60).getValBoolean())
		{
			Chroma.message("cya");
			e.setCancelled(true);
		}
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		this.setSuffix(this.getModeColored());
		
        if (this.getMode().equals("AAC"))
        {
        	if (this.mc.thePlayer.onGround)
        	{
        		return;
        	}
        	
            if (this.mc.thePlayer.hurtTime == 6) 
            {
                double nX = -Math.sin(Math.toRadians(this.mc.thePlayer.rotationYawHead)) * 0.12;
                double nZ = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYawHead)) * 0.12;
                this.mc.thePlayer.motionX = nX;
                this.mc.thePlayer.motionZ = nZ;
                this.mc.thePlayer.jumpMovementFactor = 0.08f;
            } 
            else 
            {
                this.mc.thePlayer.jumpMovementFactor = 0.025999999f;
            }
        }
        else if (this.getMode().equals("AAC (1)"))
        {
			double mX = mc.thePlayer.motionX;
			double mZ = mc.thePlayer.motionZ;
			
			if (mc.thePlayer.hurtTime == 1)		
			{
				
			}
			else if (mc.thePlayer.hurtTime == 0)
			{
				mX = mc.thePlayer.motionX;
				mZ = mc.thePlayer.motionZ;
			}
			else if (mc.thePlayer.hurtTime > 2)
			{
				mc.thePlayer.motionX *= -mX * 0.2;
				mc.thePlayer.motionZ *= -mZ * 0.2;
			}
        }
        else if (this.getMode().equals("AAC (2)"))
        {
			if (mc.thePlayer.hurtTime > 0)
			{
				mc.thePlayer.motionX *= 0.35;
				mc.thePlayer.motionZ *= 0.35;
			}
        }
        else if (this.getMode().equals("AAC (3)"))
        {
			if (mc.gameSettings.keyBindForward.pressed)
			{
				if (mc.thePlayer.hurtTime > 1)
				{
					mc.thePlayer.jumpMovementFactor = 0.1F;
					mc.thePlayer.speedInAir = 0.06F;
				}
				else
				{
					mc.thePlayer.jumpMovementFactor = 0.025999999F;
					mc.thePlayer.speedInAir = 0.02F;
				}
			}
			else
			{
				if (mc.thePlayer.hurtTime > 0)
				{
					mc.thePlayer.motionX *= 0.35;
					mc.thePlayer.motionZ *= 0.35;
				}
			}
        }
        else if (this.getMode().equals("AAC (4)"))
        {
			if (!mc.gameSettings.keyBindJump.pressed)
			{
				if (mc.thePlayer.hurtTime > 0)
				{
					mc.thePlayer.onGround = true;
				}
			}
			else
			{
				if (mc.thePlayer.hurtTime > 0)
				{
					mc.thePlayer.motionX *= 0.35;
					mc.thePlayer.motionZ *= 0.35;
				}
			}
        }
        else if (this.getMode().equals("AAC (5)"))
        {
			if (mc.thePlayer.hurtTime == 1)
			{
				if (mc.thePlayer.onGround)
				{
					mc.thePlayer.jump();
				}
				
				e.setYaw(mc.thePlayer.rotationYaw - 180F);
			}
			else if (mc.thePlayer.hurtTime > 1)
			{
				mc.thePlayer.rotationYaw = e.getYaw();
			}
        }
        else if (this.getMode().equals("Global"))
        {
            if (mc.thePlayer.hurtTime > 0)
            {
                mc.thePlayer.onGround = true;
            }
        }
	}
}