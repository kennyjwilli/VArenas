/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.Exceptions;

/**
 *
 * @author jeppe
 */
public class ScoreboardNotEqualException extends Exception{
    public ScoreboardNotEqualException(){
        super("Scoreboards are not equal to each other");
    }
}
