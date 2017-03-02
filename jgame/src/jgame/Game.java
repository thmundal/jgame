/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
/**
 *
 * @author Thomas
 */
public class Game {
    /**
     * PUBLIC VARIABLES
     */
    public static Random seed = new Random();
    public static Random random = new Random(seed.nextLong());
    
    /**
     * PRIVATE VARIABLES
     */
    private int height;
    private int width;
    private float deltaTime;
    private float frameRate;
    private double timePerFrame;
    private List keys_down;
    private boolean state;
    private Drawer frame;
    private UpdateCallback updateCallback;
    private long last_time = System.nanoTime();
    private String window_title;
    private boolean pause;
    private boolean wait;
    private int wait_seconds;
    private boolean fullscreen;
    private InputListener input;
    private MouseCallback mouseCallback;
    private Thread updateThread;
    private JFrame window;
    
    // Getters and setters
    public int width() {
        return width;
    }
    
    public int height() {
        return height;
    }
    
    public float deltaTime() {
        return deltaTime;
    }
    
    public boolean fullscreen() {
        return fullscreen;
    }
    
    public MouseCallback mouseCallback() {
        return mouseCallback;
    }
    
    // Constructor(s)
    public Game(int w, int h) {
        height = h;
        width = w;
        
        defaultVariables();
        initSwing();
    }
    
    public Game() {
        // Fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height;
        width = screenSize.width;
        fullscreen = true;
        
        defaultVariables();
        initSwing();
    }
    
    public void defaultVariables() {
        frameRate = 60;
        timePerFrame = 1f / frameRate;
        window_title = "jGame Alpha 1.0";
        pause = false;
        state = true;
    }
    
    public void initSwing() {
        window = new JFrame(window_title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame = new Drawer("Testing key input", this);
        input = new InputListener();
        input.setGame(this);
        
        keys_down = new ArrayList();
        
        window.setSize(width, height);
        
        if(fullscreen) {
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setUndecorated(true);
        }
        
        frame.addKeyListener(input);
        frame.addMouseListener(input);
        frame.addMouseMotionListener(input);
        
        window.add(frame);
        
        window.setVisible(true);
        
        frame.setFocusable(true);
        frame.requestFocusInWindow();
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
    
    public void onMouse(MouseCallback cb) {
        mouseCallback = cb;
    }
    
    public void Update(UpdateCallback c) {
        //c.run(this);
        updateCallback = c;
    }
    
    public void Draw(DrawCallback c) {
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
    
    public void exit() {
        state = false;
        System.exit(0);
    }
    
    public void run() {
        Game g = this;
        
        updateThread = new Thread() {
            public void run() {
                while(running()) {
                    long time = System.nanoTime();

                    deltaTime = ((time - last_time) / 1000000000f);
                    last_time = time;

                    if(!pause) {
                        if(updateCallback != null) {
                            updateCallback.run(g, new Float(deltaTime));
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

                    if(frameRate > 0) {
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
        };
        updateThread.start();
    }
    
    public void setFrameRate(float fps) {
        frameRate = fps;
    }
    
    public float frameRate() {
        return frameRate;
    }
    
    public void addComponent(JComponent c) {
        frame.add(c);
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