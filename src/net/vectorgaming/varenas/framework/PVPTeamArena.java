package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.framework.stats.stats.KillCounter;
import net.vectorgaming.varenas.framework.teams.ArenaTeamData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PVPTeamArena extends TeamArena{
    private KillCounter killCounter;

    public PVPTeamArena(String name, String map, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, ZoneWorld world) {
        super(name, map, lobby, spectatorBox, world);
    }

    public PVPTeamArena(String name, String map, ZoneWorld world) {
        super(name, map, world);
    }

    public void onTeamPlayerDeath(ArenaTeamData killedTeam, ArenaTeamData killingTeam){
        
    }
    
    @Override
    public void start(){
        super.start();
        killCounter = new KillCounter();
        getStats().addStat(killCounter);
    }
    
    @Override
    public void onDeath(Player death, Entity killer) {
        ArenaTeamData killedTeam = getTeamManager().getTeam(death);
        ArenaTeamData killingTeam = getTeamManager().getTeam(killer);
        //No reason to call onTeamPlayerDeath if no one was on a team
        if(killedTeam != null || killingTeam != null){
            killCounter.recordKill(killer, death);
            onTeamPlayerDeath(killedTeam,killingTeam);
        }
    }

    @Override
    public void onRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(getSpawnLocation(event.getPlayer()));
    }
    
    
    /**
     * Due to teams being able to have entities on their side, mobs also need to be
     * accounted for with @onTeamPlayerDeath()
     * @param event
     */
    public void onEntityDamage(EntityDamageEvent event){
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent eventDamage = (EntityDamageByEntityEvent) event;
            ArenaTeamData killedTeam = getTeamManager().getTeam(eventDamage.getEntity());
            ArenaTeamData killingTeam = getTeamManager().getTeam(eventDamage.getDamager());
            //No reason to call onTeamPlayerDeath if no one was on a team
            if(killedTeam != null || killingTeam != null){
                onTeamPlayerDeath(killedTeam,killingTeam);
            }
        }
    }

    @Override
    public void sendEndMessage() {
    }

    @Override
    public void onQuit(PlayerQuitEvent event) {
    }
    
}
