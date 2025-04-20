package net.minecraft.client.particle.chroma.utils;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class PlayerUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float getDirection() {
        float yaw = PlayerUtil.mc.thePlayer.rotationYaw;
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (PlayerUtil.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        yaw = (float)((double)yaw * Math.toRadians(1.0));
        return yaw;
    }

    public static void toFwd(double speed) {
        float yaw = (float)((double)PlayerUtil.mc.thePlayer.rotationYaw * Math.toRadians(1.0));
        PlayerUtil.mc.thePlayer.motionX -= (double)MathHelper.sin(yaw) * speed;
        PlayerUtil.mc.thePlayer.motionZ += (double)MathHelper.cos(yaw) * speed;
    }

    public static void setSpeed(double speed) {
        PlayerUtil.mc.thePlayer.motionX = -(Math.sin(PlayerUtil.getDirection()) * speed);
        PlayerUtil.mc.thePlayer.motionZ = Math.cos(PlayerUtil.getDirection()) * speed;
    }

    public static double getSpeed() {
        return Math.sqrt(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ);
    }

    public static ArrayList<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
        double d;
        ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
        double posX = tpX - PlayerUtil.mc.thePlayer.posX;
        double posY = tpY - (PlayerUtil.mc.thePlayer.posY + (double)PlayerUtil.mc.thePlayer.getEyeHeight() + 1.1);
        double posZ = tpZ - PlayerUtil.mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793 - 90.0);
        float pitch = (float)(-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ)) * 180.0 / 3.141592653589793);
        double tmpX = PlayerUtil.mc.thePlayer.posX;
        double tmpY = PlayerUtil.mc.thePlayer.posY;
        double tmpZ = PlayerUtil.mc.thePlayer.posZ;
        double steps = 1.0;
        for (d = speed; d < PlayerUtil.getDistance(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY, PlayerUtil.mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            steps += 1.0;
        }
        for (d = speed; d < PlayerUtil.getDistance(PlayerUtil.mc.thePlayer.posX, PlayerUtil.mc.thePlayer.posY, PlayerUtil.mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            tmpX = PlayerUtil.mc.thePlayer.posX - Math.sin(PlayerUtil.getDirection(yaw)) * d;
            tmpZ = PlayerUtil.mc.thePlayer.posZ + Math.cos(PlayerUtil.getDirection(yaw)) * d;
            positions.add(new Vector3f((float)tmpX, (float)(tmpY -= (PlayerUtil.mc.thePlayer.posY - tpY) / steps), (float)tmpZ));
        }
        positions.add(new Vector3f((float)tpX, (float)tpY, (float)tpZ));
        return positions;
    }

    public static float getDirection(float yaw) {
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (PlayerUtil.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (PlayerUtil.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (PlayerUtil.mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        yaw = (float)((double)yaw * Math.toRadians(1.0));
        return yaw;
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static boolean MovementInput() {
        return PlayerUtil.mc.gameSettings.keyBindForward.pressed || PlayerUtil.mc.gameSettings.keyBindLeft.pressed || PlayerUtil.mc.gameSettings.keyBindRight.pressed || PlayerUtil.mc.gameSettings.keyBindBack.pressed;
    }
}