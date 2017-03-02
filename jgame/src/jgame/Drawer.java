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
    
    public Drawer(String title, Game g) {
        game = g;
        
        setSize(game.width(), game.height());
        setLayout(null);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void onDraw(DrawCallback c) {
        System.out.println("Drwcallback received");
        drawCallback = c;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(drawCallback != null) {
            drawCallback.run((Graphics) g, new Float(game.deltaTime())); // 0 -> deltaTime
        } else {
            System.out.println("No drawcallback defined");
        }
    }
}
