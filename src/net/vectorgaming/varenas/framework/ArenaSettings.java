
package net.vectorgaming.varenas.framework;

import net.vectorgaming.varenas.framework.interfaces.Settings;
import net.vectorgaming.varenas.framework.enums.ArenaType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kenny
 */
public class ArenaSettings implements Settings
{
    private String name;
    private boolean isBlockBreak;
    private boolean isTNT;
    private boolean showRespawnScreen;
    private boolean isSpawnKit;
    private boolean clearInv;
    private boolean isCustomKits;
    private boolean respawnWithKit;
    private int maxPlayers;
    private int minPlayers;
    private int lobbyDuration;
    private int winningKills;
    private int gameDuration;
    private double mapVersion;
    private List<String> lobbyMsgInterval = new ArrayList<>();
    private List<String> rules = new ArrayList<>();
    private List<String> allowedKits = new ArrayList<>();
    private String authors;
    private String objective;
    private String type;
    private String postGameSpawn;
    private String spawnKit;
    private String itemDrop;
    
    public ArenaSettings(String name)
    {
        this.name = name;
        isBlockBreak = false;
        isTNT = false;
        maxPlayers = 2;
        minPlayers = 2;
        lobbyDuration = 30;
        authors = "Arcane Realms";
        objective = "Default objective";
        setupDefaultInterval();
        type = ArenaType.PVP_ARENA.name();
        showRespawnScreen = true;
        winningKills = -1;
        gameDuration = 600;
        postGameSpawn = "{default}";
        mapVersion = 1;
        isSpawnKit = false;
        spawnKit = "default";
        respawnWithKit = true;
        clearInv = true;
        itemDrop = "ALL";
    }
    
    /**
     * Gets the name of the arena
     * @return Name of the arena
     */
    @Override
    public String getName() {return name;}
    
    /**
     * Enables or disables the breaking of blocks 
     * @param value boolean value
     */
    @Override
    public void setBlockBreak(boolean value){this.isBlockBreak = value;}
    
    /**
     * Gets if the block break is enabled in the arena
     * @return boolean value
     */
    @Override
    public boolean isBlockBreakEnabled(){return isBlockBreak;}
    
    /**
     * Sets the use of TNT in the arena
     * @param value boolean value
     */
    @Override
    public void setTNTUse(boolean value) {isTNT = value;}
    
    /**
     * Gets if TNT is enabled in the arena
     * @return boolean value
     */
    @Override
    public boolean isTNTEnabled() {return isTNT;}
    
    /**
     * Sets the max players allowed in the arena
     * @param maxPlayers The maximum amount of players allowed
     */
    @Override
    public void setMaxPlayers(int maxPlayers) {this.maxPlayers = maxPlayers;}
    
    /**
     * Gets the max players allowed in the arena
     * @return Number of players allowed in the arena
     */
    @Override
    public int getMaxPlayers() {return maxPlayers;}
    
    /**
     * Sets the minimum number of players allowed in the arena
     * @param minPlayers Minimum number of players allowed
     */
    @Override
    public void setMinPlayers(int minPlayers) {this.minPlayers = minPlayers;}

    /**
     * Gets the minimum number of players allowed in the arena
     * @return Minimum number of players
     */
    @Override
    public int getMinPlayers() {return minPlayers;}
    
    /**
     * Sets the authors for the arena
     * @param authors A String list of the authors
     */
    @Override
    public void setAuthors(String authors) {this.authors = authors;}
    
    /**
     * Gets the authors of the arena
     * @return A String of authors
     */
    @Override
    public String getAuthors(){return authors;}
    
    /**
     * Sets the objective of the arena
    * @param objective A sentence describing the objective
     */
    @Override
    public void setObjective(String objective) {this.objective = objective;}
    
    /**
     * Gets the objective for the arena
     * @return A sentence describing the objective
     */
    @Override
    public String getObjective() {return objective;}
    
    /**
     * Gets the duration that the players will remain in the arena lobby
     * @return The length in seconds for the lobby duration
     */
    @Override
    public int getLobbyDuration() {return lobbyDuration;}
    
    /**
     * Sets the length of time that the players will be in the lobby
     * @param duration Time in seconds
     */
    @Override
    public void setLobbyDuration(int duration) {this.lobbyDuration = duration;}
    
    /**
     * The interval that the lobby will broadcast the message that it will be starting it 
    * @return A list of numbers specifying the time to output the message (Time in seconds)
     */
    @Override
    public List<String> getLobbyMessageInterval() {return lobbyMsgInterval;}
    
    /**
     * Sets the message interval for the lobby to broadcast its starting in message
     * @param interval A list of numbers at which it will broadcast (Time in seconds)
     */
    @Override
    public void setLobbyMessageInterval(List<String> interval) {lobbyMsgInterval = interval;}
    
    /**
     * Gets the type of arena
     * @return The arena type
     */
    @Override
    public String getType() {return type;}
    
    /**
     * Sets the type of arena 
     * @param type Type of arena. Should be all CAPS and spaces are underscores
     */
    @Override
    public void setType(String type) {this.type = type;}
    
    /**
     * Gets if the respawn screen should be shown to the player
     * @return boolean value
     */
    @Override
    public boolean isShowRespawnScreen() {return showRespawnScreen;}
    
    /**
     * Sets if the respawn screen should be shown to the players
     * @param value booleab value
     */
    @Override
    public void setShowRespawnScreen(boolean value) {this.showRespawnScreen = value;}
    
    /**
     * Gets the number of kills that will end the match
     * @return The number of kills to win
     */
    @Override
    public int getWinningKills() {return this.winningKills;}
    
    /**
     * Sets the number of kills that will win the match
     * @param winningKills Number of kills to win the match
     */
    @Override
    public void setWinningKills(int winningKills) {this.winningKills = winningKills;}
    
    /**
     * Gets the duration of the game
     * @return The length of the game in seconds
     */
    @Override
    public int getGameDuration() {return this.gameDuration;}
    
    /**
     * Sets the length of the game
     * @param duration Time in seconds
     */
    @Override
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

    @Override
    public String getPostGameSpawn() {return postGameSpawn;}
    
    @Override
    public void setPostGameSpawn(String location) {this.postGameSpawn = location;}

    @Override
    public double getMapVersion() {return mapVersion;}

    @Override
    public void setMapVersion(double mapVersion){this.mapVersion = mapVersion;}

    @Override
    public List<String> getRules() {return rules;}

    @Override
    public void setRules(List<String> rules) {this.rules = rules;}

    @Override
    public void addRule(String rule)
    {
        if(!rules.contains(rule))
            rules.add(rule);
    }

    @Override
    public void removeRule(String rule)
    {
        if(rules.contains(rule))
            rules.remove(rule);
    }

    @Override
    public String getSpawnKitName()
    {
        return spawnKit;
    }

    @Override
    public void setSpawnKitName(String kit)
    {
        spawnKit = kit;
    }

    @Override
    public boolean isSpawnKitEnabled()
    {
        return isSpawnKit;
    }

    @Override
    public void setSpawnKitEnabler(boolean value)
    {
        isSpawnKit = value;
    }

    @Override
    public boolean isKitClearInventory() {return clearInv;}

    @Override
    public void setKitClearInventory(boolean value) {clearInv = value;}

    @Override
    public boolean isCustomKitsEnabled() {return isCustomKits;}

    @Override
    public void setCustomKitsEnabled(boolean value) {isCustomKits = value;}

    @Override
    public List<String> getAllowedCustomKits() {return allowedKits;}

    @Override
    public void setAllowedCustomKits(List<String> list) {allowedKits = list;}

    @Override
    public void addAllowedKit(String kit)
    {
        if(!allowedKits.contains(kit))
            allowedKits.add(kit);
    }

    @Override
    public void removeAllowedKit(String kit)
    {
        if(allowedKits.contains(kit))
            allowedKits.add(kit);
    }

    @Override
    public boolean isRespawnWithKit() {return respawnWithKit;}

    @Override
    public void setRespawnWithKit(boolean value) {respawnWithKit = value;}

    @Override
    public String getAllowedItemDrop() {return itemDrop;}

    @Override
    public void setAllowItemDrop(String type) {itemDrop = type;}
    
    public ArrayList<String> getAllowedItemDropTypes()
    {
        ArrayList<String> result = new ArrayList<>();
        String[] split;
        if(itemDrop.contains(","))
        {
            split = itemDrop.split(",");
            for(int i = 0; i < split.length; i++)
                result.add(split[i].toUpperCase());
        }else
        {
            result.add(itemDrop.toUpperCase());
        }
        return result;
    }
}
