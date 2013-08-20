
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author Kenny
 */
public class PlayerDeathListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event)
    {
        final Player dead = event.getEntity();
        Player killer = dead.getKiller();
        
        //Basic checks to see if the death event should actually be fired
        if(!ArenaPlayerManager.isPlayerInArena(dead))
            return;
        
        String arenaName = ArenaPlayerManager.getArenaNameFromPlayer(dead);
        
        if(!ArenaManager.isArenaRunning(arenaName))
            return;
        
        final Arena arena = ArenaManager.getArena(arenaName);
        
        if(!arena.getWorld().getName().equalsIgnoreCase(dead.getWorld().getName()))
            return;
        
        //Fires arena death event
        arena.onDeath(dead, killer);
        
        if(!arena.isRunning())
        {
            ArenaAPI.cancelRespawnScreen(dead);
            dead.teleport(arena.getPostGameSpawn());
        }
        //Removes dropped items if needed
        if(!arena.getSettings().getAllowedItemDropTypes().contains("DEATH") && !arena.getSettings().getAllowedItemDropTypes().contains("ALL"))
            event.getDrops().clear();
        
        //Checks to see if the respawn screen is enabled
        if(arena.getSettings().isShowRespawnScreen())
            return;
        
        ArenaAPI.cancelRespawnScreen(dead);
        dead.teleport(arena.getSpawnLocation(dead));
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(ArenaAPI.getPlugin(), new Runnable()
        {
            public void run()
            {
                if(arena.getSettings().isRespawnWithKit())
                    ArenaPlayerManager.getKitFromPlayer(dead).giveKit(dead, arena.getSettings().isKitClearInventory());
            }
        }, 4L);
    }
}