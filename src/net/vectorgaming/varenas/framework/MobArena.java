
package net.vectorgaming.varenas.framework;

import com.garbagemule.MobArena.ArenaMasterImpl;
import com.garbagemule.MobArena.commands.Commands;
import com.garbagemule.MobArena.framework.Arena;
import com.garbagemule.MobArena.framework.ArenaMaster;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class MobArena extends net.vectorgaming.varenas.framework.VArena
{
    private ArenaMaster am;
    private String arenaName;
    private Arena mobArena;
    private ArrayList<Location> polygon;
    
    public MobArena(String arenaName, String type)
    {
        super(arenaName, type);
        this.arenaName = arenaName;
        if(Bukkit.getPluginManager().getPlugin("MobArena") == null || !Bukkit.getPluginManager().getPlugin("MobArena").isEnabled())
        {
            Bukkit.getLogger().log(Level.SEVERE, "[VEvents] MobArena must be enabled to create MobArena events!");
            return;
        }
        com.garbagemule.MobArena.MobArena maPlugin = new com.garbagemule.MobArena.MobArena();
        
//        Field field;
//        try {
//            field = com.garbagemule.MobArena.MobArena.class.getDeclaredField("arenaMaster");
//            field.setAccessible(true);
//            Object value = field.get(maPlugin);
//            am = (ArenaMasterImpl) value;
//        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
//            Logger.getLogger(MobArena.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        am = maPlugin.getArenaMaster();

        
//        am = new ArenaMasterImpl(maPlugin);
//        am.initialize();
        mobArena = am.getArenaWithName("default");
        if(mobArena == null)
            System.out.println("null arena");
        System.out.println(am.getArenas());
        System.out.println(am.getEnabledArenas());
        System.out.println(am.getPermittedArenas(Bukkit.getPlayer("comedyallianz")));
    }
    
    @Override
    public void start()
    {
        for(Player p : getPlayers())
        {
            mobArena = Commands.getArenaToJoinOrSpec(am, p, arenaName);
        }
    }
    
    @Override
    public void forceStop()
    {
        mobArena.forceEnd();
    }
    
    @Override
    public boolean isRunning()
    {
        if(mobArena.isRunning())
            return true;
        return false;
    }
    
    @Override
    public void readyArena(){}
    
    @Override
    public void sendEndMessage(){}
    
}
