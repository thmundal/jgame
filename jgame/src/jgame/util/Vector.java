/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.util;

import java.util.Arrays;


/**
 *
 * @author thmun
 */
public class Vector {
    protected float[] coords;
    int size;
    
    public Vector(int size) {
        coords = new float[size];
        this.size = size;
    }

    public Vector copy() {
        Vector c = new Vector(size);
        c.coords = coords.clone();
        return c;
    }
    
    @FunctionalInterface
    private interface cb {
        public void run(Vector v, int i,  float c);
    }
    
    public void forEach(cb fi) {
        for(int i=0; i<coords.length; i++) {
            fi.run(this, i, coords[i]);
        }
    }
    
    public Vector add(Vector vec) {
        Vector out = this.copy();
        
        out.forEach((v, i, c) -> {
            v.coords[i] = v.coords[i] + vec.coords[i];
        });
        
        return out;
    }
    
    public Vector subtract(Vector v) {
        return add(v.scale(-1.0f));
    }
    
    public Vector scale(float n) {
        Vector out = this.copy();
        
        out.forEach((v, i, c) -> {
            v.coords[i] = v.coords[i] * n;
        });
        
        return out;
    }
    
    public float length() {
        float out = 0;
        
        for(float c : coords) {
            out += (c * c);
        }
        
        return (float) Math.sqrt(out);
    }
    
    public Vector normalized() {
        
        //or:
        return scale(1 / length());
    }
    
    
    public Vector toUnitVector() {
        return normalized();
    }
    
    public String toString() {
        return Arrays.toString(coords);
    }
}
