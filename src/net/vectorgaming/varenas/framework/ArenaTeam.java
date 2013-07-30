/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
import java.util.Set;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author jeppe
 */
public class ArenaTeam extends BukkitTeamWrapper{
    private int id;
    private ArrayList<Entity> friendlyEntities = new ArrayList();
    public ArenaTeam(int id, Team teamBase){
        super(teamBase);
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean addFriendlyEntity(Entity entity){
        return friendlyEntities.add(entity);
    }
    public boolean removeFriendlyEntity(Entity entity){
        return friendlyEntities.remove(entity);
    }
    public ArrayList<Entity> getFriendlyEntities(){
        return friendlyEntities;
    }
    public boolean isOnTeamAsPlayer(OfflinePlayer player){
        return hasPlayer(player);
    }
    public boolean isOnTeamAsPlayer(String playerName){
        Set<OfflinePlayer> players = this.getPlayers();
        for(OfflinePlayer player : players){
            if(player.getName().equalsIgnoreCase(playerName)){
                return true;
            }
        }
        return false;
    }
    public boolean isOnTeamAsPlayer(int id){
        Set<OfflinePlayer> players = this.getPlayers();
        for(OfflinePlayer player : players){
            if(player.getPlayer() != null){
                if(player.getPlayer().getEntityId() == id){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isOnTeam(Entity entity){
        if(getFriendlyEntities().contains(entity)){
            return true;
        } else if(entity instanceof OfflinePlayer){
            return this.hasPlayer((OfflinePlayer)entity);
        }
        return false;
    }
    public boolean isOnTeam(int id){
        for(Entity entity : getFriendlyEntities()){
            if(entity.getEntityId() == id){
                return true;
            }
        }
        return false;
    }
}
