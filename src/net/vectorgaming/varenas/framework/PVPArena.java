
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.stats.stats.KillCounter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Kenny
 */
public class PVPArena extends TeamArena
{
    private int TASK_ID;
    private int timeLeftLobby = 120;
    
    
    /**
     *
     * @param arenaName Name of the arena
     * @param map Type of the arena
     * @param world World the arena will be located in
     */
    public PVPArena(String arenaName, String map, ZoneWorld world)
    {
        super(arenaName, map, world);
    }

    @Override
    public void start()
    {
        super.start();
        KillCounter killCounter = new KillCounter();
        getStats().addStat(killCounter);
    }
    
    @Override
    public void forceStop() 
    {
        super.forceStop();
    }

    @Override
    public void sendEndMessage() 
    {
        KillCounter kc = (KillCounter) this.getStats().getStat("killcounter");
        Bukkit.broadcastMessage("Game Over! "+kc.getPlayerWithHighestKills().getDisplayName()+" won with "+kc.getHighestKills());
    }

    @Override
    public Location onRespawn(Player player)
    {
        System.out.println("ttttttttttttttttttttttttttttttttttttt");
        return this.getSpawnLocation(player);
    }

    @Override
    public void onQuit(PlayerQuitEvent event)
    {
        super.onQuit(event);
    }

    @Override
    public void onDeath(Player dead, Entity killer)
    {
        System.out.println("1");
        KillCounter kc = (KillCounter) this.getStats().getStat("killcounter");
        
        Player killerPlayer;
        if(killer instanceof Player)
        {
            killerPlayer = (Player) killer;
            kc.recordKill(killerPlayer, dead);
        }
        System.out.println("2");
        
        ArenaSettings settings = ArenaManager.getArenaSettings(ArenaPlayerManager.getArenaFromPlayer(dead));
        Arena arena = ArenaPlayerManager.getArenaFromPlayer(dead);
        
        /*
         * If winning kills is set to -1 then arena does not use kills to end the match. 
         * Default is a timed match.
         * Ends the arena if the highest kills is the same as the most kills.
        */
        if(settings.getWinningKills() >= 0 && kc.getHighestKills() >= settings.getWinningKills())
        {
            System.out.println("3");
            arena.end();
            System.out.println("4");
        }
        System.out.println("5");
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event){}

    @Override
    public void onBlockBreak(BlockBreakEvent event) {}

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent event)
    {
        
    }

    @Override
    public void onJoin(Player player)
    {
        super.onJoin(player);
        player.teleport(getLobby().getSpawn());
    }
}
