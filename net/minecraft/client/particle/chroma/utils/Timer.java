package net.minecraft.client.particle.chroma.utils;

import java.util.concurrent.*;

public class Timer
{
    private long last;
    private int lastMS;
    
    private final double convert(final double d)
    {
        return 1000.0 / d;
    }
    
    public final boolean hasReach(final double d)
    {
        return this.hasReach(d, false);
    }
    
    public final boolean hasReach(final double d, final boolean convert)
    {
        return this.sleep(convert ? this.convert(d) : d, TimeUnit.MILLISECONDS);
    }
    
    private boolean sleep(final double time, final TimeUnit timeUnit)
    {
        final long convert = timeUnit.convert(System.nanoTime() - this.last, TimeUnit.NANOSECONDS);
        return convert >= time;
    }
    
    public final long getTimePassed()
    {
        return (System.nanoTime() - this.last) / 1000000L;
    }
    
    public final void reset()
    {
        this.last = System.nanoTime();
    }
    
    public long getCurrentMS()
    {
        return System.nanoTime() / 1000000L;
    }
    
    public long getLastMS()
    {
        return this.last;
    }
}