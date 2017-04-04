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
 * @author thmun
 */
public class MinSpanTree<ValueType extends UGNodeInterface> {
    private UndirectedGraph<ValueType> source_graph;
    private UndirectedGraph<ValueType> mst;
    
    private ArrayList<UGEdge<ValueType>> edge_queue;
    private ArrayList<UGEdge<ValueType>> visited_edges;
    
    public MinSpanTree(UndirectedGraph<ValueType> graph, UGNode<ValueType> startnode) {
        source_graph = graph;
        mst = new UndirectedGraph<ValueType>();
        
        edge_queue = new ArrayList<UGEdge<ValueType>>();
        visited_edges = new ArrayList<UGEdge<ValueType>>();
        // Make a copy of the edges into the queue, cause we dont want to remove edges from the graphs arraylist
        // java pass-by-reference issue
        source_graph.edges().forEach(edge -> {
            edge_queue.add(new UGEdge<ValueType>(edge.a(), edge.b()));
        });
        
        makeTree(startnode);
    }
    
    public void makeTree(UGNode<ValueType> current) {
        //ArrayList<UGEdge<ValueType>> edge_queue = new ArrayList<UGEdge<ValueType>>();
        createEdgeQueue(current);

        if(edge_queue.isEmpty())
            return;
        
        //while(!edge_queue.isEmpty()) {
            UGEdge<ValueType> selected_edge = null;
            
            UGNode<ValueType> selected_node = null;
            
            for(int i=0; i<edge_queue.size(); i++) {
                UGEdge<ValueType> current_edge = edge_queue.get(i);

                if(selected_edge == null || current_edge.weight() < selected_edge.weight()) {
                    // partner = current.getPartner(current_edge);
                    selected_edge = current_edge;
                }
            }
            
            if(selected_edge != null) {
                edge_queue.remove(selected_edge);
                visited_edges.add(selected_edge);
                
                // The a and b components of the edge
                UGNode<ValueType> a = selected_edge.a();
                UGNode<ValueType> b = selected_edge.b();

                if(!(mst.contains(a) && mst.contains(b))) {
                    // Both nodes are already in the tree
                    UGNode<ValueType> _a = mst.addNode(a.val());
                    UGNode<ValueType> _b = mst.addNode(b.val());
                    mst.createEdge(_a, _b);
                }
                
                // Select the node that is already in the graph
                current = a;
                if(!mst.contains(current))
                    current = b;
                
                makeTree(current.getPartner(selected_edge));
            }
        //}
        
        // a & !b + !b & a
        // A!B + !AB
        // a && !b || b && !a
        // (A || B) && !(A && B)
    }
    
    public void createEdgeQueue(UGNode<ValueType> current) {        
        current.edges().forEach(edge -> {
            if(!edge_queue.contains(edge) && !visited_edges.contains(edge))
                edge_queue.add(edge);
        });

    }
    
    public void makeTree() {
        while(!edge_queue.isEmpty()) {
            // Find the edge with lowest weight
            UGEdge<ValueType> selected_edge = null;
            for(int i=0; i<edge_queue.size(); i++) {
                if(selected_edge == null || edge_queue.get(i).weight() < selected_edge.weight()) {
                    selected_edge = edge_queue.get(i);
                }
            }
            
            if(selected_edge != null) {
                UGNode<ValueType> a = selected_edge.a();
                UGNode<ValueType> b = selected_edge.b();
                
                if(mst.nodeByValue(a.val()) != null && mst.nodeByValue(b.val()) != null) {
                    // This means that these values are already in the mst, and something has gone wrong
                    System.out.println("Dont do this please");
                } else {
                    UGNode<ValueType> newa = mst.addNode(a.val());
                    UGNode<ValueType> newb = mst.addNode(b.val());
                    
                    mst.createEdge(newa, newb);
                }
                edge_queue.remove(selected_edge);
            }
        }
    }
    
    public UndirectedGraph<ValueType> graph() {
        return mst;
    }
}
