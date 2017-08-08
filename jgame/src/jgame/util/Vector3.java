/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.util;

/**
 *
 * @author thmun
 */
public class Vector3 extends Vector {
    public Vector3(float x, float y, float z) {
        super(3);
        
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
    }
    
    public float x() {
        return coords[0];
    }
    
    public float y() {
        return coords[1];
    }
    
    public float z() {
        return coords[2];
    }
}
