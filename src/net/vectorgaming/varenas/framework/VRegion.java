
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public abstract class VRegion
{
    private ArrayList<Player> players = new ArrayList<>();
    private Location spawn;
    private TriggerBox polygon;
    
    /**
     * Sets the spawn for a region
     * @param spawn Location
     */
    public void setSpawn(Location spawn)
    {
        this.spawn = spawn;
    }
    
    /**
     * Gets the spawn for a region
     * @return Location
     */
    public Location getSpawn()
    {
        return spawn;
    }
    
    /**
     * Sets the region for an arena element
     * @param polygon TriggerBox
     */
    public void setRegion(TriggerBox polygon)
    {
        this.polygon = polygon;
    }
    
    /**
     * Gets the region for the arena element
     * @return TriggerBox
     */
    public TriggerBox getRegion()
    {
        return polygon;
    }
}
