package net.vectorgaming.varenas.framework;

import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public abstract class PVPTeamArena extends TeamArena{

    public PVPTeamArena(String name, String type, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, World world) {
        super(name, type, lobby, spectatorBox, world);
    }

    public PVPTeamArena(String name, String type, World world) {
        super(name, type, world);
    }

    public abstract void onTeamPlayerDeath(EntityEvent event, ArenaTeam killedTeam, ArenaTeam killingTeam);
    
    @Override
    public void onDeath(PlayerDeathEvent event) {
        ArenaTeam killedTeam = getTeamManager().getTeam(event.getEntity());
        ArenaTeam killingTeam = getTeamManager().getTeam(event.getEntity().getKiller());
        //No reason to call onTeamPlayerDeath if no one was on a team
        if(killedTeam != null || killingTeam != null){
            onTeamPlayerDeath(event,killedTeam,killingTeam);
        }
    }

    @Override
    public void onRespawn(PlayerRespawnEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * Due to teams being able to have entities on their side, mobs also need to be
     * accounted for with @onTeamPlayerDeath()
     * @param event
     */
    public void onEntityDamage(EntityDamageEvent event){
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent eventDamage = (EntityDamageByEntityEvent) event;
            ArenaTeam killedTeam = getTeamManager().getTeam(eventDamage.getEntity());
            ArenaTeam killingTeam = getTeamManager().getTeam(eventDamage.getDamager());
            //No reason to call onTeamPlayerDeath if no one was on a team
            if(killedTeam != null || killingTeam != null){
                onTeamPlayerDeath(event,killedTeam,killingTeam);
            }
        }
    }
    
}
