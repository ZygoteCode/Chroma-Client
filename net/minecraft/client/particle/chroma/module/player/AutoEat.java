package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class AutoEat extends Module
{
	private int oldSlot = -1;
	
	public AutoEat()
	{
		super("AutoEat", 83, Category.PLAYER);
	}
	
	@Override
	public void onDisable()
	{
		this.stopIfEating();
		super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        if (!this.shouldEat())
        {
            this.stopIfEating();
            return;
        }
        
        int bestSlot = -1;
        float bestSaturation = -1.0f;
        
        for (int i = 0; i < 9; ++i)
        {
            float saturation;
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            
            if (stack == null || !(stack.getItem() instanceof ItemFood) || !((saturation = ((ItemFood)stack.getItem()).getSaturationModifier(stack)) > bestSaturation)) continue;
            
            bestSaturation = saturation;
            bestSlot = i;
        }
        
        if (bestSlot == -1)
        {
            this.stopIfEating();
            return;
        }
        
        if (!this.isEating())
        {
            this.oldSlot = mc.thePlayer.inventory.currentItem;
        }
        
        mc.thePlayer.inventory.currentItem = bestSlot;
        mc.gameSettings.keyBindUseItem.pressed = true;
        processRightClick();
	}
	
    private boolean shouldEat()
    {
        if (!mc.thePlayer.canEat(false))
        {
            return false;
        }
        
        if (mc.currentScreen != null)
        {
            return false;
        }
        
        if (mc.currentScreen == null && mc.objectMouseOver != null)
        {
            Block block;
            Entity entity = mc.objectMouseOver.entityHit;
            
            if (entity instanceof EntityVillager || entity instanceof EntityTameable)
            {
                return false;
            }
            
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            
            if (pos != null && ((block = mc.theWorld.getBlockState(pos).getBlock()) instanceof BlockContainer || block instanceof BlockWorkbench))
            {
                return false;
            }
        }
        
        return true;
    }

    public boolean isEating()
    {
        return this.oldSlot != -1;
    }

    private void stopIfEating()
    {
        if (!this.isEating())
        {
            return;
        }
        
        mc.gameSettings.keyBindUseItem.pressed = false;
        mc.thePlayer.inventory.currentItem = this.oldSlot;
        this.oldSlot = -1;
    }
    
    public static void processRightClick()
    {
        mc.playerController.processRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
    }
}