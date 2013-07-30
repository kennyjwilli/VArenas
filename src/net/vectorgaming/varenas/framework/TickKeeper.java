package net.vectorgaming.varenas.framework;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class TickKeeper {
    private final Plugin plugin;
    private long totalTicks = 0;
    private long relativeTicks = 0;
    private int ticksPerTick = 0;
    private final boolean synchronizedTick;
    private TickTask task;
    private boolean paused = false;
    public TickKeeper(int ticksPerTick, boolean synchronizedTick, Plugin plugin){
        this.ticksPerTick = ticksPerTick;
        this.synchronizedTick = synchronizedTick;
        this.plugin = plugin;
        task = new TickTask();
        updateSchedual();
    }
    
    private void updateSchedual(){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        if(this.synchronizedTick){
            scheduler.runTaskTimer(plugin, task,0,ticksPerTick);
        } else {
            scheduler.runTaskTimerAsynchronously(plugin, task,0,ticksPerTick);
        }
    }

    public void pause(){
        paused = true;
    }
    public void unPause(){
        paused = false;
    }
    
    public Plugin getPlugin() {
        return plugin;
    }

    public long getTotalTicks() {
        return totalTicks;
    }

    public long getRelativeTicks() {
        return relativeTicks;
    }

    public int getTicksPerTick() {
        return ticksPerTick;
    }

    public boolean isSynchronizedTick() {
        return synchronizedTick;
    }

    public TickTask getTask() {
        return task;
    }
    
    public abstract void tick();
    
    public class TickTask implements Runnable{
        @Override
        public void run() {
            if(paused){
                return;
            }
            relativeTicks++;
            totalTicks += ticksPerTick;
            tick();
        }
    }
}
