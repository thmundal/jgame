/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

/**
 *
 * @author thmun
 */
public class Shape3D {
    private float[] vertices;
    public Transform3D transform;
    
    public Shape3D(String filename) {
        // To load a 3d model file .fbx or something
    }
    
    public Shape3D(float[] v) {
        vertices = v;
    }
}
