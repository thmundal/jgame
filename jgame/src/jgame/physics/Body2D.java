/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.physics;

import jgame.Transform;
import jgame.util.Vector2;

/**
 *
 * @author Thomas Mundal <thomas@munso.no>
 */
public class Body2D<T extends jgame.shapes.Entity> {
    private Transform transform;
    private T entity;
    private Vector2 velocity;
    private Vector2 bounds;
    
    public Body2D() {
        
    }
    
    public Body2D(T entity) {
        this.entity = entity;
        this.bounds = new Vector2(entity.width, entity.height);
    }
    
    public void move(Vector2 pos) {
        transform.position = pos;
        
        if(entity != null)
            entity.transform.position = pos;
    }
}
