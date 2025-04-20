package net.minecraft.client.particle.chroma.module.player;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class TNTBlock extends Module
{
    private boolean blocked;
    
	public TNTBlock()
	{
		super("TNTBlock", 133, Category.PLAYER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(332, "Fuse", "", this, 10, 0, 80, true));
		this.getSetManager().rSetting(new Setting(333, "Reach", "", this, 9, 1, 20, true));
		this.getSetManager().rSetting(new Setting(334, "Auto Sword", "", this, true));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        for (final Entity entity : mc.theWorld.loadedEntityList)
        {
            if (entity instanceof EntityTNTPrimed && mc.thePlayer.getDistanceToEntity(entity) <= this.getSetManager().getSettingById(333).getValueD())
            {
                final EntityTNTPrimed tntPrimed = (EntityTNTPrimed) entity;

                if (tntPrimed.fuse <= this.getSetManager().getSettingById(332).getValueI())
                {
                    if (this.getSetManager().getSettingById(334).getValBoolean())
                    {
                        int slot = -1;
                        float bestDamage = 1F;

                        for (int i = 0; i < 9; i++)
                        {
                            final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);

                            if (itemStack != null && itemStack.getItem() instanceof ItemSword)
                            {
                                final float itemDamage = ((ItemSword) itemStack.getItem()).getDamageVsEntity() + 4F;

                                if (itemDamage > bestDamage)
                                {
                                    bestDamage = itemDamage;
                                    slot = i;
                                }
                            }
                        }

                        if (slot != -1 && slot != mc.thePlayer.inventory.currentItem)
                        {
                            mc.thePlayer.inventory.currentItem = slot;
                            mc.playerController.updateController();
                        }
                    }

                    if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)
                    {
                        mc.gameSettings.keyBindUseItem.pressed = true;
                        blocked = true;
                    }
                    
                    return;
                }
            }
        }

        if (blocked && !GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem))
        {
            mc.gameSettings.keyBindUseItem.pressed = false;
            blocked = false;
        }
	}
}