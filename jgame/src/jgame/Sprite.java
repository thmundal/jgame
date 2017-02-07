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
    BufferedImage image;
    private Vector2 position;
    
    public Sprite(String file) {
        position = new Vector2(0, 0);
        
        try {
            image = ImageIO.read(new File(file));
        } catch (IOException ex) {
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
        }
        loaded = true;
    }
    
    public boolean Loaded() {
        return loaded;
    }
    
    public void SetPosition(Vector2 p) {
        position = p;
    }
    
    public void Draw(Graphics g) {
        if(Loaded()) {
            g.drawImage(image, (int) position.x, (int) position.y, null);
        }
    }
}
