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


import org.lwjgl.*;


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
        if(game.keyboardCallback() != null) {
            game.keyboardCallback().down(e);
        }
        
        String action = game.getBoundAction(e.getKeyChar());
        if(action != null) {
            game.runBoundAction(action);
        }
    }
    
    public void keyPressed(KeyEvent e) {
        game.setKeyState(e.getKeyChar(), KeyState.DOWN);
        
        if(game.keyboardCallback() != null) {
            game.keyboardCallback().hold(e);
        }
    }
    
    public void keyReleased(KeyEvent e) {
        game.setKeyState(e.getKeyChar(), KeyState.UP);
        
        if(game.keyboardCallback() != null) {
            game.keyboardCallback().release(e);
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if(game.mouseCallback() != null)
            game.mouseCallback().click(e);
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
        if(game.mouseCallback() != null)
            game.mouseCallback().move(e);
    }
}