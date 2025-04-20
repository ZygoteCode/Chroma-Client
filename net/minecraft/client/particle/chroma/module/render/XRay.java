package net.minecraft.client.particle.chroma.module.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.chroma.compatibility.WBlock;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventGetAmbientOcclusionLightValue;
import net.minecraft.client.particle.chroma.event.events.EventRenderBlockModel;
import net.minecraft.client.particle.chroma.event.events.EventRenderTileEntity;
import net.minecraft.client.particle.chroma.event.events.EventSetOpaqueCube;
import net.minecraft.client.particle.chroma.event.events.EventShouldSideBeRendered;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.init.Blocks;

public class XRay extends Module
{
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private ArrayList<String> blockNames = new ArrayList<String>();
	
	public XRay()
	{
		super("XRay", "", Keyboard.KEY_X, Category.RENDER, false, 103);
		this.setInheritance(this.getClass());
	}
	
	@Override
	public void onEnable()
	{
		blocks = new ArrayList<Block>();
		blockNames = new ArrayList<String>();
		blocks.add(Blocks.coal_ore);
		blocks.add(Blocks.coal_block);
		blocks.add(Blocks.emerald_ore);
		blocks.add(Blocks.emerald_block);	
		blocks.add(Blocks.diamond_ore);
		blocks.add(Blocks.diamond_block);	
		blocks.add(Blocks.quartz_ore);
		blocks.add(Blocks.quartz_block);
		blocks.add(Blocks.gold_ore);
		blocks.add(Blocks.gold_block);	
		blocks.add(Blocks.iron_ore);
		blocks.add(Blocks.iron_block);	
		blocks.add(Blocks.lapis_ore);
		blocks.add(Blocks.lapis_block);	
		blocks.add(Blocks.redstone_ore);
		blocks.add(Blocks.redstone_block);		
		blocks.add(Blocks.lit_redstone_ore);	
		mc.renderGlobal.loadRenderers();
		
		for (Block block: blocks)
		{
			blockNames.add(WBlock.getName(block));
		}
		
		super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
        mc.renderGlobal.loadRenderers();
        
        if (!this.getModuleManager().getModuleByID(12).isToggled())
        {
            mc.gameSettings.gammaSetting = 0.5f;
        }
        
		super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		mc.gameSettings.gammaSetting = 16.0f;
	}
	
	@EventTarget
	public void onSetOpaqueBlock(EventSetOpaqueCube event)
	{
		event.cancel();
	}
	
	@EventTarget
	public void onGetAmbientOcclusionLightValue(EventGetAmbientOcclusionLightValue event)
	{
		event.setF(1.0F);
	}
	
	@EventTarget
	public void onShouldSideBeRendered(EventShouldSideBeRendered event)
	{
		event.setB(this.isVisible(event.getState().getBlock()));
	}
	
	@EventTarget
	public void onRenderBlockModel(EventRenderBlockModel event)
	{
        if (!this.isVisible(event.getState().getBlock()))
        {
            event.cancel();
        }
	}
	
	@EventTarget
	public void onRenderTileEntity(EventRenderTileEntity event)
	{
        if (!this.isVisible(event.getTileEntity().getBlockType()))
        {
            event.cancel();
        }
	}
	
    private boolean isVisible(Block block)
    {
        String name = WBlock.getName(block);
        int index = Collections.binarySearch(this.blockNames, name);
        return index >= 0;
    }
}