package net.minecraft.client.particle.chroma.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class Overlay extends Module
{
	public Overlay()
	{
		super("Overlay", 53, Category.RENDER);
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event)
	{
        if (mc.objectMouseOver == null)
        {
            return;       
        }
        
        BlockPos pos = mc.objectMouseOver.getBlockPos();
        
        if (pos == null)
        {
            return;
        }
        
    	if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)
        {
        	return;
        }
        
        if (!this.getModuleManager().getModuleByID(124).isToggled())
        {
        	if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid)
            {
            	return;
            }
        }
        
    	GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
        GL11.glTranslated((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        float progress = 1.01F;
        GL11.glTranslated((double)0.5, (double)0.5, (double)0.5);
        GL11.glScaled((double)progress, (double)progress, (double)progress);
        GL11.glTranslated((double)-0.5, (double)-0.5, (double)-0.5);
        float red = progress * 2.0f;
        float green = 2.0f - red;
        red = Color.cyan.darker().darker().getRed();
        green = Color.cyan.darker().darker().getGreen();
        float blue = Color.cyan.darker().darker().getBlue();
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)0.25f);
        RenderUtils.drawSolidBox();
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)0.5f);
        RenderUtils.drawOutlinedBox();
        GL11.glPopMatrix();
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
	}
}