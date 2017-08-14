/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gltest;
import jgame.*;
import jgame.shapes.*;
import jgame.util.*;
/**
 *
 * @author thmun
 */
public class GLTest {
    static Game game;
    static Rect square;
    static Vector2 pos;        
    static int speedx = 1;
    static int speedy = 1;
    
    public static void main(String[] args) {
        game = new Game();
        
        game.Wakeup((g, d) -> {
            pos = new Vector2(0, 0);
            
            float[] s = new float[] {
                -0.5f,  0.5f,// 1.0f, 0.0f, 0.0f, // Top-left
                 0.5f,  0.5f,// 0.0f, 1.0f, 0.0f, // Top-right
                 0.5f, -0.5f,// 0.0f, 0.0f, 1.0f, // Bottom-right

                 0.5f, -0.5f,// 0.0f, 0.0f, 1.0f, // Bottom-right
                -0.5f, -0.5f,// 1.0f, 1.0f, 1.0f, // Bottom-left
                -0.5f,  0.5f //, 1.0f, 0.0f, 0.0f  // Top-left
            };
            
            //square = new Shape2D(s);
            square = new Rect(pos, 100, 100);
            square.transform.position = pos;
        });
        
        game.Update((g, d) -> {
            if(game.gl_input.isKeyDown('w')) {
                pos.y+=10;
            }
            if(game.gl_input.isKeyDown('s')) {
                pos.y-=10;
            }
            if(game.gl_input.isKeyDown('a')) {
                pos.x-=10;
            }
            if(game.gl_input.isKeyDown('d')) {
                pos.x+=10;
            }
            
            pos.x += speedx * 20;
            pos.y += speedy * 20;
            
            if(pos.x < Global.LEFT_EDGE || pos.x > Global.RIGHT_EDGE)
                speedx = -speedx;
            
            if(pos.y < Global.BOTTOM_EDGE || pos.y > Global.TOP_EDGE)
                speedy = -speedy;
        });
        
        game.Draw((g, d) -> {
            square.Draw();
        });
        
        game.run();
        
    }
    
}
