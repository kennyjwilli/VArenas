
package net.vectorgaming.varenas.listeners;

import net.minecraft.server.v1_6_R2.Packet205ClientCommand;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
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
        
        //Removes dropped items if needed
        if(arena.getSettings().getAllowedItemDropTypes().contains("death"))
        {
            for(ItemStack drop : event.getDrops())
                event.getDrops().remove(drop);
        }
        
        //Checks to see if the respawn screen is enabled
        if(arena.getSettings().isShowRespawnScreen())
            return;
        
        Packet205ClientCommand packet = new Packet205ClientCommand();
        packet.a = 1;
        ((CraftPlayer) dead).getHandle().playerConnection.a(packet);
        arena.onRespawn(dead);
    }
}
