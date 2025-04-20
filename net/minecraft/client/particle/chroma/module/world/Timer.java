package net.minecraft.client.particle.chroma.module.world;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class Timer extends Module
{
	public Timer()
	{
		super("Timer", 19, Category.WORLD);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(19, "Timer speed", "", this, 1.0D, 1.0D, 5.0D, false));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		mc.timer.timerSpeed = 1.0F;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		String coso = Double.toString(this.getSetManager().getSettingById(19).getValueD());
		this.setSuffix(" §7" + coso.substring(0, 3));
		mc.timer.timerSpeed = (float) this.getSetManager().getSettingById(19).getValueD();
	}
}