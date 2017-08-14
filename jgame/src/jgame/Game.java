/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import jgame.util.Vector2;
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
import java.util.Map;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import jgame.util.Global;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GLUtil.setupDebugMessageCallback;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
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
    private DrawCallback drawCallback;
    private float last_time = System.nanoTime();
    private String window_title;
    private boolean pause;
    private boolean wait;
    private int wait_seconds;
    private boolean fullscreen;
    private InputListener input;
    public GLInputListener gl_input;
    private MouseCallback mouseCallback;
    private Thread updateThread;
    private JFrame window;
    private long gl_window;
    private GameGraphics gameGraphics;
    
    private JTextArea log_output;
    private KeyboardCallback keyboardCallback;
    private JScrollPane text_scroll;
    private JTextField console_input;
    private Hashtable<String, CommandInterface> commands;
    private JPanel logpanel;
    private Hashtable<Character, String> default_keys;
    private Hashtable<Character, String> custom_keys;
    private boolean console_on;
    private Vector2 origin;
    private UpdateCallback wakeupCallback;
    private GLCapabilities glCapabilities;
    private float now;
    
    public static int WIDTH;
    public static int HEIGHT;
    
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
        
        HEIGHT = height;
        WIDTH = width;
        
        Global.WIDTH = width;
        Global.HEIGHT = height;
        
        defaultVariables();
        //initSwing();
        initGL();
    }
    
    public float x(int x) {
        return x / width;
    }
    
    public float y(int y) {
        return y / height;
    }
    
    public Game() {
        // Fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height;
        width = screenSize.width;
        fullscreen = true;
        
        HEIGHT = height;
        WIDTH = width;
        
        defaultVariables();
        //initSwing();
        initGL();
    }
    
    public void defaultVariables() {
        frameRate = 60;
        timePerFrame = 1f / frameRate;
        window_title = "jGame Alpha 1.0";
        pause = false;
        state = true;
        random = new Random(seedValue);
        commands = new Hashtable<String, CommandInterface>();
        default_keys = new Hashtable<Character, String>();
        custom_keys = new Hashtable<Character, String>();
        
        default_keys.put('|', "console");
        custom_keys = default_keys;
        console_on = false;
        origin = new Vector2(0, 0);
        gameGraphics = new GameGraphics();
        
        Transform.global_size = new Vector2(width, height);
    }
    
    public Vector2 origin() {
        return origin;
    }
    
    public void setOrigin(Vector2 o) {
        origin = o;
    }
    
    public void bindKey(char key, String action) {
        custom_keys.put(key, action);
    }
    
    public String getBoundAction(char key) {
        return custom_keys.get(key);
    }
    
    public Character getKeyForAction(String action) {
        for(Object e : custom_keys.entrySet()) {
            Map.Entry entry = (Map.Entry) e;
            if(entry.getValue() == action) {
                return (Character) entry.getKey();
            }
        }
        
        return null;
    }
    
    public void runBoundAction(String action) {
        CommandInterface cmd = commands.get(action);
        
        if(cmd != null)  {
            cmd.run(this);
        } else {
            if(action == "console") {
                toggleConsole();
            }
        }
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
    
    public void initGL() {
        GLFWErrorCallback.createPrint(System.err).set();
        
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        
        // Get primary monitor information
        long primary_monitor = glfwGetPrimaryMonitor();
        long selected_monitor = primary_monitor;
        /*
        // Get all monitors
        PointerBuffer monitors = glfwGetMonitors();
        */
        
        gl_window = glfwCreateWindow(width, height, "Hello World!", (fullscreen?selected_monitor:NULL), NULL);
        if(gl_window == NULL) {
            throw new RuntimeException("Fail to create GLFW window");
        }
        
        gl_input = new GLInputListener(gl_window);
        
        glfwSetKeyCallback(gl_window, (window, key, scancode, action, mods) -> {
            gl_input.Listen(window, key, scancode, action, mods);
        });
        
        try(MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            
            IntBuffer pixelWidth = stack.mallocInt(1);
            IntBuffer pixelHeight = stack.mallocInt(1);
            
            glfwGetWindowSize(gl_window, pWidth, pHeight);
            glfwGetFramebufferSize(gl_window, pixelWidth, pixelHeight);
            
            GLFWVidMode vidmode = glfwGetVideoMode(selected_monitor);
            glfwSetWindowPos(gl_window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }
        
        glfwMakeContextCurrent(gl_window);
        
        glCapabilities = GL.createCapabilities();
        glfwSwapInterval(1);
        glfwShowWindow(gl_window);
        
        setupDebugMessageCallback(System.out);
    }
    
    public void useConsole() {
        setupLogger(-origin.intX(), -origin.intY(), width(), height() / 4);
    }
    
    public void useConsole(int x, int y, int width, int height) {
        setupLogger(x, y, width, height);
    }
    
    public void setupLogger(int x, int y, int width, int height) {
        Color background = new Color(0.0f, 0.0f, 0.0f, 0.5f);
        
        logpanel = new JPanel();
        logpanel.setBounds(x, y, width, height);
        logpanel.setBackground(background);
        logpanel.setBorder(null);
        logpanel.setLayout(null);
        
        log_output = new JTextArea();
        log_output.setEditable(false);
        log_output.setBackground(new Color(0,0,0,0));
        log_output.setForeground(Color.WHITE);
        log_output.setBounds(0, 0, width, height);
        
        text_scroll = new JScrollPane(log_output);
        text_scroll.setBackground(new Color(0, 0, 0, 0));
        text_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        text_scroll.setBounds(0, 0, width, height - 30);
        
        console_input = new JTextField();
        console_input.setBounds(0, height - 30, width, 25);
        console_input.setBorder(null);
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
        
        logpanel.add(console_input);
        logpanel.add(text_scroll);
        addComponent(logpanel);
        
        hideConsole();
    }
    
    public void replaceStdOut() {
        System.setOut(new GameOutput(System.out));
    }
    
    public void hideConsole() {
        console_on = false;
        logpanel.setVisible(false);
    }
    
    public void showConsole() {
        console_on = true;
        logpanel.setVisible(true);
    }
    
    public void toggleConsole() {
        if(console_on) {
            hideConsole();
        } else {
            showConsole();
        }
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
        return keys_down.indexOf(k.toCharArray()[0]) > -1;
    }
    
    public void setKeyState(char k, KeyState state) {
        int index = keys_down.indexOf(k);
        
        if(index > -1 && state == KeyState.UP) {
            keys_down.remove(index);
        } else if(state == KeyState.DOWN && index == -1) {
            keys_down.add(k);
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
        //frame.onDraw(c);
        drawCallback = c;
    }
    
    public void Wakeup(UpdateCallback c) {
        wakeupCallback = c;
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
        
        GL.setCapabilities(glCapabilities);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        if(wakeupCallback != null)
            wakeupCallback.run(g, 0);
        
        while(!glfwWindowShouldClose(gl_window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            long time = System.nanoTime();

            deltaTime = ((time - last_time) / 1000000000f);
            
            last_time = time;

            if(!pause) {
                if(updateCallback != null) {
                    updateCallback.run(g, new Float(deltaTime));
                    drawCallback.run(gameGraphics, deltaTime);
                } else {
                    System.out.println("No update callback defined");
                    state = false;
                }
            }

            if(wait) {
                wait = false;
                try {
                    Thread.sleep(wait_seconds * 1000);
                    wait = false;
                } catch(InterruptedException e) {
                    System.out.println("Wait failed");
                }
            }

            now = (float) glfwGetTime();
            deltaTime = now - last_time;
            last_time = now;
            
            glfwPollEvents();
            glfwSwapBuffers(gl_window);
        }

        glfwFreeCallbacks(gl_window);
        glfwDestroyWindow(gl_window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
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