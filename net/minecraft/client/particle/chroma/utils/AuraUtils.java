package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import optifine.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuraUtils
{
    public static ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
    public static ArrayList<EntityLivingBase> blackList = new ArrayList<EntityLivingBase>();
    private static boolean disableAura;
    private static boolean reachExploit;
    private static int timercap = 15;
    private static double range = 7;
    private static boolean headsnap;
    private static double chargerange = 8.0;
    private static double packetTPRange = 10;
    static Minecraft mc = Minecraft.getMinecraft();

    public static boolean hasEntity(Entity en)
    {
        if (en == null)
        {
            return false;
        }

        if (!targets.isEmpty())
        {
            for (EntityLivingBase en1 : targets)
            {
                if (en1 == null)
                {
                    continue;
                }

                if (en1.isEntityEqual(en))
                {
                    return true;
                }
            }
        }

        return false;
    }


    public static boolean blackEntity(Entity en)
    {
        if (en == null)
        {
            return false;
        }

        if (!blackList.isEmpty())
        {
            for (EntityLivingBase en1 : blackList)
            {
                if (en1 == null)
                {
                    continue;
                }

                if (en1.isEntityEqual(en))
                {
                    return true;
                }
            }
        }

        return false;
    }
    
    public static Entity getMouseOver(float yaw, float pitch, Entity target)
    {
        Entity pointedEntity = null;
        float p_78473_1_ = 1.0f;
        Entity var2 = mc.getRenderViewEntity();

        if (var2 != null && mc.theWorld != null)
        {
            mc.mcProfiler.startSection("pick");
            mc.pointedEntity = null;
            double var3 = mc.playerController.getBlockReachDistance();
            mc.objectMouseOver = var2.rayTrace(var3, p_78473_1_);
            double var5 = var3;
            Vec3 var7 = var2.getPositionEyes(var2.getEyeHeight());
            var3 = target.getDistanceToEntity(mc.thePlayer);
            var5 = target.getDistanceToEntity(mc.thePlayer);

            if (mc.objectMouseOver != null)
            {
                var5 = mc.objectMouseOver.hitVec.distanceTo(var7);
            }

            float ThePitch = pitch;
            float TheYaw = yaw;
            Vec3 var8 = var2.getVectorForRotation(ThePitch, TheYaw);
            Vec3 var9 = var7.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
            pointedEntity = null;
            Vec3 var10 = null;
            float var11 = 0.3f;
            List var12 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3).expand(var11, var11, var11));
            double var13 = var5;
            int var15 = 0;

            while (var15 < var12.size())
            {
                Entity var16 = (Entity) var12.get(var15);

                if (var16.canBeCollidedWith())
                {
                    double var20;
                    float var17 = var16.getCollisionBorderSize();
                    AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
                    MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);

                    if (var18.isVecInside(var7))
                    {
                        if (0.0 < var13 || var13 == 0.0)
                        {
                            pointedEntity = var16;
                            var10 = var19 == null ? var7 : var19.hitVec;
                            var13 = 0.0;
                        }
                    }
                    else if (var19 != null && ((var20 = var7.distanceTo(var19.hitVec)) < var13 || var13 == 0.0))
                    {
                        if (var16 == var2.ridingEntity)
                        {
                            if (var13 == 0.0)
                            {
                                pointedEntity = var16;
                                var10 = var19.hitVec;
                            }
                        }
                        else
                        {
                            pointedEntity = var16;
                            var10 = var19.hitVec;
                            var13 = var20;
                        }
                    }
                }

                ++var15;
            }

            if (pointedEntity != null && (var13 < var5 || mc.objectMouseOver == null))
            {
                mc.objectMouseOver = new MovingObjectPosition(pointedEntity, var10);

                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                {
                    mc.pointedEntity = pointedEntity;
                }
            }

            mc.mcProfiler.endSection();
        }

        return pointedEntity;
    }

    public static boolean getDisableAura()
    {
        return disableAura;
    }

    public static void setDisableAura(boolean disableAura)
    {
        AuraUtils.disableAura = disableAura;
    }

    public static boolean isReachExploit()
    {
        return reachExploit;
    }

    public static void setReachExploit(boolean reachExploit)
    {
        AuraUtils.reachExploit = reachExploit;
    }

    public static double getPacketTPRange()
    {
        return packetTPRange;
    }

    public static void setPacketTPRange(double packetTPRange)
    {
        AuraUtils.packetTPRange = packetTPRange;
    }

    public static double getRange()
    {
        return range;
    }

    public static void setRange(double value)
    {
        range = value;
    }

    public static boolean getHeadsnap()
    {
        return headsnap;
    }

    public static int getAPS()
    {
        return timercap;
    }

    public static void setTimer(int set)
    {
        timercap = set;
    }

    public static void setHeadSnap(boolean selected)
    {
        headsnap = selected;
    }

    public static double getChargeRange()
    {
        return chargerange;
    }

    public static void setChargeRange(double chargerange)
    {
        AuraUtils.chargerange = chargerange;
    }

    public static double[] getRotationToEntity(Entity entity)
    {
        double pX = mc.thePlayer.posX;
        double pY = mc.thePlayer.posY + (mc.thePlayer.getEyeHeight());
        double pZ = mc.thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (entity.height / 2);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2));
        double yaw = (Math.toDegrees(Math.atan2(dZ, dX)) + 90);
        double pitch = (Math.toDegrees(Math.atan2(dH, dY)));
        return new double[]{yaw, 90 - pitch};
    }
    
    public static boolean isOnTeam(EntityLivingBase en)
    {
        if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("§"))
        {
            if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2 || en.getDisplayName().getUnformattedText().length() <= 2)
            {
                return false;
            }
            
            if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(en.getDisplayName().getUnformattedText().substring(0, 2)))
            {
                return true;
            }
        }
        
        return false;
    }
    public static double angleDifference(double a, double b)
    {

        return ((((a - b) % 360D) + 540D) % 360D) - 180D;
    }

    public boolean isWithingFOV(Entity en, float angle)
    {
        angle *= 0.5;
        double angleDifference = angleDifference(mc.thePlayer.rotationYaw, getRotationToEntity(en)[0]);
        return (angleDifference > 0 && angleDifference < angle) || (-angle < angleDifference && angleDifference < 0);
    }
}