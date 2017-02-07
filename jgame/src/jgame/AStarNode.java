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
public interface AStarNode {
    public List<AStarNode> getSuccessors();
    public float h();
    public float f();
    public float g();
    
    public void seth(float h);
    public void setg(float g);
    
    public int x();
    public int y();
    
    public void setx(int x);
    public void sety(int y);
    
    public AStarNode parentNode();
    public void setParent(AStarNode p);
    
    public void setColor(Color c);
    public void setNext(AStarNode p);
    
    public AStarNode next();
    public AStarNode previous();
    
    public Transform transform();
    
    public void reset();
    // public Object clone() throws CloneNotSupportedException;
}
