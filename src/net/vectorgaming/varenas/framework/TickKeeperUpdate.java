package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
import org.bukkit.plugin.Plugin;

public class TickKeeperUpdate extends TickKeeper{
    private ArrayList<Runnable> tasks = new ArrayList();
    public TickKeeperUpdate(int ticksPerTick, boolean synchronizedTick, Plugin plugin) {
        super(ticksPerTick, synchronizedTick, plugin);
    }
    
    public boolean addRunnable(Runnable task){
        return tasks.add(task);
    }
    public boolean removeRunnable(Runnable task){
        return tasks.remove(task);
    }
    public ArrayList<Runnable> getTasks(){
        return tasks;
    }
    
    @Override
    public void tick() {
        for(Runnable task : getTasks()){
            task.run();
        }
    }
}
