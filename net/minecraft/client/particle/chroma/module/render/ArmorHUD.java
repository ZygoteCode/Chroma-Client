package net.minecraft.client.particle.chroma.module.render;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;

public class ArmorHUD extends Module
{
	public ArmorHUD()
	{
		super("ArmorHUD", "", Keyboard.KEY_NONE, Category.RENDER, false, 2);
	}
}