
package net.vectorgaming.varenas.framework.user;

import info.jeppes.ZoneCore.Users.ZoneUserData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaPlayer extends ZoneUserData{
    public ArenaPlayer(Player player, ConfigurationSection configurationSection) {
        super(player, configurationSection);
    }
    public ArenaPlayer(String userName, ConfigurationSection configurationSection) {
        super(userName, configurationSection);
    }
}
