/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.user;

import info.jeppes.ZoneCore.Users.ZoneCoreUserData;
import info.jeppes.ZoneCore.Users.ZoneUser;
import info.jeppes.ZoneCore.Users.ZoneUserManager;
import info.jeppes.ZoneCore.ZoneConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jeppe
 */
public class ArenaPlayerManager<E extends ArenaPlayer> extends ZoneUserManager<E>{
    public ArenaPlayerManager(Plugin plugin, ZoneConfig usersConfig) {
        super(plugin, usersConfig);
    }

    @Override
    public E loadUser(String userName, ConfigurationSection config) {
        return (E) new ArenaPlayer(userName,config);
    }
}
