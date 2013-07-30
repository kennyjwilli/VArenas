package net.vectorgaming.varenas.framework;

import org.bukkit.World;

public abstract class PVPTeamArena extends TeamArena{

    public PVPTeamArena(String name, String type, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, World world) {
        super(name, type, lobby, spectatorBox, world);
    }

    public PVPTeamArena(String name, String type, World world) {
        super(name, type, world);
    }
}
