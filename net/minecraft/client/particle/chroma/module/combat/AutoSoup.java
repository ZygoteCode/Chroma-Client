package net.minecraft.client.particle.chroma.module.combat;

import net.minecraft.block.BlockContainer;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;

public class AutoSoup extends Module
{
	private int oldSlot = -1;
	
	public AutoSoup()
	{
		super("AutoSoup", 61, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(213, "Health", "", this, 8.0D, 1.0D, 18.0D, true));
		super.setup();
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
        for (int i = 0; i < 36; ++i)
        {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack == null || stack.getItem() != Items.bowl || i == 9) continue;
            ItemStack emptyBowlStack = mc.thePlayer.inventory.getStackInSlot(9);
            boolean swap = !isNullOrEmpty(emptyBowlStack) && emptyBowlStack.getItem() != Items.bowl;
            windowClick_PICKUP(i < 9 ? 36 + i : i);
            windowClick_PICKUP(9);
            if (!swap) continue;
            windowClick_PICKUP(i < 9 ? 36 + i : i);
        }
        
        int soupInHotbar = this.findSoup(0, 9);
        
        if (soupInHotbar != -1)
        {
            if (!this.shouldEatSoup())
            {
                this.stopIfEating();
                return;
            }
            
            if (this.oldSlot == -1)
            {
                this.oldSlot = mc.thePlayer.inventory.currentItem;
            }
            
            mc.thePlayer.inventory.currentItem = soupInHotbar;
            mc.gameSettings.keyBindUseItem.pressed = true;
            processRightClick();
            return;
        }
        
        this.stopIfEating();
        int soupInInventory = this.findSoup(9, 36);
        
        if (soupInInventory != -1)
        {
            windowClick_QUICK_MOVE(soupInInventory);
        }
	}
	
    private int findSoup(int startSlot, int endSlot)
    {
        for (int i = startSlot; i < endSlot; ++i)
        {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemSoup)) continue;
            return i;
        }
        
        return -1;
    }

    private boolean shouldEatSoup()
    {
        if (mc.thePlayer.getHealth() > this.getSetManager().getSettingById(213).getValueD())
        {
            return false;
        }
        
        if (mc.currentScreen != null)
        {
            return false;
        }
        
        if (mc.currentScreen == null && mc.objectMouseOver != null)
        {
            Entity entity = mc.objectMouseOver.entityHit;
            
            if (entity instanceof EntityVillager || entity instanceof EntityTameable)
            {
                return false;
            }
            
            if (mc.objectMouseOver.getBlockPos() != null && mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()) instanceof BlockContainer)
            {
                return false;
            }
        }
        
        return true;
    }
    
    private void stopIfEating()
    {
        if (this.oldSlot == -1)
        {
            return;
        }
        
        mc.gameSettings.keyBindUseItem.pressed = false;
        mc.thePlayer.inventory.currentItem = this.oldSlot;
        this.oldSlot = -1;
    }
    
    public static boolean isNullOrEmpty(Item item)
    {
        return item == null;
    }

    public static boolean isNullOrEmpty(ItemStack stack)
    {
        return stack == null || isNullOrEmpty(stack.getItem());
    }
    
    public static ItemStack windowClick_QUICK_MOVE(int slot)
    {
        return mc.playerController.windowClick(0, slot, 0, 1, mc.thePlayer);
    }

    public static void processRightClick()
    {
    	mc.playerController.processRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
    }
    
    public static ItemStack windowClick_PICKUP(int slot)
    {
        return mc.playerController.windowClick(0, slot, 0, 0, mc.thePlayer);
    }
}