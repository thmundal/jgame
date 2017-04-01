/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

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
    public List<AStarNode> neighbours;
    private float h;
    private float g;
    private AStarNode _parent;
    private AStarNode _next;
    
    private int wallSprite;
    private int groundSprite;
    
    public Cell(Vector2 s, Vector2 p, Color c) {
        wallSprite = -1;
        groundSprite = -1;
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
    
    public void Draw(Graphics g, Sprite[] groundSprites, Sprite[] wallSprites) {
        Random rand = new Random();
        
        if(groundSprites == null) {
            g.setColor(color);
            g.fillRect((int) transform.position.x, (int) transform.position.y, (int) size.x, (int) size.y);
        } else {
            if(groundSprite == -1)
                groundSprite = rand.nextInt(groundSprites.length);
            
            groundSprites[groundSprite].SetPosition(transform.position);
            groundSprites[groundSprite].SetSize(size);
            groundSprites[groundSprite].Draw(g);
        }
        
        if(wall) {
            if(wallSprites != null) {
                if(wallSprite == -1)
                    wallSprite = rand.nextInt(wallSprites.length);
                
                wallSprites[wallSprite].SetPosition(transform.position);
                wallSprites[wallSprite].SetSize(size);
                wallSprites[wallSprite].Draw(g);
            } else {
                g.setColor(Color.black);
                g.fillRect((int) transform.position.x, (int) transform.position.y, (int) size.x, (int) size.y);
            }
        }
    }
    
    public void Draw(Graphics g) {
        Draw(g, null, null);
    }

    @Override
    public List<AStarNode> getSuccessors() {
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
        if(wall) {
            return g + 10;
        }
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
    public float x() {
        return coords.x;
    }

    @Override
    public float y() {
        return coords.y;
    }

    @Override
    public void setx(float x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sety(float y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    public String toString() {
        return "Cell at " + coords;
    }

    @Override
    public void setNext(AStarNode p) {
        _next = p;
    }

    @Override
    public AStarNode next() {
        return _next;
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
