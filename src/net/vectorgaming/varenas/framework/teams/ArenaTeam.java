package net.vectorgaming.varenas.framework.teams;

import java.util.ArrayList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;

public interface ArenaTeam extends Team{
    /**
     * Get the ID of the team
     * @return Integer unique team ID
     */
    public int getId();
    
    /**
     * Add an entity as a part of a player team as "friendly entities" which
     * basically makes them a part of the team
     * @param entity the friendly entity
     * @return wether or not it was added
     */
    public boolean addFriendlyEntity(Entity entity);
    /**
     * Remove a friendly entity from the team, see @addFriendlyEntity(Entity entity) 
     * for description of friendly entities
     * @param entity
     * @return
     */
    public boolean removeFriendlyEntity(Entity entity);
    /**
     * Get the teams list of friendly entities
     * @return an array list of friendly entities with the type Entity
     */
    public ArrayList<Entity> getFriendlyEntities();
    /**
     * Check if a player is on the team
     * @param player 
     * @return True if player is on team, false if not
     */
    public boolean isOnTeamAsPlayer(OfflinePlayer player);
    /**
     * Check if a player is on the team
     * @param playerName Name of player
     * @return True if player is on team, false if not
     */
    public boolean isOnTeamAsPlayer(String playerName);
    /**
     * Check if a player is on the team
     * @param id Entity id as Integer
     * @return True if player is on team, false if not
     */
    public boolean isOnTeamAsPlayer(int id);
    /**
     * Check if an entity is on team, no matter if part of the team as player
     * or as a friendly entity
     * @param entity the entity object
     * @return True if player is on team, false if not
     */
    public boolean isOnTeam(Entity entity);
    /**
     * Check if an entity is on team, no matter if part of the team as player
     * or as a friendly entity
     * @param id Entity id as Integer
     * @return True if player is on team, false if not
     */
    public boolean isOnTeam(int id);
    
    /**
     * get a child team of the team that the given entity is on
     * @param entity
     * @return a SubTeam as the child team
     */
    public SubTeam getChildTeam(Entity entity);
    /**
     * Get the list of child teams within the team
     * @return An array list of the class SubTeam 
     */
    public ArrayList<SubTeam> getChildTeams();
    /**
     * Create a child team with a given name
     * @param name
     * @return The new child team as a SubTeam
     */
    public SubTeam createChildTeam(String name);
    /**
     * Create a child team with a name based on the players name
     * @param name
     * @return The new child team as a SubTeam
     */
    public SubTeam createChildTeam(OfflinePlayer player);
    /**
     * Add an already existing SubTeam as a child team
     * @param team
     * @return same SubTeam as given in the params
     */
    public SubTeam addChildTeam(SubTeam team);
    /**
     * 
     * @param team The SubTeam objected to be removed as a child team
     * @return True if removed, false if not
     */
    public boolean removeChildTeam(SubTeam team);
}
