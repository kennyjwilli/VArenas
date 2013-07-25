
package net.vectorgaming.varenas;

import java.util.ArrayList;
import java.util.HashMap;
import net.vectorgaming.varenas.framework.ArenaLobby;
import net.vectorgaming.varenas.framework.ArenaSpectatorBox;
import net.vectorgaming.varenas.framework.MobArena;
import net.vectorgaming.varenas.framework.VArena;
import net.vectorgaming.varenas.framework.PVPArena;
import net.vectorgaming.vevents.EventManager;
import net.vectorgaming.vevents.VEvents;
import net.vectorgaming.vevents.event.VEvent;
import net.vectorgaming.vevents.event.type.EventType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaManager 
{
    private static HashMap<String, VArena> arenas = new HashMap<>();
    private static ArrayList<String> readyArenas = new ArrayList<>();
    private static ArrayList<Player> arenaPlayers = new ArrayList<>();
    private static VEvents plugin;
    
    public ArenaManager(VEvents instance)
    {
        plugin = instance;
    }
    
    public static void createArena(String name, EventType type) throws Exception
    {
        String temp = "";
        try
        {
            EventManager.getEventClass(type.toString()).newInstance();
        }catch(Exception e)
        {
            
        }
        
        switch(type)
        {
            case PVP_ARENA:
                System.out.println("4");
                arenas.put(name, new PVPArena(name));
                System.out.println("3");
            case MOB_ARENA:
                System.out.println("5");
                //arenas.put(name, new MobArena(name));
                return;
            case TDM:
                System.out.println("6");
                return;
            case CTF:
                System.out.println("7");
                return;
            default:
        }
        System.out.println("1");
        getArena(name).setLobby(new ArenaLobby());
        getArena(name).setSpectatorBox(new ArenaSpectatorBox());
        System.out.println("2");
    }
    
    public static void addArena(String name, VArena arena){arenas.put(name, arena);}
    
    public static void removeArena(String name){arenas.remove(name);}
    
    public static VArena getArena(String name){return arenas.get(name);}
    
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
    
    public static boolean isArenaReady(VArena arena) {return isArenaReady(arena.getName());}
    
    public static ArrayList<Player> getAllArenaPlayers() {return arenaPlayers;}
    
    public static void addPlayerToArena(Player player, String arena)
    {
        if(arenaExists(arena) && isArenaReady(arena) && !isPlayerInArena(player))
        {
            if(!arenaPlayers.contains(player)) arenaPlayers.add(player);
            getArena(arena).addPlayer(player);
        }
    }
        
    public static void addPlayerToArena(Player player, VArena arena) {addPlayerToArena(player, arena.getName());}
    
    public static void removePlayerFromArena(Player player, String arena)
    {
        if(arenaExists(arena))
        {
            arenaPlayers.remove(player);
            getArena(arena).removePlayer(player);
        }
    }
    
    public static boolean isPlayerInArena(Player player)
    {
        for(Player p : getAllArenaPlayers())
        {
            if(p == player) return true;
        }
        return false;
    }
    
    public static ArrayList<Location> getPolygonPoints(Location minY, Location maxY)
    {
        ArrayList<Location> result = new ArrayList<>();
        double xChange = maxY.getBlockX() - minY.getBlockX();
        double yChange = maxY.getBlockY() - minY.getBlockY();
        double zChange = maxY.getBlockZ() - minY.getBlockZ();

        Location loc1 = new Location(minY.getWorld(), minY.getBlockX() + xChange, minY.getBlockY(), minY.getBlockZ());
        Location loc2 = new Location(minY.getWorld(), minY.getBlockX(), minY.getBlockY() + yChange, minY.getBlockZ());
        Location loc3 = new Location(minY.getWorld(), minY.getBlockX(), minY.getBlockY(), minY.getBlockZ() + zChange);
        result.add(minY);
        result.add(maxY);
        result.add(loc1);
        result.add(loc2);
        result.add(loc3);
        return result;
    }
    
    public static VEvents getVEventsPlugin()
    {
        return plugin;
    }
    
}
