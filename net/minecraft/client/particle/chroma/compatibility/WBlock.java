package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.world.World;

public final class WBlock {
    private static final AxisAlignedBB CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);

    public static IBlockState getState(BlockPos pos) {
        return WMinecraft.getWorld().getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return WBlock.getState(pos).getBlock();
    }

    public static int getId(BlockPos pos) {
        return Block.getIdFromBlock(WBlock.getBlock(pos));
    }

    public static String getName(Block block) {
        return "" + Block.blockRegistry.getNameForObject(block);
    }

    public static Material getMaterial(BlockPos pos) {
        return WBlock.getBlock(pos).getMaterial();
    }

    public static int getIntegerProperty(IBlockState state, PropertyInteger prop) {
        return (Integer)state.getValue(prop);
    }

    public static AxisAlignedBB getBoundingBox(BlockPos pos) {
        Block block = WBlock.getBlock(pos);
        if (block instanceof BlockChest) {
            return CHEST_AABB.offset(pos);
        }
        return block.getSelectedBoundingBox(WMinecraft.getWorld(), pos);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return WBlock.getBlock(pos).canCollideCheck(WBlock.getState(pos), false);
    }

    public static float getHardness(BlockPos pos) {
        return WBlock.getBlock(pos).getPlayerRelativeBlockHardness(WMinecraft.getPlayer(), WMinecraft.getWorld(), pos);
    }

    public static boolean canFallThrough(BlockPos pos) {
        return BlockFalling.canFallInto(WMinecraft.getWorld(), pos);
    }

    public static boolean isFullyOpaque(BlockPos pos) {
        throw new UnsupportedOperationException();
    }
}