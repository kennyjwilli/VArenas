package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


public abstract class TeamArena extends Arena{
    private final TeamManager teamManager;
    public TeamArena(String name, String type, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, World world) {
        super(name, type, lobby, spectatorBox, world);
        teamManager = new TeamManager();
    }
    public TeamArena(String name, String type, World world) {
        super(name, type, world);
        teamManager = new TeamManager();
    }

    /**
     * Get the team manager used with the respective arena
     * @return The team manager used by the arena
     */
    public TeamManager getTeamManager() {
        return teamManager;
    }

    private String getBaseTeamSpawnKey(ArenaTeam team){
        return "team-"+team.getId();
    }
    private String getNextAvailableTeamSpawnKey(ArenaTeam team){
        int highestKeyAvaiable = 0;
        String baseTeamSpawnKey = getBaseTeamSpawnKey(team);
        for(String key : this.getSpawnPointsNames()){
            if(key.startsWith(baseTeamSpawnKey)){
                String[] split = key.split("-");
                try{
                    int id = Integer.parseInt(split[2]);
                    if(highestKeyAvaiable <= id ){
                        highestKeyAvaiable = id + 1;
                    }
                } catch(Exception e){}
            }
        }
        return baseTeamSpawnKey + "-" + highestKeyAvaiable;
    }
    
    /**
     * Add a spawn point for a team
     * @param location
     * @param team
     */
    public void addSpawnPoint(Location location, ArenaTeam team){
        String teamSpawnKey = getNextAvailableTeamSpawnKey(team);
        this.addSpawnPoint(teamSpawnKey, location);
    }
    
    /**
     * Get all the spawn points for a given team
     * @param team
     * @return A list of spawnpoints for a given team
     */
    public ArrayList<Location> getTeamSpawnPoints(ArenaTeam team){
        ArrayList<Location> spawnPoints = new ArrayList();
        String teamSpawnKey = getBaseTeamSpawnKey(team);
        for(String spawnKey : getSpawnPointsNames()){
            if(spawnKey.startsWith(teamSpawnKey)){
                spawnPoints.add(getSpawnPointMap().get(spawnKey));
            }
        }
        return spawnPoints;
    }
    
    /**
     * Get a spawnpoint for the given team
     * @param team
     * @return A spawn location for the given team
     */
    public Location getSpawnLocation(ArenaTeam team) {
        if(team == null){
            return super.getSpawnLocation(null);
        }
        if(getSpawnPointMap() != null && !getSpawnPointMap().isEmpty()){
            ArrayList<Location> teamSpawnPoints = getTeamSpawnPoints(team);
            Location spawnLocation = teamSpawnPoints.get((int)(Math.random() * (double)teamSpawnPoints.size()));
            return spawnLocation;
        }
        return super.getSpawnLocation(null);
    }
    
    @Override
    public Location getSpawnLocation(Player player) {
        return getSpawnLocation(getTeamManager().getTeam(player));
    }
}
