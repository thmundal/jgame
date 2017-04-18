/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valuepacks;

import java.awt.Graphics;
import jgame.Vector2;
import undirectedgraph.UGNodeInterface;
/**
 * 
 * An arbitrary class to have a value for the Vertices. I chose a vector value so that it could be easily drawn and visualized
 * This class implements the required UGNodeInterface interface, and a method for calculating a weight relative to another value,
 * in this case the distance between the two vectors. This class also inherits from Vector2 which has methods for doing vector calculations.
 * The Vector2 class is defined in the jgame project that is included as a library for this Undirected Graph project.
 * @author thmun
 */
public class Vector extends Vector2 implements UGNodeInterface<Vector> {
    /**
     * Constructing a vector
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Vector(float x, float y) {
        super(x, y);
    }
    
    /**
     * Calculate weight between two vectors
     * @param p The Vector to compare to
     * @return The weight
     */
    @Override
    public float weight(Vector p) {
        return subtract(p).length();
    }
}
