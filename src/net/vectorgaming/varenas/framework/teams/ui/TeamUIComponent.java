/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.teams.ui;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 *
 * @author jeppe
 */
public abstract class TeamUIComponent implements Listener{
    public void remove() {
        HandlerList.unregisterAll(this);
    }
}
