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
public class AStarNode implements AStarNodeInterface {

    private Vector2 coords;
    private float h;
    private float g;
    private AStarNode _parent;
    private Color color;
    private AStarNode next;
    private Transform transform;
    private Cell cell;
    public static Grid grid = null;
    public static Hashtable<String, AStarNode> nodelist = new Hashtable<String, AStarNode>();
    
    public AStarNode(Vector2 _coords) {
        coords = _coords;
        AStarNode.nodelist.put(coords.toString(), this);
    }

    public static void setGrid(Grid g) {
        AStarNode.grid = g;
    }
    
    public void setCell(Cell c) {
        cell = c;
    }
    
    public Cell Cell() {
        return grid.CellAt(coords);
    }
    
    // Maybe create a function called setNodeList() That sets a list of nodes from the grid?
    public static AStarNode nodeAt(Vector2 _coords) {
        AStarNode n = AStarNode.nodelist.get(_coords.toString());
        
        if(n == null && AStarNode.grid != null) {
            Cell c = AStarNode.grid.CellAt(_coords);
            
            if(c != null) {
                n = new AStarNode(_coords);
                //n.setCell(c);
                AStarNode.nodelist.put(_coords.toString(), n);
            }
        }
        return n;
    }
    
    @Override
    public List<AStarNode> getSuccessors() {
        List<AStarNode> n = new ArrayList<AStarNode>();
        
        // all 8 neighbours
        /*
        for(float _y = coords.y - 1; _y <= coords.y+1; _y++) {
            for(float _x = coords.x - 1; _x <= coords.x+1; _x++) {
                n.add(AStarNode.nodeAt(new Vector2(_x, _y)));
            }
        }*/
        
        // Only 4 neighbours
        n.add(AStarNode.nodeAt(new Vector2(coords.x - 1, coords.y)));
        n.add(AStarNode.nodeAt(new Vector2(coords.x + 1, coords.y)));
        n.add(AStarNode.nodeAt(new Vector2(coords.x, coords.y - 1)));
        n.add(AStarNode.nodeAt(new Vector2(coords.x, coords.y + 1)));
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
        if(Cell().wall) {
            _g += 10;
        }
        g = _g;
    }

    @Override
    public float x() {
        return coords.x;
    }

    @Override
    public float y() {
        return coords.y;
    }

    @Override
    public void setx(float x) {
        coords.x = x;
    }

    @Override
    public void sety(float y) {
        coords.y = y;
    }

    @Override
    public AStarNode parentNode() {
        return _parent;
    }

    @Override
    public void setParent(AStarNode p) {
        _parent = p;
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void setNext(AStarNode p) {
        next = p;
    }

    @Override
    public AStarNode next() {
        return next;
    }

    @Override
    public AStarNode previous() {
        return _parent;
    }

    @Override
    public Transform transform() {
        return transform;
    }

    @Override
    public void reset() {
        AStarNode.nodelist.clear();
    }
    
}
