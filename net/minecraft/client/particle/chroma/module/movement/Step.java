package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.PlayerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class Step extends Module
{
	public Step()
	{
		super("Step", "Automatically step on a block.", Keyboard.KEY_NONE, Category.MOVEMENT, false, 35);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Vanilla");
		modes.add("NCP");
		this.getSetManager().rSetting(new Setting(114, "Mode", "", this, "NCP", modes));
		this.getSetManager().rSetting(new Setting(115, "Height", "", this, 1.0D, 1.0D, 5.0D, true));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		mc.thePlayer.stepHeight = 0.5F;
		mc.timer.timerSpeed = 1.0F;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate e)
	{
		int height = this.getSetManager().getSettingById(115).getValueI();
		
		if (this.getMode().equalsIgnoreCase("Vanilla"))
		{
			this.setSuffix(" §7Vanilla | " + height + ".0");
			mc.thePlayer.stepHeight = height;
		}
		else if (this.getMode().equalsIgnoreCase("NCP"))
		{
			this.setSuffix(" §7NCP | " + height + ".0");
	        EntityPlayerSP player = mc.thePlayer;
	        player.stepHeight = 0.5f;
	        
	        if (!player.isCollidedHorizontally)
	        {
	            return;
	        }
	        
	        if (!player.onGround || player.isOnLadder() || player.isInWater() || player.isInLava())
	        {
	            return;
	        }
	        
	        if (player.movementInput.moveForward == 0.0f && player.movementInput.moveStrafe == 0.0f)
	        {
	            return;
	        }
	        
	        if (player.movementInput.jump)
	        {
	            return;
	        }
	        
	        AxisAlignedBB box = player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).expand(0.05, 0.05, 0.05);
	        
	        if (!mc.theWorld.getCollidingBoundingBoxes(player, box.offset(0.0, height, 0.0)).isEmpty())
	        {
	            return;
	        }
	        
	        double stepHeight = (height - height - height);
	        
	        for (AxisAlignedBB bb : mc.theWorld.getCollidingBoundingBoxes(player, box))
	        {
	            if (!(bb.maxY > stepHeight)) continue;
	            stepHeight = bb.maxY;
	        }
	        
	        if ((stepHeight -= player.posY) < 0.0 || stepHeight > height)
	        {
	            return;
	        }
	        
	        mc.thePlayer.setSprinting(true);

	        if (height >= 1 && stepHeight == 1.0)
	        {
		        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + 0.42 * stepHeight, player.posZ, player.onGround));
		        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + 0.753 * stepHeight, player.posZ, player.onGround));
	        }
	        else if (height >= 2 && stepHeight == 2.0)
	        {
	        	
		    }
	        else if (height >= 3 && stepHeight == 3.0)
	        {
	        	
	        }
	        else if (height >= 4 && stepHeight == 4.0)
	        {
	        	
	        }
	        else if (height >= 5 && stepHeight == 5.0)
	        {
	        	
	        }
	        
            Step.mc.timer.timerSpeed = 0.37f;
            
            new Thread(() ->
            {
                try
                {
                    Thread.sleep(125);
                }
                catch (InterruptedException interruptedException)
                {
                	
                }
                
                Step.mc.timer.timerSpeed = 1.0f;
            }).start();
	        
	        player.setPosition(player.posX, player.posY + 1.0 * stepHeight, player.posZ);
		}
	}
}