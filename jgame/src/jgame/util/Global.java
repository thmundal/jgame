/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.util;

import jgame.Game;

/**
 *
 * @author Thomas Mundal <thomas@munso.no>
 */
public abstract class Global<T> {
    public static int WIDTH = make(Game.WIDTH);
    public static int HEIGHT = make(Game.HEIGHT);
    public static int LEFT_EDGE = make(-Game.WIDTH);
    public static int RIGHT_EDGE = make(Game.WIDTH);
    public static int TOP_EDGE = make(Game.HEIGHT);
    public static int BOTTOM_EDGE = make(-Game.HEIGHT);
    
    T value;
    private Global(T n) {
        this.value = n;
    }
    
    public T val() {
        return value;
    }
    
    public static <E>E make(E n) {
        return n;
    }
}
