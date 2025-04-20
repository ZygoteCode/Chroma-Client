package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;

public final class WItem
{
    public static boolean isNullOrEmpty(Item item)
    {
        return item == null;
    }

    public static boolean isNullOrEmpty(ItemStack stack)
    {
        return stack == null || WItem.isNullOrEmpty(stack.getItem());
    }

    public static int getArmorType(ItemArmor armor)
    {
        return 3 - armor.armorType;
    }

    public static float getArmorToughness(ItemArmor armor)
    {
        return 0.0f;
    }

    public static boolean isThrowable(ItemStack stack)
    {
        Item item = stack.getItem();
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemPotion && ItemPotion.isSplash(stack.getItemDamage());
    }

    public static boolean isPotion(ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemPotion;
    }

    public static Item getFromRegistry(ResourceLocation location)
    {
        return (Item)Item.itemRegistry.getObject(location);
    }

    public static int getStackSize(ItemStack stack)
    {
        return stack.stackSize;
    }

    public static float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return WItem.isNullOrEmpty(stack) ? 1.0f : stack.getStrVsBlock(state.getBlock());
    }
}