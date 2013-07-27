
package net.vectorgaming.varenas;

import java.util.ArrayList;
import java.util.HashMap;
import net.vectorgaming.varenas.framework.VArena;

/**
 *
 * @author Kenny
 */
public class ArenaRegistration 
{
    public static HashMap<String, Class<? extends VArena>> arenas = new HashMap<>();
    
    /**
     * Gets all registered arena types 
     * @return ArrayList<String>
     */
    public static ArrayList<String> getArenaTypes()
    {
        ArrayList<String> result = new ArrayList<>();
        for(String s : arenas.keySet())
        {
            if(!result.contains(s))
                result.add(s);
        }
        return result;
    }
    
    /**
     * Gets if the specified event type exists
     * @param type String
     * @return Boolean
     */
    public static boolean eventTypeExists(String type)
    {
        if(arenas.containsKey(type))
            return true;
        return false;
    }
    
    /**
     * Registers an arena type.
     * WARNING: This method MUST be called BEFORE you can create an arena in the ArenaManager
     * @param type String
     * @param c Class<? extends VArena>
     */
    public static void registerArenaType(String type, Class<? extends VArena> c)
    {
        arenas.put(type, c);
    }
    
    /**
     * Unregisters an arena type
     * @param type String
     */
    public static void unregisterArenaType(String type)
    {
        arenas.remove(type);
    }
    
    /**
     * Gets a new instance of the arena class for the specified type. 
     * You should do all initialization of variables in your class constructor
     * @param type String
     * @return VArena
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static VArena getArenaClass(String type) throws InstantiationException, IllegalAccessException
    {
        return arenas.get(type).newInstance();
    }
}
