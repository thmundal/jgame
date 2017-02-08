/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class AStarNode<T> implements AStarNodeInterface {
    private Vector2 coords;
    private float h;
    private float g;
    private AStarNodeInterface _parent;
    private Color color;
    private AStarNodeInterface next;
    private Transform transform;
    private T cell;
    public static Hashtable<Vector2, AStarNode> nodelist;
    
    public AStarNode(Vector2 _coords) {
        coords = _coords;
        nodelist.put(coords, this);
    }
    
    public void setCell(T c) {
        cell = c;
    }
    
    public T Cell() {
        return cell;
    }
    
    public static AStarNode nodeAt(Vector2 _coords) {
        return nodelist.get(_coords);
    }
    
    @Override
    public List<AStarNodeInterface> getSuccessors() {
        List<AStarNodeInterface> n = new ArrayList<AStarNodeInterface>();
        
        for(float _y = coords.y - 1; _y <= coords.y+1; _y++) {
            for(float _x = coords.x - 1; _x <= coords.x+1; _x++) {
                // This has to find a reference to an existing node at these coords
                // Not make a new instance
                //n.add(new AStarNode(new Vector2(_x, _y))); 
                n.add(AStarNode.nodeAt(new Vector2(_x, _y)));
            }
        }
        return n;
    }

    @Override
    public float h() {
        return h;
    }

    @Override
    public float f() {
        return g + h;
    }

    @Override
    public float g() {
        return g;
    }

    @Override
    public void seth(float _h) {
        h = _h;
    }

    @Override
    public void setg(float _g) {
        g = _g;
    }

    @Override
    public int x() {
        return (int) coords.x;
    }

    @Override
    public int y() {
        return (int) coords.y;
    }

    @Override
    public void setx(int x) {
        coords.x = (float) x;
    }

    @Override
    public void sety(int y) {
        coords.y = (float) y;
    }

    @Override
    public AStarNodeInterface parentNode() {
        return _parent;
    }

    @Override
    public void setParent(AStarNodeInterface p) {
        _parent = p;
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void setNext(AStarNodeInterface p) {
        next = p;
    }

    @Override
    public AStarNodeInterface next() {
        return next;
    }

    @Override
    public AStarNodeInterface previous() {
        return _parent;
    }

    @Override
    public Transform transform() {
        return transform;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
