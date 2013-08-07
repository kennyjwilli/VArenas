
package net.vectorgaming.varenas.framework.enums;

/**
 *
 * @author Kenny
 */
public enum EventResult 
{
    GAME_RUNNING(0),
    ONE_WINNER(1),
    TEAM_WINNER(2);
    
    private final int eventResult;
    
    EventResult(int i)
    {
        eventResult = i;
    }
}
