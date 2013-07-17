
package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author Kenny
 */
public class VRegion 
{
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;
    
    public VRegion(Location loc1, Location loc2)
    {
        if(loc1.getWorld() != loc2.getWorld())
        {
            Bukkit.getLogger().log(Level.SEVERE, "Region points must be in the same world!");
            return;
        }
        minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        
        minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        
        minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
    }
    
    public List<String> getSaveFormat()
    {
        List<String> result = new ArrayList<>(3);
        result.add(minX+";"+maxX);
        result.add(minY+";"+maxY);
        result.add(minZ+";"+maxZ);
        return result;
    }
    
}
