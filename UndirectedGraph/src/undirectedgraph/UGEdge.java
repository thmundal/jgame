/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

/**
 * All comments are done in english, because, programming...
 * 
 * This class represents an edge in the Undirected Graph
 * It keeps track of which pair of vertices it is connected to, and 
 * has a float value for its weight
 * 
 * @author Thomas Mundal
 * @param <Value> 
 */
public class UGEdge<Value extends UGNodeInterface> {
    private UGNode<Value>[] pair;
    private float weight;
    
    /**
     * Construct an edge between two vertices in an Undirected Graph
     * @param a Vertex a
     * @param b Vertex b
     */
    public UGEdge(UGNode a, UGNode b) {
        pair = new UGNode[2];
        pair[0] = a;
        pair[1] = b;
        
        // Calculate and save the weight using the vertice's values
        // The values must implement UGNodeInterface so that we can call
        // its weight() method here.
        weight = a.val().weight(b.val());
    }
    
    /**
     * Returns the first Vertex in the pair array
     * @return A Vertex
     */
    public UGNode<Value> a() {
        return pair[0];
    }
    
    /**
     * Returns the second Vertex in the pair array
     * @return A Vertex
     */
    public UGNode<Value> b() {
        return pair[1];
    }
    
    /**
     * Check if this edge contains a specific Vertex
     * @param n The vertex to look for
     * @return true if the given Vertex is a part of this edge, false otherwise
     */
    public boolean contains(UGNode<Value> n) {
        return (a() == n || b() == n);
    }
    
    /**
     * Calculate and save the weight of this edge using the Verices' weight function
     * @return The weight of this edge.
     */
    public float weight() {
        weight = a().val().weight(b().val());
        return weight;
    }
}
