package net.minecraft.client.particle.chroma.module.combat;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastBow extends Module
{
	private boolean timerStarted;
	private Timer arrowTimer;
	
	public FastBow()
	{
		super("FastBow", 75, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		arrowTimer = new Timer();
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Vanilla");
		modes.add("NCP");
		this.getSetManager().rSetting(new Setting(241, "Mode", "", this, "Vanilla", modes));
		this.getSetManager().rSetting(new Setting(242, "Arrow power", "", this, 20, 0, 20, true));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		timerStarted = false;
		arrowTimer.reset();
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
			this.setSuffix(" §7" + this.getMode());
			
	        if (this.getMode().equals("Vanilla"))
	        {
	        	if (!mc.gameSettings.keyBindUseItem.pressed)
		        {
		            return;
		        }
		        
		        if (!mc.thePlayer.onGround && !mc.thePlayer.capabilities.isCreativeMode)
		        {
		            return;
		        }
		        
		        if (mc.thePlayer.getHealth() <= 0.0f)
		        {
		            return;
		        }
		        
		        ItemStack stack = mc.thePlayer.inventory.getCurrentItem();
		        
		        if (isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemBow))
		        {
		            return;
		        }
		        
		        processRightClick();
		        
		        for (int i = 0; i < this.getSetManager().getSettingById(242).getValueI(); ++i)
		        {
		            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
		        }
		        
		        mc.playerController.onStoppedUsingItem(mc.thePlayer);
	        }
	        else if (this.getMode().equals("NCP"))
	        {
	        	ItemStack stack = mc.thePlayer.inventory.getCurrentItem();
	        	
	        	if (mc.thePlayer.getHealth() <= 0.0F || (!mc.thePlayer.onGround && !mc.thePlayer.capabilities.isCreativeMode) || (isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemBow)) || (mc.thePlayer.moveForward != 0.0F))
	        	{
	        		if (timerStarted)
	        		{
	        			timerStarted = false;
		        		arrowTimer.reset();
	        		}
	        		
	        		return;
	        	}
	        	
	        	if (!timerStarted)
	        	{
	        		timerStarted = true;
	        		arrowTimer.reset();
	        	}
	        	else
	        	{
	        		if (arrowTimer.hasReach(500D))
	        		{
	    		        for (int i = 0; i < this.getSetManager().getSettingById(242).getValueI(); ++i)
	    		        {
	    		            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
	    		        }
	        			
	        			mc.playerController.onStoppedUsingItem(mc.thePlayer);
	        			arrowTimer.reset();
	        		}
	        	}
	        }
		}
	}
	
    public static void processRightClick()
    {
        mc.playerController.processRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
    }
    
    public static boolean isNullOrEmpty(Item item)
    {
        return item == null;
    }

    public static boolean isNullOrEmpty(ItemStack stack)
    {
        return stack == null || isNullOrEmpty(stack.getItem());
    }
}