/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework;

import java.util.HashMap;

/**
 *
 * @author jeppe
 */
public class KillStreakManager {
    private HashMap<Integer,String> killStreakMessages = new HashMap();
    private String firstBloodMessage = null;
    
    public HashMap<Integer,String> getKillStreakMessages(){
        return killStreakMessages;
    }
    public void addKillStreak(int kills, String message){
        getKillStreakMessages().put(kills, message);
    }
    public String getKillStreakMessage(int kills){
        return getKillStreakMessages().get(kills);
    }
    public String getKillStreakMessage(int kills, String playerName){
        return getKillStreakMessages().get(kills);
    }
    
    public String format(String message, String playerName){
        return message.replace("<player>", playerName);
    }

    public String getFirstBloodMessage() {
        return firstBloodMessage;
    }
    public void setFirstBloodMessage(String firstBloodMessage) {
        this.firstBloodMessage = firstBloodMessage;
    }
    
    public void addDefaultKillStreakMessages(){
        firstBloodMessage = "First Blood";
        killStreakMessages.put(2, "<player> Double Kill!");
        killStreakMessages.put(3, "<player> Thriple Kill!");
        killStreakMessages.put(4, "<player> Ultra Kill!");
        killStreakMessages.put(5, "<player> Rampage!");
        killStreakMessages.put(7, "<player> Monster Kill!");
        killStreakMessages.put(11, "<player> God Like!");
    }
    public void addKillMessagesForEveryKill(){
        firstBloodMessage = "First Blood";
        killStreakMessages.put(2, "<player> Double Kill!");
        killStreakMessages.put(3, "<player> Thriple Kill!");
        killStreakMessages.put(4, "<player> Ultra Kill!");
        killStreakMessages.put(5, "<player> Rampaage!");
        killStreakMessages.put(6, "<player> Killing spree!");
        killStreakMessages.put(7, "<player> Dominating!");
        killStreakMessages.put(8, "<player> Unstoppable!");
        killStreakMessages.put(9, "<player> Wicked Sick!");
        killStreakMessages.put(10, "<player> Monster Kill!");
        killStreakMessages.put(11, "<player> Godlike!");
    }
}
