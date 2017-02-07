/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

/**
 *
 * @author Thomas
 */
public class Vector2 {
    public float x;
    public float y;
    
    public Vector2(float _x, float _y) {
        x = _x;
        y = _y;
    }
    
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
        return new Vector2(this.x * n, this.y * n);
    }
    
    public String toString() {
        return "[" + x + ", " + y +"]";
    }
}
