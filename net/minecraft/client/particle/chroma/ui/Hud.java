package net.minecraft.client.particle.chroma.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.ui.clickgui.ColorUtil;
import net.minecraft.client.particle.chroma.utils.CustomFontRenderer;
import net.minecraft.client.particle.chroma.utils.FontLoader;
import net.minecraft.client.particle.chroma.utils.FontManager;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.client.particle.chroma.utils.UnicodeFontRenderer;
import net.minecraft.client.particle.chroma.utils.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Hud extends GuiIngame
{
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static final Path MAIN = Minecraft.getMinecraft().mcDataDir.toPath().resolve("chroma");
	public static CustomFontRenderer moduleDisplayer = new CustomFontRenderer(new Font("Verdana", 0, 30), true);
	public static UnicodeFontRenderer logoDisplayer = new UnicodeFontRenderer(FontLoader.createFont("metal").deriveFont(42));
	public static CustomFontRenderer stringDisplayer = new CustomFontRenderer(new Font("Comic Sans MS", 0, 30), true);

	public Hud(Minecraft mcIn)
	{
		super(mcIn);
	}

	@Override
	public void renderGameOverlay(float partialTicks)
	{
		super.renderGameOverlay(partialTicks);

		if (!mc.getChroma().isGhostMode())
		{
			boolean canRenderLogo = true;
			boolean canRenderArrayList = true;
			boolean canRenderTabGui = true;
			
			if (mc.getChroma().getModuleManager().getModuleByID(38).isToggled())
			{
				canRenderLogo = mc.getChroma().getSetManager().getSettingById(120).getValBoolean();
				canRenderArrayList = mc.getChroma().getSetManager().getSettingById(122).getValBoolean();
				canRenderTabGui = mc.getChroma().getSetManager().getSettingById(124).getValBoolean();
			}
			
			if (canRenderLogo)
			{
				renderLogo();
			}
			
			if (canRenderArrayList)
			{
				renderArrayList();
			}
		
			if (mc.getChroma().getModuleManager().getModuleByID(3).isToggled())
			{
				renderPotions();
			}
			
			if (mc.getChroma().getModuleManager().getModuleByID(2).isToggled())
			{
				renderArmorHUD();
			}
			
			if (canRenderTabGui)
			{
				TabGui.drawTabGui();
			}
		}
	}

	public void renderLogo()
	{
		boolean lol = false;
		
		if (mc.getChroma().getModuleManager().getModuleByID(38).isToggled())
		{
			lol = mc.getChroma().getSetManager().getSettingById(121).getValBoolean();
		}
		
		if (!lol)
		{
			//logoDisplayer.drawStringWithShadow((mc.getChroma().getSetManager().getSettingById(6).getValBoolean() ? "§l" : "") + mc.getChroma().getClientName() + " " + mc.getChroma().getClientVersion(), 2, 33, (mc.getChroma().getSetManager().getSettingById(9).getValBoolean() ? Wrapper.getRainbow(6000, -15) : Color.CYAN.darker().darker().getRGB()));
			//stringDisplayer.drawStringWithShadow("FPS: " + mc.getDebugFPS(), 70, 12, (mc.getChroma().getSetManager().getSettingById(9).getValBoolean() ? Wrapper.getRainbow(6000, -15) : Color.CYAN.darker().darker().getRGB()));
			//stringDisplayer.drawStringWithShadow("Username: " + mc.session.username, 70, 20, (mc.getChroma().getSetManager().getSettingById(9).getValBoolean() ? Wrapper.getRainbow(6000, -15) : Color.CYAN.darker().darker().getRGB()));
			if (mc.getChroma().getSetManager().getSettingById(266).getValBoolean())
			{
				logoDisplayer.drawStringWithShadow((mc.getChroma().getSetManager().getSettingById(6).getValBoolean() ? "§l" : "") + mc.getChroma().getClientVersion(), 1925, 1085, (mc.getChroma().getSetManager().getSettingById(9).getValBoolean() ? Wrapper.getRainbow(6000, -15) : Color.WHITE.getRGB()));
			}
			else
			{
				logoDisplayer.drawStringWithShadow((mc.getChroma().getSetManager().getSettingById(6).getValBoolean() ? "§l" : "") + mc.getChroma().getClientName() + " " + mc.getChroma().getClientVersion(), 2, 3, (mc.getChroma().getSetManager().getSettingById(9).getValBoolean() ? Wrapper.getRainbow(6000, -15) : Color.CYAN.darker().darker().getRGB()));
			}
		}
		else
		{
			if (mc.getChroma().getSetManager().getSettingById(266).getValBoolean())
			{
				mc.fontRendererObj.drawStringWithShadow((mc.getChroma().getSetManager().getSettingById(6).getValBoolean() ? "§l" : "") + mc.getChroma().getClientName() + " " + mc.getChroma().getClientVersion(), 5, 6, (mc.getChroma().getSetManager().getSettingById(9).getValBoolean() ? Wrapper.getRainbow(6000, -15) : Color.CYAN.darker().darker().getRGB()));
			}
		}
		
		if (mc.getChroma().getSetManager().getSettingById(266).getValBoolean())
		{
			try
			{
				Image img = new Image(Minecraft.getMinecraft().mcDataDir.toPath().resolve("chroma").toString() + "\\watermark.png");
				img.draw(2, 1, 32, 32);
			}
			catch (SlickException e)
			{
				
			}
		}	
	}

	public void renderPotions()
	{
		if (!Chroma.getModuleManager().getModuleByID(3).isToggled())
		{
			return;
		}
		
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();
		final int xPos = width - 14;
		int count = 0;
		GlStateManager.disableCull();
		final int ticksticks = (int) System.currentTimeMillis() / 50 % 360;

		for (final PotionEffect pe : mc.thePlayer.getActivePotionEffects())
		{
			GlStateManager.pushMatrix();
			final Potion pot = pe.getPotion();
			final float[] c = RenderUtils.getRGBA(pot.getLiquidColor());
			final Color cc = new Color(c[0], c[1], c[2], (pe.getDuration() < 100) ? (mc.thePlayer.ticksExisted % 10.0f / 10.0f) : 1.0f);
			final Color ccc = new Color(c[0], c[1], c[2], (pe.getDuration() < 100) ? (mc.thePlayer.ticksExisted % 10.0f / 10.0f) : 0.5f);
			GlStateManager.pushMatrix();
			GlStateManager.translate(xPos - count * 24 + 5.5, height - 8.5, 0.0);
			GlStateManager.translate(-(xPos - count * 24 + 5.5), -(height - 8.5), 0.0);
			GL11.glLineWidth(2.0f);
			RenderUtils.drawBorderedCircle(xPos - count * 24, height - 14, 11.0, ccc.getRGB());
			GL11.glLineWidth(4.0f);
			RenderUtils.drawCircle(xPos - count * 24, height - 14, 180, ticksticks * 15, 11.0, cc.brighter().getRGB());
			GL11.glLineWidth(1.0f);
			RenderUtils.drawCircle(xPos - count * 24, height - 14, 180, ticksticks * 15, 12.0, cc.darker().getRGB());
			
			if (pot.hasStatusIcon())
			{
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();
				GlStateManager.color(1.0f, 1.0f, 1.0f,
						(pe.getDuration() < 100) ? (mc.thePlayer.ticksExisted % 10.0f / 10.0f) : 1.0f);
				GlStateManager.enableBlend();
				mc.getTextureManager().bindTexture(GuiContainer.inventoryBackground);
				int k = width - count * 24 - 1;
				final int l = height - 26;
				final int i1 = pot.getStatusIconIndex();
				int j = 0;
				final int m = 0;
				++j;
				k -= 25 * j;
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				RenderUtils.drawTexturedModalRect(k + 3, l + 3, i1 % 8 * 18, 198 + i1 / 8 * 18, 18.0, 18.0);
				GlStateManager.popMatrix();
				final String timer = Potion.getDurationString(pe);
				moduleDisplayer.drawCenteredStringWithShadow(timer, xPos - count * 24, height - 16, -1);
			}
			else
			{
				GlStateManager.popMatrix();
			}
			
			GlStateManager.popMatrix();
			++count;
		}
	}

	public void renderArmorHUD()
	{
		if (!Chroma.getModuleManager().getModuleByID(2).isToggled())
		{
			return;
		}
		
		try
		{
			if (!mc.thePlayer.capabilities.isCreativeMode)
			{
				for (byte b = 0; b < 5; ++b)
				{
					final ItemStack stack = (b == 4) ? mc.thePlayer.getCurrentEquippedItem() : mc.thePlayer.inventory.armorInventory[b];
					
					if (stack != null)
					{
						ScaledResolution scaledresolution = new ScaledResolution(this.mc);
						int width = scaledresolution.getScaledWidth();
						int height = scaledresolution.getScaledHeight();
						final int prot = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
						final int unb = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
						final int sharp = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
						final boolean xp = mc.thePlayer.experienceLevel == 0;
						final int ayy = (b == 4) ? (width / 2 - 8) : ((b < 2) ? (width / 2 + 95) : (width / 2 - 110));
						final int nay = (b == 4) ? (mc.displayHeight - (xp ? 46 : 52)) : (height - (b % 2 + 1) * 16 - 2);
						GlStateManager.disableDepth();
						GlStateManager.disableTexture2D();
						GlStateManager.enableTexture2D();
						GlStateManager.enableDepth();
						RenderHelper.enableStandardItemLighting();
						RenderHelper.enableGUIStandardItemLighting();
						mc.getRenderItem().renderItemAndEffectIntoGUI(stack, ayy, nay);
						mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, ayy, nay);
						GlStateManager.disableDepth();
						GlStateManager.disableLighting();
						GlStateManager.enableDepth();
					}
				}
			}
		}
		catch (Exception ex)
		{
			
		}
	}

	public void renderArrayList()
	{
		boolean lol = false;
		
		if (mc.getChroma().getModuleManager().getModuleByID(38).isToggled())
		{
			lol = mc.getChroma().getSetManager().getSettingById(123).getValBoolean();
		}
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int ycount = 1;
		ArrayList<Module> actmods = mc.getChroma().getModuleManager().sortModules(mc.getChroma().getModuleManager().getToggledModules(), true, true, moduleDisplayer);
		
		int index = 0;
		
		for (Module m : actmods)
		{
			if (!(m.getCategory() == Category.GUI || m.getCategory() == Category.NONE))
			{
				if (!lol)
				{
					index++;
					
					if (mc.getChroma().getSetManager().getSettingById(350).getValBoolean())
					{
						Gui.drawRect((sr.getScaledWidth() - moduleDisplayer.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 7 : 5)) - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 6 : 0), ycount, sr.getScaledWidth(), ycount + 10, 0x80000000);
					}
					
					Color temp = ColorUtil.getClickGUIColor().darker();
					int outlineColor = mc.getChroma().getSetManager().getSettingById(8).getValBoolean() ? Wrapper.getRainbow(6000 , ycount * -15) : new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
					moduleDisplayer.drawStringWithShadow((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ", sr.getScaledWidth_double() - moduleDisplayer.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 4 : 2) - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 5 : 0), ycount + 2, (mc.getChroma().getSetManager().getSettingById(8).getValBoolean() ? Wrapper.getRainbow(6000, ycount * -15) : (mc.getChroma().getSetManager().getSettingById(12).getValBoolean() ? m.getColor().getRGB() : 0xffffff)));
					double x = (sr.getScaledWidth_double() - moduleDisplayer.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 4 : 2)) - 3 - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 6 : 0);
					double y = ycount;
					double height = 10;
					
					if (mc.getChroma().getSetManager().getSettingById(311).getValBoolean())
					{
						Gui.drawRect((int) x - 2, (int) y, (int) x, (int) y + (int) height, outlineColor);
					}
					
					int some = + moduleDisplayer.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") + 5 + (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 8 : 0);
					
					if (mc.getChroma().getSetManager().getSettingById(312).getValBoolean())
					{
						Gui.drawRect((int) x + some, (int) y, (int) x + some - 2, (int) y + (int) height, outlineColor);
					}
					
					ycount += 10;
				}
				else
				{
					index++;
					Gui.drawRect((sr.getScaledWidth() - mc.fontRendererObj.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 7 : 5)), ycount, sr.getScaledWidth(), ycount + 10, 0x80000000);
					Color temp = ColorUtil.getClickGUIColor().darker();
					int outlineColor = mc.getChroma().getSetManager().getSettingById(8).getValBoolean() ? Wrapper.getRainbow(6000 , ycount * -15) : new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
					mc.fontRendererObj.drawStringWithShadow((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ", ((float) sr.getScaledWidth_double() - mc.fontRendererObj.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 4 : 2)), ycount + 2, (mc.getChroma().getSetManager().getSettingById(8).getValBoolean() ? Wrapper.getRainbow(6000, ycount * -15) : (mc.getChroma().getSetManager().getSettingById(12).getValBoolean() ? m.getColor().getRGB() : 0xffffff)));
					double x = (sr.getScaledWidth_double() - mc.fontRendererObj.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") - (mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? 4 : 2)) - 3;
					double y = ycount;
					double height = 10;
					
					if (mc.getChroma().getSetManager().getSettingById(311).getValBoolean())
					{
						Gui.drawRect((int) x - 2, (int) y, (int) x, (int) y + (int) height, outlineColor);
					}
					
					int some = + moduleDisplayer.getStringWidth((mc.getChroma().getSetManager().getSettingById(5).getValBoolean() ? "§l" : "") + m.getName() + m.getSuffix() + " ") + 5;
					
					if (mc.getChroma().getSetManager().getSettingById(312).getValBoolean())
					{
						Gui.drawRect((int) x + some, (int) y, (int) x + some - 2, (int) y + (int) height, outlineColor);
					}
					ycount += 10;
				}
			}
		}
	}
}