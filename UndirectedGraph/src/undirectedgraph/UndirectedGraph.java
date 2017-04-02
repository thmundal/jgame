/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author thmun
 */
public class UndirectedGraph<Value extends UGNodeInterface> {
    private ArrayList<UGNode<Value>> nodes;
    private ArrayList<UGEdge<Value>> edges;
    private Random rand;
    
    public UndirectedGraph() {
        nodes = new ArrayList<UGNode<Value>>();
        edges = new ArrayList<UGEdge<Value>>();
        rand = new Random();
    }
    
    public void setRandom(Random r) {
        rand = r;
    }
    
    private void addNode(UGNode<Value> n) {
        nodes.add(n);
    }
    
    public UGNode<Value> addNode(Value n) {
        UGNode<Value> exist = nodeByValue(n);
        
        if(exist == null) {              // Only add node if the value does not exist in the graph
            UGNode<Value> newnode = new UGNode<Value>(n);
            addNode(newnode);
            return newnode;
        }
        
        return exist;
    }
    
    public int size() {
        return nodes.size();
    }
    
    public UGNode<Value> randomNode() {
        if(size() > 0)
            return nodes.get(rand.nextInt(size()));
        
        return null;
    }
    
    public UGNode<Value> get(int index) {
        return nodes.get(index);
    }
    
    public void createEdge(UGNode<Value> a, UGNode<Value> b) {
        UGEdge<Value> e = new UGEdge<Value>(a, b);
        boolean found = false;
        
        // Check if there already is an edge between these nodes
        for(int i=0; i<edges.size(); i++) {
            UGEdge<Value> edge = edges.get(i);
            
            if(edge.contains(a) && edge.contains(b))
                found = true;
        }
        
        // If an edge between a and b was not found, add this edge
        if(!found) {
            // Add edge as reference in the two adjacent nodes
            a.addEdge(e);
            b.addEdge(e);
            
            // Add edges to the graph's list
            edges.add(e);
        }
    }
    
    public ArrayList<UGNode<Value>> nodes() {
        return nodes;
    }
    
    public ArrayList<UGEdge<Value>> edges() {
        return edges;
    }
    
    public boolean hasNode(UGNode<Value> n) {
        return nodes.contains(n);
    }
    
    public UGNode<Value> nodeByValue(Value v) {
        for(int i=0; i<nodes.size(); i++) {
            if(nodes.get(i).val() == v) {
                return nodes.get(i);
            }
        }
        
        return null;
    }
}
