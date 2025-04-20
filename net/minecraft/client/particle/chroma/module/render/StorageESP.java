package net.minecraft.client.particle.chroma.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class StorageESP extends Module
{
    private final ArrayList<AxisAlignedBB> basicChests = new ArrayList();
    private final ArrayList<AxisAlignedBB> trappedChests = new ArrayList();
    private final ArrayList<AxisAlignedBB> enderChests = new ArrayList();
    private final ArrayList<Entity> minecarts = new ArrayList();
    private int solidBox;
    private int outlinedBox;
    private int crossBox;
    private int normalChests;
    private int chestCounter;
    private TileEntityChest openChest;
    private final LinkedHashSet<BlockPos> emptyChests = new LinkedHashSet();
    private final LinkedHashSet<BlockPos> nonEmptyChests = new LinkedHashSet();
    private static final AxisAlignedBB CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
	
	public StorageESP()
	{
		super("StorageESP", 50, Category.RENDER);
	}
	
	@Override
	public void setup()
	{
		this.getSetManager().rSetting(new Setting(201, "HiveMC chests", "", this, false));
		super.setup();
	}
	
	@Override
	public void onEnable()
	{
        this.emptyChests.clear();
        this.nonEmptyChests.clear();
        this.solidBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.solidBox, (int)4864);
        RenderUtils.drawSolidBox();
        GL11.glEndList();
        this.outlinedBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.outlinedBox, (int)4864);
        RenderUtils.drawOutlinedBox();
        GL11.glEndList();
        this.crossBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.crossBox, (int)4864);
        RenderUtils.drawCrossBox();
        GL11.glEndList();
        this.normalChests = GL11.glGenLists((int)1);
        super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
        GL11.glDeleteLists((int)this.solidBox, (int)1);
        this.solidBox = 0;
        GL11.glDeleteLists((int)this.outlinedBox, (int)1);
        this.outlinedBox = 0;
        GL11.glDeleteLists((int)this.crossBox, (int)1);
        this.crossBox = 0;
        GL11.glDeleteLists((int)this.normalChests, (int)1);
        this.normalChests = 0;
        super.onDisable();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        ArrayList<AxisAlignedBB> basicNew = new ArrayList<AxisAlignedBB>();
        ArrayList<AxisAlignedBB> basicEmpty = new ArrayList<AxisAlignedBB>();
        ArrayList<AxisAlignedBB> basicNotEmpty = new ArrayList<AxisAlignedBB>();
        ArrayList<AxisAlignedBB> trappedNew = new ArrayList<AxisAlignedBB>();
        ArrayList<AxisAlignedBB> trappedEmpty = new ArrayList<AxisAlignedBB>();
        ArrayList<AxisAlignedBB> trappedNotEmpty = new ArrayList<AxisAlignedBB>();
        this.enderChests.clear();
        
        for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList)
        {
            if (tileEntity instanceof TileEntityChest)
            {
                AxisAlignedBB bb2;
                AxisAlignedBB bb;
                TileEntityChest chest = (TileEntityChest)tileEntity;
                
                if (chest.adjacentChestXPos != null || chest.adjacentChestZPos != null || (bb = getBoundingBox(chest.getPos())) == null) continue;
                
                if (chest.adjacentChestXNeg != null)
                {
                    BlockPos pos2 = chest.adjacentChestXNeg.getPos();
                    bb2 = getBoundingBox(pos2);
                    bb = bb.union(bb2);
                }
                else if (chest.adjacentChestZNeg != null)
                {
                    BlockPos pos2 = chest.adjacentChestZNeg.getPos();
                    bb2 = getBoundingBox(pos2);
                    bb = bb.union(bb2);
                }
                
                boolean trapped = isTrappedChest(chest);
                
                if (this.emptyChests.contains(chest.getPos()))
                {
                    if (trapped)
                    {
                        trappedEmpty.add(bb);
                        continue;
                    }
                    
                    basicEmpty.add(bb);
                    continue;
                }
                
                if (this.nonEmptyChests.contains(chest.getPos()))
                {
                    if (trapped)
                    {
                        trappedNotEmpty.add(bb);
                        continue;
                    }
                    
                    basicNotEmpty.add(bb);
                    continue;
                }
                
                if (trapped)
                {
                    trappedNew.add(bb);
                    continue;
                }
                
                basicNew.add(bb);
                continue;
            }
            
            if (!(tileEntity instanceof TileEntityEnderChest)) continue;
            
            AxisAlignedBB bb = getBoundingBox(((TileEntityEnderChest)tileEntity).getPos());
            this.enderChests.add(bb);
        }
        
        this.basicChests.clear();
        this.basicChests.addAll(basicNew);
        this.basicChests.addAll(basicEmpty);
        this.basicChests.addAll(basicNotEmpty);
        this.trappedChests.clear();
        this.trappedChests.addAll(trappedNew);
        this.trappedChests.addAll(trappedEmpty);
        this.trappedChests.addAll(trappedNotEmpty);
        GL11.glNewList((int)this.normalChests, (int)4864);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.25f);
        this.renderBoxes(basicNew, this.solidBox);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.5f);
        this.renderBoxes(basicNew, this.outlinedBox);
        this.renderBoxes(basicEmpty, this.outlinedBox);
        this.renderBoxes(basicNotEmpty, this.outlinedBox);
        this.renderBoxes(basicNotEmpty, this.crossBox);
        GL11.glColor4f((float)1.0f, (float)0.5f, (float)0.0f, (float)0.25f);
        this.renderBoxes(trappedNew, this.solidBox);
        GL11.glColor4f((float)1.0f, (float)0.5f, (float)0.0f, (float)0.5f);
        this.renderBoxes(trappedNew, this.outlinedBox);
        this.renderBoxes(trappedEmpty, this.outlinedBox);
        this.renderBoxes(trappedNotEmpty, this.outlinedBox);
        this.renderBoxes(trappedNotEmpty, this.crossBox);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)0.25f);
        this.renderBoxes(this.enderChests, this.solidBox);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        this.renderBoxes(this.enderChests, this.outlinedBox);
        GL11.glEndList();
        this.minecarts.clear();
        
        for (Entity entity : mc.theWorld.loadedEntityList)
        {
            if (!(entity instanceof EntityMinecartChest)) continue;
            this.minecarts.add(entity);
        }
        
        this.chestCounter = basicNew.size() + basicEmpty.size() + basicNotEmpty.size() + trappedNew.size() + trappedEmpty.size() + trappedNotEmpty.size() + this.enderChests.size() + this.minecarts.size();
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
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
        ArrayList<AxisAlignedBB> minecartBoxes = new ArrayList<AxisAlignedBB>(this.minecarts.size());
        
        this.minecarts.forEach(e ->
        {
            double offsetX = -(e.posX - e.lastTickPosX) + (e.posX - e.lastTickPosX) * (double)partialTicks;
            double offsetY = -(e.posY - e.lastTickPosY) + (e.posY - e.lastTickPosY) * (double)partialTicks;
            double offsetZ = -(e.posZ - e.lastTickPosZ) + (e.posZ - e.lastTickPosZ) * (double)partialTicks;
            minecartBoxes.add(e.boundingBox.offset(offsetX, offsetY, offsetZ));
        });
        
        GL11.glCallList((int)this.normalChests);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.25f);
        this.renderBoxes(minecartBoxes, this.solidBox);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.5f);
        this.renderBoxes(minecartBoxes, this.outlinedBox);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        
        if (this.getSetManager().getSettingById(201).getValBoolean())
        {
    		for (int x = -20; x < 20; x++)
    		{
    			for (int y = 20; y > -20; y--)
    			{
    				for (int z = -20; z < 20; z++)
    				{
    					int xPos = (int) (mc.thePlayer.posX + x), yPos = (int) (mc.thePlayer.posY + y), zPos = (int) (mc.thePlayer.posZ + z);
    					BlockPos bp = new BlockPos(xPos, yPos, zPos);
    					Block block = mc.theWorld.getBlockState(bp).getBlock();
    					
    					if (block == Blocks.barrier)
    					{
    				    	GL11.glEnable((int)3042);
    				        GL11.glBlendFunc((int)770, (int)771);
    				        GL11.glEnable((int)2848);
    				        GL11.glLineWidth((float)2.0f);
    				        GL11.glDisable((int)3553);
    				        GL11.glEnable((int)2884);
    				        GL11.glDisable((int)2929);
    				        GL11.glPushMatrix();
    				        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
    				        GL11.glTranslated((double)xPos, (double)yPos, (double)zPos);
    				        float progress = 1.01F;
    				        GL11.glTranslated((double)0.5, (double)0.5, (double)0.5);
    				        GL11.glScaled((double)progress, (double)progress, (double)progress);
    				        GL11.glTranslated((double)-0.5, (double)-0.5, (double)-0.5);
    				        float red = progress * 2.0f;
    				        float green = 2.0f - red;
    				        red = Color.cyan.darker().darker().getRed();
    				        green = Color.cyan.darker().darker().getGreen();
    				        float blue = Color.cyan.darker().darker().getBlue();
    				        GL11.glColor4f((float)red, (float)green, (float)blue, (float)0.25f);
    				        RenderUtils.drawSolidBox();
    				        GL11.glColor4f((float)red, (float)green, (float)blue, (float)0.5f);
    				        RenderUtils.drawOutlinedBox();
    				        GL11.glPopMatrix();
    				        GL11.glEnable((int)2929);
    				        GL11.glEnable((int)3553);
    				        GL11.glDisable((int)3042);
    				        GL11.glDisable((int)2848);
    					}
    				}
    			}
    		}
        }
	}
	
    private void renderBoxes(ArrayList<AxisAlignedBB> boxes, int displayList)
    {
        for (AxisAlignedBB bb : boxes)
        {
            GL11.glPushMatrix();
            GL11.glTranslated((double)bb.minX, (double)bb.minY, (double)bb.minZ);
            GL11.glScaled((double)(bb.maxX - bb.minX), (double)(bb.maxY - bb.minY), (double)(bb.maxZ - bb.minZ));
            GL11.glCallList((int)displayList);
            GL11.glPopMatrix();
        }
    }
    
    public void openChest(BlockPos pos)
    {
        TileEntity tileEntity = mc.theWorld.getTileEntity(pos);
        
        if (tileEntity instanceof TileEntityChest)
        {
            this.openChest = (TileEntityChest)tileEntity;
            
            if (this.openChest.adjacentChestXPos != null)
            {
                this.openChest = this.openChest.adjacentChestXPos;
            }
            
            if (this.openChest.adjacentChestZPos != null)
            {
                this.openChest = this.openChest.adjacentChestZPos;
            }
        }
    }

    public void closeChest(Container chest)
    {
        if (this.openChest == null)
        {
            return;
        }
        
        boolean empty = true;
        
        for (int i = 0; i < chest.inventorySlots.size() - 36; ++i)
        {
            if (isNullOrEmpty(chest.inventorySlots.get(i).getStack())) continue;
            empty = false;
            break;
        }
        
        BlockPos pos = this.openChest.getPos();
        
        if (empty)
        {
            if (!this.emptyChests.contains(pos))
            {
                this.emptyChests.add(pos);
            }
            
            this.nonEmptyChests.remove(pos);
        }
        else 
        {
            if (!this.nonEmptyChests.contains(pos))
            {
                this.nonEmptyChests.add(pos);
            }
            
            this.emptyChests.remove(pos);
        }
        
        this.openChest = null;
    }
    
    public static AxisAlignedBB getBoundingBox(BlockPos pos)
    {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        
        if (block instanceof BlockChest)
        {
            return CHEST_AABB.offset(pos);
        }
        
        return block.getSelectedBoundingBox(mc.theWorld, pos);
    }
    
    public static boolean isNullOrEmpty(Item item)
    {
        return item == null;
    }

    public static boolean isNullOrEmpty(ItemStack stack)
    {
        return stack == null || isNullOrEmpty(stack.getItem());
    }
    
    public static boolean isTrappedChest(TileEntityChest chest)
    {
        return chest.getChestType() == 1;
    }
}