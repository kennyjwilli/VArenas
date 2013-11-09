
package net.vectorgaming.varenas.chat;

import net.vectorgaming.vchat.framework.channel.Channel;
import net.vectorgaming.vchat.framework.channel.ChannelCreator;

/**
 *
 * @author Kenny
 */
public class ArenaChannelCreator extends ChannelCreator
{
    @Override
    public Channel createChannel(String arena)
    {
        return new ArenaChannel(arena);
    }

    @Override
    public String getType()
    {
        return "ARENA_CHANNEL";
    }

}
