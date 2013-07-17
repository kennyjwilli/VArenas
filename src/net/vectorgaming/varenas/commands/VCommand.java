
package net.vectorgaming.varenas.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public abstract class VCommand 
{
    //private ArrayList<String> subCommands = new ArrayList<>();
    private HashMap<String, Class<? extends VCommand>> subCommands = new HashMap<>();
    
    public abstract boolean run(CommandSender cs, String[] args);
    public abstract String getName();
    public abstract String getUsage();
    public abstract boolean isPlayerOnlyCommand();
    public abstract String[] getAliases();
    public abstract void setupSubCommands();
    public Class<? extends VCommand> getSubCommand(String subCommand)
    {
        if(subCommands.containsKey(subCommand)) 
            return subCommands.get(subCommand);
        return null;
    }
    public void runSubCommand(CommandSender cs, String subCommand, String[] args)
    {
//        try
//        {
//            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
//            if(newArgs.length == 0)
//            {
//                cs.sendMessage(getUsage());
//                return;
//            }
//        }catch(Exception e)
//        {
//            cs.sendMessage(getUsage());
//            return;
//        }
        for(Map.Entry<String, Class<? extends VCommand>> kv : subCommands.entrySet())
        {
            if(subCommand.equalsIgnoreCase(kv.getKey()))
            {
                try {
                    VCommand cmd = kv.getValue().newInstance();
                    
                    if(!cs.hasPermission(getPermission()))
                    {
                        cs.sendMessage(ChatColor.RED+"You don't have permission.");
                        return;
                    }
                    
                    if(cs instanceof ConsoleCommandSender && cmd.isPlayerOnlyCommand())
                    {
                        cs.sendMessage("[VArenas] The command you are trying to access can only be used in-game.");
                        return;
                    }
                    cmd.run(cs, args);
                    return;
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(VCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        cs.sendMessage(ChatColor.DARK_BLUE+"[VArenas] "+ChatColor.RED+"Unrecognized aregument. Type "+ChatColor.YELLOW+"/events help"+ChatColor.RED+" for a list of commands.");
    }
    public void addSubCommand(String subCommand, Class<? extends VCommand> cmd){subCommands.put(subCommand, cmd);}
    public void removeSubCommand(String subCommand) {if(subCommands.containsKey(subCommand)) subCommands.remove(subCommand);}
    public abstract String getPermission();
}
