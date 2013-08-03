/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.teams.ui;

import net.vectorgaming.varenas.framework.teams.TeamManager;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 *
 * @author jeppe
 */
public abstract class TeamUIComponent implements Listener{
    private final TeamManager teamManager;
    public TeamUIComponent(TeamManager teamManager){
        this.teamManager = teamManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }
    
    public void remove() {
        HandlerList.unregisterAll(this);
        getTeamManager().getUIComponents().remove(this);
    }
}
