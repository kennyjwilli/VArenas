/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.teams;

import java.util.ArrayList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author jeppe
 */
public interface SubTeam extends Team{        
    public int getId();

    public ArenaTeamData getParentTeam();
    public void setParentTeam(ArenaTeamData parentTeam);

    public boolean addFriendlyEntity(Entity entity);
    public boolean removeFriendlyEntity(Entity entity);
    public ArrayList<Entity> getFriendlyEntities();
    public boolean isOnTeamAsPlayer(OfflinePlayer player);
    public boolean isOnTeamAsPlayer(String playerName);
    public boolean isOnTeamAsPlayer(int id);
    public boolean isOnTeam(Entity entity);
    public boolean isOnTeam(int id);
}
