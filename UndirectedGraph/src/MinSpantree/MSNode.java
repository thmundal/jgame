/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinSpantree;

import java.util.ArrayList;

/**
 *
 * @author thmun
 */
public class MSNode<ValueType> {
    private ValueType value;
    private MSNode parent;
    private float priority;
    private ArrayList<MSNode<ValueType>> children;
    
    public MSNode(ValueType v) {
        value = v;
        parent = null;
        priority = Float.MAX_VALUE;
        children = new ArrayList<MSNode<ValueType>>();
    }
    
    public void addChild(MSNode<ValueType> n) {
        n.setParent(this);
        children.add(n);
    }
    
    public void setParent(MSNode<ValueType> n) {
        parent = n;
    }
    
    public MSNode<ValueType> parent() {
        return parent;
    }
    
    public void setPriority(float n) {
        priority = n;
    }
    
    public float priority() {
        return priority;
    }
    
    public ValueType val() {
        return value;
    }
    
    public ArrayList<MSNode<ValueType>> children() {
        return children;
    }
}
