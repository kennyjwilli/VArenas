
package net.vectorgaming.varenas.framework.config;

import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.List;
import net.vectorgaming.varenas.framework.interfaces.Settings;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Kenny
 */
public class SettingsConfig extends ZoneConfig implements Settings
{
    public SettingsConfig(Plugin plugin, File file) 
    {
        super(plugin, file);
    }
    public SettingsConfig(Plugin plugin, File file, boolean loadDefaults) 
    {
        super(plugin, file, loadDefaults);
    }
    
    public String getArenaName()
    {
        return this.getString("name");
    }
    
    public String getAuthors()
    {
        return this.getString("info.authors");
    }
    
    public void setAuthors(String authors)
    {
        this.set("info.authors", authors);
    }
    
    public String getObjective()
    {
        return this.getString("info.objective");
    }
    
    public void setObjective(String objective)
    {
        this.set("info.objective", objective);
    }
    
    public String getType()
    {
        return this.getString("settings.type");
    }
    
    public void setType(String type)
    {
        this.set("settings.type", type);
    }
    
    public int getMaxPlayers()
    {
        return this.getInt("settings.max-players");
    }
    
    public void setMaxPlayers(int maxPlayers)
    {
        this.set("settings.max-players", maxPlayers);
    }
    
    public int getMinPlayers()
    {
        return this.getInt("settings.min-players");
    }
    
    public void setMinPlayers(int minPlayers)
    {
        this.set("settings.min-players", minPlayers);
    }
    
    public boolean isTNTEnabled()
    {
        return this.getBoolean("settings.tnt-enabled");
    }
    
    public void setTNTUse(boolean value)
    {
        this.set("settings.tnt-use", value);
    }
    
    public boolean isBlockBreakEnabled()
    {
        return this.getBoolean("settings.block-break");
    }
    
    public void setBlockBreak(boolean value)
    {
        this.set("settings.block-break", value);
    }
    
    public int getLobbyDuration()
    {
        return this.getInt("settings.lobby.time");
    }
    
    public void setLobbyDuration(int t)
    {
        this.set("settings.lobby.time", t);
    }
    
    public List<String> getLobbyMessageInterval()
    {
        return this.getStringList("settings.lobby.message-interval");
    }
    
    public void setLobbyMessageInterval(List<String> list)
    {
        this.set("settings.lobby.message-interval", list);
    }

    @Override
    public boolean isShowRespawnScreen()
    {
        return this.getBoolean("settings.respawn.show-screen");
    }

    @Override
    public void setShowRespawnScreen(boolean value)
    {
        this.set("settings.respawn.show-screen", value);
    }

    @Override
    public int getWinningKills()
    {
        return this.getInt("settings.winning-kills");
    }

    @Override
    public void setWinningKills(int winningKills)
    {
        this.set("settings.winning-kills", winningKills);
    }

    @Override
    public int getGameDuration()
    {
        return this.getInt("settings.game-duration");
    }

    @Override
    public void setGameDuration(int duration)
    {
        this.set("settings.game-duration", duration);
    }
}
