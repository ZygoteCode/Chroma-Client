package net.minecraft.client.particle.chroma.utils;

import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.*;
import net.minecraft.client.settings.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;
import java.awt.*;
import java.awt.List;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;

public class Wrapper
{
    /*public static final Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    public static final EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }
    
    public static final WorldClient getWorld() {
        return getMinecraft().theWorld;
    }
    
    public static final Block getBlock(final BlockPos pos) {
        return getWorld().getBlockState(pos).getBlock();
    }
    
    public static final GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }
    
    public static final FontRenderer getFontRenderer() {
        return getMinecraft().fontRendererObj;
    }
    
    public static final Block getBlock(final double x, final double y, final double z) {
        return getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static final ScaledResolution getScreen() {
        return new ScaledResolution(getMinecraft());
    }
    
    public static final void sendPacket(final Packet packet) {
        try {
            getPlayer().sendQueue.addToSendQueue(packet);
        }
        catch (Exception ex) {}
    }
    
    public static final void addChatWithPrefix(final String prefix, final Object message) {
        final IChatComponent chat = new ChatComponentText("[" + prefix + "§r]: ");
        chat.appendText(message.toString()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE));
        getPlayer().addChatMessage(chat);
    }
    
    public static int getScreenWidth() {
        return getScreen().getScaledWidth();
    }
    
    public static int getScreenHeight() {
        return getScreen().getScaledHeight();
    }
    
    public static int drawStringWithShadow(final String string, final float x, final float y, final int color) {
        getFontRenderer().drawString(StringUtils.stripControlCodes(string), x + 0.7f, y + 0.7f, 0, false);
        return getFontRenderer().drawString(string, x, y, color, false);
    }
    
    public static float[] getRainbow(final int ticks) {
        if (ticks == -1) {
            return RenderUtils.getRGBA(new Color(0, 0, 0, 1).getRGB());
        }
        final int frame = ticks / 255 + 1;
        final int r = (frame == 3 || frame == 4) ? 0 : ((frame == 5) ? (ticks % 255) : ((frame == 6 || frame == 1) ? 255 : (255 - ticks % 255)));
        final int g = (frame == 1 || frame == 2) ? 0 : ((frame == 3) ? (ticks % 255) : ((frame == 4 || frame == 5) ? 255 : (255 - ticks % 255)));
        final int b = (frame == 5 || frame == 6) ? 0 : ((frame == 1) ? (ticks % 255) : ((frame == 2 || frame == 3) ? 255 : (255 - ticks % 255)));
        final Color color = new Color(r, g, b);
        final float[] f = RenderUtils.getRGBA(color.getRGB());
        return f;
    }
    
    public static int getRainbowInt(final int ticks) { //ti do il mio, è il piu semplice ed ottimizzato che si possa fare
        if (ticks == -1) {
            return new Color(0, 0, 0).getRGB();
        }
        final int frame = ticks / 255 + 1;
        final int r = (frame == 3 || frame == 4) ? 0 : ((frame == 5) ? (ticks % 255) : ((frame == 6 || frame == 1) ? 255 : (255 - ticks % 255)));
        final int g = (frame == 1 || frame == 2) ? 0 : ((frame == 3) ? (ticks % 255) : ((frame == 4 || frame == 5) ? 255 : (255 - ticks % 255)));
        final int b = (frame == 5 || frame == 6) ? 0 : ((frame == 1) ? (ticks % 255) : ((frame == 2 || frame == 3) ? 255 : (255 - ticks % 255)));
        final Color color = new Color(r, g, b);
        return color.getRGB();
    }
    
    public static float[] getRainbow() {
        return getRainbow(getPlayer().ticksExisted * 10 % 1530);
    }
    
    public static int getRainbowInt() {
        return getRainbowInt(getPlayer().ticksExisted * 10 % 1530);
    }
    
    public static double randDouble(final double min, final double max) {
        final Random r = new Random();
        final double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;
    }
    
    public int getPlayerPing(final String name) {
        final EntityPlayer player = getWorld().getPlayerEntityByName(name);
        if (player instanceof EntityOtherPlayerMP) {
            return getMinecraft().getNetHandler().getPlayerInfo(player.getUniqueID()).getResponseTime();
        }
        return 0;
    }*/
    
    public static int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() + offset) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, 0.5F, 1.0F).getRGB();
    }
}