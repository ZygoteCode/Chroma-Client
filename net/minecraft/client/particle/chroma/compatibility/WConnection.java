package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.network.Packet;

public final class WConnection {
    public static void sendPacket(Packet packet) {
        WMinecraft.getConnection().addToSendQueue(packet);
    }

    public static void sendPacketBypass(Packet packet) {
        WMinecraft.getConnection().addToSendQueueBypass(packet);
    }
}

