/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

/**
 *
 * @author Thomas
 */
public class Matrix2x2 extends GeneralMatrix<Matrix2x2> {
    public Matrix2x2() {
        // Construct this as a 2x2 matrix
        super(2, 2);
    }
    
    public Matrix2x2(int[] m) {
        super(2, 2);
        populate(m);
    }
    
    public Matrix2x2(int n1, int n2, int n3, int n4) {
        super(2, 2);
        populate(new int[]{n1, n2, n3, n4});
    }
}
