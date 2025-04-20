package net.minecraft.client.particle.chroma.module.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;

public class NoWeather extends Module
{
	public NoWeather()
	{
		super("NoWeather", 93, Category.RENDER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(284, "Disable rain", "", this, true));
		this.getSetManager().rSetting(new Setting(285, "Change world time", "", this, false));
		this.getSetManager().rSetting(new Setting(286, "Change moon phase", "", this, false));
		this.getSetManager().rSetting(new Setting(287, "Time", "", this, 6000.0, 0.0, 23900.0, true));
		this.getSetManager().rSetting(new Setting(288, "Moon phase", "", this, 0.0, 0.0, 7.0, true));
		super.setup();
	}
	
    public static boolean isRainDisabled()
    {
        return Minecraft.getMinecraft().getChroma().getModuleManager().getModuleByID(93).isToggled() && Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(284).getValBoolean();
    }

    public static boolean isTimeChanged()
    {
    	return Minecraft.getMinecraft().getChroma().getModuleManager().getModuleByID(93).isToggled() && Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(285).getValBoolean();
    }

    public static long getChangedTime()
    {
        return Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(287).getValueI();
    }

    public static boolean isMoonPhaseChanged()
    {
    	return Minecraft.getMinecraft().getChroma().getModuleManager().getModuleByID(93).isToggled() && Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(286).getValBoolean();
    }

    public static int getChangedMoonPhase()
    {
    	return Minecraft.getMinecraft().getChroma().getSetManager().getSettingById(288).getValueI();
    }
}