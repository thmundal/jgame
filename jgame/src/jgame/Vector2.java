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
public class Vector2 {
    public float x = 0;
    public float y = 0;
    
    public Vector2(float _x, float _y) {
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
    
    public Vector2 add(Vector2 vec) {
        return new Vector2(this.x + vec.x, this.y + vec.y);
    }
    
    public int intX() {
        return (int) x;
    }
    
    public int intY() {
        return (int) y;
    }
    
    public Vector2 scale(float n) {
        return new Vector2((this.x * n), (this.y * n));
    }
    
    public float length() {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    
    public double angle() {
        return Math.atan(y / x);
    }
    
    public Vector2 normalized() {
        return new Vector2(x / (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)), y / (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        //or:
        // return scale(1 / length()); divide by length
    }
    
    public Vector2 subtract(Vector2 v) {
        return new Vector2(v.x - x, v.y - y);
    }
    
    public void draw(Graphics g) {
        g.fillArc((int) x, (int) y, 5, 5, 0, 360);
        g.drawLine((int) x, (int) y, (int) (x * length()), (int) (y * length()));
    }
    
    public Vector2 toUnitVector() {
        return scale(1 / length());
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
