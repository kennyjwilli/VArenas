/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
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
    public ArenaTeam(int id, Team team){
        super(team);
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
    public boolean isOnTeam(Entity entity){
        if(friendlyEntities.contains(entity)){
            return true;
        } else if(entity instanceof OfflinePlayer){
            return this.hasPlayer((OfflinePlayer)entity);
        }
        return false;
    }
}
