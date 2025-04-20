package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.WPotionEffects;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public class NoEffects extends Module
{
	private final Potion[] blockedEffects = new Potion[]{WPotionEffects.HUNGER, WPotionEffects.SLOWNESS, WPotionEffects.MINING_FATIGUE, WPotionEffects.INSTANT_DAMAGE, WPotionEffects.WEAKNESS, WPotionEffects.WITHER, WPotionEffects.POISON};
	
	public NoEffects()
	{
		super("NoEffects", 57, Category.PLAYER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        if (mc.thePlayer.capabilities.isCreativeMode)
        {
            return;
        }
        
        if (!mc.thePlayer.onGround)
        {
            return;
        }
        
        if (!this.hasBadEffect())
        {
            return;
        }
        
        for (int i = 0; i < 3; i++)
        {
        	mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
        }
	}
	
    private boolean hasBadEffect()
    {
        if (mc.thePlayer.getActivePotionEffects().isEmpty())
        {
            return false;
        }
        
        for (Potion effect : this.blockedEffects)
        {
            if (!mc.thePlayer.isPotionActive(effect)) continue;
            return true;
        }
        return false;
    }
}