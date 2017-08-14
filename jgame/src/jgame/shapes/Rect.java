/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.shapes;

import jgame.Game;
import jgame.Transform;
import jgame.util.Global;
import jgame.util.Vector2;

/**
 *
 * @author thmun
 */
public class Rect extends Shape2D {
    public Rect(Vector2 pos, int width, int height) {
        super(Rect.vertices(width, height));
        
        this.width = width;
        this.height = height;
        
        transform = new Transform(pos, 0.0f);
    }
    
    public static float[] vertices(int width, int height) {
        float _w = ((float) width / (float) Global.WIDTH);
        float _h = ((float) height / (float) Global.HEIGHT);
        
        float[] vertices = new float[] {
                -_w,  _h,// 1.0f, 0.0f, 0.0f, // Top-left
                 _w,  _h,// 0.0f, 1.0f, 0.0f, // Top-right
                 _w, -_h,// 0.0f, 0.0f, 1.0f, // Bottom-right

                 _w, -_h,// 0.0f, 0.0f, 1.0f, // Bottom-right
                -_w, -_h,// 1.0f, 1.0f, 1.0f, // Bottom-left
                -_w,  _h //, 1.0f, 0.0f, 0.0f  // Top-left
            };
        return vertices;
    }
}
