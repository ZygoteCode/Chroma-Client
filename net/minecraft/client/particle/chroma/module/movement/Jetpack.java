package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class Jetpack extends Module
{
	private double flyHeight;
	
	public Jetpack()
	{
		super("Jetpack", 112, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(293, "Kick Bypass", "", this, false));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (mc.gameSettings.keyBindJump.pressed)
		{
			mc.thePlayer.jump();
		}
		
		if (this.getSetManager().getSettingById(293).getValBoolean())
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