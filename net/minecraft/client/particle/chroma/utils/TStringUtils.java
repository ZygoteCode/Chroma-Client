package net.minecraft.client.particle.chroma.utils;

import java.util.*;

public class TStringUtils
{
    public String getAfter(final String text, final String splitter, final int index) {
        final String[] words = text.split(splitter);
        if (words.length < index) {
            return null;
        }
        int splitIndex = 0;
        for (int i = 0; i < index; ++i) {
            splitIndex += words[i].length() + 1;
        }
        return text.substring(splitIndex);
    }
    
    public String getBefore(final String text, final String splitter, final int index) {
        final String[] words = text.split(splitter);
        if (words.length < index) {
            return null;
        }
        int splitIndex = 0;
        for (int i = 0; i < index; ++i) {
            splitIndex += words[i].length() + 1;
        }
        return text.substring(0, splitIndex);
    }
    
    public static boolean isInteger(final String text) {
        try {
            Integer.parseInt(text);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static boolean isDouble(final String text) {
        try {
            Double.parseDouble(text);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static boolean isFloat(final String text) {
        try {
            Float.parseFloat(text);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static boolean isBoolean(final String text) {
        try {
            Boolean.parseBoolean(text);
            if (text.toLowerCase().contains("true") || text.toLowerCase().contains("false")) {
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static boolean isArraylist(final String text) {
        return text.startsWith("[") && text.endsWith("]");
    }
    
    public static ArrayList getArraylist(final String text) {
        final ArrayList arr = new ArrayList();
        if (isArraylist(text)) {
            final String items = text.substring(1, text.length() - 1);
            final String[] split;
            final String[] items_arr = split = items.split(",");
            for (final String s : split) {
                arr.add(s.trim());
            }
            return arr;
        }
        return null;
    }
    
    public static Object get(final String text) {
        if (isInteger(text)) {
            return Integer.parseInt(text);
        }
        if (isFloat(text)) {
            return Float.parseFloat(text);
        }
        if (isBoolean(text)) {
            return Boolean.parseBoolean(text);
        }
        return text;
    }
}