/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author Thomas Mundal <thomas@munso.no>
 */
public class GLInputListener {
    long window;
    
    public GLInputListener(long window) {
        this.window = window;
    }
    
    public void Listen(long window, int key, int scancode, int action, int mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
        }
    }
    
    public boolean isKeyDown(char key) {
        return glfwGetKey(window, (int) Character.toUpperCase(key)) == 1;
    }
}
