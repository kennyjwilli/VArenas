
package net.vectorgaming.varenas.framework;

import net.vectorgaming.varenas.framework.enums.ArenaType;
import java.util.ArrayList;

/**
 *
 * @author Kenny
 */
public class ArenaSettings 
{
    private String name;
    private boolean isBlockBreak;
    private boolean isTNT;
    private boolean showRespawnScreen;
    private Integer maxPlayers;
    private Integer lobbyDuration;
    private Integer winningKills;
    private Integer gameDuration;
    private ArrayList<String> lobbyMsgInterval = new ArrayList<>();
    private String authors;
    private String objective;
    private String type;
    
    public ArenaSettings(String name)
    {
        this.name = name;
        isBlockBreak = false;
        isTNT = false;
        maxPlayers = 2;
        lobbyDuration = 30;
        authors = "Arcane Realms";
        objective = "Default objective";
        setupDefaultInterval();
        type = ArenaType.PVP_ARENA.toString();
        showRespawnScreen = true;
        winningKills = -1;
        gameDuration = 600;
    }
    
    public String getName() {return name;}
    
    public void setBlockBreak(boolean value){this.isBlockBreak = value;}
    
    public boolean isBlockBreakEnabled(){return isBlockBreak;}
    
    public void setTNTUse(boolean value) {isTNT = value;}
    
    public boolean isTNTEnabled() {return isTNT;}
    
    public void setMaxPlayers(int maxPlayers) {this.maxPlayers = maxPlayers;}
    
    public Integer getMaxPlayers() {return maxPlayers;}
    
    public void setAuthors(String authors) {this.authors = authors;}
    
    public String getAuthors(){return authors;}
    
    public void setObjective(String objective) {this.objective = objective;}
    
    public String getObjective() {return objective;}
    
    public Integer getLobbyDuration() {return lobbyDuration;}
    
    public void setLobbyDuration(int duration) {this.lobbyDuration = duration;}
    
    public ArrayList<String> getLobbyMessageInterval() {return lobbyMsgInterval;}
    
    public void setLobbyMessageInterval(ArrayList<String> interval) {lobbyMsgInterval = interval;}
    
    public String getType() {return type;}
    
    public void setType(String type) {this.type = type;}
    
    public boolean isShowRespawnScreen() {return showRespawnScreen;}
    
    public void setShowRespawnScreen(boolean value) {this.showRespawnScreen = value;}
    
    public Integer getWinningKills() {return this.winningKills;}
    
    public void setWinningKills(int winningKills) {this.winningKills = winningKills;}
    
    public Integer getGameDuration() {return this.gameDuration;}
    
    public void setGameDuration(int duration) {gameDuration = duration;}
    
    private void setupDefaultInterval()
    {
        lobbyMsgInterval.add("120");
        lobbyMsgInterval.add("60");
        lobbyMsgInterval.add("30");
        lobbyMsgInterval.add("15");
        lobbyMsgInterval.add("10");
        lobbyMsgInterval.add("5");
        lobbyMsgInterval.add("4");
        lobbyMsgInterval.add("3");
        lobbyMsgInterval.add("2");
        lobbyMsgInterval.add("1");
    }
}
