package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.*;
import com.mojang.authlib.yggdrasil.*;
import java.util.*;
import com.mojang.authlib.*;
import java.net.*;
import java.io.*;

public class LoginUtils extends Thread
{
    public static Session authenticate(final String username, String password) {
        try {
            if (password == null) {
                password = "";
            }
            if (!username.equals("")) {
                if (!password.equals("")) {
                    final YggdrasilAuthenticationService yService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
                    final UserAuthentication userAuth = yService.createUserAuthentication(Agent.MINECRAFT);
                    userAuth.setUsername(username);
                    userAuth.setPassword(password);
                    try {
                        userAuth.logIn();
                        final Session session = new Session(userAuth.getSelectedProfile().getName(), userAuth.getSelectedProfile().getId().toString(), userAuth.getAuthenticatedToken(), username.contains("@") ? "mojang" : "legacy");
                        Minecraft.getMinecraft().session = session;
                        return session;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                return new Session(username, username, "0", "legacy");
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    public static boolean isMinecraftAuthOnline() {
        return DownloadUtils.isOnline("https://authserver.mojang.com/authenticate");
    }
    
    public static String checkAccount(final String name, final String password) {
        if (name == null || password == null || name.length() == 0 || password.length() == 0) {
            return "Login Failed";
        }
        try {
            return auth("{\"agent\": { \"name\": \"Minecraft\", \"version\": 1 }, \"username\": \"" + name + "\", \"password\": \"" + password + "\"}");
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Login Failed";
        }
    }
    
    private static String auth(final String data) throws Exception {
        final URL url = new URL("https://authserver.mojang.com/authenticate");
        final byte[] contentBytes = data.getBytes("UTF-8");
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", Integer.toString(contentBytes.length));
        final OutputStream requestStream = connection.getOutputStream();
        requestStream.write(contentBytes, 0, contentBytes.length);
        requestStream.close();
        final BufferedReader responseStream = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        final String response = responseStream.readLine();
        responseStream.close();
        if (connection.getResponseCode() == 200) {
            final String s = response.split("availableProfiles")[1].split("name")[1];
            return "Logged in as " + s.substring(3, s.length() - 4);
        }
        return "Login Failed!";
    }
}