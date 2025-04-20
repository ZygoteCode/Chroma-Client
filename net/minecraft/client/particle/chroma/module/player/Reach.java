package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventBlockReach;
import net.minecraft.client.particle.chroma.event.events.EventEntityReach;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class Reach extends Module
{
	public Reach()
	{
		super("Reach", 80, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(261, "Blocks", "", this, false));
		this.getSetManager().rSetting(new Setting(262, "Entities", "", this, true));
		super.setup();
	}
	
	@EventTarget
	public void onBlockReach(EventBlockReach event)
	{
		if (!this.getSetManager().getSettingById(261).getValBoolean())
		{
			return;
		}
		
		event.setReach(4.7F);
	}
	
	@EventTarget
	public void onEntityReach(EventEntityReach event)
	{
		if (!this.getSetManager().getSettingById(262).getValBoolean())
		{
			return;
		}
		
		if (event.getDistance() <= 4.2F)
		{
			event.setDistance(0.0F);
		}
	}
}