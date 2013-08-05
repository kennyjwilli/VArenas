package net.vectorgaming.varenas.framework.teams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.vectorgaming.varenas.ArenaAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ArenaTeamData implements ArenaTeam, Team, Listener{
    private int id;
    private final Team teamBase;
    private ArrayList<Entity> friendlyEntities = new ArrayList();
    private ArrayList<SubTeam> childTeams = new ArrayList();
    public ArenaTeamData(int id, Team teamBase){
        this.id = id;
        this.teamBase = teamBase;
        Bukkit.getPluginManager().registerEvents(this, ArenaAPI.getPlugin());
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public ArrayList<SubTeam> getChildTeams() {
        return childTeams;
    }
    
    @Override
    public SubTeam createChildTeam(String name) {
        Team newChildTeam = this.getScoreboard().registerNewTeam(name);
        SubTeamData subTeam = new SubTeamData(childTeams.size(),newChildTeam);
        return subTeam;
    }
    @Override
    public SubTeam createChildTeam(OfflinePlayer player) {
        SubTeam childTeam = createChildTeam(this.getName() + "_" + player.getName());
        return mirrorTeamSettings(childTeam);
    }
    private SubTeam mirrorTeamSettings(SubTeam team){
        team.setAllowFriendlyFire(this.allowFriendlyFire());
        team.setCanSeeFriendlyInvisibles(this.canSeeFriendlyInvisibles());
        team.setDisplayName(this.getDisplayName());
        team.setParentTeam(this);
        team.setPrefix(this.getPrefix());
        team.setSuffix(this.getSuffix());
        return team;
    }

    @Override
    public SubTeam addChildTeam(SubTeam childTeam) {
        this.childTeams.add(childTeam);
        return childTeam;
    }
    @Override
    public boolean removeChildTeam(SubTeam childTeam) {
        return childTeams.remove(childTeam);
    }
    
    @Override
    public SubTeam getChildTeam(OfflinePlayer player){
        for(SubTeam childTeam : getChildTeams()){
            if(childTeam.hasPlayer(player)){
                return childTeam;
            }
        }
        return null;
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
        return isOnTeamAsPlayer(player.getName());
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

    @Override
    public String getName() throws IllegalStateException {
        return teamBase.getName();
    }

    @Override
    public String getDisplayName() throws IllegalStateException {
        return teamBase.getDisplayName();
    }

    @Override
    public void setDisplayName(String string) throws IllegalStateException, IllegalArgumentException {
        teamBase.setDisplayName(string);
    }

    @Override
    public String getPrefix() throws IllegalStateException {
        return teamBase.getPrefix();
    }

    @Override
    public void setPrefix(String string) throws IllegalStateException, IllegalArgumentException {
        teamBase.setPrefix(string);
    }

    @Override
    public String getSuffix() throws IllegalStateException {
        return teamBase.getSuffix();
    }

    @Override
    public void setSuffix(String string) throws IllegalStateException, IllegalArgumentException {
        teamBase.setSuffix(string);
    }

    @Override
    public boolean allowFriendlyFire() throws IllegalStateException {
        return teamBase.allowFriendlyFire();
    }

    @Override
    public void setAllowFriendlyFire(boolean bln) throws IllegalStateException {
        teamBase.setAllowFriendlyFire(bln);
        for(SubTeam childTeam : getChildTeams()){
            childTeam.setAllowFriendlyFire(bln);
        }
    }

    @Override
    public boolean canSeeFriendlyInvisibles() throws IllegalStateException {
        return teamBase.canSeeFriendlyInvisibles();
    }

    @Override
    public void setCanSeeFriendlyInvisibles(boolean bln) throws IllegalStateException {
        teamBase.setCanSeeFriendlyInvisibles(bln);
        for(SubTeam childTeam : getChildTeams()){
            childTeam.setCanSeeFriendlyInvisibles(bln);
        }
    }

    @Override
    public Set<OfflinePlayer> getPlayers() throws IllegalStateException {
        HashSet<OfflinePlayer> players = new HashSet();
        players.addAll(teamBase.getPlayers());
        for(SubTeam childTeam : getChildTeams()){
            players.addAll(childTeam.getPlayers());
        }
        return players;
    }

    @Override
    public int getSize() throws IllegalStateException {
        int size = teamBase.getSize();
        for(SubTeam childTeam : getChildTeams()){
            size += childTeam.getSize();
        }
        return size;
    }

    @Override
    public Scoreboard getScoreboard() {
        return teamBase.getScoreboard();
    }

    @Override
    public void addPlayer(OfflinePlayer op) throws IllegalStateException, IllegalArgumentException {
        teamBase.addPlayer(op);
        SubTeam createChildTeam = createChildTeam(op);
        createChildTeam.addPlayer(op);
    }

    @Override
    public boolean removePlayer(OfflinePlayer op) throws IllegalStateException, IllegalArgumentException {
        if(teamBase.removePlayer(op)){
            return true;
        }
        for(SubTeam childTeam : getChildTeams()){
            if(childTeam.removePlayer(op)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void unregister() throws IllegalStateException {
        teamBase.unregister();
        for(SubTeam childTeam : getChildTeams()){
            childTeam.unregister();
        }
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean hasPlayer(OfflinePlayer op) throws IllegalArgumentException, IllegalStateException {
        if(teamBase.hasPlayer(op)){
            return true;
        }
        for(SubTeam childTeam : getChildTeams()){
            if(childTeam.hasPlayer(op)){
                return true;
            }
        }
        return false;
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamage(EntityDamageEvent event){
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent eventDamage = (EntityDamageByEntityEvent) event;
            Entity defender = eventDamage.getEntity();
            Entity attacker = eventDamage.getDamager();
            if(!allowFriendlyFire()) {
                if(isOnTeam(defender) && isOnTeam(attacker)){
                    event.setCancelled(true);
                }
            }
        }
    }
}
