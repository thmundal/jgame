/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvastest;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 */
public class Cell {
    public Transform transform;
    public Vector2 size;
    public Color color;
    public boolean wall;
    public boolean visited;
    public Vector2 coords;
    
    public Cell(Vector2 s, Vector2 p, Color c) {
        size = s;
        color = c;
        transform = new Transform();
        transform.position = p;
        wall = true;
        visited = false;
        coords = new Vector2(p.x / s.x, p.y / s.y);
    }
    
    public Cell() {
        color = Color.white;
    }
    
    public void Draw(Graphics g) {
        if(wall) {
            g.setColor(Color.black);
        } else {
            g.setColor(color);
        }
        g.fillRect((int) transform.position.x, (int) transform.position.y, (int) size.x, (int) size.y);
    }
}
