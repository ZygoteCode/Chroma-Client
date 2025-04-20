package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public enum CombatUtil {
    INSTANCE;
    
    public static Minecraft mc;

    static {
        mc = Minecraft.getMinecraft();
    }

    public static float[] getRotations(Entity entity) {
    	double pX = mc.thePlayer.posX;
        double pY = mc.thePlayer.posY + (double)CombatUtil.mc.thePlayer.getEyeHeight();
        double pZ = mc.thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double)(entity.height / 2.0f);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new float[]{(float)yaw, (float)(90.0 - pitch)};
    }

    public static float[] getIntaveRots(BlockPos bp, EnumFacing enumface) {
        double x = (double)bp.getX() + 0.5;
        double y = (double)bp.getY() + 0.5;
        double z = (double)bp.getZ() + 0.5;
        if (enumface != null) {
            if (EnumFacing.UP != null) {
                y += 0.5;
            } else if (EnumFacing.DOWN != null) {
                y -= 0.5;
            } else if (EnumFacing.WEST != null) {
                x += 0.5;
            } else if (EnumFacing.EAST != null) {
                x -= 0.5;
            } else if (EnumFacing.NORTH != null) {
                z += 0.5;
            } else if (EnumFacing.SOUTH != null) {
                z -= 0.5;
            }
        }
        double dX = x - CombatUtil.mc.thePlayer.posX;
        double dY = y - CombatUtil.mc.thePlayer.posY + (double)CombatUtil.mc.thePlayer.getEyeHeight();
        double dZ = z - CombatUtil.mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(dZ, dX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(dY, Math.sqrt(dX * dX + dZ * dZ)) * 180.0 / 3.141592653589793));
        yaw = MathHelper.wrapAngleTo180_float(yaw);
        pitch = MathHelper.wrapAngleTo180_float(pitch);
        return new float[]{yaw, pitch};
    }

    public static float[] getRotationFromPosition(double x, double y, double z) {
        double xDiff = x - CombatUtil.mc.thePlayer.posX;
        double zDiff = z - CombatUtil.mc.thePlayer.posZ;
        double yDiff = y - CombatUtil.mc.thePlayer.posY - (double)CombatUtil.mc.thePlayer.getEyeHeight();
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotationsNeededBlock(double x, double y, double z) {
        double diffX = x + 0.5 - CombatUtil.mc.thePlayer.posX;
        double diffZ = z + 0.5 - CombatUtil.mc.thePlayer.posZ;
        double diffY = y + 0.5 - (CombatUtil.mc.thePlayer.posY + (double)CombatUtil.mc.thePlayer.getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[]{CombatUtil.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - CombatUtil.mc.thePlayer.rotationYaw), CombatUtil.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - CombatUtil.mc.thePlayer.rotationPitch)};
    }

    public static float getNewAngle(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public static float[] faceEntity(Entity p_706251, float curYaw, float curPitch, double yawSpeed, double pitchSpeed) {
        double var6;
        float pitch;
        double var4 = p_706251.posX - CombatUtil.mc.thePlayer.posX;
        double var8 = p_706251.posZ - CombatUtil.mc.thePlayer.posZ;
        if (p_706251 instanceof EntityLivingBase) {
            EntityLivingBase var14 = (EntityLivingBase)p_706251;
            var6 = var14.posY + (double)var14.getEyeHeight() - (CombatUtil.mc.thePlayer.posY + (double)CombatUtil.mc.thePlayer.getEyeHeight());
        } else {
            var6 = (p_706251.getEntityBoundingBox().minY + p_706251.getEntityBoundingBox().maxY) / 2.0 - (CombatUtil.mc.thePlayer.posY + (double)CombatUtil.mc.thePlayer.getEyeHeight());
        }
        double var141 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float)(-(Math.atan2(var6, var141) * 180.0 / 3.141592653589793));
        float yaw = (float)CombatUtil.updateRotation(curYaw, var12, yawSpeed);
        return new float[]{yaw, pitch = (float)CombatUtil.updateRotation(curPitch, var13, pitchSpeed)};
    }

    private static double updateRotation(double p_706631, double p_706632, double d) {
        double var4 = MathHelper.wrapAngleTo180_double(p_706632 - p_706631);
        if (var4 > d) {
            var4 = d;
        }
        if (var4 < -d) {
            var4 = -d;
        }
        return p_706631 + var4;
    }

    public static float getYawDifference(float current, float target) {
        float rot = 0;
        return rot + ((rot = (target + 180.0f - current) % 360.0f) > 0.0f ? -180.0f : 180.0f);
    }
}
