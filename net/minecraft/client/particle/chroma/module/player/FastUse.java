package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;

public class FastUse extends Module
{
	public FastUse()
	{
		super("FastUse", 66, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(277, "Uses", "", this, 2.0, 1.0, 20.0, true));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE) && !mc.getChroma().getModuleManager().getModuleByID(71).isToggled())
		{
			if (mc.gameSettings.keyBindUseItem.pressed && mc.objectMouseOver != null && mc.thePlayer.inventory.getCurrentItem() != null && !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow) && !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood))
			{
				for (int i = 0; i < this.getSetManager().getSettingById(277).getValueI(); i++)
				{
					mc.rightClickMouse();
				}
			}
		}
	}
}