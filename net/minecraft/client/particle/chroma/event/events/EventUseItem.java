package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.item.ItemStack;

public class EventUseItem extends EventCancellable
{
	private ItemStack itemStack;
	
	public EventUseItem(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack()
	{
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}
}