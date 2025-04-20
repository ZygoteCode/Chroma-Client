package net.minecraft.client.particle.chroma.module.render;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class PotionHUD extends Module
{
	public PotionHUD()
	{
		super("PotionHUD", "", Keyboard.KEY_NONE, Category.RENDER, false, 3);
	}
}