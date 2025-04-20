package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.*;
import java.awt.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import org.lwjgl.*;
import java.nio.*;
import net.minecraft.util.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.*;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DONT_CARE;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_POLYGON_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glScissor;

import java.awt.Color;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class RenderUtils
{
    static RenderManager renderManager;
    protected static float zLevel;
    private static final IntBuffer VIEWPORT;
    private static final FloatBuffer MODELVIEW;
    private static final FloatBuffer PROJECTION;
    private static final FloatBuffer OBJECTCOORDS;
    
	public static void color(int color, float alpha) {
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;

		glColor4f(red, green, blue, alpha);
	}
	
    public static void applyRenderOffset() {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
    }

	public static void color(int color) {
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		float alpha = (float) (color & 255) / 255.0F;

		glColor4f(red, green, blue, alpha);
	}
	public static void blockESPBox(BlockPos blockPos, Color color) {
		double x = (double) blockPos.getX() - RenderManager.renderPosX;
		double y = (double) blockPos.getY() - RenderManager.renderPosY;
		double z = (double) blockPos.getZ() - RenderManager.renderPosZ;
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
		glLineWidth(1.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		color(color.getRGB(), 0.125F);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
		RenderGlobal.func_181563_a(new AxisAlignedBB(x, y, z, x + 1, y + 1.0D, z + 1.0D),
				Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue(), Color.black.getAlpha());
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void entityESPBox(EntityLivingBase e, int color) {
		if (e.hurtTime != 0) {
			color = Color.RED.hashCode();
		}

		Minecraft mc = Minecraft.getMinecraft();
		double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) mc.timer.renderPartialTicks
				- RenderManager.renderPosX;
		double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) mc.timer.renderPartialTicks
				- RenderManager.renderPosY;
		double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) mc.timer.renderPartialTicks
				- RenderManager.renderPosZ;
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
		glLineWidth(1.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		color(color, 0.19F);
		drawColorBox(
				new AxisAlignedBB(x - e.width / 2, y, z - e.width / 2, x + e.width / 2, y + 1.8D, z + e.width / 2));
		RenderGlobal.func_181563_a(
				new AxisAlignedBB(x - e.width / 2, y, z - e.width / 2, x + e.width / 2, y + 1.8D, z + e.width / 2),
				Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue(), Color.RED.getAlpha());
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawColorBox(AxisAlignedBB mask) {
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
		Tessellator tessellator = Tessellator.getInstance();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		tessellator.draw();
	}

	public static void drawColorBox(AxisAlignedBB mask, int color) {
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
		Tessellator tessellator = Tessellator.getInstance();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.pos(mask.minX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.maxZ);
		worldRenderer.pos(mask.minX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.minX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.minZ);
		worldRenderer.pos(mask.maxX, mask.maxY, mask.maxZ);
		worldRenderer.pos(mask.maxX, mask.minY, mask.maxZ);
		tessellator.draw();
		GL11.glColor3f(1, 1, 1);
	}

	public static void doGlScissor(int x, int y, int width, int height) {
		if (x == width || y == height) {
			return;
		}
		Minecraft mc = Minecraft.getMinecraft();
		int scaleFactor = 1;
		int k = mc.gameSettings.guiScale;

		while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320
				&& mc.displayHeight / (scaleFactor + 1) >= 240) {
			++scaleFactor;
		}
		glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor,
				height * scaleFactor);

	}

	public static void drawFullCircle(double d, double e, double r, int c) {
		float alpha = (c >> 24 & 0xFF) / 255.0F;
		float red = (c >> 16 & 0xFF) / 255.0F;
		float green = (c >> 8 & 0xFF) / 255.0F;
		float blue = (c & 0xFF) / 255.0F;

		GL11.glColor4f(red, green, blue, alpha);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glPushMatrix();
		GL11.glLineWidth(1F);
		GL11.glBegin(GL11.GL_POLYGON);
		for (int i = 0; i <= 360; i++)
			GL11.glVertex2d(d + Math.sin(i * Math.PI / 180.0D) * r, e + Math.cos(i * Math.PI / 180.0D) * r);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(1F, 1F, 1F, 1F);

	}

	public static void drawLine(Point start, Point end, float width, int color, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		color(color, alpha);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(width);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(start.getX(), start.getY());
		GL11.glVertex2d(end.getX(), end.getY());
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static void drawLine2(Point start, Point end, float width, int color, float alpha) {
		GL11.glPushMatrix();
		color(color, alpha);
		GL11.glLineWidth(width);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(start.getX(), start.getY());
		GL11.glVertex2d(end.getX(), end.getY());
		GL11.glEnd();
		GL11.glPopMatrix();
	}
    
    public static double[] interpolate(final Entity entity) {
        final float ticks = Minecraft.getMinecraft().timer.renderPartialTicks;
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks - RenderUtils.renderManager.renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks - RenderUtils.renderManager.renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks - RenderUtils.renderManager.renderPosZ;
        return new double[] { x, y, z };
    }
    
    private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

    public static void scissorBox(int x, int y, int xend, int yend) {
        int width = xend - x;
        int height = yend - y;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int factor = sr.getScaleFactor();
        int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
        GL11.glScissor((int)(x * factor), (int)(bottomY * factor), (int)(width * factor), (int)(height * factor));
    }

    public static void setColor(Color c) {
        GL11.glColor4f((float)((float)c.getRed() / 255.0f), (float)((float)c.getGreen() / 255.0f), (float)((float)c.getBlue() / 255.0f), (float)((float)c.getAlpha() / 255.0f));
    }

    public static void drawSolidBox() {
        RenderUtils.drawSolidBox(DEFAULT_AABB);
    }

    public static void drawSolidBox(AxisAlignedBB bb) {
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
    }

    public static void drawOutlinedBox() {
        RenderUtils.drawOutlinedBox(DEFAULT_AABB);
    }

    public static void drawOutlinedBox(AxisAlignedBB bb) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
    }

    public static void drawCrossBox() {
        RenderUtils.drawCrossBox(DEFAULT_AABB);
    }

    public static void drawCrossBox(AxisAlignedBB bb) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glEnd();
    }

    public static void drawNode(AxisAlignedBB bb) {
        double midX = (bb.minX + bb.maxX) / 2.0;
        double midY = (bb.minY + bb.maxY) / 2.0;
        double midZ = (bb.minZ + bb.maxZ) / 2.0;
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)bb.minX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.minZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)bb.maxX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.maxZ);
        GL11.glVertex3d((double)midX, (double)bb.maxY, (double)midZ);
        GL11.glVertex3d((double)bb.maxX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.maxY, (double)midZ);
        GL11.glVertex3d((double)bb.minX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.maxY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.minZ);
        GL11.glVertex3d((double)midX, (double)bb.maxY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.maxZ);
        GL11.glVertex3d((double)midX, (double)bb.minY, (double)midZ);
        GL11.glVertex3d((double)bb.maxX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.minY, (double)midZ);
        GL11.glVertex3d((double)bb.minX, (double)midY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)bb.minY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.minZ);
        GL11.glVertex3d((double)midX, (double)bb.minY, (double)midZ);
        GL11.glVertex3d((double)midX, (double)midY, (double)bb.maxZ);
    }
    
    public static double[] interpolate(final BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        x -= RenderUtils.renderManager.renderPosX;
        y -= RenderUtils.renderManager.renderPosY;
        z -= RenderUtils.renderManager.renderPosZ;
        return new double[] { x, y, z };
    }
    
    public static void drawBlendRectangle(final double x, final double y, final double x1, final double y1, final int... colors) {
        GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glBegin(7);
        final double height = (y1 - y) / colors.length;
        for (int i = 0; i < colors.length - 1; ++i) {
            final float[] cTop = getRGBA(colors[i]);
            final float[] cBottom = getRGBA(colors[i + 1]);
            GL11.glColor4f(cTop[0], cTop[1], cTop[2], cTop[3]);
            GL11.glVertex2d(x, y + i * height);
            GL11.glVertex2d(x1, y + i * height);
            GL11.glColor4f(cBottom[0], cBottom[1], cBottom[2], cBottom[3]);
            GL11.glVertex2d(x1, y + i * height + height);
            GL11.glVertex2d(x, y + i * height + height);
        }
        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GL11.glPopMatrix();
    }
    
    public static void drawRoundedRect(final double x, final double y, final double x1, final double y1, final double radius, final int col) {
        drawFilledCircle(x + radius, y + radius, 90, 180, radius, col);
        drawFilledCircle(x1 - radius, y1 - radius, 90, 0, radius, col);
        drawFilledCircle(x + radius, y1 - radius, 90, 90, radius, col);
        drawFilledCircle(x1 - radius, y + radius, 90, 270, radius, col);
        drawRect(x + radius, y + radius, x1 - radius, y1 - radius, col);
        drawRect(x + radius, y, x1 - radius, y + radius, col);
        drawRect(x + radius, y1 - radius, x1 - radius, y1, col);
        drawRect(x, y + radius, x + radius, y1 - radius, col);
        drawRect(x1 - radius, y + radius, x1, y1 - radius, col);
    }
    
    public static void drawBorderedRoundedRect(final double x, final double y, final double x1, final double y1, final float l, final double radius, final int col1, final int col2) {
        drawRoundedRect(x, y, x1, y1, radius, col2);
        GL11.glLineWidth(l);
        drawCircle(x + radius, y + radius, 90, 180, radius, col1);
        drawCircle(x1 - radius, y1 - radius, 90, 0, radius, col1);
        drawCircle(x + radius, y1 - radius, 90, 90, radius, col1);
        drawCircle(x1 - radius, y + radius, 90, 270, radius, col1);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.pushMatrix();
        GlStateManager.color(f2, f3, f4, f);
        GL11.glLineWidth(l);
        GL11.glBegin(1);
        GL11.glVertex2d(x + radius, y);
        GL11.glVertex2d(x1 - radius, y);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x + radius, y1);
        GL11.glVertex2d(x1 - radius, y1);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x, y + radius);
        GL11.glVertex2d(x, y1 - radius);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x1, y + radius);
        GL11.glVertex2d(x1, y1 - radius);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(2848);
    }
    
    public static void drawBorderedEllipse(final double d, final double e, final double r, final double r2, final int color) {
        drawEllipse(d, e, 360, 0, r, r2, color);
        drawFilledEllipse(d, e, 360, 0, r, r2, color);
    }
    
    public static void drawBorderedCircle(final double d, final double e, final double r, final int color) {
        drawCircle(d, e, 360, 0, r, color);
        drawFilledCircle(d, e, 360, 0, r, color);
    }
    
    public static void drawFilledCircle(final double d, final double e, final int degrees, final int rotate, final double r, final int color) {
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.color(green, blue, alpha, red);
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GlStateManager.translate(d, e, 0.0);
        GlStateManager.rotate(rotate, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-d, -e, 0.0);
        GL11.glBegin(6);
        GL11.glVertex2d(d, e);
        for (int i = 0; i <= degrees; ++i) {
            final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(d + x2, e + y2);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawCircle(final double d, final double e, final int degrees, final int rotate, final double r, final int color) {
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.color(green, blue, alpha, red);
        GlStateManager.enableBlend();
        GlStateManager.translate(d, e, 0.0);
        GlStateManager.rotate(rotate, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-d, -e, 0.0);
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GlStateManager.blendFunc(770, 771);
        GL11.glBegin(3);
        for (int i = 0; i <= degrees; ++i) {
            final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(d + x2, e + y2);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawEllipse(final double d, final double e, final int degrees, final int rotate, final double r, final double r2, final int color) {
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.color(green, blue, alpha, red);
        GlStateManager.enableBlend();
        GlStateManager.translate(d, e, 0.0);
        GlStateManager.rotate(rotate, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-d, -e, 0.0);
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GlStateManager.blendFunc(770, 771);
        GL11.glBegin(3);
        for (int i = 0; i <= degrees; ++i) {
            final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d(d + x2, e + y2);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawFilledEllipse(final double d, final double e, final int degrees, final int rotate, final double r, final double r2, final int color) {
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.color(green, blue, alpha, red);
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GlStateManager.translate(d, e, 0.0);
        GlStateManager.rotate(rotate, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-d, -e, 0.0);
        GL11.glBegin(6);
        GL11.glVertex2d(d, e);
        for (int i = 0; i <= degrees; ++i) {
            final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d(d + x2, e + y2);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawCircle(final double d, final double e, final float r, final int color) {
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.color(green, blue, alpha, red);
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GlStateManager.blendFunc(770, 771);
        GL11.glBegin(3);
        for (int i = 0; i <= 360; ++i) {
            final double x2 = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y2 = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(d + x2, e + y2);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawRect(final double x, final double y, final double x1, final double y1, final int color) {
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f4, f5, f6, f3);
        var10.startDrawingQuads();
        var10.pos(x, y1, 0.0);
        var10.pos(x1, y1, 0.0);
        var10.pos(x1, y, 0.0);
        var10.pos(x, y, 0.0);
        var9.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawBorderedTriangle(final double x, final double y, final double x1, final double y1, final double x2, final double y2, final float lw, final int color, final int borderColor) {
        drawTriangle(x, y, x1, y1, x2, y2, borderColor);
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.pushMatrix();
        GlStateManager.color(f2, f3, f4, f);
        GL11.glLineWidth(lw);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(2848);
    }
    
    public static void drawTriangle(final double x, final double y, final double x1, final double y1, final double x2, final double y2, final int color) {
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        final float a = (color >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(r, g, b, a);
        GL11.glBegin(6);
        if ((y > y1 && x < x2) || (y < y1 && x > x2)) {
            GL11.glVertex2d(x2, y2);
            GL11.glVertex2d(x1, y1);
            GL11.glVertex2d(x, y);
        }
        else {
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x1, y1);
            GL11.glVertex2d(x2, y2);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void enableGL2D() {
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void enableGL3D(final float lineWidth) {
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GL11.glEnable(2884);
        GlStateManager.disableLighting();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }
    
    public static void disableGL3D() {
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static Color getRandomColor() {
        final Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(6000) + 1000) / 10000.0f;
        final float luminance = 0.9f;
        return Color.getHSBColor(hue, saturation, 0.9f);
    }
    
    public static void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        GlStateManager.disableTexture2D();
        GL11.glLineWidth(width);
        GL11.glEnable(2848);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
    }
    
    public static int genTexture() {
        return GL11.glGenTextures();
    }
    
    public static int applyTexture(final int texId, final File file, final boolean linear, final boolean repeat) throws IOException {
        applyTexture(texId, ImageIO.read(file), linear, repeat);
        return texId;
    }
    
    public static int applyTexture(final int texId, final BufferedImage image, final boolean linear, final boolean repeat) {
        final int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                final int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 0xFF));
                buffer.put((byte)(pixel >> 8 & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)(pixel >> 24 & 0xFF));
            }
        }
        buffer.flip();
        applyTexture(texId, image.getWidth(), image.getHeight(), buffer, linear, repeat);
        return texId;
    }
    
    public static int applyTexture(final int texId, final int width, final int height, final ByteBuffer pixels, final boolean linear, final boolean repeat) {
        GL11.glBindTexture(3553, texId);
        GL11.glTexParameteri(3553, 10241, linear ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10240, linear ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10242, repeat ? 10497 : 10496);
        GL11.glTexParameteri(3553, 10243, repeat ? 10497 : 10496);
        GL11.glPixelStorei(3317, 1);
        GL11.glTexImage2D(3553, 0, 32856, width, height, 0, 6408, 5121, pixels);
        return texId;
    }
    
    public static void renderTexture(final int texID, final float x, final float y, final float width, final float height) {
        GlStateManager.bindTexture(texID);
        renderTexture(x, y, width, height);
    }
    
    public static void renderTexture(final int textureWidth, final int textureHeight, final float x, final float y, final float width, final float height, final float srcX, final float srcY, final float srcWidth, final float srcHeight) {
        final float renderSRCX = srcX / textureWidth;
        final float renderSRCY = srcY / textureHeight;
        final float renderSRCWidth = srcWidth / textureWidth;
        final float renderSRCHeight = srcHeight / textureHeight;
        final boolean tex2D = GL11.glGetBoolean(3553);
        final boolean blend = GL11.glGetBoolean(3042);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.enableTexture2D();
        GL11.glBegin(4);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2f(x + width, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
        if (!tex2D) {
            GlStateManager.disableTexture2D();
        }
        if (!blend) {
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    
    public static void renderTexture(float x, float y, float width, float height) {
        final boolean tex2D = GL11.glGetBoolean(3553);
        final boolean blend = GL11.glGetBoolean(3042);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        x *= 2.0f;
        y *= 2.0f;
        width *= 2.0f;
        height *= 2.0f;
        GL11.glBegin(4);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(x + width, y);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
        if (!tex2D) {
            GlStateManager.disableTexture2D();
        }
        if (!blend) {
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    
    public static void drawTexturedModalRect(final double x, final double y, final int textureX, final int textureY, final double width, final double height) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV(x + 0.0, y + height, RenderUtils.zLevel, (textureX + 0) * var7, (textureY + height) * var8);
        var10.addVertexWithUV(x + width, y + height, RenderUtils.zLevel, (textureX + width) * var7, (textureY + height) * var8);
        var10.addVertexWithUV(x + width, y + 0.0, RenderUtils.zLevel, (textureX + width) * var7, (textureY + 0) * var8);
        var10.addVertexWithUV(x + 0.0, y + 0.0, RenderUtils.zLevel, (textureX + 0) * var7, (textureY + 0) * var8);
        var9.draw();
    }
    
    public static float[] getARGB(final int par1Hex) {
        final float a = (par1Hex >> 24 & 0xFF) / 255.0f;
        final float r = (par1Hex >> 16 & 0xFF) / 255.0f;
        final float g = (par1Hex >> 8 & 0xFF) / 255.0f;
        final float b = (par1Hex & 0xFF) / 255.0f;
        return new float[] { a, r, g, b };
    }
    
    public static float[] getRGBA(final int hex) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        return new float[] { red, green, blue, alpha };
    }
    
    public static void drawSupportBeams(final AxisAlignedBB axisAlignedBB) {
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glEnd();
    }
    
    public static void drawBorderedRect(final double x, final double y, final double x1, final double d, final float l1, final int col1, final int col2) {
        drawRect(x, y, x1, d, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.pushMatrix();
        GlStateManager.color(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x1, d);
        GL11.glVertex2d(x1, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x1, d);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(2848);
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB bb) {
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB axisalignedbb) {
        GL11.glBegin(7);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        GL11.glEnd();
    }
    
    public static void drawLines(final AxisAlignedBB bb) {
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glEnd();
    }
    
    public static void drawBorderedGradientRect(final double x, final double y, final double x2, final double y2, final float lw, final int col1, final int col2, final int col3) {
        drawGradientRect(x, y, x2, y2, col2, col3);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.pushMatrix();
        GlStateManager.color(f2, f3, f4, f);
        GL11.glLineWidth(lw);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(2848);
    }
    
    public static void drawGradientRect(final double x, final double y, final double x1, final double y1, final int startColor, final int endColor) {
        final float var7 = (startColor >> 24 & 0xFF) / 255.0f;
        final float var8 = (startColor >> 16 & 0xFF) / 255.0f;
        final float var9 = (startColor >> 8 & 0xFF) / 255.0f;
        final float var10 = (startColor & 0xFF) / 255.0f;
        final float var11 = (endColor >> 24 & 0xFF) / 255.0f;
        final float var12 = (endColor >> 16 & 0xFF) / 255.0f;
        final float var13 = (endColor >> 8 & 0xFF) / 255.0f;
        final float var14 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        final Tessellator var15 = Tessellator.getInstance();
        final WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.color(var8, var9, var10, var7);
        var16.pos(x1, y, RenderUtils.zLevel);
        var16.pos(x, y, RenderUtils.zLevel);
        var16.color(var12, var13, var14, var11);
        var16.pos(x, y1, RenderUtils.zLevel);
        var16.pos(x1, y1, RenderUtils.zLevel);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void drawBorderedQuadGradientRect(final double x, final double y, final double x1, final double y1, final float lw, final int color1, final int col1, final int col2, final int col3, final int col4) {
        drawQuadGradientRect(x, y, x1, y1, col1, col2, col3, col4);
        final float f = (color1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (color1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (color1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (color1 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.pushMatrix();
        GlStateManager.color(f2, f3, f4, f);
        GL11.glLineWidth(lw);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y);
        GL11.glVertex2d(x, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(2848);
    }
    
    public static void drawQuadGradientRect(final double x, final double y, final double x1, final double y1, final int col1, final int col2, final int col3, final int col4) {
        final float a = (col1 >> 24 & 0xFF) / 255.0f;
        final float a2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float a3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float a4 = (col1 & 0xFF) / 255.0f;
        final float b = (col2 >> 24 & 0xFF) / 255.0f;
        final float b2 = (col2 >> 16 & 0xFF) / 255.0f;
        final float b3 = (col2 >> 8 & 0xFF) / 255.0f;
        final float b4 = (col2 & 0xFF) / 255.0f;
        final float c = (col3 >> 24 & 0xFF) / 255.0f;
        final float c2 = (col3 >> 16 & 0xFF) / 255.0f;
        final float c3 = (col3 >> 8 & 0xFF) / 255.0f;
        final float c4 = (col3 & 0xFF) / 255.0f;
        final float d = (col4 >> 24 & 0xFF) / 255.0f;
        final float d2 = (col4 >> 16 & 0xFF) / 255.0f;
        final float d3 = (col4 >> 8 & 0xFF) / 255.0f;
        final float d4 = (col4 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        final Tessellator tess = Tessellator.getInstance();
        final WorldRenderer wr = tess.getWorldRenderer();
        wr.startDrawingQuads();
        wr.color(a2, a3, a4, a);
        wr.pos(x, y, 0.0);
        wr.color(b2, b3, b4, b);
        wr.pos(x1, y, 0.0);
        wr.color(c2, c3, c4, c);
        wr.pos(x1, y1, 0.0);
        wr.color(d2, d3, d4, d);
        wr.pos(x, y1, 0.0);
        tess.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void drawScaledCustomSizeModalRect(final double x, final double y, final float u, final float v, final int uWidth, final int vHeight, final double width, final double height, final float tileWidth, final float tileHeight) {
        final float var10 = 1.0f / tileWidth;
        final float var11 = 1.0f / tileHeight;
        final Tessellator var12 = Tessellator.getInstance();
        final WorldRenderer var13 = var12.getWorldRenderer();
        var13.startDrawingQuads();
        var13.addVertexWithUV(x, y + height, 0.0, u * var10, (v + vHeight) * var11);
        var13.addVertexWithUV(x + width, y + height, 0.0, (u + uWidth) * var10, (v + vHeight) * var11);
        var13.addVertexWithUV(x + width, y, 0.0, (u + uWidth) * var10, v * var11);
        var13.addVertexWithUV(x, y, 0.0, u * var10, v * var11);
        var12.draw();
    }
    
    public static void startStencil() {
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 255);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1028, 6913);
    }
    
    public static void entityFill() {
        GL11.glStencilFunc(512, 0, 255);
        GL11.glPolygonMode(1028, 6914);
    }
    
    public static void entityLine(final float lineWidth) {
        GL11.glLineWidth(lineWidth);
        GL11.glStencilFunc(514, 1, 255);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1028, 6913);
    }
    
    public static void entityOffset() {
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void stopStencil() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    public static float[] get2Dfrom3D(final double x, final double y, final double z, final boolean behind) {
        RenderUtils.VIEWPORT.clear();
        RenderUtils.MODELVIEW.clear();
        RenderUtils.PROJECTION.clear();
        RenderUtils.OBJECTCOORDS.clear();
        GlStateManager.getFloat(2982, RenderUtils.MODELVIEW);
        GlStateManager.getFloat(2983, RenderUtils.PROJECTION);
        GL11.glGetInteger(2978, RenderUtils.VIEWPORT);
        GLU.gluProject((float)x, (float)y, (float)z, RenderUtils.MODELVIEW, RenderUtils.PROJECTION, RenderUtils.VIEWPORT, RenderUtils.OBJECTCOORDS);
        final float x2 = RenderUtils.OBJECTCOORDS.get(0) / 2.0f;
        final float y2 = RenderUtils.OBJECTCOORDS.get(1) / 2.0f;
        final boolean turn = RenderUtils.OBJECTCOORDS.get(2) > 1.0f;
        if (turn && !behind) {
            return new float[] { -9999.0f, -9999.0f };
        }
        final FloatBuffer fb = BufferUtils.createFloatBuffer(2);
        fb.put(new float[] { x2, y2 });
        return new float[] { turn ? (Minecraft.getMinecraft().displayWidth - fb.get(0)) : fb.get(0), turn ? fb.get(1) : (Minecraft.getMinecraft().displayHeight - fb.get(1)), turn ? -1.0f : 1.0f };
    }
    
    static {
        RenderUtils.renderManager = Minecraft.getMinecraft().getRenderManager();
        VIEWPORT = GLAllocation.createDirectIntBuffer(16);
        MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
        PROJECTION = GLAllocation.createDirectFloatBuffer(16);
        OBJECTCOORDS = GLAllocation.createDirectFloatBuffer(6);
    }
}