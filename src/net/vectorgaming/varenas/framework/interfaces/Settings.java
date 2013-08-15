
package net.vectorgaming.varenas.framework.interfaces;

import java.util.List;
import net.vectorgaming.varenas.framework.kits.Kit;

/**
 *
 * @author Kenny
 */
public interface Settings 
{
    public String getName();
    
    public void setBlockBreak(boolean value);
    public boolean isBlockBreakEnabled();
    
    public void setTNTUse(boolean value);
    public boolean isTNTEnabled();
    
    public void setMaxPlayers(int maxPlayers);
    public int getMaxPlayers();
    
    public void setMinPlayers(int minPlayers);
    public int getMinPlayers();
    
    public void setAuthors(String authors);
    public String getAuthors();
    
    public void setObjective(String objecitve);
    public String getObjective();
    
    public int getLobbyDuration();
    public void setLobbyDuration(int duration);
    
    public List<String> getLobbyMessageInterval();
    public void setLobbyMessageInterval(List<String> interval);
    
    public String getType();
    public void setType(String type);
    
    public boolean isShowRespawnScreen();
    public void setShowRespawnScreen(boolean value);
    
    public int getWinningKills();
    public void setWinningKills(int winningKills);
    
    public int getGameDuration();
    public void setGameDuration(int duration);
    
    public String getPostGameSpawn();
    public void setPostGameSpawn(String location);
    
    public double getMapVersion();
    public void setMapVersion(double mapVersion);
    
    public List<String> getRules();
    public void setRules(List<String> rules);
    public void addRule(String rule);
    public void removeRule(String rule);
    
    public String getSpawnKitName();
    public void setSpawnKitName(String kit);
    
    public boolean isSpawnKitEnabled();
    public void setSpawnKitEnabler(boolean value);
    
    public boolean isKitClearInventory();
    public void setKitClearInventory(boolean value);
}
