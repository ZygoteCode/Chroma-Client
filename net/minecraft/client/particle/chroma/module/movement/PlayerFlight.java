package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.MovementUtils;
import net.minecraft.client.particle.chroma.utils.PlayerUtil;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class PlayerFlight extends Module
{
	private double flyHeight;
	private boolean wasDead;
	private int aac3delay;
	private boolean noFlag;
	private int aac3glideDelay;
	private int spartanTimer;
	
	public PlayerFlight()
	{
		super("PlayerFlight", 46, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("Vanilla");
		options.add("Motion");
		options.add("AAC 1.9.10");
		options.add("AAC 3.0.5");
		options.add("AAC 3.2.2");
		options.add("AAC 3.3.12 (1)");
		options.add("AAC 3.3.12 (2)");
		options.add("AAC 3.3.13");
		this.getSetManager().rSetting(new Setting(146, "Mode", "The working mode of the Fly module.", this, "AAC 1.9.10", options));
		this.getSetManager().rSetting(new Setting(280, "Kick bypass", "", this, false));
		this.getSetManager().rSetting(new Setting(311, "Speed", "", this, 1.0F, 1.0F, 10.0F, true));
		super.setup();
	}
	
	@Override
	public void onDisable()
	{
		mc.timer.timerSpeed = 1.0F;
		mc.thePlayer.capabilities.isFlying = false;
		mc.thePlayer.capabilities.allowFlying = false;
		wasDead = false;
		aac3delay = 0;
		noFlag = false;
		aac3glideDelay = 0;
		spartanTimer = 0;
		super.onDisable();
	}
	
	@Override
	public void onEnable()
	{
		super.onEnable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
		
		if (this.getMode().equals("AAC 1.9.10"))
		{
            if (this.mc.thePlayer.fallDistance > 4.0f)
            {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                this.mc.thePlayer.motionY = this.mc.gameSettings.keyBindJump.pressed ? 0.4 : (this.mc.gameSettings.keyBindSneak.pressed ? -0.4 : 0.0);
                PlayerUtil.setSpeed(1.0D);
            }
		}
		else if (this.getMode().equals("AAC 3.0.5"))
		{
            if (this.mc.thePlayer.motionY < 0.0)
            {
                this.mc.thePlayer.motionY = 0.1;
                
                if (this.mc.gameSettings.keyBindJump.pressed)
                {
                    this.mc.thePlayer.motionX *= 1.2;
                    this.mc.thePlayer.motionZ *= 1.2;
                }
            }
		}
		else if (this.getMode().equals("Vanilla"))
		{
			mc.thePlayer.capabilities.isFlying = true;
			mc.thePlayer.capabilities.allowFlying = true;
		}
		else if (this.getMode().equals("AAC 3.2.2"))
		{
            if (this.mc.thePlayer.fallDistance > 0.0f && this.mc.thePlayer.hurtTime > 0)
            {
                PlayerUtil.toFwd(0.15);
                this.mc.thePlayer.motionY = 9.0;
                this.mc.thePlayer.fallDistance = 0.0f;
            }
            
            this.mc.timer.timerSpeed = this.mc.thePlayer.motionY > 0.42 || this.mc.gameSettings.keyBindJump.pressed ? 0.4f : 1.0f;
		}
		else if (this.getMode().equals("Motion"))
		{
            this.mc.thePlayer.onGround = true;
            this.mc.thePlayer.motionY = 0.0;
            
            if (this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown())
            {
                PlayerUtil.setSpeed(2.0);
            }
            
            if (this.mc.gameSettings.keyBindSneak.pressed)
            {
                this.mc.thePlayer.motionY -= 0.5;
            }
            else if (this.mc.gameSettings.keyBindJump.pressed)
            {
                this.mc.thePlayer.motionY += 0.5;
            }
		}
		else if (this.getMode().equals("AAC 3.3.13"))
		{
            if (mc.thePlayer.isDead)
            {
            	wasDead = true;
            }

            if (wasDead || mc.thePlayer.onGround)
            {
                wasDead = false;

                mc.thePlayer.motionY = this.getSetManager().getSettingById(311).getValueD();
                mc.thePlayer.onGround = false;
            }

            mc.timer.timerSpeed = 1F;

            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            {
                mc.timer.timerSpeed = 0.2F;
                mc.rightClickDelayTimer = 0;
            }
		}
		else if (this.getMode().equals("AAC 3.3.12 (1)"))
		{
            if (mc.thePlayer.posY < -70)
            {
            	mc.thePlayer.motionY = this.getSetManager().getSettingById(311).getValueD();
            }

            mc.timer.timerSpeed = 1F;

            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            {
                mc.timer.timerSpeed = 0.2F;
                mc.rightClickDelayTimer = 0;
            }
		}
		else if (this.getMode().equals("AAC 3.3.12 (2)"))
		{
            if (!mc.thePlayer.onGround)
            {
            	aac3glideDelay++;
            }

            if (aac3glideDelay == 2)
            {
            	mc.timer.timerSpeed = 1F;
            }

            if (aac3glideDelay == 12)
            {
            	mc.timer.timerSpeed = 0.1F;
            }

            if (aac3glideDelay >= 12 && !mc.thePlayer.onGround)
            {
                aac3glideDelay = 0;
                mc.thePlayer.motionY = .015;
            }
		}
		
		if (this.getSetManager().getSettingById(280).getValBoolean())
		{
            this.updateMS();
            this.updateFlyHeight();
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            
            if (this.flyHeight <= 290.0 && this.hasTimePassedM(500L) || this.flyHeight > 290.0 && this.hasTimePassedM(100L))
            {
                this.goToGround();
                this.updateLastMS();
            }
		}
	}
	
	@EventTarget
	public void onSentPacket(EventSentPacket event)
	{
		
	}
	
    private void updateFlyHeight()
    {
        double h = 1.0;
        AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox().expand(0.0625, 0.0625, 0.0625);
        this.flyHeight = 0.0;
        
        while (this.flyHeight < mc.thePlayer.posY)
        {
            AxisAlignedBB nextBox = box.offset(0.0, -this.flyHeight, 0.0);
            
            if (mc.theWorld.checkBlockCollision(nextBox))
            {
                if (h < 0.0625) break;
                this.flyHeight -= h;
                h /= 2.0;
            }
            
            this.flyHeight += h;
        }
    }

    private void goToGround()
    {
        C03PacketPlayer.C04PacketPlayerPosition packet;
        
        if (this.flyHeight > 300.0)
        {
            return;
        }
        
        double minY = mc.thePlayer.posY - this.flyHeight;
        
        if (minY <= 0.0)
        {
            return;
        }
        
        double y = mc.thePlayer.posY;
        
        while (y > minY)
        {
            if ((y -= 8.0) < minY)
            {
                y = minY;
            }
            
            packet = new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
            mc.thePlayer.sendQueue.addToSendQueue(packet);
        }
        
        y = minY;
        
        while (y < mc.thePlayer.posY)
        {
            if ((y += 8.0) > mc.thePlayer.posY)
            {
                y = mc.thePlayer.posY;
            }
            
            packet = new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
            mc.thePlayer.sendQueue.addToSendQueue(packet);
        }
    }
}