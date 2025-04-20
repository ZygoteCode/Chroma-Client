package net.minecraft.client.particle.chroma.module.other;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class AntiLag extends Module
{
	public AntiLag()
	{
		super("AntiLag", 38, Category.OTHER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(120, "Render logo", "", this, true));
		this.getSetManager().rSetting(new Setting(121, "Simple logo", "", this, false));
		this.getSetManager().rSetting(new Setting(122, "Render arraylist", "", this, true));
		this.getSetManager().rSetting(new Setting(123, "Simple arraylist", "", this, false));
		this.getSetManager().rSetting(new Setting(124, "Render TabGui", "", this, true));
		this.getSetManager().rSetting(new Setting(125, "Simple TabGui", "", this, false));
		this.getSetManager().rSetting(new Setting(171, "Render mobs", "", this, true));
		this.getSetManager().rSetting(new Setting(172, "Render animals", "", this, true));
		this.getSetManager().rSetting(new Setting(173, "Render players", "", this, true));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate event)
	{
		if (!this.getSetManager().getSettingById(171).getValBoolean() || !this.getSetManager().getSettingById(172).getValBoolean() || !this.getSetManager().getSettingById(173).getValBoolean())
		{
			for (Entity entity: mc.theWorld.loadedEntityList)
			{
				if (entity == mc.thePlayer)
				{
					continue;
				}
				
				if ((entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityGolem) && !this.getSetManager().getSettingById(171).getValBoolean())
				{
					entity.setInvisible(true);
				}
				else if (entity instanceof EntityAnimal && !this.getSetManager().getSettingById(172).getValBoolean())
				{
					entity.setInvisible(true);
				}
				else if (entity instanceof EntityPlayer && !this.getSetManager().getSettingById(173).getValBoolean())
				{
					entity.setInvisible(true);
				}
				else
				{
					entity.setInvisible(false);
				}
			}
		}
	}
}