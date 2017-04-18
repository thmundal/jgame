/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinSpantree;

import java.util.ArrayList;
import undirectedgraph.UGNode;
import undirectedgraph.UGEdge;
import undirectedgraph.UGNodeInterface;
import undirectedgraph.UndirectedGraph;

/**
 *
 * A Class that makes a graph that represents a Minimal Spanning tree of a given graph
 * 
 * @author Thomas Mundal
 * @param <ValueType> 
 */
public class MinSpanTree<ValueType extends UGNodeInterface> {
    private UndirectedGraph<ValueType> source_graph;            // The source graph
    private UndirectedGraph<ValueType> mst;                     // The generated graph that is an MST
    
    private ArrayList<UGEdge<ValueType>> edge_queue;            // The edge queue
    private ArrayList<UGEdge<ValueType>> visited_edges;         // List over visited edges
    
    /**
     * Initialize an MST of a graph from a given vertex
     * @param graph         The source graph
     * @param startnode     Start-vertex
     */
    public MinSpanTree(UndirectedGraph<ValueType> graph, UGNode<ValueType> startnode) {
        source_graph = graph;
        mst = new UndirectedGraph<ValueType>();
        
        edge_queue = new ArrayList<UGEdge<ValueType>>();
        visited_edges = new ArrayList<UGEdge<ValueType>>();
        // Make a copy of the edges into the queue, cause we dont want to remove edges from the graphs arraylist
        source_graph.edges().forEach(edge -> {
            edge_queue.add(new UGEdge<ValueType>(edge.a(), edge.b()));
        });
        
        makeTree(startnode);
    }
    
    /**
     * Calculates the MST
     * @param current The node that we are currently looking at
     */
    public void makeTree(UGNode<ValueType> current) {
        // Create edge queue from current node
        createEdgeQueue(current);
        
        // If the edge queue is empty, stop recursion
        if(edge_queue.isEmpty())
            return;
        
        UGEdge<ValueType> selected_edge = null;
        UGNode<ValueType> selected_node = null;

        // Select the edge with the lowest weight
        for(int i=0; i<edge_queue.size(); i++) {
            UGEdge<ValueType> current_edge = edge_queue.get(i);

            if(selected_edge == null || current_edge.weight() < selected_edge.weight()) {
                selected_edge = current_edge;
            }
        }

        // If we have found an edge
        if(selected_edge != null) {
            edge_queue.remove(selected_edge);                   // Remove this edge from the queue
            visited_edges.add(selected_edge);                   // Add the queue to the visited edges list

            // The a and b components of the edge
            UGNode<ValueType> a = selected_edge.a();            // The one end of the edge
            UGNode<ValueType> b = selected_edge.b();            // The other end of the edge

            // If the MST does not contain both a and b
            if(!(mst.contains(a) && mst.contains(b))) {
                UGNode<ValueType> _a = mst.addNode(a.val());    // Add a vertex to the MST with a's value
                UGNode<ValueType> _b = mst.addNode(b.val());    // Add a vertex to the MST with b's value
                mst.createEdge(_a, _b);                         // Create an edge between the two vertices
            }                                                   // mst.addNode will not add a new node if the value already
                                                                // is in the graph, instead it will return the node containing that value
            // Select the node that is already in the graph
            current = a;                                        // Set (next)current to a
            if(!mst.contains(current))                          // if mst does not contain this current
                current = b;                                    // set (next)current to b

            makeTree(current.getPartner(selected_edge));        // Continue calculate MST from this current's partner, 
                                                                // Meaning the other end of the edge that expands from current, which is already in the MST
        }
    }
    
    /**
     * Creates the edge queue from edges connected to a given vertex
     * @param current   The vertex to get edges from
     */
    public void createEdgeQueue(UGNode<ValueType> current) {        
        current.edges().forEach(edge -> {
            if(!edge_queue.contains(edge) && !visited_edges.contains(edge))
                edge_queue.add(edge);
        });

    }
    
    /**
     * Return the calculated MST
     * @return The graph representing the MST
     */
    public UndirectedGraph<ValueType> graph() {
        return mst;
    }
}
