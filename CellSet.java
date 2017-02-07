/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvastest;

/**
 *
 * @author thmun
 */
public class CellSet {
    public Cell[] cells;
    
    public CellSet(Cell[] c) {
        cells = c;
    }
    
    public Cell topLeft() {
        return cells[0];
    }
    
    public Cell top() {
        return cells[1];
    }
    
    public Cell topRight() {
        return cells[2];
    }
    
    public Cell left() {
        return cells[3];
    }
    
    public Cell middle() {
        return cells[4];
    }
    
    public Cell right() {
        return cells[5];
    }
    
    public Cell bottomLeft() {
        return cells[6];
    }
    
    public Cell bottom() {
        return cells[7];
    }
    
    public Cell bottomRight() {
        return cells[8];
    }
    
    public String toString() {
        return java.util.Arrays.toString(cells);
    }
}
