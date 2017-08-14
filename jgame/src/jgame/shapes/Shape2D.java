/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.shapes;

import java.nio.FloatBuffer;
import jgame.ShaderProgram;
import jgame.Transform3D;
import jgame.Transform;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 *
 * @author thmun
 */
public class Shape2D implements Entity {
    private float[] vertices;
    
    private FloatBuffer vertex_buffer;
    private int vaoID;
    private int vboID;
    private ShaderProgram shaderProgram;
    public Transform transform;
    
    public int width = 0;
    public int height = 0;
    
    public Shape2D() {
        
    }
    
    public Shape2D(String filename) {
        // To load a 3d model file .fbx or something
    }
    
    public Shape2D(float[] v) {
        transform = new Transform();
        vertices = v;
        
        shaderProgram = new ShaderProgram();
        shaderProgram.attachVertexShader("shaders/vertex_shader.vs");
        shaderProgram.attachFragmentShader("shaders/fragment_shader.fs");
        shaderProgram.link();
    }
    
    public void createBuffer(float[] verts) {
        vaoID = glGenVertexArrays();
        
        glBindVertexArray(vaoID);
        if(vertex_buffer == null) {
            vertex_buffer = BufferUtils.createFloatBuffer(verts.length);
        }
        
        vertex_buffer.clear();
        vertex_buffer.put(verts).flip();
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertex_buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindVertexArray(0);
    }
    
    public float[] transformVertices() {
        float[] out = new float[vertices.length];
        for(int i=0; i<vertices.length; i++) {
            if(i % 2 == 0) {
                out[i] = vertices[i] + transform.xNormalized();
            } else {
                out[i] = vertices[i] + transform.yNormalized();
            }
        }
        return out;
    }
    
    public void Draw() {
        createBuffer(transformVertices());
        shaderProgram.bind();
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shaderProgram.unbind();
    }
}
