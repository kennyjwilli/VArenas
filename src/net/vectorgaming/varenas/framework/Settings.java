
package net.vectorgaming.varenas.framework;

import java.util.ArrayList;

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
    public Integer getMaxPlayers();
    
    public void setAuthors(String authors);
    public String getAuthors();
    
    public void setObjective(String objecitve);
    public String getObjective();
    
    public Integer getLobbyDuration();
    public void setLobbyDuration(int duration);
    
    public ArrayList<String> getLobbyMessageInterval();
    public void setLobbyMessageInterval(ArrayList<String> interval);
    
    public String getType();
    public void setType(String type);
    
    public boolean isShowRespawnScreen();
    public void setShowRespawnScreen(boolean value);
    
    public Integer getWinningKills();
    public void setWinningKills(int winningKills);
    
    public Integer getGameDuration();
    public void setGameDuration(int duration);
}
