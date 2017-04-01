/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class GeneralMatrix<T extends GeneralMatrix> {
    int[] matrix;
    private int cols;
    private int rows;
    
    public GeneralMatrix() { }
    
    public GeneralMatrix(int w, int h) {
        cols = w;
        rows = h;
        matrix = new int[w * h];
        
        System.out.println(this.getClass());
    }
    
    public void populate(int[] in) {
        matrix = in.clone();
    }
    
    public int width() {
        return cols;
    }
    
    public int cols() {
        return cols;
    }
    public int rows() {
        return rows;
    }
    
    public int get(int i) {
        return matrix[i];
    }
    
    public int get(int x, int y) {
        int i = x + cols * y;
        return matrix[i];
    }
    
    public T matrixMultiply(GeneralMatrix in) throws UnsupporedMatrixException {
        // Check dimensions
        if(cols != in.rows()) {
            throw new UnsupporedMatrixException();
        }
        
        int[] newmatrix = new int[rows * in.cols()];
        int i=0;
        
        for(int x=0; x<cols; x++) {
            for(int y=0; y<rows; y++) {
                int indexa = x + cols * y;
                int indexb = y + rows * x;
                
                int sum = this.get(indexa) * in.get(indexb);
                
                newmatrix[i] = sum;
                i++;
            }
        }
        
        T out = (T) new GeneralMatrix(rows, in.cols());
        out.populate(newmatrix);
        
        return out;
    }
}

class UnsupporedMatrixException extends Exception {
    
}