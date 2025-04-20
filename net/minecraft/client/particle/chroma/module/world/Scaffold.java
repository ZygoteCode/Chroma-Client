package net.minecraft.client.particle.chroma.module.world;

import java.util.ArrayList;
import java.util.Random;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.chroma.Chroma;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSafeWalk;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.CombatUtil;
import net.minecraft.client.particle.chroma.utils.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Module
{
    private double olddelay;
    private boolean rotated = false;
    private boolean should = false;
    private BlockData blockData;
    private net.minecraft.client.particle.chroma.utils.Timer time = new net.minecraft.client.particle.chroma.utils.Timer();
    private BlockPos blockpos;
    private EnumFacing facing;
    private net.minecraft.client.particle.chroma.utils.Timer delay = new net.minecraft.client.particle.chroma.utils.Timer();
    private int phase = 0;
    
    public static int[] TheSwitchTable()
    {
        int[] arrn = new int[EnumFacing.values().length];
        
        try
        {
            arrn[EnumFacing.DOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try
        {
            arrn[EnumFacing.EAST.ordinal()] = 6;
        }
        
        catch (NoSuchFieldError noSuchFieldError) {}
        try
        {
            arrn[EnumFacing.NORTH.ordinal()] = 3;
        }
        
        catch (NoSuchFieldError noSuchFieldError) {}
        try
        {
            arrn[EnumFacing.SOUTH.ordinal()] = 4;
        }
        
        catch (NoSuchFieldError noSuchFieldError) {}
        try
        {
            arrn[EnumFacing.UP.ordinal()] = 2;
        }
        
        catch (NoSuchFieldError noSuchFieldError) {}
        try
        {
            arrn[EnumFacing.WEST.ordinal()] = 5;
        }       
        catch (NoSuchFieldError noSuchFieldError) {}
        
        return arrn;
    }
	
	public Scaffold()
	{
		super("Scaffold", 39, Category.WORLD);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("Normal");
		options.add("AAC");
		options.add("CubeCraft");
		options.add("GommeHD");
		options.add("Rewinside");
		this.getSetManager().rSetting(new Setting(130, "Mode", "The working mode of the Scaffold module.", this, "Normal", options));
		this.getSetManager().rSetting(new Setting(131, "No swing", "", this, false));
		this.getSetManager().rSetting(new Setting(132, "Silent", "", this, false));
		this.getSetManager().rSetting(new Setting(133, "Expand", "", this, false));
		this.getSetManager().rSetting(new Setting(134, "Delay", "", this, 250.0D, 1.0D, 1000.0D, true));
		super.setup();
	}
	
	@Override
	public void onDisable()
	{
        this.sendCurrentItem();
        this.mc.gameSettings.keyBindSneak.pressed = false;
        this.mc.timer.timerSpeed = 1.0f;
        this.delay.reset();
        this.time.reset();
        this.phase = 0;
        super.onDisable();
	}
	
	@EventTarget
	public void onPre(EventUpdate event)
	{			
		if (event.getState().equals(EventState.PRE))
		{
			this.setSuffix(" §7" + this.getMode());
			
			if (!couldBlockBePlaced())
			{
				return;
			}
			
	        this.blockData = this.getSetManager().getSettingById(133).getValBoolean() != false ? this.getBlockData(new BlockPos(this.mc.thePlayer.posX - Math.sin(PlayerUtil.getDirection()) * 1.0, this.mc.thePlayer.posY - 0.75, this.mc.thePlayer.posZ + Math.cos(PlayerUtil.getDirection()) * 1.0), 1) : this.getBlockData(new BlockPos(this.mc.thePlayer).add(0.0, -0.75, 0.0), 1);
			//this.blockData = this.getSetManager().getSettingById(133).getValBoolean() != false ? this.getBlockData(new BlockPos(this.mc.thePlayer.posX - Math.sin(PlayerUtil.getDirection()) * 1.0, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + Math.cos(PlayerUtil.getDirection()) * 1.0)) : this.getBlockData(new BlockPos(this.mc.thePlayer));
	        int block = this.getBlockItem();
	        Item item = this.mc.thePlayer.inventory.getStackInSlot(block).getItem();
	        
	        if (!this.getMode().equalsIgnoreCase("GommeHD"))
	        {
	            if (block != -1 && item != null && item instanceof ItemBlock)
	            {
	                if (this.getSetManager().getSettingById(132).getValBoolean())
	                {
	                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(block));
	                }
	                
	                if (this.getMode().equalsIgnoreCase("AAC") && this.mc.gameSettings.keyBindSprint.pressed && PlayerUtil.MovementInput())
	                {
	                    PlayerUtil.setSpeed(0.11);
	                }
	                
	                if (this.getMode().equalsIgnoreCase("CubeCraft") && PlayerUtil.MovementInput())
	                {
	                    PlayerUtil.setSpeed(0.05);
	                }
	            }
	            
	            if (this.getMode().equalsIgnoreCase("Rewinside") && PlayerUtil.MovementInput())
	            {
	                PlayerUtil.setSpeed(0.14);
	            }
	            
	            Random r = new Random();
	            
	            if (this.blockData != null && block != -1 && item != null && item instanceof ItemBlock)
	            {
	                Vec3 pos = this.getBlockSide(this.blockData.pos, this.blockData.face);
	                float[] rot = CombatUtil.getRotationsNeededBlock(pos.xCoord, pos.yCoord, pos.zCoord);
	                event.setPitch(this.getMode().equalsIgnoreCase("Rewinside") ? 82.500114f : rot[1]);
	                
	                if (this.getMode().equalsIgnoreCase("CubeCraft") || this.getMode().equalsIgnoreCase("Rewinside"))
	                {
	                    if (this.mc.gameSettings.keyBindForward.pressed)
	                    {
	                        event.setYaw(this.mc.thePlayer.rotationYaw >= 180.0f ? this.mc.thePlayer.rotationYaw - 180.0f + (float)new Random().nextInt(5) : this.mc.thePlayer.rotationYaw + 180.0f - (float)new Random().nextInt(5));
	                    }
	                    else if (this.mc.gameSettings.keyBindBack.pressed)
	                    {
	                        event.setYaw(this.mc.thePlayer.rotationYaw);
	                    }
	                    else if (this.mc.gameSettings.keyBindLeft.pressed)
	                    {
	                        event.setYaw(this.mc.thePlayer.rotationYaw + 90.0f);
	                    }
	                    else if (this.mc.gameSettings.keyBindRight.pressed)
	                    {
	                        event.setYaw(this.mc.thePlayer.rotationYaw - 90.0f);
	                    }
	                }
	                else
	                {
	                    event.setYaw(rot[0]);
	                }
	            }
	        }
	        else
	        {
	            if (this.rotated)
	            {
	                PlayerUtil.setSpeed(0.03877341815081586);
	            }
	            else
	            {
	                PlayerUtil.setSpeed(0.08621806584246793);
	            }
	            
	            if (PlayerUtil.MovementInput())
	            {
	                PlayerUtil.setSpeed(0.13);
	            }
	            
	            this.rotated = false;
	            this.blockpos = null;
	            this.facing = null;
	            
	            if (block != -1 && item != null && item instanceof ItemBlock)
	            {
	                if (this.getSetManager().getSettingById(132).getValBoolean())
	                {
	                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(block));
	                }
	                
	                BlockPos pos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
	                this.setBlockAndFacing(pos);
	                
	                if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)
	                {
	                    this.rotated = true;
	                }
	                
	                float[] rot2 = CombatUtil.getRotationsNeededBlock(this.blockpos.getX(), this.blockpos.getY(), this.blockpos.getZ());
	                float[] rot = CombatUtil.getIntaveRots(this.blockpos, this.facing);
	                event.setYaw((float)((double)rot[0] + ThreadLocalRandom.current().nextDouble(-0.1, 0.1)));;
	                event.setPitch(82.500114f);
	            }
	        }
		}
	}
	
	@EventTarget
	public void onSafeWalk(EventSafeWalk event)
	{
		event.setProtectGround(true);
		
		if (!couldBlockBePlaced())
		{
			return;
		}
		
        if (this.blockData != null)
        {
            int block1 = this.getBlockItem();
            Random rand = new Random();
            Item item1 = this.mc.thePlayer.inventory.getStackInSlot(block1).getItem();
            
            if (block1 != -1 && item1 != null && item1 instanceof ItemBlock)
            {
                if ((this.getMode().equalsIgnoreCase("GommeHD") || this.getMode().equalsIgnoreCase("Rewinside") || this.getMode().equalsIgnoreCase("CubeCraft")) && !this.couldBlockBePlaced())
                {
                    return;
                }
                
                Vec3 hitVec = new Vec3(this.blockData.pos).addVector(0.5, 0.5, 0.5).add(new Vec3(this.blockData.face.getDirectionVec()).subtract(0.3, 0.3, 0.3));
                
                if ((!this.getMode().equalsIgnoreCase("Rewinside") && !this.getMode().equalsIgnoreCase("AAC") && !this.getMode().equalsIgnoreCase("GommeHD") || this.delay.hasReach((long)(this.getMode().equalsIgnoreCase("GommeHD") ? ThreadLocalRandom.current().nextLong(0L, 100L) : (this.getMode().equalsIgnoreCase("Rewinside") ? 0.0 : (double) this.getSetManager().getSettingById(134).getValueD() + ThreadLocalRandom.current().nextDouble(30.0, 80.0))))) && this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(block1), this.blockData.pos, this.blockData.face, hitVec))
                {
                    this.delay.reset();
                    this.blockData = null;
                    this.time.reset();
                    
                    if (this.getSetManager().getSettingById(131).getValBoolean())
                    {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                    else
                    {
                        this.mc.thePlayer.swingItem();
                    }
                }
                else if (this.getMode().equalsIgnoreCase("CubeCraft"))
                {
                    if (this.delay.hasReach(this.getSetManager().getSettingById(134).getValueD() + rand.nextInt(50)) && this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(block1), this.blockData.pos, this.blockData.face, hitVec))
                    {
                        this.delay.reset();
                        this.blockData = null;
                        this.time.reset();
                        
                        if (this.getSetManager().getSettingById(131).getValBoolean())
                        {
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        }
                        else
                        {
                            this.mc.thePlayer.swingItem();
                        }
                    }
                    else if (this.delay.hasReach(this.getSetManager().getSettingById(134).getValueD()) && this.getMode().equalsIgnoreCase("Normal"))
                    {
                        if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getStackInSlot(block1), this.blockData.pos, this.blockData.face, hitVec))
                        {
                            this.delay.reset();
                            this.blockData = null;
                            
                            if (this.getSetManager().getSettingById(131).getValBoolean())
                            {
                                this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            }
                            else
                            {
                                this.mc.thePlayer.swingItem();
                            }
                        }
                        
                        this.delay.reset();
                    }
                }
            }
        }
	}
	
    private boolean canPlace(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 vec3)
    {
        if (heldStack.getItem() instanceof ItemBlock)
        {
            return ((ItemBlock)heldStack.getItem()).canPlaceBlockOnSide(worldIn, hitPos, side, player, heldStack);
        }
        
        return false;
    }
    
    private boolean couldBlockBePlaced()
    {
        double x = this.mc.thePlayer.posX;
        double z = this.mc.thePlayer.posZ;
        double d = ThreadLocalRandom.current().nextDouble(0.22, 0.25);
        
        switch (Scaffold.TheSwitchTable()[this.mc.thePlayer.getHorizontalFacing().ordinal()])
        {
            case 3:
            {
                if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.1, z + d)).getBlock() != Blocks.air) break;
                return true;
            }
            case 6:
            {
                if (this.mc.theWorld.getBlockState(new BlockPos(x - d, this.mc.thePlayer.posY - 0.1, this.mc.thePlayer.posZ)).getBlock() != Blocks.air) break;
                return true;
            }
            case 4:
            {
                if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.1, z - d)).getBlock() != Blocks.air) break;
                return true;
            }
            case 5:
            {
                if (this.mc.theWorld.getBlockState(new BlockPos(x + d, this.mc.thePlayer.posY - 0.1, this.mc.thePlayer.posZ)).getBlock() != Blocks.air) break;
                return true;
            }
        }
        
        return false;
    }

    private void setBlockAndFacing(BlockPos bp)
    {
        if (this.mc.theWorld.getBlockState(bp.add(0, -1, 0)).getBlock() != Blocks.air)
        {
            this.blockpos = bp.add(0, -1, 0);
            this.facing = EnumFacing.UP;
        }
        else if (this.mc.theWorld.getBlockState(bp.add(-1, 0, 0)).getBlock() != Blocks.air)
        {
            this.blockpos = bp.add(-1, 0, 0);
            this.facing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(bp.add(1, 0, 0)).getBlock() != Blocks.air)
        {
            this.blockpos = bp.add(1, 0, 0);
            this.facing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(bp.add(0, 0, -1)).getBlock() != Blocks.air)
        {
            this.blockpos = bp.add(0, 0, -1);
            this.facing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(bp.add(0, 0, 1)).getBlock() != Blocks.air)
        {
            this.blockpos = bp.add(0, 0, 1);
            this.facing = EnumFacing.NORTH;
        }
        else
        {
            bp = null;
            this.facing = null;
        }
    }

    private void sendCurrentItem()
    {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
    }

    private int getBlockItem()
    {
        int block = -1;
        
        for (int i = 8; i >= 0; --i)
        {
            if (this.mc.thePlayer.inventory.getStackInSlot(i) == null || !(this.mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) || this.mc.thePlayer.getHeldItem() != this.mc.thePlayer.inventory.getStackInSlot(i) && !this.getSetManager().getSettingById(132).getValBoolean()) continue;
            block = i;
        }
        
        return block;
    }

    public BlockData getBlockData(BlockPos pos, int i)
    {
        return this.mc.theWorld.getBlockState(pos.add(0, 0, i)).getBlock() != Blocks.air ? new BlockData(pos.add(0, 0, i), EnumFacing.NORTH) : (this.mc.theWorld.getBlockState(pos.add(0, 0, -i)).getBlock() != Blocks.air ? new BlockData(pos.add(0, 0, -i), EnumFacing.SOUTH) : (this.mc.theWorld.getBlockState(pos.add(i, 0, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(i, 0, 0), EnumFacing.WEST) : (this.mc.theWorld.getBlockState(pos.add(-i, 0, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(-i, 0, 0), EnumFacing.EAST) : (this.mc.theWorld.getBlockState(pos.add(0, -i, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(0, -i, 0), EnumFacing.UP) : null))));
    }

    public Vec3 getBlockSide(BlockPos pos, EnumFacing face)
    {
        if (face == EnumFacing.NORTH)
        {
            return new Vec3(pos.getX(), pos.getY(), (double)pos.getZ() - 0.5);
        }
        
        if (face == EnumFacing.EAST)
        {
            return new Vec3((double)pos.getX() + 0.5, pos.getY(), pos.getZ());
        }
        
        if (face == EnumFacing.SOUTH)
        {
            return new Vec3(pos.getX(), pos.getY(), (double)pos.getZ() + 0.5);
        }
        
        if (face == EnumFacing.WEST)
        {
            return new Vec3((double)pos.getX() - 0.5, pos.getY(), pos.getZ());
        }
        
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public static Vec3 getVec3(BlockPos pos, EnumFacing face) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        x += (double) face.getFrontOffsetX() / 2;
        z += (double) face.getFrontOffsetZ() / 2;
        y += (double) face.getFrontOffsetY() / 2;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += ThreadLocalRandom.current().nextDouble(0.3, -0.3);
            z += ThreadLocalRandom.current().nextDouble(0.3, -0.3);
        } else {
            y += ThreadLocalRandom.current().nextDouble(0.3, -0.3);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += ThreadLocalRandom.current().nextDouble(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += ThreadLocalRandom.current().nextDouble(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }
    
    private BlockData getBlockData(BlockPos pos) {
    	
        if (isPosSolid(pos.add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos.add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos.add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos.add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos.add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos1 = pos.add(-1, 0, 0);
        if (isPosSolid(pos1.add(0, -1, 0))) {
            return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos1.add(-1, 0, 0))) {
            return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos1.add(1, 0, 0))) {
            return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos1.add(0, 0, 1))) {
            return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos1.add(0, 0, -1))) {
            return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos2 = pos.add(1, 0, 0);
        if (isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos3 = pos.add(0, 0, 1);
        if (isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos4 = pos.add(0, 0, -1);
        if (isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos19 = pos.add(-2, 0, 0);
        if (isPosSolid(pos1.add(0, -1, 0))) {
            return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos1.add(-1, 0, 0))) {
            return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos1.add(1, 0, 0))) {
            return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos1.add(0, 0, 1))) {
            return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos1.add(0, 0, -1))) {
            return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos29 = pos.add(2, 0, 0);
        if (isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos39 = pos.add(0, 0, 2);
        if (isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos49 = pos.add(0, 0, -2);
        if (isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos5 = pos.add(0, -1, 0);
        if (isPosSolid(pos5.add(0, -1, 0))) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos5.add(-1, 0, 0))) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos5.add(1, 0, 0))) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos5.add(0, 0, 1))) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos5.add(0, 0, -1))) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos6 = pos5.add(1, 0, 0);
        if (isPosSolid(pos6.add(0, -1, 0))) {
            return new BlockData(pos6.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos6.add(-1, 0, 0))) {
            return new BlockData(pos6.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos6.add(1, 0, 0))) {
            return new BlockData(pos6.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos6.add(0, 0, 1))) {
            return new BlockData(pos6.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos6.add(0, 0, -1))) {
            return new BlockData(pos6.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos7 = pos5.add(-1, 0, 0);
        if (isPosSolid(pos7.add(0, -1, 0))) {
            return new BlockData(pos7.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos7.add(-1, 0, 0))) {
            return new BlockData(pos7.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos7.add(1, 0, 0))) {
            return new BlockData(pos7.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos7.add(0, 0, 1))) {
            return new BlockData(pos7.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos7.add(0, 0, -1))) {
            return new BlockData(pos7.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos8 = pos5.add(0, 0, 1);
        if (isPosSolid(pos8.add(0, -1, 0))) {
            return new BlockData(pos8.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos8.add(-1, 0, 0))) {
            return new BlockData(pos8.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos8.add(1, 0, 0))) {
            return new BlockData(pos8.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos8.add(0, 0, 1))) {
            return new BlockData(pos8.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos8.add(0, 0, -1))) {
            return new BlockData(pos8.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos9 = pos5.add(0, 0, -1);
        if (isPosSolid(pos9.add(0, -1, 0))) {
            return new BlockData(pos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (isPosSolid(pos9.add(-1, 0, 0))) {
            return new BlockData(pos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (isPosSolid(pos9.add(1, 0, 0))) {
            return new BlockData(pos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (isPosSolid(pos9.add(0, 0, 1))) {
            return new BlockData(pos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (isPosSolid(pos9.add(0, 0, -1))) {
            return new BlockData(pos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    private boolean isPosSolid(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        if ((block.getMaterial().isSolid() || !block.isTranslucent() || block.isFullCube() || block instanceof BlockLadder || block instanceof BlockCarpet
                || block instanceof BlockSnow || block instanceof BlockSkull)
                && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer)) {
            return true;
        }
        return false;
    }
	
    public class BlockData
    {
        public BlockPos pos;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face)
        {
            this.pos = position;
            this.face = face;
        }
    }
}