package net.minecraft.client.particle.chroma.utils;

import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class NetworkUtils
{
    public static void downloadAt(final String link, final File file) throws Exception {
        final URL url = new URL(link);
        final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        final FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
        fos.close();
        System.out.println("Finished downloading as '" + file.getName() + "'");
    }
    
    public static int getFileSize(final String link) throws Exception {
        final URL url = new URL(link);
        final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("HEAD");
        conn.getInputStream();
        final int lenght = conn.getContentLength();
        conn.disconnect();
        return lenght;
    }
    
    public static boolean isOnline(final String url) {
        try {
            final URLConnection connection = new URL(url).openConnection();
            connection.connect();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static void checkAndDownloadFile(final String link, final File file) throws Exception {
        if (getFileSize(link) != file.length()) {
            downloadAt(link, file);
        }
        else {
            System.out.println("Already downloaded!");
        }
    }
    
    public static List<String> readFileLinesFull(final String link) throws Exception {
        final List<String> lines = new ArrayList<String>();
        final URL url = new URL(link);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}