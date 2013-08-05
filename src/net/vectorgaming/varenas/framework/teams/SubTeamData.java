/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.teams;

import java.util.ArrayList;
import java.util.Set;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author jeppe
 */
public class SubTeamData extends BukkitTeamWrapper implements SubTeam{
    private ArenaTeamData parentTeam = null;
    private int id;
    private ArrayList<Entity> friendlyEntities = new ArrayList();
    public SubTeamData(int id, Team teamBase){
        super(teamBase);
        this.id = id;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public ArenaTeamData getParentTeam() {
        return parentTeam;
    }
    @Override
    public void setParentTeam(ArenaTeamData parentTeam) {
        this.parentTeam = parentTeam;
    }

    @Override
    public boolean addFriendlyEntity(Entity entity){
        return friendlyEntities.add(entity);
    }
    @Override
    public boolean removeFriendlyEntity(Entity entity){
        return friendlyEntities.remove(entity);
    }
    @Override
    public ArrayList<Entity> getFriendlyEntities(){
        return friendlyEntities;
    }
    @Override
    public boolean isOnTeamAsPlayer(OfflinePlayer player){
        return hasPlayer(player);
    }
    @Override
    public boolean isOnTeamAsPlayer(String playerName){
        Set<OfflinePlayer> players = this.getPlayers();
        for(OfflinePlayer player : players){
            if(player.getName().equalsIgnoreCase(playerName)){
                return true;
            }
        }
        return false;
    }
    @Override
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
    @Override
    public boolean isOnTeam(Entity entity){
        if(getFriendlyEntities().contains(entity)){
            return true;
        } else if(entity instanceof OfflinePlayer){
            return this.hasPlayer((OfflinePlayer)entity);
        }
        return false;
    }
    @Override
    public boolean isOnTeam(int id){
        for(Entity entity : getFriendlyEntities()){
            if(entity.getEntityId() == id){
                return true;
            }
        }
        return false;
    }
}
