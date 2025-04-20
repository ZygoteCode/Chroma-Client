package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public final class WEnchantments
{
    public static final Enchantment PROTECTION = Enchantment.protection;
    public static final Enchantment EFFICIENCY = Enchantment.efficiency;
    public static final Enchantment SILK_TOUCH = Enchantment.silkTouch;
    public static final Enchantment LUCK_OF_THE_SEA = Enchantment.luckOfTheSea;
    public static final Enchantment LURE = Enchantment.lure;
    public static final Enchantment UNBREAKING = Enchantment.unbreaking;
    public static final Enchantment MENDING = null;

    public static int getEnchantmentLevel(Enchantment enchantment, ItemStack stack)
    {
        if (enchantment == null) 
        {
            return 0;
        }
        
        return EnchantmentHelper.getEnchantmentLevel(enchantment.effectId, stack);
    }

    public static boolean hasVanishingCurse(ItemStack stack)
    {
        return false;
    }
}