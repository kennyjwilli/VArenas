
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onDeath(Player dead, Entity killer)
    {
        KillCounter kc = (KillCounter) this.getStats().getStat("killcounter");
        
        Player killerPlayer;
        if(killer instanceof Player)
        {
            killerPlayer = (Player) killer;
            kc.recordKill(killerPlayer, dead);
        }
        
        ArenaSettings settings = ArenaManager.getArenaSettings(ArenaPlayerManager.getArenaFromPlayer(dead));
        Arena arena = ArenaPlayerManager.getArenaFromPlayer(dead);
        
        /*
         * If winning kills is set to -1 then arena does not use kills to end the match. 
         * Default is a timed match.
         * Ends the arena if the highest kills is the same as the most kills.
        */
        if(settings.getWinningKills() >= 0 && kc.getHighestKills() >= settings.getWinningKills())
        {
            arena.end();
        }
    }
    
    public void endTeleportAction()
    {
        for(Player p : getPlayers())
            p.teleport(Bukkit.getWorld("spawn").getSpawnLocation());
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event){}

    @Override
    public void onBlockBreak(BlockBreakEvent event) {}
}
