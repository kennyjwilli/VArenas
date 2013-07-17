
package net.vectorgaming.varenas.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public enum Msg 
{
    ARENA_READY("The arena is now ready to start!");
    
    private final String msg;
    
    Msg(String str){msg = str;}
    
    public static void sendPluginMessage(CommandSender cs, String msg)
    {
        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.DARK_BLUE+"[VArenas] "+ChatColor.WHITE+msg));
    }
    
    public static ChatColor getPluginColor(){return ChatColor.BLUE;}
}
