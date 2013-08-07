package net.vectorgaming.varenas.framework.teams.ui;

import java.util.List;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.framework.teams.ArenaTeamData;
import net.vectorgaming.varenas.framework.teams.SubTeam;
import net.vectorgaming.varenas.framework.teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;

public class HealthBarManager extends TeamUIComponent implements Listener{
    private final String[] HEALTH_BAR = {"█","▉","▊","▋","▌","▍","▎","▏"};
    private double healthPerBar = 2;
    
    public HealthBarManager(TeamManager teamManager){
        super(teamManager);
    }

    public double getHealthPerBar() {
        return healthPerBar;
    }
    public void setHealthPerBar(double healthPerBar) {
        this.healthPerBar = healthPerBar;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntyDamage(EntityDamageEvent event){
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
        healthBar += HEALTH_BAR[HEALTH_BAR.length - 1 - (int)((double)HEALTH_BAR.length / (healthPerBar / healthLeft))];
        
        return healthBar;
    }
    
    public void update(Player player, Team team){
        update(player,team,player.getHealth());
    }
    public void update(Player player, Team team, double newHealth){
        String healthBar = getHealthBar(newHealth,player.getMaxHealth(),player.getHealthScale());
        ChatColor color = getColorBasedOnHealth(player.getHealth(), player.getMaxHealth());
        team.setSuffix(color + healthBar);
    }
    public void update(LivingEntity entity){
        update(entity,entity.getHealth());
    }
    public void update(LivingEntity entity, double newHealth){
        String healthBar = getHealthBar(newHealth,entity.getMaxHealth(),1);
        ChatColor color = getColorBasedOnHealth(entity.getHealth(), entity.getMaxHealth());
        entity.setCustomName(color + healthBar);
        entity.setCustomNameVisible(true);
    }
}
