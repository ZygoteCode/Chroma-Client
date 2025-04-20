package net.minecraft.client.particle.chroma.event.events;

import net.minecraft.client.particle.chroma.event.EventCancellable;
import net.minecraft.client.particle.chroma.event.EventState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class EventAttack extends EventCancellable
{
	private EventState state;
	private EntityPlayer player;
	private Entity entity;
	private int type;
	private MovingObjectPosition moving;
	private Vec3 vec;
	
	public EventAttack(EventState state, EntityPlayer player, Entity entity, int type, MovingObjectPosition moving, Vec3 vec)
	{
		this.state = state;
		this.player = player;
		this.entity = entity;
		this.type = type;
		this.moving = moving;
		this.vec = vec;
	}

	public EntityPlayer getPlayer()
	{
		return player;
	}

	public void setPlayer(EntityPlayer player)
	{
		this.player = player;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public MovingObjectPosition getMoving()
	{
		return moving;
	}

	public void setMoving(MovingObjectPosition moving)
	{
		this.moving = moving;
	}

	public EventState getState()
	{
		return state;
	}

	public void setState(EventState state)
	{
		this.state = state;
	}

	public Vec3 getVec()
	{
		return vec;
	}

	public void setVec(Vec3 vec)
	{
		this.vec = vec;
	}
}