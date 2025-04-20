package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public final class WPlayerController
{
    private static PlayerControllerMP getPlayerController()
    {
        return Minecraft.getMinecraft().playerController;
    }

    public static ItemStack windowClick_PICKUP(int slot)
    {
        return WPlayerController.getPlayerController().windowClick(0, slot, 0, 0, WMinecraft.getPlayer());
    }

    public static ItemStack windowClick_QUICK_MOVE(int slot)
    {
        return WPlayerController.getPlayerController().windowClick(0, slot, 0, 1, WMinecraft.getPlayer());
    }

    public static ItemStack windowClick_THROW(int slot)
    {
        return WPlayerController.getPlayerController().windowClick(0, slot, 1, 4, WMinecraft.getPlayer());
    }

    public static void processRightClick()
    {
        WPlayerController.getPlayerController().processRightClick(WMinecraft.getPlayer(), WMinecraft.getWorld(), WMinecraft.getPlayer().getCurrentEquippedItem());
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3 hitVec)
    {
        WPlayerController.getPlayerController().onPlayerRightClick(WMinecraft.getPlayer(), WMinecraft.getWorld(), WMinecraft.getPlayer().getCurrentEquippedItem(), pos, side, hitVec);
    }
}