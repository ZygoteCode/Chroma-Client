package net.minecraft.client.particle.chroma.module.other;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class Eagle extends Module
{
	public Eagle()
	{
		super("Eagle", 44, Category.OTHER);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("Vanilla");
		options.add("Normal");
		this.getSetManager().rSetting(new Setting(144, "Mode", "The working mode of the Eagle module.", this, "Normal", options));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
		
		if (this.getMode().equals("Vanilla"))
		{
            ItemStack item = this.mc.thePlayer.getCurrentEquippedItem();
            
            if (item.getItem() instanceof ItemBlock)
            {
                this.mc.gameSettings.keyBindSneak.pressed = false;
                BlockPos BlockPos2 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
                
                if (this.mc.theWorld.getBlockState(BlockPos2).getBlock() == Blocks.air)
                {
                    this.mc.gameSettings.keyBindSneak.pressed = true;
                }
            }
		}
		else if (this.getMode().equals("Normal"))
		{
            ItemStack item = this.mc.thePlayer.getCurrentEquippedItem();
            
            if (item.getItem() instanceof ItemBlock)          	
            {
                this.mc.gameSettings.keyBindSneak.pressed = false;
                BlockPos BlockPos3 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
                
                if (this.mc.theWorld.getBlockState(BlockPos3).getBlock() == Blocks.air)
                {
                    this.mc.rightClickDelayTimer = 0;
                    this.mc.gameSettings.keyBindSneak.pressed = true;
                }
                else if (this.mc.theWorld.getBlockState(BlockPos3).getBlock() == Blocks.air && this.mc.thePlayer.onGround)
                {
                    this.mc.rightClickDelayTimer = 4;
                }
            }
		}
	}
}