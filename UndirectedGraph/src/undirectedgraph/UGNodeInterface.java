/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

/**
 *
 * An interface that must be implemented by a value saved in a Vertex to ensure that it can calculate a relative edge to another vertex's value
 * @author thmun
 * @param <T> 
 */
public interface UGNodeInterface<T> {
    /**
     * A function to calculate a weight between two values
     * @param p The other value to compare to
     * @return The weight
     */
    public float weight(T p);
}
