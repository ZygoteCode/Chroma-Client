package net.minecraft.client.particle.chroma.module.combat;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender2D;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.ui.Hud;
import net.minecraft.client.particle.chroma.utils.CombatUtil;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class BowAimbot extends Module
{
    private static final AxisAlignedBB TARGET_BOX = new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5);
    private Entity target;
    private float velocity;
    
	public BowAimbot()
	{
		super("BowAimbot", 63, Category.COMBAT);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(214, "Invisibles", "", this, false));
		this.getSetManager().rSetting(new Setting(215, "Dead entities", "", this, false));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
	        if (!mc.gameSettings.keyBindUseItem.pressed)
	        {
	            this.target = null;
	            return;
	        }

	        ItemStack item = mc.thePlayer.inventory.getCurrentItem();
	        
	        if (item == null || !(item.getItem() instanceof ItemBow))
	        {
	            this.target = null;
	            return;
	        }
	        
	        if (!isEntityValid(this.target))
	        {
		        for (final Entity e : mc.theWorld.loadedEntityList)
		        {
		            if (e instanceof EntityLivingBase)
		            {
		                if (!isEntityValid(e))
		                {
		                    continue;
		                }
		                
		                target = (EntityLivingBase) e;
		            }
		        }
	        }
	        
	        if (!isEntityValid(this.target))
	        {
	            return;
	        }
	        
	        this.velocity = (float)(72000 - mc.thePlayer.getItemInUseCount()) / 20.0f;
	        this.velocity = (this.velocity * this.velocity + this.velocity * 2.0f) / 3.0f;
	        
	        if (this.velocity > 1.0f)
	        {
	            this.velocity = 1.0f;
	        }
	        
	        double d = getEyesPos().distanceTo(this.target.boundingBox.getCenter());
	        double posX = this.target.posX + (this.target.posX - this.target.prevPosX) * d - mc.thePlayer.posX;
	        double posY = this.target.posY + (this.target.posY - this.target.prevPosY) * d + (double)this.target.height * 0.5 - mc.thePlayer.posY - (double)mc.thePlayer.getEyeHeight();
	        double posZ = this.target.posZ + (this.target.posZ - this.target.prevPosZ) * d - mc.thePlayer.posZ;
	        event.setYaw((float)Math.toDegrees(Math.atan2(posZ, posX)) - 90.0f);
	        float velocitySq = this.velocity * this.velocity;
	        float velocityPow4 = velocitySq * velocitySq;
	        float g = 0.006f;
	        double hDistance = Math.sqrt(posX * posX + posZ * posZ);
	        double hDistanceSq = hDistance * hDistance;
	        float neededPitch = (float)(-Math.toDegrees(Math.atan(((double)velocitySq - Math.sqrt((double)velocityPow4 - (double)g * ((double)g * hDistanceSq + 2.0 * posY * (double)velocitySq))) / ((double)g * hDistance))));
	        
	        if (Float.isNaN(neededPitch))
	        {
	        	final float[] rotations = CombatUtil.getRotations(target); 
	        	Random rnd = new Random();
	        	event.setYaw(rotations[0]);
	        	event.setPitch(rotations[1]);
	        }
	        else
	        {
	            event.setPitch(neededPitch);
	        }
		}
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event)
	{
        if (this.target == null)
        {
            return;
        }
        
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
        GL11.glTranslated((double)this.target.posX, (double)this.target.posY, (double)this.target.posZ);
        double boxWidth = (double)this.target.width + 0.1;
        double boxHeight = (double)this.target.height + 0.1;
        GL11.glScaled((double)boxWidth, (double)boxHeight, (double)boxWidth);
        GL11.glTranslated((double)0.0, (double)0.5, (double)0.0);
        double v = 1.0f / this.velocity;
        GL11.glScaled((double)v, (double)v, (double)v);
        GL11.glColor4d((double)1.0, (double)0.0, (double)0.0, (double)(0.5f * this.velocity));
        RenderUtils.drawOutlinedBox(TARGET_BOX);
        GL11.glColor4d((double)1.0, (double)0.0, (double)0.0, (double)(0.25f * this.velocity));
        RenderUtils.drawSolidBox(TARGET_BOX);
        GL11.glPopMatrix();
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
	}
	
	public boolean isEntityValid(Entity entity)
	{
		if (entity == null)
		{
			return false;
		}
		
		if (entity == mc.thePlayer)
		{
			return false;
		}
		
		if (!(entity instanceof EntityPlayer))
		{
			return false;
		}
		
		if (!entity.isEntityAlive() && !this.getSetManager().getSettingById(215).getValBoolean())
		{
			return false;
		}
		
		if (!mc.thePlayer.canEntityBeSeen(entity))
		{
			return false;
		}
		
		if (entity.isInvisible() && !this.getSetManager().getSettingById(214).getValBoolean())
		{
			return false;
		}
		
		if (FriendsManager.isFriend(entity.getName()))
		{
			return false;
		}
		
		if (mc.thePlayer.getDistanceToEntity(entity) > 30.0D)
		{
			return false;
		}
			
		return true;
	}
	
	  public static Vec3 getEyesPos()
	  {
		  return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
	  }
}