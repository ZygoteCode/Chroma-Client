package net.minecraft.client.particle.chroma.module.combat;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventHitbox;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class Hitbox extends Module
{
	public Hitbox()
	{
		super("Hitbox", 22, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(23, "Hitbox", "Hitbox.", this, 0.3, 0.1, 2.0, false));
		super.setup();
	}
	
	@EventTarget
	public void onHitbox(EventHitbox e)
	{
		e.setHitbox((float) this.getSetManager().getSettingById(23).getValueD());
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		String hitbox = Double.toString(this.getSetManager().getSettingById(23).getValueD());
		String original = "";
		
		if (hitbox.length() == 3 || hitbox.length() == 4 || hitbox.length() == 1)
		{
			original = hitbox;
		}
		else if (hitbox.length() > 4)
		{
			original = hitbox.substring(0, 4);
		}
		
		this.setSuffix(" §7" + original);
	}
}