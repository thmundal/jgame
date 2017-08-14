/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import jgame.util.Vector2;

/**
 *
 * @author thmun
 */
public class Transform {
    public Vector2 position;
    public float rotation;
    public static Vector2 global_size;
    
    public Transform(Vector2 position, float rotation) {
        this.position = position;
        this.rotation = rotation;
    }
    
    public Transform() {
        position = new Vector2(0, 0);
        rotation = 0.0f;
    }
    
    public float xNormalized() {
        return position.x / global_size.x;
    }
    
    public float yNormalized() {
        return position.y / global_size.y;
    }
}
