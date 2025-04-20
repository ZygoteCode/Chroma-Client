package net.minecraft.client.particle.chroma.module.combat;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventAttack;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.entity.EntityLivingBase;

public class SuperKnockback extends Module
{
	public SuperKnockback()
	{
		super("SuperKnockback", 76, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Vanilla");
		modes.add("NCP");
		modes.add("AAC");
		modes.add("Global");
		this.getSetManager().rSetting(new Setting(250, "Mode", "", this, "Vanilla", modes));
		this.getSetManager().rSetting(new Setting(310, "Hurt Time", "", this, 10, 0, 10, true));
		super.setup();
	}
	
	@EventTarget
	public void onAttackEntity(EventAttack e)
	{
		if (((EntityLivingBase) e.getEntity()).hurtTime > this.getSetManager().getSettingById(310).getValueI())
		{
			return;
		}
		
		if (e.getType() == 0 && e.getState() == EventState.PRE)
		{
			doSuperKnockback();
		}
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
	}
	
	public static void doSuperKnockback()
	{
		if (!mc.getChroma().getModuleManager().getModuleByID(76).isToggled())
		{
			return;
		}
		
		if (mc.getChroma().getSetManager().getSettingById(250).getValString().equals("Vanilla"))
		{
			for (int i = 0; i < 50; i++)
			{
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
			}
		}
		else if (mc.getChroma().getSetManager().getSettingById(250).getValString().equals("NCP"))
		{
			for (int i = 0; i < 3; i++)
			{
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
			}
		}
		else if (mc.getChroma().getSetManager().getSettingById(250).getValString().equals("AAC"))
		{
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
		}
		else if (mc.getChroma().getSetManager().getSettingById(250).getValString().equals("Global"))
		{
			if (mc.thePlayer.isSprinting())
			{
				mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
			}
			
			mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
			mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
			mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
            mc.thePlayer.setSprinting(true);
            mc.thePlayer.serverSprintState = true;
		}
	}
}