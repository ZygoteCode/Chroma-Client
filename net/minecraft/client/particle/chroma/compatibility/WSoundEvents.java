package net.minecraft.client.particle.chroma.compatibility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.ResourceLocation;

public final class WSoundEvents
{
    public static boolean isBobberSplash(S29PacketSoundEffect soundEffect)
    {
        return "random.splash".equals(soundEffect.getSound());
    }

    public static void playButtonClick()
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
    }
}