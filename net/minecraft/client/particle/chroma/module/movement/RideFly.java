package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.PlayerUtil;
import net.minecraft.entity.Entity;

public class RideFly extends Module
{
	public RideFly()
	{
		super("RideFly", 98, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (mc.thePlayer.ridingEntity == null)
		{
			return;
		}
		
		if (!mc.thePlayer.isRiding())
		{
			return;
		}
		
		Entity ridingEntity = mc.thePlayer.ridingEntity;
		ridingEntity.onGround = true;
		mc.thePlayer.onGround = true;
		
        if (this.mc.gameSettings.keyBindSneak.pressed)
        {
            ridingEntity.motionY -= 0.3;
        }
        else if (this.mc.gameSettings.keyBindJump.pressed)
        {
            ridingEntity.motionY += 0.3;
        }
        else if (this.mc.gameSettings.keyBindForward.pressed)
        {
        	ridingEntity.motionX += 0.3;
        }
	}
}