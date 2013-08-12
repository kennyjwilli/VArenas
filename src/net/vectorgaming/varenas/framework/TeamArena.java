package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.framework.teams.TeamManager;
import net.vectorgaming.varenas.framework.teams.ArenaTeamData;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public abstract class TeamArena extends Arena{
    public TeamArena(String name, String type, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, ZoneWorld world) {
        super(name, type, lobby, spectatorBox, world);
    }
    public TeamArena(String name, String type, ZoneWorld world) {
        super(name, type, world);
    }

    private String getBaseTeamSpawnKey(ArenaTeamData team){
        return "team-"+team.getId();
    }
    private String getNextAvailableTeamSpawnKey(ArenaTeamData team){
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
    public void addSpawnPoint(Point3D point, ArenaTeamData team){
        String teamSpawnKey = getNextAvailableTeamSpawnKey(team);
        this.addSpawnPoint(teamSpawnKey, point);
    }
    
    /**
     * Get all the spawn points for a given team
     * @param team
     * @return A list of spawnpoints for a given team
     */
    public ArrayList<Location> getTeamSpawnPoints(ArenaTeamData team){
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
    public Location getSpawnLocation(ArenaTeamData team) {
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
