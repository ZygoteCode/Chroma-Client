package net.minecraft.client.particle.chroma.module.combat;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.WPotionEffects;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AutoPot extends Module
{
	private int timer;
	
	public AutoPot()
	{
		super("AutoPot", 60, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(212, "Health", "", this, 8.0D, 1.0D, 18.0D, true));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        int potionInHotbar = this.findPotion(0, 9);
        
        if (potionInHotbar != -1)
        {
            if (this.timer > 0)
            {
                --this.timer;
                return;
            }
            
            if (mc.thePlayer.getHealth() > this.getSetManager().getSettingById(212).getValueD())
            {
                return;
            }
            
            if (mc.currentScreen != null)
            {
                return;
            }
            
            int oldSlot = mc.thePlayer.inventory.currentItem;
            mc.thePlayer.inventory.currentItem = potionInHotbar;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, 90.0f, mc.thePlayer.onGround));
            processRightClick();
            mc.thePlayer.inventory.currentItem = oldSlot;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
            this.timer = 10;
            return;
        }
        
        int potionInInventory = this.findPotion(9, 36);
        
        if (potionInInventory != -1)
        {
            windowClick_QUICK_MOVE(potionInInventory);
        }
	}
	
    private int findPotion(int startSlot, int endSlot)
    {
        for (int i = startSlot; i < endSlot; ++i)
        {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (!isSplashPotion(stack) || !hasEffect(stack, WPotionEffects.INSTANT_HEALTH)) continue;
            return i;
        }
        
        return -1;
    }
    
    public static boolean isSplashPotion(ItemStack stack)
    {
        return !isNullOrEmpty(stack) && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage());
    }

    public static boolean hasEffect(ItemStack stack, Potion potion)
    {
        for (PotionEffect effect : ((ItemPotion)stack.getItem()).getEffects(stack))
        {
            if (effect.getPotionID() != potion.id) continue;
            return true;
        }
        
        return false;
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
}