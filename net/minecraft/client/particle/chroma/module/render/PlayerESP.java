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

public class PlayerESP extends Module
{
    private int playerBox;
    private final ArrayList<EntityPlayer> players = new ArrayList();
	
	public PlayerESP()
	{
		super("PlayerESP", 51, Category.RENDER);
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
        this.renderBoxes(partialTicks);    
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
	}
	
    private void renderBoxes(double partialTicks)
    {
        double extraSize = 0.0;
        
        for (EntityPlayer e : this.players)
        {
            GL11.glPushMatrix();
            GL11.glTranslated((double)(e.prevPosX + (e.posX - e.prevPosX) * partialTicks), (double)(e.prevPosY + (e.posY - e.prevPosY) * partialTicks), (double)(e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks));
            GL11.glScaled((double)((double)e.width + extraSize), (double)((double)e.height + extraSize), (double)((double)e.width + extraSize));
            
            if (FriendsManager.isFriend(e.getName()))
            {
                GL11.glColor4f((float)0.0f, (float)0.0f, (float)1.0f, (float)0.5f);
            }
            else
            {
                float f = mc.thePlayer.getDistanceToEntity(e) / 20.0f;
                GL11.glColor4f((float)(2.0f - f), (float)f, (float)0.0f, (float)0.5f);
            }
            
            GL11.glCallList((int)this.playerBox);
            GL11.glPopMatrix();
        }
    }
}