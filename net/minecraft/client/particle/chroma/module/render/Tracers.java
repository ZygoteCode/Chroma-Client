package net.minecraft.client.particle.chroma.module.render;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class Tracers extends Module
{
    private int playerBox;
    private final ArrayList<EntityPlayer> players = new ArrayList();
	
	public Tracers()
	{
		super("Tracers", 82, Category.RENDER);
	}
	
	@Override
	public void onEnable()
	{
        this.playerBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.playerBox, (int)4864);
        AxisAlignedBB bb = new AxisAlignedBB(-0.5, 0.0, -0.5, 0.5, 1.0, 0.5);
        RenderUtils.drawOutlinedBox(bb);
        GL11.glEndList();
        super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
        GL11.glDeleteLists((int)this.playerBox, (int)1);
        this.playerBox = 0;
        super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        EntityPlayerSP player = mc.thePlayer;
        WorldClient world = mc.theWorld;
        this.players.clear();
        Stream<EntityPlayer> stream = world.playerEntities.parallelStream().filter(e -> e != player);
        stream = stream.filter(e -> !e.isPlayerSleeping());
        stream = stream.filter(e -> !e.isInvisible());
        this.players.addAll(stream.collect(Collectors.toList()));
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event)
	{
		float partialTicks = event.getPartialTicks();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        RenderUtils.applyRenderOffset();    
        this.renderTracers(partialTicks);   
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
	}
	
    private void renderTracers(double partialTicks)
    {
        Vec3 start = getClientLookVec().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0).addVector(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY, mc.getRenderManager().renderPosZ);
        GL11.glBegin((int)1);
        
        for (EntityPlayer e : this.players)
        {
            Vec3 end = e.getEntityBoundingBox().getCenter().subtract(new Vec3(e.posX, e.posY, e.posZ).subtract(e.prevPosX, e.prevPosY, e.prevPosZ).scale(1.0 - partialTicks));
            
            if (FriendsManager.isFriend(e.getName()))
            {
                GL11.glColor4f((float)0.0f, (float)0.0f, (float)1.0f, (float)0.5f);
            }
            else
            {
                float f = mc.thePlayer.getDistanceToEntity(e) / 20.0f;
                GL11.glColor4f((float)(2.0f - f), (float)f, (float)0.0f, (float)0.5f);
            }
            
            GL11.glVertex3d((double)start.xCoord, (double)start.yCoord, (double)start.zCoord);
            GL11.glVertex3d((double)end.xCoord, (double)end.yCoord, (double)end.zCoord);
        }
        
        GL11.glEnd();
    }
    
    public static Vec3 getClientLookVec()
    {
        float f = (float) Math.cos(-mc.thePlayer.rotationYaw * 0.017453292f - 3.1415927f);
        float f1 = (float) Math.sin(-mc.thePlayer.rotationYaw * 0.017453292f - 3.1415927f);
        float f2 = (float) -Math.cos(-mc.thePlayer.rotationPitch * 0.017453292f);
        float f3 = (float) Math.sin(-mc.thePlayer.rotationPitch * 0.017453292f);
        return new Vec3(f1 * f2, f3, f * f2);
    }
}