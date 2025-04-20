package net.minecraft.client.particle.chroma.module.movement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventApplyMotion;
import net.minecraft.client.particle.chroma.event.events.EventEntityMove;
import net.minecraft.client.particle.chroma.event.events.EventJump;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.utils.MovementUtils;
import net.minecraft.client.particle.chroma.utils.PlayerUtil;
import net.minecraft.client.particle.chroma.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class Speed extends Module
{
    private boolean legitHop = false;
	private int airMoves = 0;
	private int jumps;
    private int motionDelay;
    private float ground;
    private int motionTicks;
    private boolean move;
    private Timer timer;
    private int level = 1;
    private double moveSpeed = 0.2873;
    private double lastDist;
    private int timerDelay;
    private boolean damageToGround;
    private boolean jumped;
    private boolean canBoost;
    private boolean teleported;
    private float gwenSpeed;
	
	public Speed()
	{
		super("Speed", 45, Category.MOVEMENT);
	}
	
	@Override
	public void setup()
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add("NCP Latest");
		options.add("NCP Old (1)");
		options.add("NCP Old (2)");
		options.add("NCP Old (3)");
		options.add("NCP Old (4)");
		options.add("NCP Old (5)");	
		options.add("NCP Old (6)");
		options.add("NCP Old (7)");
		options.add("NCP Old (8)");
		options.add("NCP Old (9)");
		options.add("NCP Old (10)");
		options.add("NCP Old (11)");
		options.add("NCP Old (12)");
		options.add("NCP Old (13)");
		options.add("NCP Old (14)");
		options.add("NCP Old (15)");
		options.add("Spartan");
		options.add("Hypixel");
		options.add("Mineplex");
		this.getSetManager().rSetting(new Setting(145, "Mode", "The working mode of the Speed module.", this, "NCP Latest", options));
		timer = new Timer();
		super.setup();
	}
	
	@Override
	public void onToggle()
	{
		mc.timer.timerSpeed = 1.0F;
		jumps = 0;
		airMoves = 0;
		mc.thePlayer.speedInAir = 0.02F;
		timer.reset();
		moveSpeed = getBaseMoveSpeed();
		lastDist = 0;
		timerDelay = 0;
		mc.thePlayer.jumpMovementFactor = 0.02F;
		damageToGround = false;
		
		if (this.getMode().equals("NCP Old (14)"))
		{
			level = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 || mc.thePlayer.isCollidedVertically ? 1 : 4;
		}
		else if (this.getMode().equals("NCP Old (15)"))
		{
			level = 4;
			moveSpeed = 0;
		}
		
	       if(mc.thePlayer.onGround)
	            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
		
		super.onToggle();
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
		

		if (this.getMode().equals("NCP Old (1)"))
		{
            if (this.mc.thePlayer.onGround && PlayerUtil.MovementInput() && !this.mc.thePlayer.isInWater())
            {
                this.mc.timer.timerSpeed = 1.0f;
                this.mc.thePlayer.jump();
            }
            else if (PlayerUtil.MovementInput() && !this.mc.thePlayer.isInWater())
            {
                this.mc.timer.timerSpeed = 1.095f;
                PlayerUtil.setSpeed(0.26);
            }
            
            if (!PlayerUtil.MovementInput())
            {
                this.mc.thePlayer.motionZ = 0.0;
                this.mc.thePlayer.motionX = 0.0;
            }
		}
		else if (this.getMode().equals("NCP Old (2)"))
		{
            if (PlayerUtil.MovementInput())
            {
                this.mc.thePlayer.motionY = 0.399399995003033;
                
                if (this.mc.thePlayer.onGround)
                {
                    this.mc.thePlayer.jump();
                }
                
                this.mc.timer.timerSpeed = 1.5f;
            }
            
            if (!this.mc.thePlayer.onGround)
            {
                this.mc.thePlayer.motionY = -0.5;
            }
		}
		else if (this.getMode().equals("NCP Old (3)"))
		{
			if (mc.gameSettings.keyBindForward.pressed)
			{
				mc.gameSettings.keyBindJump.pressed = false;
				
				if (mc.thePlayer.onGround)
				{
					mc.thePlayer.jump();
					mc.timer.timerSpeed = 1.05F;
					mc.thePlayer.motionX *= 1.1D;
					mc.thePlayer.motionZ *= 1.1D;
					mc.thePlayer.moveStrafing *= 2;
				}
				else
				{
					mc.thePlayer.jumpMovementFactor = 0.0265F;
				}
			}
		}
		else if (this.getMode().equals("NCP Old (5)"))
		{
			if (mc.gameSettings.keyBindForward.pressed)
			{
				mc.gameSettings.keyBindJump.pressed = false;
				
				if (mc.thePlayer.onGround)
				{
					mc.thePlayer.jump();
					mc.timer.timerSpeed = 1.0F;
					mc.thePlayer.motionX *= 1.0808D;
					mc.thePlayer.motionZ *= 1.0808D;
					mc.thePlayer.moveStrafing *= 2;
				}
				else
				{
					mc.thePlayer.jumpMovementFactor = 0.0265F;
				}
			}
		}

		else if (this.getMode().equals("NCP Old (4)"))
		{
	        if (mc.thePlayer.isSneaking() || mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f)
	        {
	            return;
	        }
	        
	        if (mc.thePlayer.onGround)
	        {
	            double maxSpeed;
	            mc.thePlayer.motionY += 0.1;
	            mc.thePlayer.motionX *= 1.8;
	            mc.thePlayer.motionZ *= 1.8;
	            double currentSpeed = Math.sqrt(Math.pow(mc.thePlayer.motionX, 2.0) + Math.pow(mc.thePlayer.motionZ, 2.0));
	            
	            if (currentSpeed > (maxSpeed = 0.6600000262260437))
	            {
	                mc.thePlayer.motionX = mc.thePlayer.motionX / currentSpeed * maxSpeed;
	                mc.thePlayer.motionZ = mc.thePlayer.motionZ / currentSpeed * maxSpeed;
	            }
	        }
		}

		else if (this.getMode().equals("Spartan"))
		{
			if (mc.gameSettings.keyBindForward.pressed)
			{
				mc.gameSettings.keyBindJump.pressed = false;
				
				if (mc.thePlayer.onGround)
				{
					mc.thePlayer.jump();
					this.airMoves = 0;
				}
				else
				{
					mc.timer.timerSpeed = 1.08F;
					
					if (this.airMoves >= 3)
					{
						mc.thePlayer.jumpMovementFactor = 0.0275F;
					}
					
					if (this.airMoves >= 5 && this.airMoves % 2 == 0.0)
					{
						mc.thePlayer.jumpMovementFactor = 0.0238F;
					}
					
					this.airMoves++;
				}
			}
		}
		else if (this.getMode().equals("NCP Old (6)"))
		{
	        if (!MovementUtils.isMoving())
	            return;

	        if (mc.thePlayer.fallDistance > 3.994)
	            return;

	        if (mc.thePlayer.isInWater() || mc.thePlayer.isOnLadder() || mc.thePlayer.isCollidedHorizontally)
	            return;

	        mc.thePlayer.posY -= 0.3993000090122223;
	        mc.thePlayer.motionY = -1000.0;
	        mc.thePlayer.cameraPitch = 0.3F;
	        mc.thePlayer.distanceWalkedModified = 44.0F;
	        mc.timer.timerSpeed = 1F;

	        if (mc.thePlayer.onGround)
	        {
	            mc.thePlayer.posY += 0.3993000090122223;
	            mc.thePlayer.motionY = 0.3993000090122223;
	            mc.thePlayer.distanceWalkedOnStepModified = 44.0f;
	            mc.thePlayer.motionX *= 1.590000033378601;
	            mc.thePlayer.motionZ *= 1.590000033378601;
	            mc.thePlayer.cameraPitch = 0.0f;
	            mc.timer.timerSpeed = 1.199F;
	        }
		}
		else if (this.getMode().equals("NCP Old (7)"))
		{
	        if(mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || mc.thePlayer.isInWeb || !MovementUtils.isMoving())
	            return;

	        if(mc.thePlayer.onGround)
	            mc.thePlayer.jump();
	        else
	            mc.thePlayer.motionY = -1D;

	        MovementUtils.strafe();
		}
		else if (this.getMode().equals("NCP Old (8)"))
		{
	        if(mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || mc.thePlayer.isInWeb || !MovementUtils.isMoving() || mc.thePlayer.isInWater())
	            return;

	        if(jumps >= 4 && mc.thePlayer.onGround)
	            jumps = 0;

	        if(mc.thePlayer.onGround) {
	            mc.thePlayer.motionY = jumps <= 1 ? 0.42F : 0.4F;

	            float f = mc.thePlayer.rotationYaw * 0.017453292F;
	            mc.thePlayer.motionX -= MathHelper.sin(f) * 0.2F;
	            mc.thePlayer.motionZ += MathHelper.cos(f) * 0.2F;

	            jumps++;
	        }else if(jumps <= 1)
	            mc.thePlayer.motionY = -5D;

	        MovementUtils.strafe();
		}
		else if (this.getMode().equals("NCP Old (9)"))
		{
			mc.timer.timerSpeed = 1.0865F;
	        if(MovementUtils.isMoving()) {
	            if(mc.thePlayer.onGround) {
	                mc.thePlayer.jump();
	                mc.thePlayer.speedInAir = 0.0223F;
	            }

	            MovementUtils.strafe();
	        }else{
	            mc.thePlayer.motionX = 0D;
	            mc.thePlayer.motionZ = 0D;
	        }
		}
		else if (this.getMode().equals("NCP Old (10)"))
		{
			mc.timer.timerSpeed = 1.0866F;
	        if(MovementUtils.isMoving()) {
	            if(mc.thePlayer.onGround) {
	                mc.thePlayer.jump();
	                mc.thePlayer.motionX *= 1.01D;
	                mc.thePlayer.motionZ *= 1.01D;
	                mc.thePlayer.speedInAir = 0.0223F;
	            }

	            mc.thePlayer.motionY -= 0.00099999D;

	            MovementUtils.strafe();
	        }else{
	            mc.thePlayer.motionX = 0D;
	            mc.thePlayer.motionZ = 0D;
	        }
		}
		else if (this.getMode().equals("NCP Old (11)"))
		{
	        if(!MovementUtils.isMoving())
	            return;

	        if(mc.thePlayer.onGround && !mc.thePlayer.movementInput.jump) {
	            mc.thePlayer.motionY += 0.1;

	            final double multiplier = 1.8;

	            mc.thePlayer.motionX *= multiplier;
	            mc.thePlayer.motionZ *= multiplier;

	            final double currentSpeed = Math.sqrt(Math.pow(mc.thePlayer.motionX, 2) + Math.pow(mc.thePlayer.motionZ, 2));
	            final double maxSpeed = 0.66;
	            if(currentSpeed > maxSpeed) {
	                mc.thePlayer.motionX = mc.thePlayer.motionX / currentSpeed * maxSpeed;
	                mc.thePlayer.motionZ = mc.thePlayer.motionZ / currentSpeed * maxSpeed;
	            }
	        }

	        MovementUtils.strafe();
		}
		else if (this.getMode().equals("NCP Old (12)"))
		{
	        double speed = 3.1981D;
	        double offset = 4.69D;
	        boolean shouldOffset = true;

	        for(final Object o : mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX / offset, 0.0D, mc.thePlayer.motionZ / offset))) {
	            if(o instanceof AxisAlignedBB) {
	                shouldOffset = false;
	                break;
	            }
	        }

	        if(mc.thePlayer.onGround && ground < 1.0F)
	            ground += 0.2F;

	        if(!mc.thePlayer.onGround)
	            ground = 0.0F;

	        if(ground == 1.0F && shouldSpeedUp()) {
	            if(!mc.thePlayer.isSprinting())
	                offset += 0.8D;

	            if(mc.thePlayer.moveStrafing != 0F) {
	                speed -= 0.1D;
	                offset += 0.5D;
	            }

	            if(mc.thePlayer.isInWater())
	                speed -= 0.1D;

	            motionDelay += 1;

	            switch(motionDelay) {
	                case 1:
	                    mc.thePlayer.motionX *= speed;
	                    mc.thePlayer.motionZ *= speed;
	                    break;
	                case 2:
	                    mc.thePlayer.motionX /= 1.458D;
	                    mc.thePlayer.motionZ /= 1.458D;
	                    break;
	                case 4:
	                    if(shouldOffset)
	                        mc.thePlayer.setPosition(mc.thePlayer.posX + mc.thePlayer.motionX / offset, mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ / offset);

	                    motionDelay = 0;
	                    break;
	            }
	        }
		}
		else if (this.getMode().equals("NCP Old (13)"))
		{
	        if(mc.thePlayer.movementInput.moveForward > 0.0f || mc.thePlayer.movementInput.moveStrafe > 0.0f) {
	            final double speed = 4.25;

	            if(mc.thePlayer.onGround) {
	                mc.thePlayer.jump();

	                if(motionTicks == 1) {
	                    timer.reset();
	                    if(move) {
	                        mc.thePlayer.motionX = 0;
	                        mc.thePlayer.motionZ = 0;
	                        move = false;
	                    }
	                    motionTicks = 0;
	                }else
	                    motionTicks = 1;
	            }else if(!move && motionTicks == 1 && timer.hasReach(250D)) {
	                mc.thePlayer.motionX *= speed;
	                mc.thePlayer.motionZ *= speed;
	                move = true;
	            }

	            if(!mc.thePlayer.onGround)
	                MovementUtils.strafe();            
	        }
		}
		else if (this.getMode().equals("NCP Old (14)"))
		{
	        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
	        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
	        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
		else if (this.getMode().equals("NCP Old (15)"))
		{
	        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
	        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
	        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
		else if (this.getMode().equals("NCP Latest"))
		{
			if (jumped)
			{
	            if (mc.thePlayer.onGround || mc.thePlayer.capabilities.isFlying)
	            {
	                jumped = false;
	                mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
	                return;
	            }
	            
	            MovementUtils.strafe(MovementUtils.getSpeed() * (canBoost ? 1.9125F : 1.0F));
	            
	            canBoost = false;
			}
			
	        if (mc.thePlayer.onGround && MovementUtils.isMoving())
	        {
	            jumped = true;
	            mc.thePlayer.jump();
	        }
		}
		else if (this.getMode().equals("Hypixel"))
		{
	        if(MovementUtils.isMoving())
	        {

	            if(mc.thePlayer.onGround)
	            {
	                mc.thePlayer.jump();

	                float speed = MovementUtils.getSpeed() < 0.56F ? MovementUtils.getSpeed() * 1.045F : 0.56F;

	                if(mc.thePlayer.onGround && mc.thePlayer.isPotionActive(Potion.moveSpeed))
	                    speed *= 1F + 0.13F * (1 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier());

	                MovementUtils.strafe(speed);
	                return;
	            }else if(mc.thePlayer.motionY < 0.2D)
	                mc.thePlayer.motionY -= 0.02D;

	            MovementUtils.strafe(MovementUtils.getSpeed() * 1.01889F);
	        }else{
	            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0D;
	        }
		}
	}
	
	@EventTarget
	public void onMove(EventApplyMotion event)
	{
		if (this.getMode().equals("NCP Old (14)"))
		{
	        ++timerDelay;
	        timerDelay %= 5;
	        if(timerDelay != 0) {
	            mc.timer.timerSpeed = 1F;
	        }else{
	            if(MovementUtils.isMoving())
	                mc.timer.timerSpeed = 32767F;

	            if(MovementUtils.isMoving()) {
	                mc.timer.timerSpeed = 1.3F;
	                mc.thePlayer.motionX *= 1.0199999809265137;
	                mc.thePlayer.motionZ *= 1.0199999809265137;
	            }
	        }

	        if(mc.thePlayer.onGround && MovementUtils.isMoving())
	            level = 2;

	        if(round(mc.thePlayer.posY - (double) ((int) mc.thePlayer.posY)) == round(0.138)) {
	            EntityPlayerSP thePlayer = mc.thePlayer;
	            thePlayer.motionY -= 0.08;
	            event.setY(event.getY() - 0.09316090325960147);
	            thePlayer.posY -= 0.09316090325960147;
	        }

	        if(level == 1 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
	            level = 2;
	            moveSpeed = 1.35 * getBaseMoveSpeed() - 0.01;
	        }else if(level == 2) {
	            level = 3;
	            mc.thePlayer.motionY = 0.399399995803833;
	            event.setY(0.399399995803833);
	            moveSpeed *= 2.149;
	        }else if(level == 3) {
	            level = 4;
	            double difference = 0.66 * (lastDist - getBaseMoveSpeed());
	            moveSpeed = lastDist - difference;
	        }else{
	            if(mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 || mc.thePlayer.isCollidedVertically)
	                level = 1;

	            moveSpeed = lastDist - lastDist / 159.0;
	        }

	        moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
	        final MovementInput movementInput = mc.thePlayer.movementInput;
	        float forward = movementInput.moveForward;
	        float strafe = movementInput.moveStrafe;
	        float yaw = mc.thePlayer.rotationYaw;
	        if(forward == 0.0f && strafe == 0.0f) {
	            event.setX(0.0);
	            event.setZ(0.0);
	        }else if(forward != 0.0f) {
	            if(strafe >= 1.0f) {
	                yaw += (float) (forward > 0.0f ? -45 : 45);
	                strafe = 0.0f;
	            }else if(strafe <= -1.0f) {
	                yaw += (float) (forward > 0.0f ? 45 : -45);
	                strafe = 0.0f;
	            }
	            if(forward > 0.0f) {
	                forward = 1.0f;
	            }else if(forward < 0.0f) {
	                forward = -1.0f;
	            }
	        }

	        final double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
	        final double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
	        event.setX((double) forward * moveSpeed * mx2 + (double) strafe * moveSpeed * mz2);
	        event.setZ((double) forward * moveSpeed * mz2 - (double) strafe * moveSpeed * mx2);

	        mc.thePlayer.stepHeight = 0.6F;
	        if(forward == 0.0F && strafe == 0.0F) {
	            event.setX(0.0);
	            event.setZ(0.0);
	        }
		}
		else if (this.getMode().equals("NCP Old (15)"))
		{
	        ++timerDelay;
	        timerDelay %= 5;
	        if(timerDelay != 0) {
	            mc.timer.timerSpeed = 1F;
	        }else{
	            if(MovementUtils.isMoving())
	                mc.timer.timerSpeed = 32767F;

	            if(MovementUtils.isMoving()) {
	                mc.timer.timerSpeed = 1.3F;
	                mc.thePlayer.motionX *= 1.0199999809265137;
	                mc.thePlayer.motionZ *= 1.0199999809265137;
	            }
	        }

	        if(mc.thePlayer.onGround && MovementUtils.isMoving())
	            level = 2;

	        if(round(mc.thePlayer.posY - (double) ((int) mc.thePlayer.posY)) == round(0.138)) {
	            mc.thePlayer.motionY -= 0.08;
	            event.setY(event.getY() - 0.09316090325960147);
	            mc.thePlayer.posY -= 0.09316090325960147;
	        }

	        if(level == 1 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
	            level = 2;
	            moveSpeed = 1.35 * getBaseMoveSpeed() - 0.01;
	        }else if(level == 2) {
	            level = 3;
	            mc.thePlayer.motionY = 0.399399995803833;
	            event.setY(0.399399995803833);
	            moveSpeed *= 2.149;
	        }else if(level == 3) {
	            level = 4;
	            double difference = 0.66 * (lastDist - getBaseMoveSpeed());
	            moveSpeed = lastDist - difference;
	        }else if(level == 88) {
	            moveSpeed = getBaseMoveSpeed();
	            lastDist = 0;
	            level = 89;
	        }else if(level == 89) {
	            if(mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 || mc.thePlayer.isCollidedVertically)
	                level = 1;
	            lastDist = 0;
	            moveSpeed = getBaseMoveSpeed();
	            return;
	        }else{
	            if(mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 || mc.thePlayer.isCollidedVertically) {
	                moveSpeed = getBaseMoveSpeed();
	                lastDist = 0;
	                level = 88;
	                return;
	            }

	            moveSpeed = lastDist - lastDist / 159.0;
	        }

	        moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());

	        final MovementInput movementInput = mc.thePlayer.movementInput;
	        float forward = movementInput.moveForward;
	        float strafe = movementInput.moveStrafe;
	        float yaw = mc.thePlayer.rotationYaw;
	        if(forward == 0.0f && strafe == 0.0f) {
	            event.setX(0.0);
	            event.setZ(0.0);
	        }else if(forward != 0.0f) {
	            if(strafe >= 1.0f) {
	                yaw += (float) (forward > 0.0f ? -45 : 45);
	                strafe = 0.0f;
	            }else if(strafe <= -1.0f) {
	                yaw += (float) (forward > 0.0f ? 45 : -45);
	                strafe = 0.0f;
	            }
	            if(forward > 0.0f) {
	                forward = 1.0f;
	            }else if(forward < 0.0f) {
	                forward = -1.0f;
	            }
	        }

	        final double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
	        final double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
	        event.setX((double) forward * moveSpeed * mx2 + (double) strafe * moveSpeed * mz2);
	        event.setZ((double) forward * moveSpeed * mz2 - (double) strafe * moveSpeed * mx2);

	        mc.thePlayer.stepHeight = 0.6F;
	        
	        if (forward == 0.0F && strafe == 0.0F)
	        {
	            event.setX(0.0);
	            event.setZ(0.0);
	        }
		}
		else if (this.getMode().equals("NCP Latest"))
		{
			if (!MovementUtils.isMoving() && jumped)
			{
	            mc.thePlayer.motionX = 0;
	            mc.thePlayer.motionZ = 0;
	            event.zeroXZ();
			}
		}
		else if (this.getMode().equals("Mineplex"))
		{	
            if (mc.thePlayer.onGround)
            {
                gwenSpeed = 2.28F;
                MovementUtils.strafe(0.0001F);
                mc.thePlayer.motionY = 0.41F;
            }
            else
            {
                if (gwenSpeed > 0.98)
                {
                	gwenSpeed -= 0.037;
                }
                
                MovementUtils.strafe(0.28731F * gwenSpeed);
            }
		}
	}
	
	@EventTarget
	public void onJump(EventJump event)
	{
        jumped = true;
        canBoost = true;
        teleported = false;
	}
	
    private boolean shouldSpeedUp() 
    {
        return !mc.thePlayer.isInWater() && (!mc.thePlayer.isOnLadder()) && !mc.thePlayer.isSneaking() && MovementUtils.isMoving();
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if(mc.thePlayer.isPotionActive(Potion.moveSpeed))
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        return baseSpeed;
    }

    private double round(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}