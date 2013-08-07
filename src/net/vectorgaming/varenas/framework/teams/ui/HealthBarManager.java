package net.vectorgaming.varenas.framework.teams.ui;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.framework.teams.ArenaTeam;
import net.vectorgaming.varenas.framework.teams.ArenaTeamData;
import net.vectorgaming.varenas.framework.teams.SubTeam;
import net.vectorgaming.varenas.framework.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

public class HealthBarManager extends TeamUIComponent implements Listener{
    private final String[] HEALTH_BAR = {"█","▉","▊","▋","▌","▍","▎","▏"};
    private double healthPerBar = 2;
    private final BukkitTask updateTast;
    private boolean usingScaledHealth = false;
    
    public HealthBarManager(TeamManager teamManager){
        super(teamManager);
        updateTast = Bukkit.getScheduler().runTaskTimer(ArenaAPI.getPlugin(), new Runnable() {
            @Override
            public void run() {
                updateAll();
            }
        }, 100, 100); //100 ticks = 5 seconds
    }
    
    public double getHealthPerBar() {
        return healthPerBar;
    }
    public void setHealthPerBar(double healthPerBar) {
        this.healthPerBar = healthPerBar;
    }

    public boolean isUsingScaledHealth() {
        return usingScaledHealth;
    }
    public void setUsingScaledHealth(boolean useScaledHealth) {
        this.usingScaledHealth = useScaledHealth;
    }
    
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity)event.getEntity();
            ArenaTeamData team = getTeamManager().getTeam(livingEntity);
            if(team != null){
                if(livingEntity instanceof Player){
                    SubTeam childTeam = team.getChildTeam(livingEntity);
                    schedualUpdate((Player)livingEntity, childTeam);
                } else {
                    schedualUpdate(livingEntity);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRegainHealth(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity)event.getEntity();
            ArenaTeamData team = getTeamManager().getTeam(livingEntity);
            if(team != null){
                if(livingEntity instanceof Player){
                    SubTeam childTeam = team.getChildTeam(livingEntity);
                    schedualUpdate((Player)livingEntity, childTeam);
                } else {
                    schedualUpdate(livingEntity);
                }
            }
        }
    }
    
    public ChatColor getColorBasedOnHealth(double health, double maxHealth){
        double healthPercentage = health / maxHealth;
        if(healthPercentage <= 0.33){
            return ChatColor.RED;
        } else if(healthPercentage <= 0.66){
            return ChatColor.YELLOW;
        }
        return ChatColor.GREEN;
    }
    public String getHealthBar(double health, double maxHealth, double healthScale){
        String healthBar = "";
        //Makes sure a max of 28 bars are showed
        double scaledHealth;
        int fullHealthBars;
        if(healthScale / healthPerBar > 28){
            scaledHealth = health / maxHealth * 28 * healthPerBar;
            fullHealthBars = (int)(scaledHealth / healthPerBar);
        } else {
            scaledHealth = health / maxHealth * healthScale;
            fullHealthBars = (int)(scaledHealth / healthPerBar);
        }
        
        for(int i = 0; i < fullHealthBars; i++){
            healthBar += HEALTH_BAR[0];
        }
        double healthLeft = scaledHealth - fullHealthBars * healthPerBar;
        if(healthLeft > 0){
            healthBar += HEALTH_BAR[HEALTH_BAR.length - 1 - (int)((double)HEALTH_BAR.length / (healthPerBar / healthLeft))];
        }
        
        return healthBar;
    }
    
    public void updateAll(){
        for(ArenaTeam team : this.getTeamManager().getTeams()){
            for(SubTeam childTeam : team.getChildTeams()){
                for(OfflinePlayer offlinePlayer : childTeam.getPlayers()){
                    if(offlinePlayer.isOnline()){
                        Player player = offlinePlayer.getPlayer();
                        if(player.getHealth() != player.getMaxHealth()){
                            update(player,childTeam);
                        }
                    }
                }
                for(Entity entity : childTeam.getFriendlyEntities()){
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity)entity;
                        if(livingEntity.getHealth() != livingEntity.getMaxHealth()){
                            update(livingEntity);
                        }
                    }
                }
            }
            for(Entity entity : team.getFriendlyEntities()){
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity)entity;
                    if(livingEntity.getHealth() != livingEntity.getMaxHealth()){
                        update(livingEntity);
                    }
                }
            }
        }
    }
    public void schedualUpdate(final LivingEntity entity){
        Bukkit.getScheduler().runTask(ArenaAPI.getPlugin(), new Runnable(){
            @Override
            public void run() {
                update(entity);
            }
        });
    }
    public void schedualUpdate(final Player player, final Team team){
        Bukkit.getScheduler().runTask(ArenaAPI.getPlugin(), new Runnable(){
            @Override
            public void run() {
                update(player,team);
            }
        });
    }
    
    public void update(Player player, Team team){
        update(player,team,player.getHealth());
    }
    public void update(Player player, Team team, double newHealth){
        double healthScale = player.getMaxHealth();
        if(isUsingScaledHealth()){
            healthScale = player.getHealthScale();
        }
        String healthBar = getHealthBar(newHealth,player.getMaxHealth(),healthScale);
        ChatColor color = getColorBasedOnHealth(newHealth, player.getMaxHealth());
        team.setSuffix(color + healthBar);
    }
    public void update(LivingEntity entity){
        update(entity,entity.getHealth());
    }
    public void update(LivingEntity entity, double newHealth){
        String healthBar = getHealthBar(newHealth,entity.getMaxHealth(),entity.getMaxHealth());
        ChatColor color = getColorBasedOnHealth(newHealth, entity.getMaxHealth());
        entity.setCustomName(color + healthBar);
        entity.setCustomNameVisible(true);
    }

    @Override
    public void remove() {
        super.remove();
        updateTast.cancel();
    }
}
