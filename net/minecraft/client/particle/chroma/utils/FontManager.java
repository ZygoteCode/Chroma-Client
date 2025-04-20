package net.minecraft.client.particle.chroma.utils;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;

import net.minecraft.client.Minecraft;

public class FontManager
{
    public static UnicodeFontRenderer getFont(String name, float size) {
        UnicodeFontRenderer unicodeFont = null;
        try {
            InputStream inputStream = FontManager.class.getResourceAsStream("chroma/fonts/" + name + ".ttf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
        }
        catch (Exception inputStream) {
        	inputStream.printStackTrace();
            // empty catch block
        }
        return unicodeFont;
    }

    public UnicodeFontRenderer getFont(String name, float size, boolean b) {
        UnicodeFontRenderer unicodeFont = null;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("fonts/" + name + ".otf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            HashMap<Float, UnicodeFontRenderer> map = new HashMap<Float, UnicodeFontRenderer>();
        }
        catch (Exception inputStream) {
            // empty catch block
        }
        return unicodeFont;
    }
}