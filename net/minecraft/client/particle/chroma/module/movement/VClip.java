package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class VClip extends Module
{
	public VClip()
	{
		super("VClip", 117, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Spartan");
		this.getSetManager().rSetting(new Setting(298, "Mode", "", this, "Spartan", modes));
		this.getSetManager().rSetting(new Setting(299, "Length", "", this, 1.0, 1.0, 10.0, true));
		super.setup();
	}
	
	@Override
	public void onEnable()
	{
		int length = this.getSetManager().getSettingById(299).getValueI();
		String mode = this.getMode();
		
		if (mode.equals("Spartan"))
		{
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + length, mc.thePlayer.posZ);
			
			for (int i = 0; i < 3; i++)
			{
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
			}
		}
		
		this.setToggled(false);
		super.onEnable();
	}
}