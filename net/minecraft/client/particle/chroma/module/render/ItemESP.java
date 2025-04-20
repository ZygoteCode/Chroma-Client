package net.minecraft.client.particle.chroma.module.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;

public class ItemESP extends Module
{
    private int itemBox;
    private final ArrayList<EntityItem> items = new ArrayList();
	
	public ItemESP()
	{
		super("ItemESP", 52, Category.RENDER);
	}
	
	@Override
	public void onEnable()
	{
        this.itemBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.itemBox, (int)4864);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2896);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)0.5f);
        RenderUtils.drawOutlinedBox(new AxisAlignedBB(-0.5, 0.0, -0.5, 0.5, 1.0, 0.5));
        GL11.glEndList();
        super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
		GL11.glDeleteLists((int)this.itemBox, (int)1);
		super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        this.items.clear();
        
        for (Entity entity : mc.theWorld.loadedEntityList)
        {
            if (!(entity instanceof EntityItem)) continue;
            this.items.add((EntityItem)entity);
        }
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event)
	{
		float partialTicks = event.getPartialTicks();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glPushMatrix();
        RenderUtils.applyRenderOffset();
        this.renderBoxes(partialTicks);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
	}
	
    private void renderBoxes(double partialTicks)
    {
        double extraSize = 0.0;
        
        for (EntityItem e : this.items)
        {
            GL11.glPushMatrix();
            GL11.glTranslated((double)(e.prevPosX + (e.posX - e.prevPosX) * partialTicks), (double)(e.prevPosY + (e.posY - e.prevPosY) * partialTicks), (double)(e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks));
            GL11.glPushMatrix();
            GL11.glScaled((double)((double)e.width + extraSize), (double)((double)e.height + extraSize), (double)((double)e.width + extraSize));
            GL11.glCallList((int)this.itemBox);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
}