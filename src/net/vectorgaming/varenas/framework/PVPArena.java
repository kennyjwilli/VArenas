
package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
import net.vectorgaming.vevents.event.type.EventType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 *
 * @author Kenny
 */
public class PVPArena extends Arena
{
    public PVPArena(String name) throws Exception 
    {
        super(name);
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void readyArena() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRunning() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void forceStop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventType getEventType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendEndMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
