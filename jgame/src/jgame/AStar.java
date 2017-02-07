/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author thmun
 */
public class AStar {
    private List<AStarNode> openset;
    private List<AStarNode> closedset;
    
    private AStarNode end;
    private AStarNode start;
    
    public AStar(AStarNode s, AStarNode e) {
        start = s;
        end = e;
        
        openset = new ArrayList<AStarNode>();
        closedset = new ArrayList<AStarNode>();
    }
    
    public List<AStarNode> findPath() {
        List<AStarNode> path = new ArrayList<AStarNode>();
        System.out.println("Start pathfinding");
        openset.clear();
        closedset.clear();
        
        openset.add(start);
        
        while(!openset.isEmpty()) {
             //System.out.println(openset.size());
            // Find the node with lowest f in the openlist
            AStarNode q = null;
            for(AStarNode n : openset) {
                if(q == null || n.f() < q.f()) {
                    q = n;
                }
            }
            
            openset.remove(q);
            
            q.setColor(java.awt.Color.LIGHT_GRAY);
            
            List<AStarNode> successors = q.getSuccessors();
            
            for(AStarNode s : successors) {
                if(s == q) {
                    continue;
                }
                
                if(s == end) {
                    s.setParent(q);
                    
                    
                    // Make an actual path that we can traverse both ways
                    //try {
                        AStarNode a = s;
                        // path.add((AStarNode) a.clone());

                        while(a.parentNode() != null) {
                            // path.add((AStarNode) ((Cell) a).clone());
                            a.parentNode().setNext(a);
                            a = a.parentNode();
                        }

                        // Clean up
                        
                        System.out.println("End pathfinding");
                    //} catch(CloneNotSupportedException ex) {
                        
                    //}
                    return path;
                }
                
                s.setg(q.g() + distance(s, q));
                s.seth(heuristic(s));
                // s should return f = g + h by its own
                
                int openindex = openset.indexOf(s);
                int closedindex = closedset.indexOf(s);

                if(openindex > -1 && openset.get(openindex).g() <= s.f()) {
                    continue;
                } else if(closedindex > -1 && closedset.get(closedindex).g() <= s.f()) {
                    continue;
                } else {
                    openset.add(s);
                }
                
                s.setParent(q);
            }
            
            closedset.add(q);
        }
        System.out.println("End pathfinding");
        return path;
    }
    
    public List<AStarNode> closedset() {
        return closedset;
    }
    
    public List<AStarNode> openset() {
        return openset;
    }
    
    public float heuristic(AStarNode a) {
        // The distance between the two points
        return (float) Math.sqrt(Math.pow(end.x() - a.x(), 2) + Math.pow(end.y() - a.y(), 2));
    }
    
    public float distance(AStarNode a, AStarNode b) {
        return (float) Math.sqrt(Math.pow(b.x() - a.x(), 2) + Math.pow(b.y() - a.y(), 2));
    }
}
