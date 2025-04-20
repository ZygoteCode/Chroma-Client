package net.minecraft.client.particle.chroma.module.other;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.chroma.compatibility.WEnchantments;
import net.minecraft.client.particle.chroma.compatibility.WItem;
import net.minecraft.client.particle.chroma.compatibility.WPlayerController;
import net.minecraft.client.particle.chroma.compatibility.WSoundEvents;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventReceivePacket;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class AutoFish extends Module
{
    private int timer;
    private Vec3 lastSoundPos;
    private int box;
    private int cross;
	
	public AutoFish()
	{
		super("AutoFish", 96, Category.OTHER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(289, "Reach", "", this, 1.25, 0.25, 8.0, false));
		this.getSetManager().rSetting(new Setting(290, "Drawing", "", this, true));
		super.setup();
	}
	
	@Override
	public void onEnable()
	{
        this.timer = 0;
        this.lastSoundPos = null;
        this.box = GL11.glGenLists((int)1);
        this.cross = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.cross, (int)4864);
        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)0.5f);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)-0.125, (double)0.0, (double)-0.125);
        GL11.glVertex3d((double)0.125, (double)0.0, (double)0.125);
        GL11.glVertex3d((double)0.125, (double)0.0, (double)-0.125);
        GL11.glVertex3d((double)-0.125, (double)0.0, (double)0.125);
        GL11.glEnd();
        GL11.glEndList();
		super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
        GL11.glDeleteLists((int)this.box, (int)1);
        GL11.glDeleteLists((int)this.cross, (int)1);
		super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        if (this.getSetManager().getSettingById(290).getValBoolean())
        {
            GL11.glNewList((int)this.box, (int)4864);
            AxisAlignedBB box = new AxisAlignedBB(-this.getSetManager().getSettingById(289).getValueD(), -0.0625, -this.getSetManager().getSettingById(289).getValueD(), this.getSetManager().getSettingById(289).getValueD(), 0.0625, this.getSetManager().getSettingById(289).getValueD());
            GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)0.5f);
            RenderUtils.drawOutlinedBox(box);
            GL11.glEndList();
        }
        
        EntityPlayerSP player = mc.thePlayer;
        InventoryPlayer inventory = player.inventory;
        
        if (this.timer < 0)
        {
            WPlayerController.windowClick_PICKUP(-this.timer);
            this.timer = 15;
            return;
        }
        
        int bestRodValue = this.getRodValue(inventory.getStackInSlot(inventory.currentItem));
        int bestRodSlot = bestRodValue > -1 ? inventory.currentItem : -1;
        
        for (int slot = 0; slot < 36; ++slot)
        {
            ItemStack stack = inventory.getStackInSlot(slot);
            int rodValue = this.getRodValue(stack);
            if (rodValue <= bestRodValue) continue;
            bestRodValue = rodValue;
            bestRodSlot = slot;
        }
        
        if (bestRodSlot == inventory.currentItem)
        {
            if (this.timer > 0)
            {
                --this.timer;
                return;
            }
            
            if (player.fishEntity == null)
            {
                this.rightClick();
            }
            
            return;
        }
        
        if (bestRodSlot == -1)
        {
            this.setToggled(false);
            return;
        }
        
        if (bestRodSlot < 9)
        {
            inventory.currentItem = bestRodSlot;
            return;
        }
        
        int firstEmptySlot = inventory.getFirstEmptyStack();
        
        if (firstEmptySlot != -1)
        {
            if (firstEmptySlot >= 9)
            {
                WPlayerController.windowClick_QUICK_MOVE(36 + inventory.currentItem);
            }
            
            WPlayerController.windowClick_QUICK_MOVE(bestRodSlot);
        }
        else
        {
            WPlayerController.windowClick_PICKUP(bestRodSlot);
            WPlayerController.windowClick_PICKUP(36 + inventory.currentItem);
            this.timer = -bestRodSlot;
        }
	}
	
	@EventTarget
	public void onReceivePacket(EventReceivePacket event)
	{
        EntityPlayerSP player = mc.thePlayer;
        
        if (player == null || player.fishEntity == null)
        {
            return;
        }
        
        if (!(event.getPacket() instanceof S29PacketSoundEffect))
        {
            return;
        }
        
        S29PacketSoundEffect sound = (S29PacketSoundEffect)event.getPacket();
        
        if (!WSoundEvents.isBobberSplash(sound))
        {
            return;
        }
        
        if (this.getSetManager().getSettingById(290).getValBoolean())
        {
            this.lastSoundPos = new Vec3(sound.getX(), sound.getY(), sound.getZ());
        }
        
        EntityFishHook bobber = player.fishEntity;
        
        if (Math.abs(sound.getX() - bobber.posX) > this.getSetManager().getSettingById(289).getValueD() || Math.abs(sound.getZ() - bobber.posZ) > this.getSetManager().getSettingById(289).getValueD())
        {
            return;
        }
        
        this.rightClick();
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event)
	{
        if (!this.getSetManager().getSettingById(290).getValBoolean())
        {
            return;
        }
        
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
        EntityFishHook bobber = mc.thePlayer.fishEntity;
        
        if (bobber != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslated((double)bobber.posX, (double)bobber.posY, (double)bobber.posZ);
            GL11.glCallList((int)this.box);
            GL11.glPopMatrix();
        }
        
        if (this.lastSoundPos != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslated((double)this.lastSoundPos.xCoord, (double)this.lastSoundPos.yCoord, (double)this.lastSoundPos.zCoord);
            GL11.glCallList((int)this.cross);
            GL11.glPopMatrix();
        }
        
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
	}
	
    private int getRodValue(ItemStack stack)
    {
        if (WItem.isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemFishingRod))
        {
            return -1;
        }
        
        int luckOTSLvl = WEnchantments.getEnchantmentLevel(WEnchantments.LUCK_OF_THE_SEA, stack);
        int lureLvl = WEnchantments.getEnchantmentLevel(WEnchantments.LURE, stack);
        int unbreakingLvl = WEnchantments.getEnchantmentLevel(WEnchantments.UNBREAKING, stack);
        int mendingBonus = WEnchantments.getEnchantmentLevel(WEnchantments.MENDING, stack);
        int noVanishBonus = WEnchantments.hasVanishingCurse(stack) ? 0 : 1;
        return luckOTSLvl * 9 + lureLvl * 9 + unbreakingLvl * 2 + mendingBonus + noVanishBonus;
    }

    private void rightClick()
    {
        ItemStack stack = mc.thePlayer.inventory.getCurrentItem();
        
        if (WItem.isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemFishingRod))
        {
            return;
        }
        
        mc.rightClickMouse();
        this.timer = 15;
    }
}