package net.vectorgaming.varenas.framework;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


public abstract class TeamArena extends Arena{
    private final TeamManager teamManager;
    public TeamArena(String name, String type, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, World world) {
        super(name, type, lobby, spectatorBox, world);
        teamManager = new TeamManager();
    }
    public TeamArena(String name, String type, World world) {
        super(name, type, world);
        teamManager = new TeamManager();
    }

    /**
     * Get the team manager used with the respective arena
     * @return The team manager used by the arena
     */
    public TeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public Location getSpawnLocation(Player player) {
        //This should be updated to support team based spawns, but i need to
        //talk with kenny about how this is done
        return super.getSpawnLocation(player);
    }
}
