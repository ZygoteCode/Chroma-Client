package net.minecraft.client.particle.chroma.module.movement;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventJump;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class HighJump extends Module
{
	public HighJump()
	{
		super("HighJump", 34, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(113, "Distance", "", this, 3.0D, 1.0, 30.0D, true));
		super.setup();
	}
	
	@EventTarget
	public void onJump(EventJump e)
	{
		e.setMotionUpward(e.getMotionUpward() * this.getSetManager().getSettingById(113).getValueD());
	}
}