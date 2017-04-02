/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

import java.util.ArrayList;

/**
 *
 * @author thmun
 */
public class UGNode<Value extends UGNodeInterface> {
    private Value value;
    private ArrayList<UGEdge<Value>> edges;
    
    public UGNode(Value val) {
        value = val;
        edges = new ArrayList<UGEdge<Value>>();
    }
    
    public void addEdge(UGEdge<Value> e) {
        edges.add(e);
    }
    
    public ArrayList<UGEdge<Value>> edges() {
        return edges;
    }
    
    public Value val() {
        return value;
    }
    
    public boolean hasEdge(UGEdge<Value> e) {
        return edges.contains(e);
    }
    
    public UGNode<Value> getPartner(UGEdge<Value> e) {
        if(e.a() == this)
            return e.b();
        
        return e.a();
    }
}
