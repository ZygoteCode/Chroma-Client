package net.minecraft.client.particle.chroma.event;

import net.minecraft.client.particle.chroma.event.Cancellable;
import net.minecraft.client.particle.chroma.event.Event;

/**
 * Abstract example implementation of the Cancellable interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    protected EventCancellable() {
    }

    /**
     * @see net.minecraft.client.particle.chroma.event.darkmagician6.eventapi.events.Cancellable.isCancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @see net.minecraft.client.particle.chroma.event.darkmagician6.eventapi.events.Cancellable.setCancelled
     */
    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }
    
    @Override
    public void cancel()
    {
    	cancelled = true;
    }

}
