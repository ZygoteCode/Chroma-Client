package net.minecraft.client.particle.chroma.module.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventReceivePacket;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.DamageSource;

public class AutoArmor extends Module
{
	private Timer timer;
	
	public AutoArmor()
	{
		super("AutoArmor", "Automatically put the best armor to the player.", Keyboard.KEY_NONE, Category.COMBAT, false, 23);
	}
	
	@Override
	public void setup()
	{
		timer = new Timer();
		this.getSetManager().rSetting(new Setting(15, "Delay", "Delay of AutoArmor.", this, 200, 1, 1000, true));
		this.getSetManager().rSetting(new Setting(16, "Swap while moving", "Put armor while moving too.", this, false));
		this.getSetManager().rSetting(new Setting(17, "Use enchantments", "Put armor with the best enchantments.", this, false));
		this.getSetManager().rSetting(new Setting(210, "No inventory", "", this, false));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		timer.reset();
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e)
	{
		if (e.getState() == EventState.POST)
		{
	        Object item;
	        ItemStack stack;
	        
	        if (!(timer.hasReach(this.getSetManager().getSettingById(15).getValueD())))
	        {
	            return;
	        }
	        
	        if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof InventoryEffectRenderer))
	        {
	            return;
	        }
	        
	        if (mc.currentScreen instanceof GuiInventory && !this.getSetManager().getSettingById(210).getValBoolean())
	        {
	        	return;
	        }
	        
	        EntityPlayerSP player = mc.thePlayer;
	        InventoryPlayer inventory = player.inventory;
	        
	        if (!(this.getSetManager().getSettingById(16).getValBoolean() || player.movementInput.moveForward == 0.0f && player.movementInput.moveStrafe == 0.0f))
	        {
	            return;
	        }
	        
	        int[] bestArmorSlots = new int[4];
	        int[] bestArmorValues = new int[4];
	        
	        for (int type = 0; type < 4; ++type)
	        {
	            bestArmorSlots[type] = -1;
	            stack = inventory.armorItemInSlot(type);
	            if (isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemArmor)) continue;
	            item = (ItemArmor)stack.getItem();
	            bestArmorValues[type] = this.getArmorValue((ItemArmor)item, stack);
	        }
	        
	        for (int slot = 0; slot < 36; ++slot)
	        {
	            stack = inventory.getStackInSlot(slot);
	            if (isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemArmor)) continue;	            
	            item = (ItemArmor)stack.getItem();	                        
	            int armorType = getArmorType((ItemArmor)item);
	            int armorValue = this.getArmorValue((ItemArmor)item, stack);
	            if (armorValue <= bestArmorValues[armorType]) continue;
	            bestArmorSlots[armorType] = slot;
	            bestArmorValues[armorType] = armorValue;
	        }
	        
	        ArrayList<Integer> types = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
	        Collections.shuffle(types);
	        item = types.iterator();
	        
	        while (((Iterator<Integer>) item).hasNext())
	        {
	            ItemStack oldArmor;
	            int type = (Integer) ((Iterator<Integer>) item).next();
	            int slot = bestArmorSlots[type];
	            if (slot == -1 || !isNullOrEmpty(oldArmor = inventory.armorItemInSlot(type)) && inventory.getFirstEmptyStack() == -1) continue;
	            
	            if (slot < 9)
	            {
	                slot += 36;
	            }
	            
	            if (!isNullOrEmpty(oldArmor))
	            {
	                windowClick_QUICK_MOVE(8 - type);
	            }
	            
	            windowClick_QUICK_MOVE(slot);
	            break;
	        }
		}
	}
	
	@EventTarget
	public void onSentPacket(EventSentPacket e)
	{
		if (e.getPacket() instanceof C0EPacketClickWindow && e.getState() == EventState.PRE)
		{
			timer.reset();
		}
	}
	
    private int getArmorValue(ItemArmor item, ItemStack stack)
    {
        int armorPoints = item.damageReduceAmount;
        int prtPoints = 0;
        int armorToughness = (int) getArmorToughness(item);
        int armorType = item.getArmorMaterial().getDamageReductionAmount(2);
        
        if (this.getSetManager().getSettingById(17).getValBoolean())
        {
            Enchantment protection = Enchantment.protection;
            int prtLvl = getEnchantmentLevel(protection, stack);
            EntityPlayerSP player = mc.thePlayer;
            DamageSource dmgSource = DamageSource.causePlayerDamage(player);
            prtPoints = protection.calcModifierDamage(prtLvl, dmgSource);
        }
        
        return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
    }
    
    public static int getEnchantmentLevel(Enchantment enchantment, ItemStack stack)
    {
        return EnchantmentHelper.getEnchantmentLevel(enchantment.effectId, stack);
    }
    
    public static float getArmorToughness(ItemArmor armor)
    {
        return 0.0f;
    }
    
    public static boolean isNullOrEmpty(Item item)
    {
        return item == null;
    }

    public static boolean isNullOrEmpty(ItemStack stack)
    {
        return stack == null || isNullOrEmpty(stack.getItem());
    }
    
    public static int getArmorType(ItemArmor armor)
    {
        return 3 - armor.armorType;
    }
    
    public static ItemStack windowClick_QUICK_MOVE(int slot)
    {
        return mc.playerController.windowClick(0, slot, 0, 1, mc.thePlayer);
    }
}