package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import java.awt.image.*;
import java.awt.*;

public final class CustomFontRenderer
{
    private static final short IMAGE_WIDTH = 1024;
    private static final short IMAGE_HEIGHT = 1024;
    private static final byte DEFAULT_CHAR_WIDTH = 9;
    private static final byte DEFAULT_CHAR_HEIGHT = 9;
    private final int[] colorCode;
    private final String colorcodeIdentifiers = "0123456789abcdefklmnorupi";
    private final int texID;
    private final Font font;
    private final CharData[] boldChars;
    private final CharData[] italicChars;
    private final CharData[] boldItalicChars;
    private final CharData[] charData;
    private float fontHeight;
    private int kerning;
    private int boldTexID;
    private int italicTexID;
    private int boldItalicTexID;
    
    public CustomFontRenderer(final Font font, final boolean fractionalMetrics) {
        this.colorCode = new int[32];
        this.boldChars = new CharData[256];
        this.italicChars = new CharData[256];
        this.boldItalicChars = new CharData[256];
        this.charData = new CharData[256];
        this.fontHeight = -1.0f;
        this.kerning = 1;
        this.font = font;
        this.texID = this.setupTexture(font, fractionalMetrics, this.charData);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs(fractionalMetrics);
    }
    
    public double drawStringWithShadow(final String text, final double x, final double y, final int color) {
        this.drawString(text, x + 0.5, y + 0.5, color, true);
        return this.drawString(text, x, y, color, false);
    }
    
    public double drawString(final String text, final double x, final double y, final int color) {
        return this.drawString(text, x, y, color, false);
    }
    
    public double drawCenteredStringWithShadow(final String text, final double x, final double y, final int color) {
        return this.drawStringWithShadow(text, x - this.getStringWidth(text) / 2, y, color);
    }
    
    public double drawCenteredString(final String text, final double x, final double y, final int color) {
        return this.drawString(text, x - this.getStringWidth(text) / 2, y, color);
    }
    
    public double drawCenteredStringNoShadow(final String text, final double x, final double y, final int color) {
        return this.drawString(text, x - this.getStringWidth(text) / 2, y, color);
    }
    
    public double drawString(final String text, double x, double y, int color, final boolean shadow) {
        if (text == null) {
            return 0.0;
        }
        if (color == 553648127) {
            color = 16777215;
        }
        if ((color & 0xFC000000) == 0x0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            final float[] c = RenderUtils.getRGBA(color);
            final Color shadowz = new Color(c[0] / 5.0f, c[1] / 5.0f, c[2] / 5.0f, c[3]);
            color = shadowz.getRGB();
        }
        CharData[] curData = this.charData;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        boolean randomCase = false;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        final int size = text.length();
        x *= 4.0;
        y = (y - 3.0) * 4.0;
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.25, 0.25, 0.25);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.texID);
        for (int i = 0; i < size; ++i) {
            char ch = text.charAt(i);
            if (ch == '§' && i < size) {
                int colorIndex = "0123456789abcdefklmnorupi".indexOf(text.charAt(i + 1));
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(this.texID);
                    curData = this.charData;
                    if (colorIndex < 0 || colorIndex > 15) {
                        colorIndex = 15;
                    }
                    final int colorcode = this.colorCode[colorIndex];
                    if (shadow) {
                        GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0f / 8.0f, (colorcode >> 8 & 0xFF) / 255.0f / 8.0f, (colorcode & 0xFF) / 255.0f / 8.0f, alpha);
                    }
                    else {
                        GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0f, (colorcode >> 8 & 0xFF) / 255.0f, (colorcode & 0xFF) / 255.0f, alpha);
                    }
                }
                else if (colorIndex == 22) {
                    GlStateManager.color(0.5f, 0.9f, 0.2f, alpha);
                }
                else if (colorIndex == 23) {
                    GlStateManager.color(0.61f, 0.167f, 0.255f, alpha);
                }
                else if (colorIndex == 24) {
                    GlStateManager.color(0.47f, 0.67f, 2.55f, alpha);
                }
                else if (colorIndex == 16) {
                    randomCase = true;
                }
                else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        GlStateManager.bindTexture(this.boldItalicTexID);
                        curData = this.boldItalicChars;
                    }
                    else {
                        GlStateManager.bindTexture(this.boldTexID);
                        curData = this.boldChars;
                    }
                }
                else if (colorIndex == 18) {
                    strikethrough = true;
                }
                else if (colorIndex == 19) {
                    underline = true;
                }
                else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        GlStateManager.bindTexture(this.boldItalicTexID);
                        curData = this.boldItalicChars;
                    }
                    else {
                        GlStateManager.bindTexture(this.italicTexID);
                        curData = this.italicChars;
                    }
                }
                else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    randomCase = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.color((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, alpha);
                    GlStateManager.bindTexture(this.texID);
                    curData = this.charData;
                }
                ++i;
            }
            else if (ch < curData.length && ch >= '\0') {
                if (randomCase) {
                    char newChar;
                    for (newChar = '\0'; curData[newChar].width != curData[ch].width; newChar = (char)(Math.random() * 256.0)) {}
                    ch = newChar;
                }
                GL11.glBegin(4);
                this.drawQuad(x, (ch == '[' || ch == ']') ? (y - 1.0) : y, curData[ch].width, curData[ch].height, curData[ch].storedX, curData[ch].storedY, curData[ch].width, curData[ch].height);
                GL11.glEnd();
                if (strikethrough) {
                    RenderUtils.drawLine(x, y + curData[ch].height / 2.0f, x + curData[ch].width + this.kerning, y + curData[ch].height / 2.0f, 1.0f);
                }
                if (underline) {
                    RenderUtils.drawLine(x, y + curData[ch].height - 2.0, x + curData[ch].width + this.kerning, y + curData[ch].height - 2.0, 1.0f);
                }
                x += curData[ch].width + this.kerning;
            }
        }
        GL11.glHint(3155, 4352);
        GlStateManager.popMatrix();
        return x / 4.0;
    }
    
    public int getStringWidth(String text) {
        text = StringUtils.stripControlCodes(text);
        if (text == null) {
            return 0;
        }
        int width = 0;
        for (int size = text.length(), i = 0; i < size; ++i) {
            final char ch = text.charAt(i);
            width += this.getCharWidth(ch);
        }
        return width / 4;
    }
    
    private int getCharWidth(final char ch) {
        int width = 0;
        CharData[] curData = this.charData;
        boolean bold = false;
        boolean italic = false;
        if (ch == '§') {
            final int colorIndex = "0123456789abcdefklmnorupi".indexOf(ch);
            if (colorIndex < 16) {
                bold = false;
                italic = false;
            }
            else if (colorIndex == 17) {
                bold = true;
                if (italic) {
                    curData = this.boldItalicChars;
                }
                else {
                    curData = this.boldChars;
                }
            }
            else if (colorIndex == 20) {
                italic = true;
                if (bold) {
                    curData = this.boldItalicChars;
                }
                else {
                    curData = this.italicChars;
                }
            }
            else if (colorIndex == 21) {
                bold = false;
                italic = false;
                curData = this.charData;
            }
        }
        else if (ch < curData.length && ch >= '\0') {
            width = curData[ch].width + this.kerning;
        }
        return width;
    }
    
    public int getStringHeight(final String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        for (int size = text.length(), i = 0; i < size; ++i) {
            final char ch = text.charAt(i);
            width += this.getCharWidth(ch);
        }
        return width / 4;
    }
    
    public String trimStringToWidth(final String text, final int maxWidth, final boolean par3) {
        final StringBuilder var4 = new StringBuilder();
        int curWidth = 0;
        final int var5 = par3 ? (text.length() - 1) : 0;
        final int var6 = par3 ? -1 : 1;
        boolean var7 = false;
        boolean var8 = false;
        for (int var9 = var5; var9 >= 0 && var9 < text.length() && curWidth / 4 < maxWidth; var9 += var6) {
            final char ch = text.charAt(var9);
            final int chWidth = this.getCharWidth(ch);
            if (var7) {
                var7 = false;
                if (ch != 'l' && ch != 'L') {
                    if (ch == 'r' || ch == 'R') {
                        var8 = false;
                    }
                }
                else {
                    var8 = true;
                }
            }
            else if (chWidth < 0) {
                var7 = true;
            }
            else {
                curWidth += chWidth;
                if (var8) {
                    ++curWidth;
                }
            }
            if (curWidth / 4 > maxWidth) {
                break;
            }
            if (par3) {
                var4.insert(0, ch);
            }
            else {
                var4.append(ch);
            }
        }
        return var4.toString();
    }
    
    private void setupMinecraftColorcodes() {
        for (byte index = 0; index < 32; ++index) {
            final int shadow = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + shadow;
            int green = (index >> 1 & 0x1) * 170 + shadow;
            int blue = (index >> 0 & 0x1) * 170 + shadow;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red = 0;
                green = 0;
                blue = 0;
            }
            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
    }
    
    private void setupBoldItalicIDs(final boolean fractionalMetrics) {
        GL11.glDeleteTextures(this.boldTexID);
        GL11.glDeleteTextures(this.italicTexID);
        GL11.glDeleteTextures(this.boldItalicTexID);
        this.boldTexID = this.setupTexture(this.font.deriveFont(1), fractionalMetrics, this.boldChars);
        this.italicTexID = this.setupTexture(this.font.deriveFont(2), fractionalMetrics, this.italicChars);
        this.boldItalicTexID = this.setupTexture(this.font.deriveFont(3), fractionalMetrics, this.boldItalicChars);
    }
    
    private int setupTexture(final Font font, final boolean fractionalMetrics, final CharData[] chars) {
        final BufferedImage img = this.generateFontImage(font, fractionalMetrics, chars);
        try {
            return RenderUtils.applyTexture(RenderUtils.genTexture(), img, true, true);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    private BufferedImage generateFontImage(final Font font, final boolean fractionalMetrics, final CharData[] chars) {
        final BufferedImage bufferedImage = new BufferedImage(1024, 1024, 2);
        final Graphics2D g = (Graphics2D)bufferedImage.getGraphics();
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, 1024, 1024);
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final FontMetrics fontMetrics = g.getFontMetrics();
        int chHeight = 0;
        int positionX = 0;
        int positionY = 0;
        for (int i = 0; i < chars.length; ++i) {
            final char ch = (char)i;
            final CharData chData = new CharData();
            final float height = fontMetrics.getHeight() / ((ch == 'i') ? 1.2f : 1.0f);
            final int width = fontMetrics.charWidth(ch);
            chData.width = width;
            chData.height = height;
            if (positionX + chData.width >= 1024) {
                positionX = 0;
                positionY += chHeight;
                chHeight = 0;
            }
            if (chData.height > chHeight) {
                chHeight = (int)chData.height;
            }
            chData.storedX = positionX;
            chData.storedY = positionY;
            if (chData.height / 4.0f > this.fontHeight) {
                this.fontHeight = chData.height / 4.0f;
            }
            chars[i] = chData;
            g.drawString(String.valueOf(ch), positionX, positionY + fontMetrics.getAscent());
            positionX += chData.width + 1;
        }
        return bufferedImage;
    }
    
    private void drawQuad(final double x, final double d, final double width, final double height, final float srcX, final float srcY, final float srcWidth, final float srcHeight) {
        final float renderSRCX = srcX / 1024.0f;
        final float renderSRCY = srcY / 1024.0f;
        final float renderSRCWidth = srcWidth / 1024.0f;
        final float renderSRCHeight = srcHeight / 1024.0f;
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, d);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2d(x, d);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, d + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, d + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x + width, d + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, d);
    }
    
    public int getHeight() {
        return (int)this.fontHeight;
    }
    
    private final class CharData
    {
        private float height;
        private int width;
        private int storedX;
        private int storedY;
    }
}