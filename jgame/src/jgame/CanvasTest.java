/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;
import jgame.util.Vector2;
import jgame.shapes.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgame.util.GeneralMatrix;
import jgame.util.Matrix2x2;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.Configuration;
/**
 *
 * @author Thomas Mundal<thomas@munso.no>
 */
public class CanvasTest {
    /**
     * @param args the command line arguments
     */
    
    public static Vector2 pos = new Vector2(1f, 1f);
    public static Vector2 speed = new Vector2(0f, 0f);
    public static Sprite dude;
    public static Grid grid;
    
    // Test variables
    static int vaoID;
    static int vboID;
    static ShaderProgram shaderProgram;
    
    static Shape2D shape;
    
    public static void main(String[] args) {
        System.out.println(org.lwjgl.Version.getVersion());
        
        final Game game = new Game(800,600);
        
        
        game.Wakeup((g, d) -> {
            // A triangle
            float[] vertices = new float[] {
                0.0f, 0.8f,
                -0.8f, -0.8f,
                0.8f, -0.8f
            };
            
            // A square
            float[] square = new float[] {
                -0.5f,  0.5f,// 1.0f, 0.0f, 0.0f, // Top-left
                 0.5f,  0.5f,// 0.0f, 1.0f, 0.0f, // Top-right
                 0.5f, -0.5f,// 0.0f, 0.0f, 1.0f, // Bottom-right

                 0.5f, -0.5f,// 0.0f, 0.0f, 1.0f, // Bottom-right
                -0.5f, -0.5f,// 1.0f, 1.0f, 1.0f, // Bottom-left
                -0.5f,  0.5f //, 1.0f, 0.0f, 0.0f  // Top-left
            };
            
            shape = new Shape2D(square);
        });

        
        game.Draw((g, deltaTime) -> {
            shape.Draw();
        });
        
        game.Update((g, deltaTime) -> {
        });
        
        game.run();
        
    }
}