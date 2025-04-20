package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.chroma.compatibility.WConnection;
import net.minecraft.client.particle.chroma.compatibility.WMath;
import net.minecraft.client.particle.chroma.compatibility.WMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class RotationUtils {
    private static boolean fakeRotation;
    private static float serverYaw;
    private static float serverPitch;

    public static Vec3 getEyesPos() {
        return new Vec3(WMinecraft.getPlayer().posX, WMinecraft.getPlayer().posY + (double)WMinecraft.getPlayer().getEyeHeight(), WMinecraft.getPlayer().posZ);
    }

    public static Vec3 getClientLookVec() {
        float f = WMath.cos(-WMinecraft.getPlayer().rotationYaw * 0.017453292f - 3.1415927f);
        float f1 = WMath.sin(-WMinecraft.getPlayer().rotationYaw * 0.017453292f - 3.1415927f);
        float f2 = -WMath.cos(-WMinecraft.getPlayer().rotationPitch * 0.017453292f);
        float f3 = WMath.sin(-WMinecraft.getPlayer().rotationPitch * 0.017453292f);
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static Vec3 getServerLookVec() {
        float f = WMath.cos(-serverYaw * 0.017453292f - 3.1415927f);
        float f1 = WMath.sin(-serverYaw * 0.017453292f - 3.1415927f);
        float f2 = -WMath.cos(-serverPitch * 0.017453292f);
        float f3 = WMath.sin(-serverPitch * 0.017453292f);
        return new Vec3(f1 * f2, f3, f * f2);
    }

    private static float[] getNeededRotations(Vec3 vec) {
        Vec3 eyesPos = RotationUtils.getEyesPos();
        double diffX = vec.xCoord - eyesPos.xCoord;
        double diffY = vec.yCoord - eyesPos.yCoord;
        double diffZ = vec.zCoord - eyesPos.zCoord;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{WMath.wrapDegrees(yaw), WMath.wrapDegrees(pitch)};
    }

    private static float[] getNeededRotations2(Vec3 vec) {
        Vec3 eyesPos = RotationUtils.getEyesPos();
        double diffX = vec.xCoord - eyesPos.xCoord;
        double diffY = vec.yCoord - eyesPos.yCoord;
        double diffZ = vec.zCoord - eyesPos.zCoord;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{WMinecraft.getPlayer().rotationYaw + WMath.wrapDegrees(yaw - WMinecraft.getPlayer().rotationYaw), WMinecraft.getPlayer().rotationPitch + WMath.wrapDegrees(pitch - WMinecraft.getPlayer().rotationPitch)};
    }

    public static double getAngleToLastReportedLookVec(Vec3 vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        EntityPlayerSP player = WMinecraft.getPlayer();
        float lastReportedYaw = WMath.wrapDegrees(player.lastReportedYaw);
        float lastReportedPitch = WMath.wrapDegrees(player.lastReportedPitch);
        float diffYaw = lastReportedYaw - needed[0];
        float diffPitch = lastReportedPitch - needed[1];
        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }

    public static float limitAngleChange(float current, float intended, float maxChange) {
        float change = WMath.wrapDegrees(intended - current);
        change = WMath.clamp(change, -maxChange, maxChange);
        return WMath.wrapDegrees(current + change);
    }

    public static boolean faceVectorPacket(Vec3 vec) {
        fakeRotation = true;
        float[] rotations = RotationUtils.getNeededRotations(vec);
        serverYaw = RotationUtils.limitAngleChange(serverYaw, rotations[0], 30.0f);
        serverPitch = rotations[1];
        return Math.abs(serverYaw - rotations[0]) < 1.0f;
    }

    public static void faceVectorPacketInstant(Vec3 vec) {
        float[] rotations = RotationUtils.getNeededRotations2(vec);
        WConnection.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(rotations[0], rotations[1], WMinecraft.getPlayer().onGround));
    }

    public static boolean faceVectorClient(Vec3 vec) {
        float[] rotations = RotationUtils.getNeededRotations(vec);
        float oldYaw = WMinecraft.getPlayer().prevRotationYaw;
        float oldPitch = WMinecraft.getPlayer().prevRotationPitch;
        WMinecraft.getPlayer().rotationYaw = RotationUtils.limitAngleChange(oldYaw, rotations[0], 30.0f);
        WMinecraft.getPlayer().rotationPitch = rotations[1];
        return Math.abs(oldYaw - rotations[0]) + Math.abs(oldPitch - rotations[1]) < 1.0f;
    }

    public static boolean faceEntityClient(Entity entity) {
        Vec3 eyesPos = RotationUtils.getEyesPos();
        Vec3 lookVec = RotationUtils.getServerLookVec();
        AxisAlignedBB bb = entity.boundingBox;
        if (RotationUtils.faceVectorClient(bb.getCenter())) {
            return true;
        }
        return bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6.0))) != null;
    }

    public static boolean faceEntityPacket(Entity entity) {
        Vec3 eyesPos = RotationUtils.getEyesPos();
        Vec3 lookVec = RotationUtils.getServerLookVec();
        AxisAlignedBB bb = entity.boundingBox;
        if (RotationUtils.faceVectorPacket(bb.getCenter())) {
            return true;
        }
        return bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6.0))) != null;
    }

    public static boolean faceVectorForWalking(Vec3 vec) {
        float[] rotations = RotationUtils.getNeededRotations(vec);
        float oldYaw = WMinecraft.getPlayer().prevRotationYaw;
        WMinecraft.getPlayer().rotationYaw = RotationUtils.limitAngleChange(oldYaw, rotations[0], 30.0f);
        WMinecraft.getPlayer().rotationPitch = 0.0f;
        return Math.abs(oldYaw - rotations[0]) < 1.0f;
    }

    public static float getAngleToClientRotation(Vec3 vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        float diffYaw = WMath.wrapDegrees(WMinecraft.getPlayer().rotationYaw) - needed[0];
        float diffPitch = WMath.wrapDegrees(WMinecraft.getPlayer().rotationPitch) - needed[1];
        float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }

    public static float getHorizontalAngleToClientRotation(Vec3 vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        float angle = WMath.wrapDegrees(WMinecraft.getPlayer().rotationYaw) - needed[0];
        return angle;
    }

    public static float getAngleToServerRotation(Vec3 vec) {
        float[] needed = RotationUtils.getNeededRotations(vec);
        float diffYaw = serverYaw - needed[0];
        float diffPitch = serverPitch - needed[1];
        float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }

    public static void updateServerRotation() {
        if (fakeRotation) {
            fakeRotation = false;
            return;
        }
        serverYaw = RotationUtils.limitAngleChange(serverYaw, WMinecraft.getPlayer().rotationYaw, 30.0f);
        serverPitch = WMinecraft.getPlayer().rotationPitch;
    }

    public static float getServerYaw() {
        return serverYaw;
    }

    public static float getServerPitch() {
        return serverPitch;
    }
}
