package net.minecraft.client.particle.chroma.utils;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class RotationUtil
{
	private static Minecraft mc = Minecraft.getMinecraft();

	/*public static float[] faceEntity(Entity p_70625_1_, float p_70625_2_, float p_70625_3_) {
		double var4 = p_70625_1_.posX - mc.thePlayer.posX;
		double var8 = p_70625_1_.posZ - mc.thePlayer.posZ;
		double var6;

		if (p_70625_1_ instanceof EntityLivingBase) {
			EntityLivingBase var14 = (EntityLivingBase) p_70625_1_;
			var6 = var14.posY + (double) var14.getEyeHeight()
					- (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
		} else {
			var6 = (p_70625_1_.getEntityBoundingBox().minY + p_70625_1_.getEntityBoundingBox().maxY) / 2.0D
					- (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
		}

		double var141 = (double) MathHelper.sqrt_double(var4 * var4 + var8 * var8);
		float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
		float var13 = (float) (-(Math.atan2(var6, var141) * 180.0D / Math.PI));

		Aura aura = (Aura) Client.getInstance().modmgr.getModuleByClass(Aura.class);

		float pitch = updateRotation(aura.toLookPitch, var13, p_70625_3_);
		float yaw = updateRotation(aura.toLookYaw, var12, p_70625_2_);
		return new float[] { yaw, pitch };

	}

	public static float[] finalfaceEntity(Entity p_70625_1_) {
		Entity e = p_70625_1_;
		double var4 = p_70625_1_.posX - mc.thePlayer.posX;
		double var8 = p_70625_1_.posZ - mc.thePlayer.posZ;

		double x = (e.posX - e.lastTickPosX)
				* ((double) Client.getInstance().setmgr.getSettingByName("AuraPredictSpeed").getValDouble());
		double z = (e.posZ - e.lastTickPosZ)
				* ((double) Client.getInstance().setmgr.getSettingByName("AuraPredictSpeed").getValDouble());
		double y = (e.posY - e.lastTickPosY)
				* ((double) Client.getInstance().setmgr.getSettingByName("AuraPredictSpeed").getValDouble());
		if (Client.getInstance().modmgr.getModuleByClass(Aura.class).isEnabled()) {
			if (Client.getInstance().setmgr.getSettingByName("AuraPredict").getValBoolean()) {
				var4 += x;
				var8 += z;
			}

		}

		double var6;
		if (p_70625_1_ instanceof EntityLivingBase) {
			EntityLivingBase var14 = (EntityLivingBase) p_70625_1_;
			var6 = var14.posY + (double) var14.getEyeHeight()
					- (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
		} else {
			var6 = (p_70625_1_.getEntityBoundingBox().minY + p_70625_1_.getEntityBoundingBox().maxY) / 2.0D
					- (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
		}

		double var141 = (double) MathHelper.sqrt_double(var4 * var4 + var8 * var8);
		float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
		float var13 = (float) (-(Math.atan2(var6, var141) * 180.0D / Math.PI));

		return new float[] { var12, var13 };
	}*/

	private static float getRandom(float min, float max) {
		return (float) (min + (max - min) * Math.random());
	}

	/**
	 * Arguments: current rotation, intended rotation, max increment.
	 */
	private static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
		float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
		if (var4 > p_70663_3_) {
			var4 = p_70663_3_;
		}

		if (var4 < -p_70663_3_) {
			var4 = -p_70663_3_;
		}

		return p_70663_1_ + var4;
	}

	private static float updateRotation1(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
		float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
		if (var4 > p_70663_3_) {
			var4 = p_70663_3_;
		}

		if (var4 < -p_70663_3_) {
			var4 = -p_70663_3_;
		}

		return p_70663_1_ + var4;
	}

	public static float[] getBlockRotations(BlockPos block) {
		EntitySnowball p_70625_1_ = new EntitySnowball(mc.theWorld, block.getX(), block.getY(), block.getZ());
		double var4 = p_70625_1_.posX - mc.thePlayer.posX;
		double var8 = p_70625_1_.posZ - mc.thePlayer.posZ;

		double var6;

		var6 = (p_70625_1_.getEntityBoundingBox().minY + p_70625_1_.getEntityBoundingBox().maxY) / 2.0D
				- (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());

		double var141 = (double) MathHelper.sqrt_double(var4 * var4 + var8 * var8);
		float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
		float var13 = (float) (-(Math.atan2(var6, var141) * 180.0D / Math.PI));
		float pitch = updateRotation1(mc.thePlayer.rotationPitch, var13, 1000);
		float yaw = updateRotation1(mc.thePlayer.rotationYaw, var12, 1000);
		return new float[] { var12, var13 };

	}

	// BY DIZE
	public static float[] getRotations(Entity entityIn) {
		double x = entityIn.posX - Minecraft.getMinecraft().thePlayer.posX;
		double z = entityIn.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double result;

		Entity e = entityIn;
		double xpredict = (e.posX - e.lastTickPosX) * (double) mc.timer.renderPartialTicks;
		double ypredict = (e.posZ - e.lastTickPosZ) * (double) mc.timer.renderPartialTicks;

		x -= xpredict;
		z += ypredict;

		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) entityIn;
			result = entity.posY + entity.getEyeHeight()
					- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() * 1.5);
		} else {
			result = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D
					- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		}
		double janisttull = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (-(Math.atan2(result, janisttull) * 180.0D / Math.PI));

	

		return new float[] { yaw, pitch};
	}

	public static float[] getRotations(EntityPlayer player, Entity entityIn) {
		double x = entityIn.posX - player.posX;
		double z = entityIn.posZ - player.posZ;
		double result;

		Entity e = entityIn;
		double xpredict = (e.posX - e.lastTickPosX) * (double) mc.timer.renderPartialTicks;
		double ypredict = (e.posZ - e.lastTickPosZ) * (double) mc.timer.renderPartialTicks;

		x -= xpredict;
		z += ypredict;

		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) entityIn;
			result = entity.posY + entity.getEyeHeight() - (player.posY + player.getEyeHeight());
		} else {
			result = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D
					- (player.posY + player.getEyeHeight());
		}
		double janisttull = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (-(Math.atan2(result, janisttull) * 180.0D / Math.PI));

		float yawrdm = getRandom(-getRandom(1, 5), getRandom(1, 5));
		float pitchrdm = getRandom(-getRandom(1, 5), getRandom(3, 10));

		yaw += yawrdm;
		pitch += pitchrdm;

		return new float[] { yaw, pitch, yawrdm, pitchrdm };
	}
	
    public static float[] getRotations(final EntityLivingBase ent) {
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f - 0.5;
        return getRotationFromPosition(x, z, y);
    }
    
    public static float[] getAverageRotations(final List<EntityLivingBase> targetList) {
        double posX = 0.0;
        double posY = 0.0;
        double posZ = 0.0;
        for (final Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0;
            posZ += ent.posZ;
        }
        posX /= targetList.size();
        posY /= targetList.size();
        posZ /= targetList.size();
        return new float[] { getRotationFromPosition(posX, posZ, posY)[0], getRotationFromPosition(posX, posZ, posY)[1] };
    }
    
    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 0.6;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float getTrajAngleSolutionLow(final float d3, final float d1, final float velocity) {
        final float g = 0.006f;
        final float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }
    
    public static float getNewAngle(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
    
    public static float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 360.0f - angle3;
        }
        return angle3;
    }
}