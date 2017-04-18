/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author 888651
 */
public class Sprite {
    private boolean loaded = false;
    private BufferedImage image;
    private Vector2 position;
    private Vector2 size;
    
    public Sprite(String file) {
        position = new Vector2(0, 0);
        
        try {
            image = ImageIO.read(new File(file));
        } catch (IOException ex) {
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
        }
        loaded = true;
        
        size = new Vector2(image.getWidth(), image.getHeight());
    }
    
    public Sprite(String file, Vector2 position, Vector2 size) {
        this(file);
        SetPosition(position);
        SetSize(size);
    }
    
    public boolean Loaded() {
        return loaded;
    }
    
    public void SetPosition(Vector2 p) {
        position = p;
    }
    
    public void SetSize(Vector2 s) {
        size = s;
    }
    
    public void Draw(Graphics g) {
        Draw(g, position);
    }
    
    public void Draw(Graphics g, Vector2 pos) {
        if(Loaded()) {
            if(size == null) {
                g.drawImage(image, (int) pos.x, (int) pos.y, null);
            } else {
                g.drawImage(image, (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);
            }
        }
    }
    
    public float width() {
        return size.x;
    }
    
    public float height() {
        return size.y;
    }
    
    public int intWidth() {
        return (int) size.x;
    }
    
    public int intHeight() {
        return (int) size.y;
    }
}
