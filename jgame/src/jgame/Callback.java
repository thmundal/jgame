/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jgame;
import java.awt.Graphics;
/**
 *
 * @author Thomas
 */
@FunctionalInterface
public interface Callback {
    void run(Object a, Object b);
    //void run(Graphics g, float d);
    //void run(Game g, float d);
    //void run(float d);
}