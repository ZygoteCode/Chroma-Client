package net.minecraft.client.particle.chroma.module.movement;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class InventoryMove extends Module
{
	public InventoryMove()
	{
		super("InventoryMove", 13, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.POST))
		{
			if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat))
			{
				if ((isPressed(mc.gameSettings.keyBindJump.getKeyCode())) && (mc.thePlayer.onGround))
				{
					mc.thePlayer.jump();
				}
				
				if (isPressed(mc.gameSettings.keyBindForward.getKeyCode()))
				{
					mc.gameSettings.keyBindForward.pressed = true;
				}
				else
				{
					mc.gameSettings.keyBindForward.pressed = false;
				}
				
				if (isPressed(mc.gameSettings.keyBindBack.getKeyCode())) 
				{
					mc.gameSettings.keyBindBack.pressed = true;
				}
				else
				{
					mc.gameSettings.keyBindBack.pressed = false;
				}
				
				if (isPressed(mc.gameSettings.keyBindLeft.getKeyCode()))
				{
					mc.gameSettings.keyBindLeft.pressed = true;
				}
				else
				{
					mc.gameSettings.keyBindLeft.pressed = false;
				}
				
				if (isPressed(mc.gameSettings.keyBindRight.getKeyCode()))
				{
					mc.gameSettings.keyBindRight.pressed = true;
				}
				else
				{
					mc.gameSettings.keyBindRight.pressed = false;
				}
				
				if (isPressed(mc.gameSettings.keyBindSprint.getKeyCode()))
				{
					mc.thePlayer.setSprinting(true);
				}
				
				if (isPressed(Keyboard.KEY_DOWN))
				{
					if(mc.thePlayer.rotationPitch < 90)
					{
						mc.thePlayer.rotationPitch += 4;
					}
				}
				else if (isPressed(Keyboard.KEY_UP))
				{
					if (mc.thePlayer.rotationPitch > -90)
					{
						mc.thePlayer.rotationPitch -= 4;
					}				
				}
				else if (isPressed(Keyboard.KEY_RIGHT))
				{
					mc.thePlayer.rotationYaw += 4;
				}
				else if (isPressed(Keyboard.KEY_LEFT))
				{
					mc.thePlayer.rotationYaw -= 4;
				}
			}
		}
	}
	
	private boolean isPressed(int key)
	{
		return Keyboard.isKeyDown(key);
	}
}