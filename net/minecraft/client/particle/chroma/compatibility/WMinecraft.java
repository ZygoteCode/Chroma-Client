package net.minecraft.client.particle.chroma.compatibility;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;

public final class WMinecraft {
    public static final String VERSION = "1.8.8";
    public static final String DISPLAY_VERSION = "1.8.8";
    public static final boolean OPTIFINE = true;
    public static final boolean REALMS = false;
    public static final boolean COOLDOWN = false;
    public static final NavigableMap<Integer, String> PROTOCOLS;
    private static final Minecraft mc;

    static {
        TreeMap<Integer, String> protocols = new TreeMap<Integer, String>();
        protocols.put(47, "1.8.8");
        PROTOCOLS = Collections.unmodifiableNavigableMap(protocols);
        mc = Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer() {
        return WMinecraft.mc.thePlayer;
    }

    public static WorldClient getWorld() {
        return WMinecraft.mc.theWorld;
    }

    public static NetHandlerPlayClient getConnection() {
        return WMinecraft.getPlayer().sendQueue;
    }

    public static boolean isRunningOnMac() {
        return Minecraft.getMinecraft().isRunningOnMac;
    }
}