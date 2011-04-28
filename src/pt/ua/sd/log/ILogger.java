/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.log;

/**
 *
 * @author eriksson
 */
public interface ILogger {
    public int getClockTick();
    public void push(String type, String entity, String message,int tick);
    public String popContiguous();
}
