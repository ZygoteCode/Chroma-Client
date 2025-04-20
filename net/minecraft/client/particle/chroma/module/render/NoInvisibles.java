package net.minecraft.client.particle.chroma.module.render;

import java.util.ArrayList;

import net.minecraft.client.particle.chroma.event.EventTarget;
import net.minecraft.client.particle.chroma.event.events.EventSuperUpdate;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class NoInvisibles extends Module
{
	private ArrayList<Entity> entities = new ArrayList();
	
	public NoInvisibles()
	{
		super("NoInvisibles", 32, Category.RENDER);
	}
	
    @Override
    public void onDisable()
    {
        for (Entity e : this.entities)
        {
            e.setInvisible(true);
        }
        
        this.entities.clear();
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(EventSuperUpdate event)
    {
        for (Object o : this.mc.theWorld.loadedEntityList)
        {
            EntityLivingBase entity;
            if (!(o instanceof EntityLivingBase) || !(entity = (EntityLivingBase)o).isInvisible() || this.entities.contains(entity)) continue;
            this.entities.add(entity);
            entity.setInvisible(false);
        }
    }
}