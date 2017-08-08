/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.util;

import java.awt.Graphics;

/**
 *
 * @author Thomas
 */
public class Vector2 extends Vector {
    public float x = 0;
    public float y = 0;
    
    public Vector2(float _x, float _y) {
        super(2);
        
        coords[0] = _x;
        coords[1] = _y;
        this.x = _x;
        this.y = _y;
    }
    
    
    public static Vector2 fromAngle(float angle, float length) {
        float x = (float) (length * Math.cos(angle));
        float y = (float) (length * Math.sin(angle));
        
        return new Vector2(x, y);
    }
    
/*    public Vector2(float angle, float length) {
        x = (int) (length * Math.cos(angle));
        y = (int) (length * Math.sin(angle));
    }*/
    
    
    public int intX() {
        return (int) x;
    }
    
    public int intY() {
        return (int) y;
    }
    
    public void draw(Graphics g) {
        g.fillArc((int) x, (int) y, 5, 5, 0, 360);
        g.drawLine((int) x, (int) y, (int) (x * length()), (int) (y * length()));
    }
    
    /**
     * Not sure if this will work
     * @param min
     * @param max 
     */
    public void constrain(Vector2 min, Vector2 max) {
        if(subtract(min).length() < 0) {
            x = min.x;
            y = min.y;
        }
        
        if(subtract(max).length() > 0) {
            x = max.x;
            y = max.y;
        }
    }
    
    public void constrainX(float min, float max) {
        if(x < min)
            x = min;
        if(x > max)
            x = max;
    }
    
    public void constrainY(float min, float max) {
        if(y < min) {
            System.out.println("y is " + y);
            System.out.println("min is " + min);
            y = min;
        }
        if(y > max) {
            y = max;
        }
    }
    
    public String toString() {
        return "[" + x + ", " + y +"]";
    }
}
