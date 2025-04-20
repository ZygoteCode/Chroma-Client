package net.minecraft.client.particle.chroma.utils;

import java.math.*;
import java.io.*;
import javax.xml.bind.*;

public class LoadUtils
{
    public static String encrypt(final String toEncrypt) {
        return toHex(reverse(onePlusChar(toHex(toEncrypt))));
    }
    
    public static String reverse(final String toReverse) {
        return new StringBuilder(toReverse).reverse().toString();
    }
    
    public static String onePlusChar(final String toChar) {
        String strang = "";
        for (int i = 0; i < toChar.length(); ++i) {
            final char c = (char)(toChar.charAt(i) + '\u0001');
            strang += c;
        }
        return strang;
    }
    
    public static String oneLessChar(final String toChar) {
        String strang = "";
        for (int i = 0; i < toChar.length(); ++i) {
            final char c = (char)(toChar.charAt(i) - '\u0001');
            strang += c;
        }
        return strang;
    }
    
    public static String toHex(final String arg) {
        try {
            return String.format("%040x", new BigInteger(1, arg.getBytes("UTF-8")));
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    public static String toString(final String decode) {
        final byte[] bytes = DatatypeConverter.parseHexBinary(decode);
        try {
            return new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    public static String uncrypt(final String toUncrypt) {
        return toString(oneLessChar(reverse(toString(toUncrypt))));
    }
}