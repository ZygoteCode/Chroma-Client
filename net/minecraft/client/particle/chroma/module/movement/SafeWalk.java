package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSafeWalk;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class SafeWalk extends Module
{
	public SafeWalk()
	{
		super("SafeWalk", 85, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(278, "Safe ground", "", this, true));
		this.getSetManager().rSetting(new Setting(279, "Safe jump", "", this, false));
		super.setup();
	}
	
	@EventTarget
	public void onSafeWalk(EventSafeWalk event)
	{
		if (this.getSetManager().getSettingById(278).getValBoolean())
		{
			event.setProtectGround(true);
		}
		
		if (this.getSetManager().getSettingById(279).getValBoolean())
		{
			event.setProtectJump(true);
		}
	}
}