/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class Game {
    private List keys_down;
    private boolean state;
    private Drawer frame;
    private Callback updateCallback;
    private Callback drawCallback;
    private long last_time = System.nanoTime();
    public float deltaTime;
    float frameRate;
    double timePerFrame;
    public int height;
    public int width;
    
    private boolean pause;
    private boolean wait;
    private int wait_seconds;
    
    public static Random seed = new Random();
    public static Random random = new Random(seed.nextLong());
    
    private InputListener input;
    public Game(int w, int h) {
        frameRate = 60;
        timePerFrame = 1f / frameRate;
        
        pause = false;
        
        height = h;
        width = w;
        
        state = true;
        JFrame window = new JFrame("Test");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame = new Drawer("Testing key input", this);
        
        JPanel panel = new JPanel();
        input = new InputListener();
        input.setGame(this);
        
        keys_down = new ArrayList();
        
        panel.addKeyListener(input);
        panel.addMouseListener(input);
        panel.addMouseMotionListener(input);
        
        frame.add(panel);
        window.add(frame);
        
        window.setSize(width, height);
        window.setVisible(true);
        
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }
    
    public boolean running() {
        return state;
    }
    
    public boolean isKeyDown(String k) {
        //System.out.println(keys_down.toString());
        return keys_down.indexOf(k.toCharArray()[0]) > -1;
    }
    
    public void setKeyState(char k, KeyState state) {
        int index = keys_down.indexOf(k);
        
        if(index > -1 && state == KeyState.UP) {
            keys_down.remove(index);
            //System.out.println("Removed key state of " + k);
        } else if(state == KeyState.DOWN && index == -1) {
            keys_down.add(k);
            //System.out.println("Added key state for " + k);
        }
    }
    
    public void Update(Callback c) {
        //c.run(this);
        updateCallback = c;
    }
    
    public void Draw(Callback c) {
        System.out.println("Setting drawcallback");
        frame.onDraw(c);
    }
    
    public void Pause() {
        pause = !pause;
    }
    
    public void Wait(int sec) {
        wait_seconds = sec;
        wait = true;
    }
    
    public void run() {
        while(running()) {
            long time = System.nanoTime();
            
            deltaTime = ((time - last_time) / 1000000000f);
            last_time = time;
            
            if(!pause) {
                if(updateCallback != null) {
                    updateCallback.run(this, deltaTime);
                } else {
                    System.out.println("No update callback defined");
                    state = false;
                }
                frame.repaint();
            }
            
            if(wait) {
                try {
                    Thread.sleep(wait_seconds * 1000);
                    wait = false;
                } catch(InterruptedException e) {
                    System.out.println("Wait failed");
                }
            }
            
            // Throttle frame time based on FPS
            double remainingFrameTime = (last_time - System.nanoTime()) / 1000000000 + timePerFrame;
            // System.out.println(remainingFrameTime);
            if(remainingFrameTime > 0) {
                long millis = (long) (remainingFrameTime * 1000);
                int nanos = (int) (remainingFrameTime * 1000 - millis) * 1000000;
                // System.out.println(millis + "\t" + nanos);
                try {
                    Thread.sleep(millis, nanos);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}

enum KeyState {
    DOWN, UP
}

class Key {
    private KeyState state;
    private char keyCode;
    
    public Key(char k) {
        keyCode = k;
    }
    
    public void setState(KeyState s) {
        state = s;
    }
    
    public KeyState getState() {
        return state;
    }
}