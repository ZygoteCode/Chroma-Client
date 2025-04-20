package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventBlockCollide;
import net.minecraft.client.particle.chroma.event.events.EventUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class Jesus extends Module
{
    private boolean was = false;
    private int tick;
	
	public Jesus()
	{
		super("Jesus", 62, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("NCP");
		modes.add("AAC");
		modes.add("Horizon");
		this.getSetManager().rSetting(new Setting(205, "Mode", "", this, "NCP", modes));
		super.setup();
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event)
	{
		if (event.getState().equals(EventState.PRE))
		{
			this.setSuffix(" §7" + this.getMode());
			
	        if (this.getMode().equals("AAC"))
	        {
	            if (this.mc.thePlayer.isInWater() || mc.thePlayer.isInLava())
	            {
	                this.was = true;
	                //Wrapper.INSTANCE.multiply(1.135);
	                mc.thePlayer.motionX *= 1.135;
	                mc.thePlayer.motionZ *= 1.135;
	                this.mc.gameSettings.keyBindJump.pressed = true;
	                PlayerUtil.setSpeed(PlayerUtil.getSpeed());
	            }
	            else if (this.was)
	            {
	                this.mc.gameSettings.keyBindJump.pressed = false;
	                this.was = false;
	            }
	        }
	        else if (this.getMode().equals("NCP"))
	        {
	            BlockPos bp = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.2, this.mc.thePlayer.posZ);
	            BlockPos bp1 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
	            BlockPos bp2 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.4, this.mc.thePlayer.posZ);
	            BlockPos bp3 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.2, this.mc.thePlayer.posZ);
	            this.tick = Math.abs(this.tick) != 1 ? 1 : -this.tick;
	            
	            if ((this.mc.theWorld.getBlockState(bp).getBlock() == Blocks.water || this.mc.theWorld.getBlockState(bp).getBlock() == Blocks.lava) && this.mc.theWorld.getBlockState(bp1).getBlock() == Blocks.air)
	            {
	                event.setPosY(event.getPosY() + (double)this.tick * 0.01);
	            }
	            
	            if (this.mc.theWorld.getBlockState(bp1).getBlock() == Blocks.water)
	            {
	                this.mc.thePlayer.motionY = 0.1;
	            }
	        }
	        else if (this.getMode().equals("Horizon"))
	        {
	            if (mc.thePlayer.isInWater())
	            {
	                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1, mc.thePlayer.posZ);
	                mc.thePlayer.motionY = 0;
	            }
	        }
		}
	}
	
	@EventTarget
	public void onBlockCollide(EventBlockCollide event)
	{
        BlockPos bp = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.2, this.mc.thePlayer.posZ);
        BlockPos bp1 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
        
        if ((this.mc.theWorld.getBlockState(bp).getBlock() == Blocks.water || this.mc.theWorld.getBlockState(bp).getBlock() == Blocks.lava) && this.mc.theWorld.getBlockState(bp1).getBlock() == Blocks.air)
        {
            if (this.getMode().equals("NCP"))
            {
                event.getList().add(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(this.mc.thePlayer.posX, (int)this.mc.thePlayer.posY - 1, this.mc.thePlayer.posZ));
            }
        }
	}
}