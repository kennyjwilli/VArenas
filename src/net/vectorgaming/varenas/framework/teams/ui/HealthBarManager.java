package net.vectorgaming.varenas.framework.teams.ui;

import net.vectorgaming.varenas.framework.teams.ArenaTeamData;
import net.vectorgaming.varenas.framework.teams.SubTeam;
import net.vectorgaming.varenas.framework.teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scoreboard.Team;

public class HealthBarManager extends TeamUIComponent implements Listener{
    private final String HEALTH_BAR = "█";
    private final String HALF_HEALTH_BAR = "▌";
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
    
    @EventHandler
    public void onEntyDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player)event.getEntity();
            ArenaTeamData team = getTeamManager().getTeam(player);
            if(team != null){
                SubTeam childTeam = team.getChildTeam(player);
                if(childTeam != null){
                    update(player, childTeam);
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
    
    public void update(Player player, Team team){
        String healthBar = "";
        double health = player.getHealth() / player.getMaxHealth() * player.getHealthScale();
        int fullHealthBars = (int)(health / healthPerBar);
        for(int i = 0; i < fullHealthBars; i++){
            healthBar += HEALTH_BAR;
        }
        double healthLeft = health - fullHealthBars * healthPerBar;
        if(healthLeft < healthPerBar / 2){
            healthBar += HALF_HEALTH_BAR;
        }
        ChatColor color = getColorBasedOnHealth(player.getHealth(), player.getMaxHealth());
        team.setSuffix(color + healthBar);
    }
}
