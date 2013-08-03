/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas;

/**
 *
 * @author jeppe
 */
public class ArenaAPI {
    private static VArenas plugin;
    public ArenaAPI(VArenas plugin){
        ArenaAPI.plugin = plugin;
    }

    public static VArenas getPlugin() {
        return plugin;
    }
}
