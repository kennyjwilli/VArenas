
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
    
    public void setSpawn(Location spawn)
    {
        this.spawn = spawn;
    }
    
    public Location getSpawn()
    {
        return spawn;
    }
    
    public void setRegion(TriggerBox polygon)
    {
        this.polygon = polygon;
    }
    
    public TriggerBox getRegion()
    {
        return polygon;
    }
}
