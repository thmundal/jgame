/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Thomas
 */
public class Drawer extends JPanel {
    private DrawCallback drawCallback;
    public Game game;
    private GameGraphics gameGraphics;
    
    public Drawer(String title, Game g) {
        game = g;
        gameGraphics = new GameGraphics();
        
        setSize(game.width(), game.height());
        setLayout(null);
        
        setVisible(true);
    }
    
    public void onDraw(DrawCallback c) {
        drawCallback = c;
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        
        gameGraphics.setGraphics(g);
        
        super.paintComponent(g);
        if(drawCallback != null) {
            drawCallback.run(gameGraphics, game.deltaTime()); // 0 -> deltaTime
        } else {
            System.out.println("No drawcallback defined");
        }
    }
    
    public void Circle(Vector2 pos, int radius, Graphics g) {
        int x = pos.intX() - radius / 2;
        int y = pos.intY() - radius / 2;
        
        g.drawArc(x, y, radius, radius, 0, 360);
    }
}
