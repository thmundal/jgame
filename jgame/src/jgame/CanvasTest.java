/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import java.nio.FloatBuffer;
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
    
    public static void main(String[] args) {
        System.out.println(org.lwjgl.Version.getVersion());
        
        final Game game = new Game(800,600);
        
        /*--------- testing setup -------*/
        game.Wakeup((g, d) -> {
            shaderProgram = new ShaderProgram();
            shaderProgram.attachVertexShader("shaders/vertex_shader.vs");
            shaderProgram.attachFragmentShader("shaders/fragment_shader.fs");
            shaderProgram.link();

            vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);

            float[] vertices = new float[] {
                0.0f, 0.8f,
                -0.8f, -0.8f,
                0.8f, -0.8f
            };

            FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
            verticesBuffer.put(vertices).flip();
            vboID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
            glBindVertexArray(0);
        });

        
        game.Draw((g, deltaTime) -> {
            shaderProgram.bind();
            glBindVertexArray(vaoID);
            glEnableVertexAttribArray(0);
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
            shaderProgram.unbind();
        });
        /*--------- testing setup end --------*/
        
        game.Update((g, deltaTime) -> {
        });
        
        game.run();
        /*final int moveSpeed = 500;
        
        final Vector2 gravity = new Vector2(0, 100);
        grid = new Grid(game.height(), game.width(), 32, 32);
        
        grid.CellAt(0, 0).color = Color.black;
        grid.RandomCell().color = Color.green;
        
        game.Update(new UpdateCallback() {
            public void run(Game g, float deltaTime) {
                if(game.isKeyDown("a")) {
                    speed.x = -moveSpeed;
                }
                
                if(game.isKeyDown("d")) {
                    speed.x = moveSpeed;
                }
                
                if(game.isKeyDown("w")) {
                    speed.y = -moveSpeed;
                }
                
                if(game.isKeyDown("s")) {
                    speed.y = moveSpeed;
                }
                
                speed = speed.scale(deltaTime);
                pos = pos.add(speed); //.add(gravity.scale(deltaTime));
                
                
            }
        });
        
        game.Draw(new DrawCallback() {
            public void run(GameGraphics g, float deltaTime) {
                g.graphics.clearRect(0, 0, game.width(), game.height());
                grid.Draw(g.graphics);
                
                g.graphics.setColor(Color.red);
                g.graphics.drawOval(pos.intX(), pos.intY(), 60, 60); //FOR CIRCLE
            }
        });
        game.run();
        */
        
    }
}