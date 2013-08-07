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
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

public class HealthBarManager extends TeamUIComponent implements Listener{
    private final String[] HEALTH_BAR = {"█","▉","▊","▋","▌","▍","▎","▏"};
    private double healthPerBar = 2;
    private final BukkitTask updateTast;
    
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
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity)event.getEntity();
            ArenaTeamData team = getTeamManager().getTeam(livingEntity);
            if(team != null){
                if(livingEntity instanceof Player){
                    SubTeam childTeam = team.getChildTeam(livingEntity);
                    update((Player)livingEntity, childTeam, livingEntity.getHealth() - event.getDamage());
                } else {
                    update(livingEntity,livingEntity.getHealth() - event.getDamage());
                }
            }
        }
    }
    
    public ChatColor getColorBasedOnHealth(double health, double maxHealth){
        double healthPercentage = health / maxHealth;
        if(healthPercentage <= 0.25){
            return ChatColor.RED;
        } else if(healthPercentage <= 0.5){
            return ChatColor.YELLOW;
        }
        return ChatColor.GREEN;
    }
    public String getHealthBar(double health, double maxHealth, double healthScale){
        String healthBar = "";
        double scaledHealth = health * healthScale;
        int fullHealthBars = (int)(scaledHealth / healthPerBar);
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
                        update(offlinePlayer.getPlayer(),childTeam);
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
    
    public void update(Player player, Team team){
        update(player,team,player.getHealth());
    }
    public void update(Player player, Team team, double newHealth){
        String healthBar = getHealthBar(newHealth,player.getMaxHealth(),player.getHealthScale());
        ChatColor color = getColorBasedOnHealth(newHealth, player.getMaxHealth());
        team.setSuffix(color + healthBar);
    }
    public void update(LivingEntity entity){
        update(entity,entity.getHealth());
    }
    public void update(LivingEntity entity, double newHealth){
        String healthBar = getHealthBar(newHealth,entity.getMaxHealth(),1);
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
