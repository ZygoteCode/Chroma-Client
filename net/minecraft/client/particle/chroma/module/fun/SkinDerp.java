package net.minecraft.client.particle.chroma.module.fun;

import java.util.Random;
import java.util.Set;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class SkinDerp extends Module
{
	private final Random random = new Random();
	
	public SkinDerp()
	{
		super("SkinDerp", 91, Category.FUN);
	}
	
	@Override
	public void onDisable()
	{
        for (EnumPlayerModelParts part : EnumPlayerModelParts.values())
        {
            mc.gameSettings.setModelPartEnabled(part, true);
        }
        
		super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        if (this.random.nextInt(4) != 0)
        {
            return;
        }
        
        Set<EnumPlayerModelParts> activeParts = mc.gameSettings.getModelParts();
        
        for (EnumPlayerModelParts part : EnumPlayerModelParts.values())
        {
            mc.gameSettings.setModelPartEnabled(part, !activeParts.contains((Object)part));
        }
	}
}