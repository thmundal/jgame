/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
 *
 * @author Thomas
 */
public class InputListener implements KeyListener, MouseListener, MouseMotionListener {
    private Game game;
    
    public void setGame(Game g) {
        game = g;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("Key typed " + e.getKeyChar());
    }
    
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key pressed " + e.getKeyChar());
        game.setKeyState(e.getKeyChar(), KeyState.DOWN);
    }
    
    public void keyReleased(KeyEvent e) {
//        System.out.println("Key released " + e.getKeyChar());
        game.setKeyState(e.getKeyChar(), KeyState.UP);
    }
    
    public void mouseClicked(MouseEvent e) {
        
    }
    
    public void mousePressed(MouseEvent e) {
        
    }
    
    public void mouseReleased(MouseEvent e) {
        
    }
    
    public void mouseEntered(MouseEvent e) {
        
    }
    
    public void mouseExited(MouseEvent e) {
        
    }
    
    public void mouseDragged(MouseEvent e) {
        
    }
    
    public void mouseMoved(MouseEvent e) {
        
    }
}