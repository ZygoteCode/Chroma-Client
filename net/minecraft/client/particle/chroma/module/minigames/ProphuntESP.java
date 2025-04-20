package net.minecraft.client.particle.chroma.module.minigames;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;

public class ProphuntESP extends Module
{
	private static final AxisAlignedBB FAKE_BLOCK_BOX = new AxisAlignedBB(-0.5, 0.0, -0.5, 0.5, 1.0, 0.5);
	
	public ProphuntESP()
	{
		super("ProphuntESP", 54, Category.MINIGAMES);
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event)
	{
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
        float alpha = (float) (0.5f + 0.25f * Math.sin((float)(System.currentTimeMillis() % 1000L) / 500.0f * 3.1415927f));
        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)alpha);
        
        for (Entity entity : mc.theWorld.loadedEntityList)
        {
            if (!(entity instanceof EntityLiving) || !entity.isInvisible() || mc.thePlayer.getDistanceSqToEntity(entity) < 0.25) continue;
            GL11.glPushMatrix();
            GL11.glTranslated((double)entity.posX, (double)entity.posY, (double)entity.posZ);
            RenderUtils.drawOutlinedBox(FAKE_BLOCK_BOX);
            RenderUtils.drawSolidBox(FAKE_BLOCK_BOX);
            GL11.glPopMatrix();
        }
        
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
	}
}