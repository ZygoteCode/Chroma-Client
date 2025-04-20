package net.minecraft.client.particle.chroma.utils;

import net.minecraft.util.MathHelper;

public class MathUtils
{
    public static int clamp(int num, int min, int max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static float clamp(float num, float min, float max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static double clamp(double num, double min, double max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static float sin(float value)
    {
        return MathHelper.sin(value);
    }

    public static float cos(float value)
    {
        return MathHelper.cos(value);
    }

    public static float wrapDegrees(float value)
    {
        return MathHelper.wrapAngleTo180_float(value);
    }

    public static double wrapDegrees(double value)
    {
        return MathHelper.wrapAngleTo180_double(value);
    }
    
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static int floor(float value) {
        int i = (int)value;
        return value < (float)i ? i - 1 : i;
    }

    public static int floor(double value) {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }
}