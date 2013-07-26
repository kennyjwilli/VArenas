/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework;

import java.util.Set;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 *
 * @author jeppe
 */
public class BukkitTeamWrapper implements Team{
    private Team team;
    public BukkitTeamWrapper(Team team){
        this.team = team;
    }
    
    @Override
    public String getName() throws IllegalStateException {
        return team.getName();
    }

    @Override
    public String getDisplayName() throws IllegalStateException {
        return team.getDisplayName();
    }

    @Override
    public void setDisplayName(String string) throws IllegalStateException, IllegalArgumentException {
        team.setDisplayName(string);
    }

    @Override
    public String getPrefix() throws IllegalStateException {
        return team.getPrefix();
    }

    @Override
    public void setPrefix(String string) throws IllegalStateException, IllegalArgumentException {
        team.setPrefix(string);
    }

    @Override
    public String getSuffix() throws IllegalStateException {
        return team.getSuffix();
    }

    @Override
    public void setSuffix(String string) throws IllegalStateException, IllegalArgumentException {
        team.setSuffix(string);
    }

    @Override
    public boolean allowFriendlyFire() throws IllegalStateException {
        return team.allowFriendlyFire();
    }

    @Override
    public void setAllowFriendlyFire(boolean bln) throws IllegalStateException {
        team.setAllowFriendlyFire(bln);
    }

    @Override
    public boolean canSeeFriendlyInvisibles() throws IllegalStateException {
        return team.canSeeFriendlyInvisibles();
    }

    @Override
    public void setCanSeeFriendlyInvisibles(boolean bln) throws IllegalStateException {
        team.setCanSeeFriendlyInvisibles(bln);
    }

    @Override
    public Set<OfflinePlayer> getPlayers() throws IllegalStateException {
        return team.getPlayers();
    }

    @Override
    public int getSize() throws IllegalStateException {
        return team.getSize();
    }

    @Override
    public Scoreboard getScoreboard() {
        return team.getScoreboard();
    }

    @Override
    public void addPlayer(OfflinePlayer op) throws IllegalStateException, IllegalArgumentException {
        team.addPlayer(op);
    }

    @Override
    public boolean removePlayer(OfflinePlayer op) throws IllegalStateException, IllegalArgumentException {
        return team.removePlayer(op);
    }

    @Override
    public void unregister() throws IllegalStateException {
        team.unregister();
    }

    @Override
    public boolean hasPlayer(OfflinePlayer op) throws IllegalArgumentException, IllegalStateException {
        return team.hasPlayer(op);
    }
}
