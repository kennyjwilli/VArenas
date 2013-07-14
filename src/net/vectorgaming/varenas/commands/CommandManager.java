
package net.vectorgaming.varenas.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 *
 * @author Kenny
 */
public class CommandManager implements CommandExecutor
{
    private static HashMap<String, Class<? extends VCommand>> commandMap = new HashMap<>(); // {CommandStr, CommandClass}
    private static HashMap<String, String> commandAliasMap = new HashMap<>(); // {Alias, CommandStr}
    
    public static void registerCommand(String cmd, Class<? extends VCommand> c)
    {
        commandMap.put(cmd, c);
    }
    
    public static void unregisterCommand(String cmd){commandMap.remove(cmd);}
    
    public static Class<? extends VCommand> getCommand(String cmd){return commandMap.get(cmd);}
            
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lbl, String[] args)
    {
        for(Map.Entry<String, Class<? extends VCommand>> kv : commandMap.entrySet())
        {
            if(kv.getKey().equalsIgnoreCase(lbl))
            {
                try 
                {
                    VCommand command = kv.getValue().newInstance();
                    
                    //Checks the player for proper permissions
                    if(!cs.hasPermission(command.getPermission()))
                    {
                        cs.sendMessage(ChatColor.DARK_BLUE+"[VEvents]"+ChatColor.RED+" You do not have permission to use "+ChatColor.YELLOW+"/"+command.getName()+ChatColor.RED+".");
                        return true;
                    }
                    
                    //Ensures the cs can execute the cmd
                    if(command.isPlayerOnlyCommand() && cs instanceof ConsoleCommandSender)
                    {
                        Bukkit.getLogger().log(Level.WARNING, "[VEvents] The command you are trying to access can only be used in-game.");
                        return true;
                    }
                    
                    //Runs the command
                    command.setupSubCommands();
                    command.run(cs, args);
                    return true;
                    
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(CommandManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        /*
         * Need to add alias checking down here!
         */
        return true;
    }
}
