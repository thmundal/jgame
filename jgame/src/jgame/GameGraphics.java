/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 *
 * @author thmun
 */
public class GameGraphics {
    public Graphics graphics;
    
    public GameGraphics() {
        
    }
    
    public void setGraphics(Graphics g) {
        this.graphics = g;
    }
    
    public void Circle(Vector2 pos, int radius) {
        int x = pos.intX() - radius;
        int y = pos.intY() - radius;
        
        graphics.drawArc(x, y, radius * 2, radius * 2, 0, 360);
    }
    
    public void Text(String string, Vector2 pos) {
        Text(string, pos, graphics.getFont(), graphics.getColor());
    }
    
    public void Text(String string, Vector2 pos, Font font) {
        Text(string, pos, font, graphics.getColor());
    }
    
    public void Text(String string, Vector2 pos, Font font, Color color) {
        Color old_color = graphics.getColor();
        FontMetrics metrics = graphics.getFontMetrics(font);
        int x = pos.intX() - metrics.stringWidth(string) / 2;
        int y = (pos.intY() - metrics.getHeight() / 2) + metrics.getAscent();
        
        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString(string, x, y);
        graphics.setColor(old_color);
    }
    
    public void Line(Vector2 from, Vector2 to) {
        graphics.drawLine(from.intX(), from.intY(), to.intX(), to.intY());
    }
    
    public void Square(Vector2 pos, int size) {
        graphics.drawRect(pos.intX() - size / 2, pos.intY() - size / 2, size, size);
    }
    
    public void Sprite(Sprite s, Vector2 pos) {
        s.Draw(graphics, new Vector2(pos.x - s.width() / 2, pos.y - s.height() / 2));
    }
    
    public void LineGL(Vector2 from, Vector2 to) {
        glBegin(1);
        glVertex2f(from.x, from.y);
        glVertex2f(to.x, to.y);
        glEnd();
    }
}
