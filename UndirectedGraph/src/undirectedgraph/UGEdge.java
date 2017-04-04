/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

/**
 *
 * @author thmun
 */
public class UGEdge<Value extends UGNodeInterface> {
    private UGNode<Value>[] pair;
    private float weight;
    
    public UGEdge(UGNode a, UGNode b) {
        pair = new UGNode[2];
        pair[0] = a;
        pair[1] = b;
        weight = a.val().weight(b.val());
    }
    
    public UGNode<Value> a() {
        return pair[0];
    }
    
    public UGNode<Value> b() {
        return pair[1];
    }
    
    public boolean contains(UGNode<Value> n) {
        return (a() == n || b() == n);
    }
    
    public float weight() {
        weight = a().val().weight(b().val());
        return weight;
    }
}
