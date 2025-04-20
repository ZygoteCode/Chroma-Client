package net.minecraft.client.particle.chroma.compatibility;

import java.util.List;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public final class WPotion {
    public static List<PotionEffect> getEffectsFromStack(ItemStack stack) {
        return new ItemPotion().getEffects(stack);
    }

    public static int getIdFromEffect(PotionEffect effect) {
        return effect.getPotionID();
    }

    public static int getIdFromResourceLocation(String location) {
        return Potion.getPotionFromResourceLocation((String)location).id;
    }
}

