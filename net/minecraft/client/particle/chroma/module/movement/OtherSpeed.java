package net.minecraft.client.particle.chroma.module.movement;

import java.util.ArrayList;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.BlockAir;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.BlockUtils;
import net.minecraft.client.particle.chroma.utils.MovementUtils;
import net.minecraft.client.particle.chroma.utils.PlayerUtil;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class OtherSpeed extends Module
{
	private boolean legitJump;
	 private boolean legitHop;
	    private boolean firstJump;
	    private boolean waitForGround;
	    private boolean damageToGround;
	    
	
	public OtherSpeed()
	{
		super("OtherSpeed", 126, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("AAC 1.9.10");
		options.add("AAC 3.2.2");
		options.add("AAC 3.3.1");
		options.add("AAC 3.3.9");
		options.add("AAC 3.3.10");
		options.add("AAC 3.3.11");
		options.add("AAC 3.3.15");
		options.add("AAC 3.5.0");
		options.add("AAC 3.6.4");
		options.add("General AAC (1)");
		options.add("General AAC (2)");
		options.add("General AAC (3)");
		options.add("General AAC (4)");
		options.add("General AAC (5)");
		options.add("General AAC (6)");
		options.add("General AAC (7)");
		options.add("General AAC (8)");
		options.add("General AAC (9)");
		options.add("General AAC (10)");
		options.add("General AAC (11)");
		options.add("General AAC (12)");
		options.add("General AAC (13)");
		options.add("General AAC (14)");
		options.add("General AAC (15)");
		options.add("General AAC (16)");
		this.getSetManager().rSetting(new Setting(306, "Mode", "The working mode of the Speed module.", this, "AAC 3.5.0", options));
		super.setup();
	}
	
	@Override
	public void onDisable()
	{
		mc.thePlayer.jumpMovementFactor = 0.02F;
		mc.timer.timerSpeed = 1.0F;
		legitJump = true;
		mc.thePlayer.speedInAir = 0.02F;
		legitHop = true;
		firstJump = true;
		damageToGround = false;
		super.onDisable();
	}
	
	@Override
	public void onEnable()
	{
		
		super.onEnable();
	}
	
	@EventTarget
	public void onUpdate(EventSuperUpdate event)
	{
		this.setSuffix(" §7" + this.getMode());
		
		if (mc.thePlayer.isSneaking() || mc.thePlayer.isBlocking())
		{
			return;
		}
		
        if (mc.thePlayer.moveForward > 0.0f && !mc.thePlayer.isCollidedHorizontally)
        {
            mc.thePlayer.setSprinting(true);
        }
        
		if (this.getMode().equals("AAC 3.3.10"))
		{
	        if (this.mc.thePlayer.hurtTime == 0)
	        {
	            if (PlayerUtil.MovementInput())
	            {
	                if (this.legitHop)
	                {
	                    if (this.mc.thePlayer.onGround)
	                    {
	                        this.mc.thePlayer.jump();
	                        this.mc.thePlayer.onGround = false;
	                        this.legitHop = false;
	                    }
	                    
	                    return;
	                }
	                
	                if (this.mc.thePlayer.onGround)
	                {
	                    this.mc.thePlayer.onGround = false;
	                    PlayerUtil.setSpeed(0.375);
	                    this.mc.thePlayer.jump();
	                    this.mc.thePlayer.motionY = 0.41;
	                } 
	                else
	                {
	                    this.mc.thePlayer.speedInAir = 0.0211f;
	                }
	            }
	            else
	            {
	                this.mc.thePlayer.motionZ = 0.0;
	                this.mc.thePlayer.motionX = 0.0;
	                this.legitHop = true;
	            }
	            
	            if (this.mc.thePlayer.isAirBorne)
	            {
	                PlayerUtil.setSpeed(PlayerUtil.getSpeed());
	            }
	        }
		}
		else if (this.getMode().equals("AAC 3.2.2"))
		{
			if (mc.gameSettings.keyBindForward.pressed)
			{
				mc.gameSettings.keyBindJump.pressed = false;
				
				if (mc.thePlayer.onGround)
				{
					mc.thePlayer.jump();
					mc.timer.timerSpeed = 1.02F;
					mc.thePlayer.motionX *= 1.01D;
					mc.thePlayer.motionZ *= 1.01D;
					mc.thePlayer.moveStrafing *= 2;
				}
				else
				{
					mc.thePlayer.jumpMovementFactor = 0.0265F;
				}
			}
		}
		else if (this.getMode().equals("AAC 1.9.10"))
		{
			if (mc.gameSettings.keyBindForward.pressed)
			{
				mc.gameSettings.keyBindJump.pressed = false;
				
				if (mc.thePlayer.onGround)
				{
					mc.thePlayer.jump();
					mc.thePlayer.motionX *= 1.1D;
					mc.thePlayer.motionZ *= 1.1D;
					mc.thePlayer.moveStrafing *= 2;
				}
				else
				{
					mc.thePlayer.jumpMovementFactor = 0.03F;
				}
			}
		}
		else if (this.getMode().equals("AAC 3.5.0"))
		{
            if (MovementUtils.isMoving() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava())
            {
                mc.thePlayer.jumpMovementFactor += 0.00208F;

                if(mc.thePlayer.fallDistance <= 1F) {
                    if(mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionX *= 1.0118F;
                        mc.thePlayer.motionZ *= 1.0118F;
                    }else{
                        mc.thePlayer.motionY -= 0.0147F;

                        mc.thePlayer.motionX *= 1.00138F;
                        mc.thePlayer.motionZ *= 1.00138F;
                    }
                }
            }
		}
		else if (this.getMode().equals("AAC 3.3.15"))
		{
	        if(!MovementUtils.isMoving() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava() ||
	                mc.thePlayer.isOnLadder() || mc.thePlayer.isRiding())
	            return;

	        if(mc.thePlayer.hurtTime > 0) {
	            damageToGround = true;
	            return;
	        }

	        if(mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
	            // MotionXYZ
	            mc.thePlayer.jump();
	            if(!damageToGround) mc.thePlayer.motionY = 0.41;
	            damageToGround = false;
	        }else if(!mc.thePlayer.isCollidedHorizontally && !damageToGround) {
	            // Motion XZ
	            mc.thePlayer.jumpMovementFactor = 0.027F;

	            final double boostUp = mc.thePlayer.motionY <= 0.0D ? ThreadLocalRandom.current().nextDouble(1.002F, 1.0023F) : ThreadLocalRandom.current().nextDouble(1.0059F, 1.0061F);
	            mc.thePlayer.motionX *= boostUp;
	            mc.thePlayer.motionZ *= boostUp;

	            MovementUtils.forward(0.0019);

	            // Motion Y
	            mc.thePlayer.motionY -= 0.0149F;
	        }
		}
		else if (this.getMode().equals("AAC 3.3.1"))
		{
            if (this.mc.thePlayer.onGround && PlayerUtil.MovementInput()) 
            {
                this.mc.thePlayer.jump();
            }
            else
            {
                this.mc.thePlayer.motionY = -0.21;
            }
		}
		else if (this.getMode().equals("AAC 3.3.9"))
		{
            if (PlayerUtil.MovementInput())
            {
                if (this.mc.thePlayer.hurtTime == 0)
                {
                    if (this.mc.thePlayer.onGround)
                    {
                        this.mc.thePlayer.jump();
                        this.mc.thePlayer.motionY = 0.3875;
                    }
                    else
                    {
                        this.mc.thePlayer.motionY -= 0.0145;
                    }
                    
                    PlayerUtil.toFwd(0.00149);
                }
            }
            else
            {
                this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = (double)0;
            }
		}
		else if (this.getMode().equals("AAC 3.3.11"))
		{
            this.mc.timer.timerSpeed = 3.0F;
            this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
		}
		else if (this.getMode().equals("General AAC (1)"))    	
        {
            if(mc.thePlayer.isInWater())
                return;

            if(MovementUtils.isMoving()) {
                if(mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    mc.thePlayer.motionX *= 1.02D;
                    mc.thePlayer.motionZ *= 1.02D;
                }else if(mc.thePlayer.motionY > -0.2D) {
                    mc.thePlayer.jumpMovementFactor = 0.08F;
                    mc.thePlayer.motionY += 0.0143099999999999999999999999999D;
                    mc.thePlayer.jumpMovementFactor = 0.07F;
                }
            }else{
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
            }
        }
        else if (this.getMode().equals("General AAC (2)"))
        {
            mc.timer.timerSpeed = 1F;

            if(mc.thePlayer.isInWater())
                return;

            if(MovementUtils.isMoving()) {
                if(mc.thePlayer.onGround) {
                    if(legitJump) {
                        mc.thePlayer.jump();
                        legitJump = false;
                        return;
                    }

                    mc.thePlayer.motionY = 0.3852;
                    mc.thePlayer.onGround = false;
                    MovementUtils.strafe(0.374F);
                }else if(mc.thePlayer.motionY < 0D) {
                    mc.thePlayer.speedInAir = 0.0201F;
                    mc.timer.timerSpeed = 1.02F;
                }else
                    mc.timer.timerSpeed = 1.01F;
            }else{
                legitJump = true;
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
            }
        }
        else if (this.getMode().equals("General AAC (3)"))
        {
            if(MovementUtils.isMoving()) {
                if(legitHop) {
                    if(mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.onGround = false;
                        legitHop = false;
                    }
                    return;
                }

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.onGround = false;
                    MovementUtils.strafe(0.375F);
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY = 0.41;
                }else
                    mc.thePlayer.speedInAir = 0.0211F;
            }else{
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
                legitHop = true;
            }
        }
        else if (this.getMode().equals("General AAC (4)"))
        {
            mc.timer.timerSpeed = 1F;

            if(mc.thePlayer.isInWater())
                return;

            if(MovementUtils.isMoving()) {
                if(mc.thePlayer.onGround) {
                    if(legitJump) {
                        mc.thePlayer.jump();
                        legitJump = false;
                        return;
                    }

                    mc.thePlayer.motionY = 0.41;
                    mc.thePlayer.onGround = false;
                    MovementUtils.strafe(0.374F);
                }else if(mc.thePlayer.motionY < 0D) {
                    mc.thePlayer.speedInAir = 0.0201F;
                    mc.timer.timerSpeed = 1.02F;
                }else
                    mc.timer.timerSpeed = 1.01F;
            }else{
                legitJump = true;
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
            }
        }
        else if (this.getMode().equals("General AAC (5)"))
        {
            mc.timer.timerSpeed = 1F;

            if(mc.thePlayer.isInWater())
                return;

            if(MovementUtils.isMoving()) {
                if(mc.thePlayer.onGround) {
                    if(legitJump) {
                        mc.thePlayer.motionY = 0.4;
                        MovementUtils.strafe(0.15F);
                        mc.thePlayer.onGround = false;
                        legitJump = false;
                        return;
                    }

                    mc.thePlayer.motionY = 0.41;
                    MovementUtils.strafe(0.47458485F);
                }

                if(mc.thePlayer.motionY < 0 && mc.thePlayer.motionY > -0.2)
                    mc.timer.timerSpeed = ((float) (1.2 + mc.thePlayer.motionY));

                mc.thePlayer.speedInAir = 0.022151F;
            }else{
                legitJump = true;
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
            }
        }
        else if (this.getMode().equals("General AAC (6)"))
        {
            if(!MovementUtils.isMoving() || mc.thePlayer.ridingEntity != null || mc.thePlayer.hurtTime > 0)
                return;

            if(mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0.405;
                mc.thePlayer.motionX *= 1.004;
                mc.thePlayer.motionZ *= 1.004;
                return;
            }

            final double speed = MovementUtils.getSpeed() * 1.0072D;
            final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
            mc.thePlayer.motionX = -Math.sin(yaw) * speed;
            mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        }
        else if (this.getMode().equals("General AAC (7)"))
        {
            if(mc.thePlayer.isInWater())
                return;

            if(MovementUtils.isMoving()) {
                mc.timer.timerSpeed = 1.08F;

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.399D;
                    float f = mc.thePlayer.rotationYaw * 0.017453292F;
                    mc.thePlayer.motionX -= MathHelper.sin(f) * 0.2F;
                    mc.thePlayer.motionZ += MathHelper.cos(f) * 0.2F;
                    mc.timer.timerSpeed = 2F;
                }else{
                    mc.thePlayer.motionY *= 0.97D;
                    mc.thePlayer.motionX *= 1.008D;
                    mc.thePlayer.motionZ *= 1.008D;
                }
            }else{
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
                mc.timer.timerSpeed = 1F;
            }
        }
        else if (this.getMode().equals("General AAC (8)"))
        {
            if(!MovementUtils.isMoving())
                return;

            mc.timer.timerSpeed = 1.2F;
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        }
        else if (this.getMode().equals("General AAC (9)"))
        {
            if(!MovementUtils.isMoving())
                return;

            mc.timer.timerSpeed = 1.2F;
            MovementUtils.strafe(0.02F);
        }
        else if (this.getMode().equals("General AAC (10)"))
        {
            if(MovementUtils.isMoving()) {
                if(mc.thePlayer.onGround) {
                    if(legitJump) {
                        mc.thePlayer.jump();
                        legitJump = false;
                        return;
                    }

                    mc.thePlayer.motionY = 0.343F;
                    MovementUtils.strafe(0.534F);
                }
            }else{
                legitJump = true;
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
            }
        }
        else if (this.getMode().equals("General AAC (11)"))
        {
            mc.timer.timerSpeed = 1F;

            if(mc.thePlayer.isInWater())
                return;

            if(MovementUtils.isMoving()) {
                mc.timer.timerSpeed = 1.09F;

                if(mc.thePlayer.onGround) {
                    if(legitJump) {
                        mc.thePlayer.jump();
                        legitJump = false;
                        return;
                    }

                    mc.thePlayer.motionY = 0.343F;
                    MovementUtils.strafe(0.534F);
                }
            }else{
                legitJump = true;
                mc.thePlayer.motionX = 0D;
                mc.thePlayer.motionZ = 0D;
            }
        }
        else if (this.getMode().equals("General AAC (12)"))
        {
            if(MovementUtils.isMoving()) {
                if(mc.thePlayer.hurtTime <= 0) {
                    if(mc.thePlayer.onGround) {
                        waitForGround = false;

                        if(!firstJump)
                            firstJump = true;

                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = 0.41;
                    }else{
                        if(waitForGround)
                            return;

                        if(mc.thePlayer.isCollidedHorizontally)
                            return;

                        firstJump = false;
                        mc.thePlayer.motionY -= 0.0149;
                    }

                    if(!mc.thePlayer.isCollidedHorizontally)
                        MovementUtils.forward(firstJump ? 0.0016 : 0.001799);
                }else{
                    firstJump = true;
                    waitForGround = true;
                }
            }else{
                mc.thePlayer.motionZ = 0;
                mc.thePlayer.motionX = 0;
            }

            final double speed = MovementUtils.getSpeed();
            mc.thePlayer.motionX = -(Math.sin(MovementUtils.getDirection()) * speed);
            mc.thePlayer.motionZ = Math.cos(MovementUtils.getDirection()) * speed;
        }
        else if (this.getMode().equals("General AAC (13)"))
        {
            if(!MovementUtils.isMoving())
                return;

            final float f = mc.thePlayer.rotationYaw * 0.017453292F;
            for(double d = 0.2; d <= 1.0D; d += 0.2) {
                final double x = mc.thePlayer.posX - MathHelper.sin(f) * d;
                final double z = mc.thePlayer.posZ + MathHelper.cos(f) * d;

                if(mc.thePlayer.posY < (int) mc.thePlayer.posY + 0.5 && !(BlockUtils.getBlock(new BlockPos(x, mc.thePlayer.posY, z)) instanceof BlockAir))
                    break;

                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, mc.thePlayer.posY, z, true));
            }
        }
        else if (this.getMode().equals("General AAC (14)"))
        {
            if(MovementUtils.isMoving() && !mc.thePlayer.isSneaking()) {
                mc.thePlayer.cameraPitch = 0F;

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.3425F;
                    mc.thePlayer.motionX *= 1.5893F;
                    mc.thePlayer.motionZ *= 1.5893F;
                }else
                    mc.thePlayer.motionY = -0.19D;
            }
        }
        else if (this.getMode().equals("General AAC (15)"))
        {
            if(MovementUtils.isMoving()) {
                mc.thePlayer.cameraPitch = 0F;

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY = 0.3851F;
                    mc.thePlayer.motionX *= 1.01;
                    mc.thePlayer.motionZ *= 1.01;
                }else
                    mc.thePlayer.motionY = -0.21D;
            }
        }
        else if (this.getMode().equals("General AAC (16)"))
        {
            if(MovementUtils.isMoving()) {
                if(mc.thePlayer.onGround) {
                    MovementUtils.strafe(0.56F);
                    mc.thePlayer.motionY = 0.41999998688697815;
                }else
                    MovementUtils.strafe(MovementUtils.getSpeed() * ((mc.thePlayer.fallDistance > 0.4F) ? 1.0F : 1.01F));
            }else{
                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;
            }
        }
		else if (this.getMode().equals("AAC 3.6.4"))
		{
            if (MovementUtils.isMoving() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava())
            {
                mc.thePlayer.jumpMovementFactor += 0.00208F;

                if(mc.thePlayer.fallDistance <= 1F) {
                    if(mc.thePlayer.onGround) {
                    	mc.timer.timerSpeed = 1.02F;
                        mc.thePlayer.jump();
                        mc.thePlayer.motionX *= 1.0118F;
                        mc.thePlayer.motionZ *= 1.0118F;
                    }else{
                    	mc.timer.timerSpeed = 1.03F;
                        mc.thePlayer.motionY -= 0.0147F;

                        mc.thePlayer.motionX *= 1.00138F;
                        mc.thePlayer.motionZ *= 1.00138F;
                    }
                }
            }
		}
	}
}