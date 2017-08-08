/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import jgame.util.Vector2;
import java.util.ArrayList;
import java.util.LinkedList;
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
    
    public AStar(AStarNodeInterface s, AStarNodeInterface e) {
        start = new AStarNode(new Vector2(s.x(), s.y()));
        end = new AStarNode(new Vector2(e.x(), e.y()));
        
        openset = new ArrayList<AStarNode>();
        closedset = new ArrayList<AStarNode>();
    }
    
    public LinkedList<AStarNode> findPath() {
        LinkedList<AStarNode> path = new LinkedList<AStarNode>();
        
        openset.clear();
        closedset.clear();
        
        openset.add(start);
        path.addFirst(start);
        
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
            
            // q.Cell().color = java.awt.Color.LIGHT_GRAY;
            
            List<AStarNode> successors = q.getSuccessors();
            
            for(AStarNode s : successors) {
                if(s == q || s == null) {
                    continue;
                }
                
                if(s == end) {
                    s.setParent(q);
                    
                    
                    // Make an actual path that we can traverse both ways
                    AStarNode a = s;

                    while(a.parentNode() != null) {
                        path.addLast(a);
                        a.parentNode().setNext(a);
                        a = a.parentNode();
                    }

                    path.addLast(end);
                    
                    // Clean up
                    return path;
                }
                
                s.setg(q.g() + distance(s, q));
                s.seth(heuristic(s));
                // s should return f = g + h by its own
                
                int openindex = openset.indexOf(s);
                int closedindex = closedset.indexOf(s);
                //System.out.println(openindex);
                //System.out.println(closedindex);
                if(openindex > -1 && openset.get(openindex).g() <= s.f()) {
                    // System.out.println("Skip");
                    continue;
                } else if(closedindex > -1 && closedset.get(closedindex).g() <= s.f()) {
                    // System.out.println("Skip");
                    continue;
                } else {
                    // System.out.println("Adding");
                    openset.add(s);
                }
                
                s.setParent(q);
            }
            
            closedset.add(q);
        }
        System.out.println("End pathfinding, end of function");
        return path;
    }
    
    public List<AStarNode> closedset() {
        return closedset;
    }
    
    public List<AStarNode> openset() {
        return openset;
    }
    
    public float heuristic(AStarNodeInterface a) {
        // The distance between the two points
        return (float) Math.sqrt(Math.pow(end.x() - a.x(), 2) + Math.pow(end.y() - a.y(), 2));
    }
    
    public float distance(AStarNodeInterface a, AStarNodeInterface b) {
        return (float) Math.sqrt(Math.pow(b.x() - a.x(), 2) + Math.pow(b.y() - a.y(), 2));
    }
}
