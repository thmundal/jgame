/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

import java.util.ArrayList;

/**
 *
 * A class representing the Vertices of an Undirected Graph, here called a Node, or UGNode denoting that it is a part of an |U|ndirected |G|raph
 * The class has a generic value-type that must extend UGNodeInterface in order to ensure that the value has a method for calculating its relative weight to another Vertex(UGNode)
 * 
 * @author Thomas Mundal
 * @param <Value> 
 */
public class UGNode<Value extends UGNodeInterface> {
    private Value value;                            // The value that is contained within this Vertex
    private ArrayList<UGEdge<Value>> edges;         // The edges that this Vertex is a part of
    
    /**
     * A constructor that creates a Vertex for an Undirected Graph containing a given Value
     * @param val The value contained within this Vertex
     */
    public UGNode(Value val) {
        value = val;
        edges = new ArrayList<UGEdge<Value>>();
    }
    
    /**
     * Add an edge to the list of edges that this vertex is a part of
     * @param e The edge to add
     */
    public void addEdge(UGEdge<Value> e) {
        edges.add(e);
    }
    
    /**
     * Returns all the edges that this vertex is a part of
     * @return A list of edges
     */
    public ArrayList<UGEdge<Value>> edges() {
        return edges;
    }
    
    /**
     * Returns the value contained within this vertex
     * @return The value
     */
    public Value val() {
        return value;
    }
    
    /**
     * Checks if this vertex is part of a particular edge, more precisely, if a given edge is in the list of edges connecting this vertex to the graph
     * @param e The edge to check
     * @return True if this vertex is a part of the given edge, false otherwise
     */
    public boolean hasEdge(UGEdge<Value> e) {
        return edges.contains(e);
    }
    
    /**
     * The this vertex's parter contained in a given edge
     * @param e The edge to look in
     * @return The node at the other end of the edge
     */
    public UGNode<Value> getPartner(UGEdge<Value> e) {
        if(e.a() == this)
            return e.b();
        
        return e.a();
    }
    
    /**
     * Set the value contained in this vertex
     * @param v The value to save
     */
    public void setVal(Value v) {
        value = v;
    }
}
