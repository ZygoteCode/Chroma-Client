package net.minecraft.client.particle.chroma.module.player;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.InvUtils;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0DPacketCloseWindow;

public class InventoryCleaner extends Module
{
	private InvUtils invhelper = new InvUtils();
	private Timer delay = new Timer();
	private Timer delay1 = new Timer();
	private Timer startdelay = new Timer();
	public boolean cleaned = false;
	private int slot = -1;
	private ArrayList<Integer> usefullitems;
	private HashMap<Item, Integer> ticksExisted = Maps.newHashMap();
	
	public InventoryCleaner()
	{
		super("InventoryCleaner", 30, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		this.usefullitems = new ArrayList();
		this.usefullitems.add(264);
		this.usefullitems.add(266);
		this.usefullitems.add(265);
		this.usefullitems.add(336);
		this.usefullitems.add(345);
		this.getSetManager().rSetting(new Setting(91, "AAC", "", this, false));
		this.getSetManager().rSetting(new Setting(92, "Sort", "", this, false));
		this.getSetManager().rSetting(new Setting(93, "Open", "", this, true));
		this.getSetManager().rSetting(new Setting(94, "Sword slot", "", this, 1.0D, 1.0D, 9.0D, true));
		this.getSetManager().rSetting(new Setting(95, "Delay", "", this, 100.0D, 1.0D, 1000.0D, true));
		this.getSetManager().rSetting(new Setting(96, "Ticks", "", this, 20, 1, 200, true));
		this.getSetManager().rSetting(new Setting(97, "No move", "", this, false));
		this.getSetManager().rSetting(new Setting(98, "Packet", "", this, false));
		this.getSetManager().rSetting(new Setting(99, "Random delay", "", this, false));
		this.getSetManager().rSetting(new Setting(100, "Min random delay", "", this, 150.0D, 1.0D, 1000.0D, true));
		this.getSetManager().rSetting(new Setting(101, "Max random delay", "", this, 230.0D, 1.0D, 1000.0D, true));
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		this.ticksExisted.clear();
		this.delay.reset();
		this.delay1.reset();
		this.startdelay.reset();
		this.cleaned = false;
		this.slot = -1;
		super.onToggle();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate event)
	{
		if (this.getSetManager().getSettingById(99).getValBoolean())
		{
			this.getSetManager().getSettingById(95).setValueD(ThreadLocalRandom.current().nextDouble(this.getSetManager().getSettingById(100).getValueD(), this.getSetManager().getSettingById(101).getValueD()));
		}
		
		for (int i = 0; i < this.mc.thePlayer.inventory.getSizeInventory() - 4; i++)
		{
			if (!isUsefullItem(i))
			{
				if (invhelper.getStackInSlot(i) != null)
				{
					Item ia = invhelper.getStackInSlot(i).getItem();
					
					if (this.ticksExisted.containsKey(ia))
					{
						int ticks = getTicksExisted(ia);
						this.ticksExisted.put(ia, ticks + 1);
					}
					else
					{
						this.ticksExisted.put(ia, 1);
					}
				}
			}
		}
		
		boolean aac = this.getSetManager().getSettingById(91).getValBoolean();

		if (aac && this.getSetManager().getSettingById(97).getValBoolean() && (mc.thePlayer.moveForward > 0.0 || mc.thePlayer.moveStrafing > 0.0))
		{
			return;
		}

		if (this.mc.currentScreen == null && this.getSetManager().getSettingById(93).getValBoolean() && !this.getSetManager().getSettingById(98).getValBoolean())
		{
			this.startdelay.reset();
			return;
		}
		
		if (!this.startdelay.hasReach(110L) || !(this.mc.currentScreen instanceof GuiInventory) && this.getSetManager().getSettingById(93).getValBoolean() && !this.getSetManager().getSettingById(98).getValBoolean())
		{
			return;
		}
		
		if (mc.currentScreen instanceof GuiContainer && mc.thePlayer.openContainer != mc.thePlayer.inventoryContainer)
		{
			return;
		}
		
		this.cleaned = true;
		
		try
		{
			for (int i = 0; i < this.mc.thePlayer.inventory.getSizeInventory() - 4; i++)
			{
				if (!isUsefullItem(i))
				{
					this.cleaned = false;
					
					if (this.delay.hasReach((long) this.getSetManager().getSettingById(95).getValueD() * (aac && (mc.thePlayer.moveForward > 0.0 || mc.thePlayer.moveStrafing > 0.0) ? 3 : 1)))
					{
						if (aac)
						{
							if (invhelper.getStackInSlot(i) != null)
							{
								Item ia = invhelper.getStackInSlot(i).getItem();
								
								if (this.getTicksExisted(ia) < this.getSetManager().getSettingById(96).getValueD())
									break;
							}
						}
						
						if (mc.currentScreen == null)
						{
							if (this.getSetManager().getSettingById(98).getValBoolean())
							{
								mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
							}
						}
						
						dropItem(i < 9 ? i + 36 : i);
						
						if (this.getSetManager().getSettingById(98).getValBoolean())
						{
							mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId));
						}

						this.delay.reset();
					}
				}
			}
			
			if (this.getSetManager().getSettingById(92).getValBoolean())
			{
				for (int i = 0; i < this.mc.thePlayer.inventory.getSizeInventory() - 4; i++)
				{
					if ((this.delay1.hasReach(15L)) && (this.cleaned))
					{
						this.delay1.reset();
						
						if (mc.currentScreen == null)
						{
							if (this.getSetManager().getSettingById(98).getValBoolean())
							{
								mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
							}
						}
						
						sort();
						
						if (this.getSetManager().getSettingById(98).getValBoolean())
						{
							mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId));
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			
		}
	}
	
	private void dropItem(int slot)
	{
		int windowId = new GuiInventory(this.mc.thePlayer).inventorySlots.windowId;
		this.mc.playerController.windowClick(windowId, slot, 1, 4, this.mc.thePlayer);
	}

	private boolean isUsefullItem(int i1)
	{
		if (this.invhelper.getStackInSlot(i1) != null && this.invhelper.getStackInSlot(i1).getItem() != null)
		{
			ItemStack is = this.invhelper.getStackInSlot(i1);

			Item item = is.getItem();

			if (item == null)
			{
				return true;
			}
			
			if (item instanceof ItemFishingRod && this.invhelper.getFirstItem(item) == i1)
			{
				return true;
			}
			
			if (item instanceof ItemBow && this.invhelper.getFirstItem(item) == i1)
			{
				return true;
			}
			
			if (this.usefullitems.contains(Item.getIdFromItem(item)))
			{
				return true;
			}
			
			if (item instanceof ItemBlock)
			{
				return true;
			}
			
			if (item instanceof ItemFood)
			{
				return true;
			}
			
			if (item instanceof ItemSword && this.invhelper.isBestSword(i1))
			{
				return true;
			}
			
			if (Item.getIdFromItem(item) == 262)
			{
				return true;
			}
			
			if (item instanceof ItemArmor)
			{
				ItemArmor armor = (ItemArmor) item;
				
				if (armor.armorType == 0 && this.invhelper.isBestHelmet(i1))
				{
					return true;
				}
				
				if (armor.armorType == 1 && this.invhelper.isBestChest(i1))
				{
					return true;
				}
				
				if (armor.armorType == 2 && this.invhelper.isBestLeggings(i1))
				{
					return true;
				}
				
				if (armor.armorType == 3 && this.invhelper.isBestBoots(i1))
				{
					return true;
				}
			}
			
			if (is.getItem() instanceof ItemTool && this.invhelper.getFirstItem(is.getItem()) == i1)
			{
				return true;
			}
			
			if (is.getItem() instanceof ItemPotion)
			{
				return true;
			}
			
			if (is.getItem() instanceof ItemFlintAndSteel)
			{
				return true;
			}
			
			if (is.getItem() instanceof ItemEnderPearl)
			{
				return true;
			}
		}
		else
		{
			return true;
		}
		
		return false;
	}

	private void sort()
	{
		for (int i1 = 0; i1 < 36; i1++)
		{
			if (this.invhelper.getStackInSlot(i1) != null && this.invhelper.getStackInSlot(i1).getItem() != null)
			{
				int i = i1;

				Item item = this.invhelper.getStackInSlot(i1).getItem();
				
				if (item instanceof ItemSword)
				{
					if (this.invhelper.isBestSword(i))
					{
						this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId,
								i < 9 ? i + 36 : i,
								(int) (this.getSetManager().getSettingById(94).getValueD() - 1.0D),
								2, this.mc.thePlayer);
					}
				}
			}
		}
	}

	private int getTicksExisted(Item ia)
	{
		return this.ticksExisted.get(ia);
	}
}