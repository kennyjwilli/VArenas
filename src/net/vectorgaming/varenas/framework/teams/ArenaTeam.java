package net.vectorgaming.varenas.framework.teams;

import java.util.ArrayList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

public interface ArenaTeam extends Team{
    public int getId();
    
    public boolean addFriendlyEntity(Entity entity);
    public boolean removeFriendlyEntity(Entity entity);
    public ArrayList<Entity> getFriendlyEntities();
    public boolean isOnTeamAsPlayer(OfflinePlayer player);
    public boolean isOnTeamAsPlayer(String playerName);
    public boolean isOnTeamAsPlayer(int id);
    public boolean isOnTeam(Entity entity);
    public boolean isOnTeam(int id);
    public SubTeam getChildTeam(OfflinePlayer player);
    
    public ArrayList<SubTeam> getChildTeams();
    public SubTeam createChildTeam(String name);
    public SubTeam createChildTeam(OfflinePlayer player);
    public SubTeam addChildTeam(SubTeam team);
    public boolean removeChildTeam(SubTeam team);
}
