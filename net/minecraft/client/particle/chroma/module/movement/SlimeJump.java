package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.block.BlockSlime;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.util.BlockPos;

public class SlimeJump extends Module
{
	public SlimeJump()
	{
		super("SlimeJump", 119, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("AAC");
		this.getSetManager().rSetting(new Setting(302, "Mode", "", this, "AAC", modes));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
		BlockPos bp = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
		
		if (mc.theWorld.getBlockState(bp).getBlock() instanceof BlockSlime)
		{
			if (this.getMode().equals("AAC"))
			{
				mc.thePlayer.motionY = 1.4D;
			}
		}
	}
}