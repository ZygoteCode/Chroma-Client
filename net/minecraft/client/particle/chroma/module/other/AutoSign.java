package net.minecraft.client.particle.chroma.module.other;

import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.util.IChatComponent;

public class AutoSign extends Module
{
	private static IChatComponent[] signText;
	
	public AutoSign()
	{
		super("AutoSign", 97, Category.OTHER);
	}
	
	@Override
	public void onDisable()
	{
		this.signText = null;
		super.onDisable();
	}
	
    public static IChatComponent[] getSignText()
    {
        return signText;
    }

    public static void setSignText(IChatComponent[] signTextT)
    {
        if (mc.getChroma().getModuleManager().getModuleByID(97).isToggled() && signText == null)
        {
            signText = signTextT;
        }
    }
}