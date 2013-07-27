
package net.vectorgaming.varenas;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import net.vectorgaming.varenas.framework.PVPArena;
import net.vectorgaming.varenas.framework.Arena;

/**
 *
 * @author Kenny
 */
public class ArenaRegistration 
{
    public static HashMap<String, Class<? extends Arena>> arenas = new HashMap<>();
    
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
    public static boolean arenaTypeExists(String type)
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
    public static void registerArenaType(String type, Class<? extends Arena> c)
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
    public static Arena getArenaClass(String name, String type) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        return arenas.get(type).getDeclaredConstructor(String.class, String.class).newInstance(name, type);
    }
}
