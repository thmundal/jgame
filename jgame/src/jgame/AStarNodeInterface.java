/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thmun
 */
public interface AStarNodeInterface {
    public List<AStarNodeInterface> getSuccessors();
    public float h();
    public float f();
    public float g();
    
    public void seth(float _h);
    public void setg(float _g);
    
    public int x();
    public int y();
    
    public void setx(int _x);
    public void sety(int _y);
    
    public AStarNodeInterface parentNode();
    public void setParent(AStarNodeInterface p);
    
    public void setColor(Color c);
    public void setNext(AStarNodeInterface p);
    
    public AStarNodeInterface next();
    public AStarNodeInterface previous();
    
    public Transform transform();
    
    public void reset();
    // public Object clone() throws CloneNotSupportedException;
}
