package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class NoWeb extends Module
{
	public NoWeb()
	{
		super("NoWeb", 29, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Vanilla");
		modes.add("AAC");
		modes.add("General AAC");
		modes.add("Rewinside");
		this.getSetManager().rSetting(new Setting(86, "Mode", "", this, "Vanilla", modes));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
		
		if (mc.thePlayer.isInWeb)
		{
			mc.thePlayer.setSprinting(true);
			
			if (this.getMode().equals("Vanilla"))
			{
				mc.thePlayer.isInWeb = false;
			}
			else if (this.getMode().equals("AAC"))
			{
                mc.thePlayer.jumpMovementFactor = 0.59f;

                if (!mc.gameSettings.keyBindSneak.isKeyDown())
                    mc.thePlayer.motionY = 0.0D;
			}
			else if (this.getMode().equals("General AAC"))
			{
				mc.thePlayer.jumpMovementFactor = (mc.thePlayer.movementInput.moveStrafe != 0f) ? 1.0f : 1.21f;

                if (!mc.gameSettings.keyBindSneak.isKeyDown())
                    mc.thePlayer.motionY = 0.0D;

                if (mc.thePlayer.onGround)
                    mc.thePlayer.jump();
			}
			else if (this.getMode().equals("Rewinside"))
			{
				mc.thePlayer.jumpMovementFactor = 0.42f;

		       if (mc.thePlayer.onGround)
		           mc.thePlayer.jump();
			}
		}
	}
}