package net.minecraft.client.particle.chroma.utils;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class FontLoader
{
	private static Font ttfBase = null;
	private static Font telegraficoFont = null;
	private static InputStream myStream = null;
	private static String FONT_PATH_TELEGRAFICO = "chroma/fonts/metal.ttf";
	
	public static Font createFont(String name)
	{
		FONT_PATH_TELEGRAFICO = "chroma/fonts/" + name + ".ttf";
        try {
            myStream = new BufferedInputStream(
                    new FileInputStream(FONT_PATH_TELEGRAFICO));
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            telegraficoFont = ttfBase.deriveFont(Font.PLAIN, 24);               
        } catch (Exception ex) {
        	return new Font(name, 0, 0);
        }
        return telegraficoFont;
	}
}