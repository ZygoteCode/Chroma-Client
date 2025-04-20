package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class WPlayer {
    public static void swingArmClient()
    {
        WMinecraft.getPlayer().swingItem();
    }

    public static void swingArmPacket() {
        WConnection.sendPacket(new C0APacketAnimation());
    }

    public static void attackEntity(Entity entity) {
        if (WMinecraft.getPlayer().isBlocking()) {
            WConnection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
        }
        WPlayer.swingArmClient();
        WPlayer.sendAttackPacket(entity);
    }

    public static void sendAttackPacket(Entity entity) {
        WConnection.sendPacket(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    }

    public static float getCooldown() {
        return 1.0f;
    }

    public static void addPotionEffect(Potion potion) {
        WMinecraft.getPlayer().addPotionEffect(new PotionEffect(potion.getId(), 10801220));
    }

    public static void removePotionEffect(Potion potion) {
        WMinecraft.getPlayer().removePotionEffect(potion.getId());
    }

    public static void copyPlayerModel(EntityPlayer from, EntityPlayer to) {
        to.getDataWatcher().updateObject(10, from.getDataWatcher().getWatchableObjectByte(10));
    }
}