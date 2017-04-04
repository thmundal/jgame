/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JFrame;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
/**
 *
 * @author Thomas
 */
public class Game {
    /**
     * PUBLIC VARIABLES
     */
    public static Random seed = new Random();
    public long seedValue = seed.nextLong();
    public static Random random;
    
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
    private JTextArea log_output;
    private KeyboardCallback keyboardCallback;
    private JScrollPane text_scroll;
    private JTextField console_input;
    private Hashtable<String, CommandInterface> commands;
    private JPanel logpanel;
    
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
    
    public KeyboardCallback keyboardCallback() {
        return keyboardCallback;
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
        random = new Random(seedValue);
        commands = new Hashtable<String, CommandInterface>();
    }
    
    public long getSeed() {
        return seedValue;
    }
    
    public void setSeed(long s) {
        seedValue = s;
        random = new Random(seedValue);
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
    
    public void useConsole() {
        setupLogger(0, 0, width(), height() / 4);
    }
    
    public void useConsole(int x, int y, int width, int height) {
        setupLogger(x, y, width, height);
    }
    
    public void setupLogger(int x, int y, int width, int height) {
        Color background = new Color(0.0f, 0.0f, 0.0f, 0.5f);
        
        logpanel = new JPanel();
        logpanel.setBounds(x, y, width, height);
        logpanel.setBackground(new Color(0, 0, 0, 0));
        logpanel.setBorder(null);
        logpanel.setLayout(null);
        
        log_output = new JTextArea();
        log_output.setEditable(false);
        //log_output.setBackground(background);
        log_output.setForeground(Color.WHITE);
        
        text_scroll = new JScrollPane(log_output);
        text_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        text_scroll.setBounds(x, y, width, height);
        
        console_input = new JTextField("aksjd");
        console_input.setBounds(x, y + height - 30, width, 25);
        console_input.addActionListener(e -> {
            String command = console_input.getText();
            CommandInterface cmd = commands.get(command);
            if(cmd != null) {
                cmd.run(this);
            } else {
                log("No such command");
            }
            
            console_input.setText("");
        });
        
        //log_output.add(console_input);
        //addComponent(text_scroll);
        
        logpanel.setComponentZOrder(text_scroll, 0);
        logpanel.setComponentZOrder(console_input, 1);
        logpanel.add(text_scroll);
        logpanel.add(console_input);
        addComponent(logpanel);
    }
    
    public void replaceStdOut() {
        System.setOut(new GameOutput(System.out));
    }
    
    public void hideConsole() {
        logpanel.setVisible(false);
    }
    
    public void showConsole() {
        logpanel.setVisible(true);
    }
    
    public void addCommand(String command, CommandInterface c) {
        commands.put(command, c);
    }
    
    public void log(Object msg) {
        log(msg, true);
    }
    
    public void log(Object msg, boolean nl) {
        if(log_output != null) {
            Rectangle bounds = log_output.getBounds();
            String old_text = log_output.getText();
            
            if(nl) {
                old_text = old_text + "\n";
            }
            log_output.setText(old_text + msg.toString());
            log_output.setBounds(bounds);
        }
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
    
    public void onKeyboard(KeyboardCallback cb) {
        keyboardCallback = cb;
    }
    
    public void Update(UpdateCallback c) {
        updateCallback = c;
    }
    
    public void Draw(DrawCallback c) {
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
                        wait = false;
                        try {
                            this.sleep(wait_seconds * 1000);
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
    
    public class GameOutput extends PrintStream {
        public GameOutput(OutputStream out) {
            super(out, true);
        }
        
        public void print(String s) {
            log(s);
        }
        
        public void println(String s) {
            log(s, true);
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