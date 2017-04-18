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
 * A class representing an Undirected Graph
 * @author Thomas Mundal
 * @param <Value> 
 */
public class UndirectedGraph<Value extends UGNodeInterface> {
    private ArrayList<UGNode<Value>> nodes;                 // A list of nodes contained within this graph
    private ArrayList<UGEdge<Value>> edges;                 // A list of edges contained within this graph
    private Random rand;                                    // A random number provider
    
    /**
     * Constructor, initializes variables
     */
    public UndirectedGraph() {
        nodes = new ArrayList<UGNode<Value>>();
        edges = new ArrayList<UGEdge<Value>>();
        rand = new Random();
    }
    
    /**
     * Set the random provider from another source
     * @param r The random object
     */
    public void setRandom(Random r) {
        rand = r;
    }
    
    /**
     * Add a Vertex to the graph by UGNode construct, This is not the method used in this project
     * @param n The vertex to add
     */
    private void addNode(UGNode<Value> n) {
        nodes.add(n);
    }
    
    /**
     * Add a Vertex to the graph by value - This is the method used in this project
     * @param n The value of the node to insert
     * @return The node that was created and inserted
     */
    public UGNode<Value> addNode(Value n) {
        UGNode<Value> exist = nodeByValue(n);
        
        if(exist == null) {              // Only add node if the value does not exist in the graph
            UGNode<Value> newnode = new UGNode<Value>(n);
            addNode(newnode);
            return newnode;
        }
        
        return exist;
    }
    
    /**
     * Returns the number of vertices in the graph
     * @return The size of the nodes ArrayList
     */
    public int size() {
        return nodes.size();
    }
    
    /**
     * Get a random vertex from the graph
     * @return A random vertex
     */
    public UGNode<Value> randomNode() {
        if(size() > 0)
            return nodes.get(rand.nextInt(size()));
        
        return null;
    }
    
    /**
     * Get a vertex at a given index in the nodes ArrayList
     * @param index Index of the vertex
     * @return 
     */
    public UGNode<Value> get(int index) {
        return nodes.get(index);
    }
    
    /**
     * Creates and edge in the graph between vertex a and vertex b
     * @param a Vertex a
     * @param b Vertex b
     */
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
    
    /**
     * Returns all the vertices in the graph
     * @return An ArrayList containing all vertices
     */
    public ArrayList<UGNode<Value>> nodes() {
        return nodes;
    }
    
    /**
     * Returns all the edges in the graph
     * @return An ArrayList containing all edges
     */
    public ArrayList<UGEdge<Value>> edges() {
        return edges;
    }
    
    /**
     * Check if the graph contains a specific Vertex
     * @param n The vertex to look for
     * @return True if the vertex exists, false otherwise
     */
    public boolean hasNode(UGNode<Value> n) {
        return nodes.contains(n);
    }
    
    /**
     * Check if a given value exists in the graph
     * @param v The value to look for
     * @return The vertex that contains this value
     */
    public UGNode<Value> nodeByValue(Value v) {
        for(int i=0; i<nodes.size(); i++) {
            if(nodes.get(i).val() == v) {
                return nodes.get(i);
            }
        }
        
        return null;
    }
    
    /**
     * A cross between hasNode and nodeByValue, to check if a node exists by comparing their values and not Object references
     * @param n The node to check
     * @return true if found, false otherwise
     */
    public boolean contains(UGNode<Value> n) {
        return (nodeByValue(n.val()) != null);
    }
}
