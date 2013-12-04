
package net.vectorgaming.varenas.chat;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.vchat.framework.channel.Channel;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaChannel extends Channel
{
    private final String arenaName;
    /**
     *
     * @param arena Name of the arena that the channel will use
     */
    public ArenaChannel(String arena)
    {
        super(arena, "ARENA_CHANNEL");
        arenaName = arena;
    }
    
    @Override
    public String onChat(Player player, String message)
    {
        Arena arena = ArenaManager.getArena(arenaName);
        super.onChat(player, message);
        String formatted = getChatParser().replaceAll(getFormat());
        for(Player p : ArenaPlayerManager.getPlayersInArena(arena))
        {
            p.sendMessage(formatted);
        }
        return formatted;
    }
}
