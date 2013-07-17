
package net.vectorgaming.varenas;

import java.util.ArrayList;
import java.util.HashMap;
import net.vectorgaming.varenas.framework.Arena;

/**
 *
 * @author Kenny
 */
public class ArenaManager 
{
    private static HashMap<String, Arena> arenas = new HashMap<>();
    private static ArrayList<String> readyArenas = new ArrayList<>();
    
    public static void addArena(String name, Arena arena){arenas.put(name, arena);}
    
    public static void removeArena(String name){arenas.remove(name);}
    
    public static Arena getArena(String name){return arenas.get(name);}
    
    public static boolean arenaExists(String name)
    {
        if(arenas.containsKey(name))
            return true;
        return false;
    }
    
    public static ArrayList<String> getReadyArenas() {return readyArenas;}
    
    public static boolean isArenaReady(String name)
    {
        if(readyArenas.contains(name))
            return true;
        return false;
    }
    
}
