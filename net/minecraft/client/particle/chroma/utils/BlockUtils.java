package net.minecraft.client.particle.chroma.utils;

import java.util.ArrayDeque;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.compatibility.WBlock;
import net.minecraft.client.particle.chroma.compatibility.WConnection;
import net.minecraft.client.particle.chroma.compatibility.WMinecraft;
import net.minecraft.client.particle.chroma.compatibility.WPlayer;
import net.minecraft.client.particle.chroma.compatibility.WPlayerController;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class BlockUtils
{
    private static final AxisAlignedBB CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);

    public static IBlockState getState(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static int getId(BlockPos pos) {
        return Block.getIdFromBlock(getBlock(pos));
    }

    public static String getName(Block block) {
        return "" + Block.blockRegistry.getNameForObject(block);
    }

    public static Material getMaterial(BlockPos pos) {
        return getBlock(pos).getMaterial();
    }

    public static AxisAlignedBB getBoundingBox(BlockPos pos) {
        Block block = getBlock(pos);
        if (block instanceof BlockChest) {
            return CHEST_AABB.offset(pos);
        }
        return block.getSelectedBoundingBox(Minecraft.getMinecraft().theWorld, pos);
    }

    public static boolean canBeClicked(BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    public static float getHardness(BlockPos pos) {
        return getBlock(pos).getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, pos);
    }
    
    public static double getDistanceFromGround()
    {
    	double distance = 0.0D;
    	
    	BlockPos actualPos = mc.thePlayer.getPosition();
    	
    	for (int i = 0; i < 256; i++)
    	{
    		BlockPos newPos = mc.thePlayer.getPosition().subtract(new Vec3i(0.0, 1.0, 0.0));
    		
    		distance += 1.0D;
    		
    		if (mc.theWorld.getBlockState(newPos).getBlock() == Blocks.air)
    		{
    			break;
    		}
    	}
    	
    	return distance;
    }
    
    public static boolean isTrappedChest(TileEntityChest chest) {
        return chest.getChestType() == 1;
    }
        private static final Minecraft mc = Minecraft.getMinecraft();

        public static String getName(BlockPos pos) {
            return BlockUtils.getName(BlockUtils.getBlock(pos));
        }

        public static Block getBlockFromName(String name) {
            try {
                return Block.getBlockFromName(name);
            }
            catch (Exception e) {
                return Blocks.air;
            }
        }

        public static boolean placeBlockLegit(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 dirVec;
                Vec3 hitVec;
                BlockPos neighbor = pos.offset(side);
                if (!WBlock.canBeClicked(neighbor) || eyesPos.squareDistanceTo(hitVec = posVec.add((dirVec = new Vec3(side.getDirectionVec())).scale(0.5))) > 18.0625 || distanceSqPosVec > eyesPos.squareDistanceTo(posVec.add(dirVec)) || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
                RotationUtils.faceVectorPacketInstant(hitVec);
                WPlayerController.processRightClickBlock(neighbor, side.getOpposite(), hitVec);
                WPlayer.swingArmClient();
                BlockUtils.mc.rightClickDelayTimer = 4;
                return true;
            }
            return false;
        }

        public static boolean placeBlockScaffold(BlockPos pos) {
            Vec3 eyesPos = new Vec3(WMinecraft.getPlayer().posX, WMinecraft.getPlayer().posY + (double)WMinecraft.getPlayer().getEyeHeight(), WMinecraft.getPlayer().posZ);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec;
                BlockPos neighbor = pos.offset(side);
                EnumFacing side2 = side.getOpposite();
                if (eyesPos.squareDistanceTo(new Vec3(pos).addVector(0.5, 0.5, 0.5)) >= eyesPos.squareDistanceTo(new Vec3(neighbor).addVector(0.5, 0.5, 0.5)) || !WBlock.canBeClicked(neighbor) || eyesPos.squareDistanceTo(hitVec = new Vec3(neighbor).addVector(0.5, 0.5, 0.5).add(new Vec3(side2.getDirectionVec()).scale(0.5))) > 18.0625) continue;
                RotationUtils.faceVectorPacketInstant(hitVec);
                WPlayerController.processRightClickBlock(neighbor, side2, hitVec);
                WPlayer.swingArmClient();
                BlockUtils.mc.rightClickDelayTimer = 4;
                return true;
            }
            return false;
        }

        public static void placeBlockSimple(BlockPos pos) {
            int i;
            Enum side = null;
            EnumFacing[] sides = EnumFacing.values();
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            Vec3[] hitVecs = new Vec3[sides.length];
            for (i = 0; i < sides.length; ++i) {
                hitVecs[i] = posVec.add(new Vec3(sides[i].getDirectionVec()).scale(0.5));
            }
            for (i = 0; i < sides.length; ++i) {
                if (!WBlock.canBeClicked(pos.offset(sides[i])) || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVecs[i], false, true, false) != null) continue;
                side = sides[i];
                break;
            }
            if (side == null) {
                for (i = 0; i < sides.length; ++i) {
                    if (!WBlock.canBeClicked(pos.offset(sides[i])) || distanceSqPosVec > eyesPos.squareDistanceTo(hitVecs[i])) continue;
                    side = sides[i];
                    break;
                }
            }
            if (side == null) {
                return;
            }
            Vec3 hitVec = hitVecs[side.ordinal()];
            RotationUtils.faceVectorPacket(hitVec);
            if (RotationUtils.getAngleToLastReportedLookVec(hitVec) > 1.0) {
                return;
            }
            if (BlockUtils.mc.rightClickDelayTimer > 0) {
                return;
            }
            WPlayerController.processRightClickBlock(pos.offset((EnumFacing)side), ((EnumFacing)side).getOpposite(), hitVec);
            WPlayer.swingArmPacket();
            BlockUtils.mc.rightClickDelayTimer = 4;
        }

        public static boolean placeBlockSimple_old(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec;
                BlockPos neighbor = pos.offset(side);
                if (!WBlock.canBeClicked(neighbor) || eyesPos.squareDistanceTo(hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5))) > 36.0) continue;
                WPlayerController.processRightClickBlock(neighbor, side.getOpposite(), hitVec);
                return true;
            }
            return false;
        }

        public static boolean prepareToBreakBlockLegit(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
                if (distanceSqHitVec > 18.0625 || distanceSqHitVec >= distanceSqPosVec || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
                if (!RotationUtils.faceVectorPacket(hitVec)) {
                    return true;
                }
                return true;
            }
            return false;
        }

        public static boolean breakBlockLegit(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
                if (distanceSqHitVec > 18.0625 || distanceSqHitVec >= distanceSqPosVec || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
                if (!BlockUtils.mc.playerController.onPlayerDamageBlock(pos, side)) {
                    return false;
                }
                WPlayer.swingArmPacket();
                return true;
            }
            return false;
        }

        public static boolean breakBlockExtraLegit(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
                if (distanceSqHitVec > 18.0625 || distanceSqHitVec >= distanceSqPosVec || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
                if (!RotationUtils.faceVectorClient(hitVec)) {
                    return true;
                }
                if (BlockUtils.mc.gameSettings.keyBindAttack.pressed && !mc.gameSettings.keyBindAttack.isPressed()) {
                    BlockUtils.mc.gameSettings.keyBindAttack.pressed = false;
                    return true;
                }
                BlockUtils.mc.gameSettings.keyBindAttack.pressed = true;
                return true;
            }
            return false;
        }

        public static boolean breakBlockSimple(BlockPos pos) {
            int i;
            Enum side = null;
            EnumFacing[] sides = EnumFacing.values();
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 relCenter = WBlock.getBoundingBox(pos).offset(-pos.getX(), -pos.getY(), -pos.getZ()).getCenter();
            Vec3 center = new Vec3(pos).add(relCenter);
            Vec3[] hitVecs = new Vec3[sides.length];
            for (i = 0; i < sides.length; ++i) {
                Vec3i dirVec = sides[i].getDirectionVec();
                Vec3 relHitVec = new Vec3(relCenter.xCoord * (double)dirVec.getX(), relCenter.yCoord * (double)dirVec.getY(), relCenter.zCoord * (double)dirVec.getZ());
                hitVecs[i] = center.add(relHitVec);
            }
            for (i = 0; i < sides.length; ++i) {
                if (WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVecs[i], false, true, false) != null) continue;
                side = sides[i];
                break;
            }
            if (side == null) {
                double distanceSqToCenter = eyesPos.squareDistanceTo(center);
                for (int i2 = 0; i2 < sides.length; ++i2) {
                    if (eyesPos.squareDistanceTo(hitVecs[i2]) >= distanceSqToCenter) continue;
                    side = sides[i2];
                    break;
                }
            }
            if (side == null) {
                throw new RuntimeException("How could none of the sides be facing towards the player?!");
            }
            RotationUtils.faceVectorPacket(hitVecs[side.ordinal()]);
            if (!BlockUtils.mc.playerController.onPlayerDamageBlock(pos, (EnumFacing)side)) {
                return false;
            }
            WPlayer.swingArmPacket();
            return true;
        }

        public static boolean breakBlockSimple_old(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
                if (distanceSqHitVec > 36.0 || distanceSqHitVec >= distanceSqPosVec) continue;
                RotationUtils.faceVectorPacket(hitVec);
                if (!BlockUtils.mc.playerController.onPlayerDamageBlock(pos, side)) {
                    return false;
                }
                WPlayer.swingArmPacket();
                return true;
            }
            return false;
        }

        public static void breakBlockPacketSpam(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) >= distanceSqPosVec) continue;
                WConnection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
                WConnection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
                return;
            }
        }

        public static void breakBlocksPacketSpam(Iterable<BlockPos> blocks) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            block0 : for (BlockPos pos : blocks) {
                Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
                double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
                for (EnumFacing side : EnumFacing.values()) {
                    Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                    if (eyesPos.squareDistanceTo(hitVec) >= distanceSqPosVec) continue;
                    WConnection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
                    WConnection.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
                    continue block0;
                }
            }
        }

        public static boolean rightClickBlockLegit(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
                if (distanceSqHitVec > 18.0625 || distanceSqHitVec >= distanceSqPosVec || WMinecraft.getWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) != null) continue;
                if (!RotationUtils.faceVectorPacket(hitVec)) {
                    return true;
                }
                WPlayerController.processRightClickBlock(pos, side, hitVec);
                WPlayer.swingArmClient();
                BlockUtils.mc.rightClickDelayTimer = 4;
                return true;
            }
            return false;
        }

        public static boolean rightClickBlockSimple(BlockPos pos) {
            Vec3 eyesPos = RotationUtils.getEyesPos();
            Vec3 posVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
            double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
            for (EnumFacing side : EnumFacing.values()) {
                Vec3 hitVec = posVec.add(new Vec3(side.getDirectionVec()).scale(0.5));
                double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
                if (distanceSqHitVec > 36.0 || distanceSqHitVec >= distanceSqPosVec) continue;
                WPlayerController.processRightClickBlock(pos, side, hitVec);
                return true;
            }
            return false;
        }

        public static Iterable<BlockPos> getValidBlocks(double range, BlockValidator validator) {
            Vec3 eyesPos = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
            double rangeSq = Math.pow(range + 0.5, 2.0);
            return BlockUtils.getValidBlocks((int)Math.ceil(range), pos -> {
                Vec3 Vec32 = new Vec3(pos);
                if (eyesPos.squareDistanceTo(Vec32) > rangeSq) {
                    return false;
                }
                return validator.isValid(pos);
            });
        }

        public static interface BlockValidator {
            public boolean isValid(BlockPos var1);
        }

    }