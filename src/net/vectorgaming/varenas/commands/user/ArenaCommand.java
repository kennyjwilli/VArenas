
package net.vectorgaming.varenas.commands.user;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.commands.admin.*;
import net.vectorgaming.vcore.VCoreAPI;
import net.vectorgaming.vcore.framework.commands.VCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaCommand extends VCommand
{
    public ArenaCommand()
    {
        super("arena", ArenaAPI.getPlugin());
        addSubCommand(new ArenaCreateMapCommand());
        addSubCommand(new ArenaAddKitCommand());
        addSubCommand(new ArenaAddPlayerCommand());
        addSubCommand(new ArenaAddSpawnPointCommand());
        addSubCommand(new ArenaCreateMapCommand());
        addSubCommand(new ArenaDeleteSpawnPointCommand());
        addSubCommand(new ArenaForceStartCommand());
        addSubCommand(new ArenaForceStopCommand());
        addSubCommand(new ArenaKitCommand());
        addSubCommand(new ArenaLoadArenaCommand());
        addSubCommand(new ArenaQueueArenaCommand());
        addSubCommand(new ArenaReloadCommand());
        addSubCommand(new ArenaSetLobbySpawnCommand());
        addSubCommand(new ArenaSetPostGameSpawnCommand());
        addSubCommand(new ArenaSetRegion());
        addSubCommand(new ArenaSetSpectateSpawnCommand());
    }
    
    @Override
    public void run(CommandSender cs, String[] args) {
        cs.sendMessage(VCoreAPI.getColorScheme().getTitleBar("VArenas Help"));
        cs.sendMessage(ChatColor.GREEN+"Type "+VCoreAPI.getColorScheme().getArgumentColor()+"/arena help "+ChatColor.GREEN+"for a list of commands.");
    }

    @Override
    public String getName() {return "arena";}

    @Override
    public String getUsage() {return "Type /arena help for a list of commands.";}

    @Override
    public boolean isPlayerOnlyCommand() {return false;}

    @Override
    public String getPermission() {return "varena.help";}

    @Override
    public String getDescription()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer getMinArgsLength()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer getMaxArgsLength()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
