package net.minecraft.client.particle.chroma.utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class AngleUtility {
    static Minecraft mc = Minecraft.getMinecraft();
    private final Random random;
    private float minYawSmoothing, maxYawSmoothing, minPitchSmoothing, maxPitchSmoothing;
    private Vector3<Float> delta;
    private Angle smoothedAngle;

    public AngleUtility(float minYawSmoothing, float maxYawSmoothing, float minPitchSmoothing, float maxPitchSmoothing) {
        this.minYawSmoothing = minYawSmoothing;
        this.maxYawSmoothing = maxYawSmoothing;
        this.minPitchSmoothing = minPitchSmoothing;
        this.maxPitchSmoothing = maxPitchSmoothing;
        this.random = new Random();
        this.delta = new Vector3<>(0F, 0F, 0F);
        this.smoothedAngle = new Angle(0F, 0F);
    }

    public static float[] getAngleBlockpos(BlockPos target) {
        double xDiff = target.getX() - mc.thePlayer.posX;
        double yDiff = target.getY() - mc.thePlayer.posY;
        double zDiff = target.getZ() - mc.thePlayer.posZ;
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float) ((-Math.atan2(
                target.getY() + (double) -1 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);

        if (yDiff > -0.2 && yDiff < 0.2) {
            pitch = (float) ((-Math.atan2(
                    target.getY() + (double) -1 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                    Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
        } else if (yDiff > -0.2) {
            pitch = (float) ((-Math.atan2(
                    target.getY() + (double) -1 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                    Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
        } else if (yDiff < 0.3) {
            pitch = (float) ((-Math.atan2(
                    target.getY() + (double) -1 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()),
                    Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
        }

        return new float[]{yaw, pitch};
    }

    public float randomFloat(float min, float max) {
        return min + (this.random.nextFloat() * (max - min));
    }

    public Angle calculateAngle(Vector3<Double> destination, Vector3<Double> source) {
        Angle angles = new Angle(0F, 0F);
        //Height of where you want to aim at on the entity.
        float height = 1.5F;
        this.delta
                .setX(destination.getX().floatValue() - source.getX().floatValue())
                .setY((destination.getY().floatValue() + height) - (source.getY().floatValue() + height))
                .setZ(destination.getZ().floatValue() - source.getZ().floatValue());
        double hypotenuse = Math.hypot(this.delta.getX().doubleValue(), this.delta.getZ().doubleValue());
        float yawAtan = ((float) Math.atan2(this.delta.getZ().floatValue(), this.delta.getX().floatValue()));
        float pitchAtan = ((float) Math.atan2(this.delta.getY().floatValue(), hypotenuse));
        float deg = ((float) (180 / Math.PI));
        float yaw = ((yawAtan * deg) - 90F);
        float pitch = -(pitchAtan * deg);
        return angles.setYaw(yaw).setPitch(pitch).constrantAngle();
    }

    public Angle smoothAngle(Angle destination, Angle source) {
        return this.smoothedAngle
                .setYaw(source.getYaw() - destination.getYaw())
                .setPitch(source.getPitch() - destination.getPitch())
                .constrantAngle()
                .setYaw(source.getYaw() - this.smoothedAngle.getYaw() / 100 * randomFloat(minYawSmoothing, maxYawSmoothing))
                .setPitch(source.getPitch() - this.smoothedAngle.getPitch() / 100 * randomFloat(minPitchSmoothing, maxPitchSmoothing))
                .constrantAngle();
    }
}