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
 * @author Thomas
 */
public class Vector extends Vector2 implements UGNodeInterface<Vector> {
    public Vector(float x, float y) {
        super(x, y);
    }
    
    @Override
    public float weight(Vector p) {
        return subtract(p).length();
    }
}
