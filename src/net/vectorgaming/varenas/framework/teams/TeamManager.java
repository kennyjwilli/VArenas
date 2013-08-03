/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.teams;

import java.util.ArrayList;
import net.vectorgaming.varenas.Exceptions.ScoreboardNotEqualException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author jeppe
 */
public class TeamManager {
    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;
    private ArrayList<ArenaTeamData> teams = new ArrayList();
    public void TeamManager(){
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
    }
    
    public ArrayList<ArenaTeamData> getTeams(){
        return teams;
    }
    
    public ArenaTeamData createTeam(String name){
        Team bukkiTeam = scoreboard.registerNewTeam(name);
        int id = teams.size();
        ArenaTeamData arenaTeam = new ArenaTeamData(id,bukkiTeam);
        teams.add(arenaTeam);
        return arenaTeam;
    }
    public ArenaTeamData addTeam(ArenaTeamData team) throws ScoreboardNotEqualException{
        if(team.getScoreboard() != this.getScoreboard()){
            throw new ScoreboardNotEqualException();
        }
        int id = teams.size();
        teams.add(team);
        return team;
    }

    public ArenaTeamData getTeam(int id){
        return getTeams().get(id);
    }
    public ArenaTeamData getTeam(String name){
        for(ArenaTeamData team : getTeams()){
            if(team.getName().equalsIgnoreCase(name)){
                return team;
            }
        }
        return null;
    }
    public ArenaTeamData getTeam(Player player){
        for(ArenaTeamData team : getTeams()){
            if(team.isOnTeamAsPlayer(player)){
                return team;
            }
        }
        return null;
    }
    public ArenaTeamData getTeam(Entity entity){
        for(ArenaTeamData team : getTeams()){
            if(team.isOnTeam(entity)){
                return team;
            }
        }
        return null;
    }
    public ArenaTeamData getTeamByPlayerName(String playerName){
        for(ArenaTeamData team : getTeams()){
            if(team.isOnTeamAsPlayer(playerName)){
                return team;
            }
        }
        return null;
    }
    public ArenaTeamData getTeamByEntityID(int entityID){
        for(ArenaTeamData team : getTeams()){
            if(team.isOnTeamAsPlayer(entityID)){
                return team;
            }
        }
        return null;
    }
    
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public void setScoreboardManager(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
}
