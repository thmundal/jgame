/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 *
 */
public class Cell implements AStarNodeInterface, Cloneable {
    public Transform transform;
    public Vector2 size;
    public Color color;
    public boolean wall;
    public boolean visited;
    public Vector2 coords;
    public List<AStarNodeInterface> neighbours;
    private float h;
    private float g;
    private AStarNodeInterface _parent;
    private AStarNodeInterface _next;
    
    public Cell(Vector2 s, Vector2 p, Color c) {
        size = s;
        color = c;
        transform = new Transform();
        transform.position = p;
        wall = true;
        visited = false;
        coords = new Vector2(p.x / s.x, p.y / s.y);
    }
    
    public Cell() {
        color = Color.white;
        _parent = null;
        _next = null;
    }
    
    public void Draw(Graphics g) {
        if(wall) {
            g.setColor(Color.black);
        } else {
            g.setColor(color);
        }
        g.fillRect((int) transform.position.x, (int) transform.position.y, (int) size.x, (int) size.y);
    }

    @Override
    public List<AStarNodeInterface> getSuccessors() {
        return neighbours;
    }

    @Override
    public float h() {
        return h;
    }

    @Override
    public float f() {
        return h + g;
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
        if(wall) {
            _g += 10;
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sety(int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    public String toString() {
        return "Cell at " + coords;
    }

    @Override
    public void setNext(AStarNodeInterface p) {
        _next = p;
    }

    @Override
    public AStarNodeInterface next() {
        return _next;
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
        _next = null;
        _parent = null;
        
        g = -1;
        h = -1;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
