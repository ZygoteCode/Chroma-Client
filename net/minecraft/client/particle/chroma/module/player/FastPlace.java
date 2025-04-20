package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class FastPlace extends Module
{
	public FastPlace()
	{
		super("FastPlace", 59, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(203, "Speed", "", this, 4, 1, 4, true));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		mc.rightClickDelayTimer = 4;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		mc.rightClickDelayTimer = 4 - this.getSetManager().getSettingById(203).getValueI();
	}
}