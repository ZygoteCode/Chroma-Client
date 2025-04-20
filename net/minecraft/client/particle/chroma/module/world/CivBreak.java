package net.minecraft.client.particle.chroma.module.world;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.event.events.EventSentPacket;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.utils.CombatUtil;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class CivBreak extends Module
{
    private C07PacketPlayerDigging dig;
    private net.minecraft.client.particle.chroma.utils.Timer timer;
	
	public CivBreak()
	{
		super("CivBreak", 129, Category.WORLD);
		this.timer = new net.minecraft.client.particle.chroma.utils.Timer();
	}
	
	@Override
	public void onEnable()
	{
		this.dig = null;
		timer.reset();
		super.onEnable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
            float[] rot = CombatUtil.getRotationsNeededBlock(this.dig.getPosition().getX(), this.dig.getPosition().getY(), this.dig.getPosition().getZ());
            event.setYaw(rot[0]);
            event.setPitch(rot[1]);
            this.mc.thePlayer.swingItem();
            this.mc.getNetHandler().addToSendQueue(this.dig);
            timer.reset();
		}
	}
	
	@EventTarget
	public void onSentPacket(EventSentPacket event)
	{
        C07PacketPlayerDigging packet;
        
        if (event.getPacket() instanceof C07PacketPlayerDigging && (this.dig == null || event.getPacket() != this.dig) && (packet = (C07PacketPlayerDigging)event.getPacket()).getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK)
        {
            this.dig = packet;
            timer.reset();
        }
	}
	
    @EventTarget
    public void render(EventRender3D event)
    {
        if (this.dig.getPosition() != null)
        {
            BlockPos pos = this.dig.getPosition();
            Block block = this.mc.theWorld.getBlockState(pos).getBlock();
            String s = block.getLocalizedName();
            double x = (double)pos.getX() - RenderManager.renderPosX;
            double y = (double)pos.getY() - RenderManager.renderPosY;
            double z = (double)pos.getZ() - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glColor4f((float)0.0f, (float)0.5f, (float)1.0f, (float)0.25f);
            double minX = block instanceof BlockStairs || Block.getIdFromBlock(block) == 134 ? 0.0 : block.getBlockBoundsMinX();
            double minY = block instanceof BlockStairs || Block.getIdFromBlock(block) == 134 ? 0.0 : block.getBlockBoundsMinY();
            double minZ = block instanceof BlockStairs || Block.getIdFromBlock(block) == 134 ? 0.0 : block.getBlockBoundsMinZ();
            RenderUtils.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glColor4f((float)0.0f, (float)0.5f, (float)1.0f, (float)1.0f);
            GL11.glLineWidth((float)0.5f);
            RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glDisable((int)2848);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
    }
}