package net.minecraft.client.particle.chroma.utils;

import java.nio.channels.*;
import java.io.*;
import java.net.*;

public class DownloadUtils
{
    public static void downloadAt(final URL url, final File file)
    {
        if (getFileSize(url) == file.length())
        {
            return;
        }
        
        try
        {
            final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            @SuppressWarnings("resource")
			final FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
        }
        catch (Exception e)
        {
        }
    }
    
    public static int getFileSize(final URL url)
    {
        HttpURLConnection conn = null;
        
        try
        {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        }
        catch (IOException e)
        {
            return -1;
        }
        finally
        {
            conn.disconnect();
        }
    }
    
    public static boolean isOnline(final String url)
    {
        try
        {
            final URLConnection connection = new URL(url).openConnection();
            connection.connect();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public static void checkAndDownloadFile(final String link, final File file)
    {
        try
        {
            final URL url = new URL(link);
        }
        catch (Exception ex)
        {
        }
    }
}