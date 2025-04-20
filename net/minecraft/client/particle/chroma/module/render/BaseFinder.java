package net.minecraft.client.particle.chroma.module.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.particle.chroma.compatibility.WBlock;
import net.minecraft.client.particle.chroma.compatibility.WMinecraft;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventRender3D;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class BaseFinder extends Module
{
    private static final List<Block> NATURAL_BLOCKS = Arrays.asList(Blocks.air, Blocks.stone, Blocks.dirt, Blocks.grass, Blocks.gravel, Blocks.sand, Blocks.clay, Blocks.sandstone, Blocks.flowing_water, Blocks.water, Blocks.flowing_lava, Blocks.lava, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2, Blocks.deadbush, Blocks.iron_ore, Blocks.coal_ore, Blocks.gold_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.redstone_ore, Blocks.lapis_ore, Blocks.bedrock, Blocks.mob_spawner, Blocks.mossy_cobblestone, Blocks.tallgrass, Blocks.yellow_flower, Blocks.red_flower, Blocks.web, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.snow_layer, Blocks.vine, Blocks.waterlily, Blocks.double_plant, Blocks.hardened_clay, Blocks.red_sandstone, Blocks.ice, Blocks.quartz_ore, Blocks.obsidian, Blocks.monster_egg, Blocks.red_mushroom_block, Blocks.brown_mushroom_block);
    private final HashSet<BlockPos> matchingBlocks = new HashSet();
    private final ArrayList<int[]> vertices = new ArrayList();
    private int messageTimer = 0;
    private int counter;
	
	public BaseFinder()
	{
		super("BaseFinder", 101, Category.RENDER);
	}
	
	@Override
	public void onEnable()
	{
		this.messageTimer = 0;
		super.onEnable();
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event)
	{
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)0.15f);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-mc.getRenderManager().renderPosX), (double)(-mc.getRenderManager().renderPosY), (double)(-mc.getRenderManager().renderPosZ));
        GL11.glBegin((int)7);
        
        for (int[] vertex : this.vertices)
        {
            GL11.glVertex3d((double)vertex[0], (double)vertex[1], (double)vertex[2]);
        }
        
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
        int modulo = WMinecraft.getPlayer().ticksExisted % 64;
        
        if (modulo == 0)
        {
            this.matchingBlocks.clear();
        }
        
        int startY = 255 - modulo * 4;
        int endY = startY - 4;
        BlockPos playerPos = new BlockPos(WMinecraft.getPlayer().posX, 0.0, WMinecraft.getPlayer().posZ);
        
        block0 : for (int y = startY; y > endY; --y)
        {
            for (int x = 64; x > -64; --x)
            {
                for (int z = 64; z > -64; --z)
                {
                    if (this.matchingBlocks.size() >= 10000) break block0;
                    
                    BlockPos pos = playerPos.add(x, y, z);
                    if (NATURAL_BLOCKS.contains(WBlock.getBlock(pos))) continue;
                    this.matchingBlocks.add(pos);
                }
            }
        }
        
        if (modulo != 63)
        {
            return;
        }
        
        if (this.matchingBlocks.size() < 10000)
        {
            --this.messageTimer;
        }
        else
        {
            this.messageTimer = 3;
        }
        
        this.counter = this.matchingBlocks.size();
        this.vertices.clear();
        
        for (BlockPos pos : this.matchingBlocks)
        {
            if (!this.matchingBlocks.contains(pos.down()))
            {
                this.addVertex(pos, 0, 0, 0);
                this.addVertex(pos, 1, 0, 0);
                this.addVertex(pos, 1, 0, 1);
                this.addVertex(pos, 0, 0, 1);
            }
            
            if (!this.matchingBlocks.contains(pos.up()))
            {
                this.addVertex(pos, 0, 1, 0);
                this.addVertex(pos, 0, 1, 1);
                this.addVertex(pos, 1, 1, 1);
                this.addVertex(pos, 1, 1, 0);
            }
            
            if (!this.matchingBlocks.contains(pos.north()))
            {
                this.addVertex(pos, 0, 0, 0);
                this.addVertex(pos, 0, 1, 0);
                this.addVertex(pos, 1, 1, 0);
                this.addVertex(pos, 1, 0, 0);
            }
            
            if (!this.matchingBlocks.contains(pos.east()))
            {
                this.addVertex(pos, 1, 0, 0);
                this.addVertex(pos, 1, 1, 0);
                this.addVertex(pos, 1, 1, 1);
                this.addVertex(pos, 1, 0, 1);
            }
            
            if (!this.matchingBlocks.contains(pos.south()))
            {
                this.addVertex(pos, 0, 0, 1);
                this.addVertex(pos, 1, 0, 1);
                this.addVertex(pos, 1, 1, 1);
                this.addVertex(pos, 0, 1, 1);
            }
            
            if (this.matchingBlocks.contains(pos.west())) continue;
            
            this.addVertex(pos, 0, 0, 0);
            this.addVertex(pos, 0, 0, 1);
            this.addVertex(pos, 0, 1, 1);
            this.addVertex(pos, 0, 1, 0);
        }
	}
	
    private void addVertex(BlockPos pos, int x, int y, int z)
    {
        this.vertices.add(new int[]{pos.getX() + x, pos.getY() + y, pos.getZ() + z});
    }
}