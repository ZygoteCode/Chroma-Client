package net.minecraft.client.particle.chroma.module.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class Trajectories extends Module
{
	public Trajectories()
	{
		super("Trajectories", 58, Category.RENDER);
	}
	
	@EventTarget
	public void onRender(EventRender3D event)
	{
        EntityPlayerSP player = mc.thePlayer;
        ItemStack stack = player.inventory.getCurrentItem();
        
        if (stack == null)
        {
            return;
        }
        
        if (!isThrowable(stack))
        {
            return;
        }
        
        boolean usingBow = stack.getItem() instanceof ItemBow;
        double arrowPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)mc.timer.renderPartialTicks - (double)(Math.cos((float)Math.toRadians(player.rotationYaw)) * 0.16f);
        double arrowPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)Minecraft.getMinecraft().timer.renderPartialTicks + (double)player.getEyeHeight() - 0.1;
        double arrowPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - (double)(Math.sin((float)Math.toRadians(player.rotationYaw)) * 0.16f);
        float arrowMotionFactor = usingBow ? 1.0f : 0.4f;
        float yaw = (float)Math.toRadians(player.rotationYaw);
        float pitch = (float)Math.toRadians(player.rotationPitch);
        float arrowMotionX = (float) (-Math.sin(yaw) * Math.cos(pitch) * arrowMotionFactor);
        float arrowMotionY = (float) (-Math.sin(pitch) * arrowMotionFactor);
        float arrowMotionZ = (float) (Math.cos(yaw) * Math.cos(pitch) * arrowMotionFactor);
        double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
        arrowMotionX = (float)((double)arrowMotionX / arrowMotion);
        arrowMotionY = (float)((double)arrowMotionY / arrowMotion);
        arrowMotionZ = (float)((double)arrowMotionZ / arrowMotion);
        
        if (usingBow)
        {
            float bowPower = (float)(72000 - player.getItemInUseCount()) / 20.0f;
            
            if ((bowPower = (bowPower * bowPower + bowPower * 2.0f) / 3.0f) > 1.0f || bowPower <= 0.1f)
            {
                bowPower = 1.0f;
            }
            
            arrowMotionX *= (bowPower *= 3.0f);
            arrowMotionY *= bowPower;
            arrowMotionZ *= bowPower;
        }
        else
        {
            arrowMotionX = (float)((double)arrowMotionX * 1.5);
            arrowMotionY = (float)((double)arrowMotionY * 1.5);
            arrowMotionZ = (float)((double)arrowMotionZ * 1.5);
        }
        
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        RenderManager renderManager = mc.getRenderManager();
        double gravity = usingBow ? 0.05 : (stack.getItem() instanceof ItemPotion ? 0.4 : (stack.getItem() instanceof ItemFishingRod ? 0.15 : 0.03));
        Vec3 playerVector = new Vec3(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.75f);
        GL11.glBegin((int)3);
        
        for (int i = 0; i < 1000; ++i)
        {
            GL11.glVertex3d((double)(arrowPosX - renderManager.renderPosX), (double)(arrowPosY - renderManager.renderPosY), (double)(arrowPosZ - renderManager.renderPosZ));
            arrowMotionX = (float)((double)arrowMotionX * 0.999);
            arrowMotionY = (float)((double)arrowMotionY * 0.999);
            arrowMotionZ = (float)((double)arrowMotionZ * 0.999);
            arrowMotionY = (float)((double)arrowMotionY - gravity * 0.1);
            if (mc.theWorld.rayTraceBlocks(playerVector, new Vec3(arrowPosX += (double)arrowMotionX * 0.1, arrowPosY += (double)arrowMotionY * 0.1, arrowPosZ += (double)arrowMotionZ * 0.1)) != null) break;
        }
        
        GL11.glEnd();
        double renderX = arrowPosX - renderManager.renderPosX;
        double renderY = arrowPosY - renderManager.renderPosY;
        double renderZ = arrowPosZ - renderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glTranslated((double)(renderX - 0.5), (double)(renderY - 0.5), (double)(renderZ - 0.5));
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.25f);
        RenderUtils.drawSolidBox();
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.75f);
        RenderUtils.drawOutlinedBox();
        GL11.glPopMatrix();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
	}
	
    public static boolean isThrowable(ItemStack stack)
    {
        Item item = stack.getItem();
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemPotion && ItemPotion.isSplash(stack.getItemDamage());
    }
}