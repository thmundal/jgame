/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import jgame.util.FileUtil;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 *
 * @author thmun
 */
public class ShaderProgram {
    int programID;
    int vertexShaderID;
    int fragmentShaderID;
    
    public ShaderProgram() {
        if(glfwInit() != true) {
            throw new RuntimeException("GL Not initialized");
        }
        programID = glCreateProgram();
        
    }
    
    public void attachVertexShader(String name) {
        String vertexShaderSource = FileUtil.readFromFile(name);
        
        vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderID, vertexShaderSource);
        glCompileShader(vertexShaderID);
        
        if(glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Error creating vertex shader\n"
                    + glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));
        }
        
        glAttachShader(programID, vertexShaderID);
    }
    
    public void attachFragmentShader(String name) {
        String fragmentShaderSource = FileUtil.readFromFile(name);
        
        fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderID, fragmentShaderSource);
        glCompileShader(fragmentShaderID);
        
        if(glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Error creating fragment shader\n"
            + glGetShaderInfoLog(fragmentShaderID, glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH)));
        }
        
        glAttachShader(programID, fragmentShaderID);
    }
    
    public void link() {
        glLinkProgram(programID);
        
        if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Unable to link shader program");
        }
    }
    
    public void bind() {
        glUseProgram(programID);
    }
    
    public void unbind() {
        glUseProgram(0);
    }
    public void dispose()
    {
        unbind();
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }
    
    public int getID()
    {
        return programID;
    }
}
