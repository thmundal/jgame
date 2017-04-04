/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JTextField;
import jgame.*;

/**
 *
 * @author thmun
 */
public class GameProject {
    public static int x;
    public static int y;
    public static Cell startCell;
    public static Cell goalCell;
    public static AStar a;
    public static Vector2 ai_pos;
    public static AStarNode currentNode;
    public static Vector2 player_pos;
    
    public static LinkedList<AStarNode> path;
    
    public static Thread path_thread;
    
    public static int moveDelay = 2;
    public static int moveCounter = moveDelay;

    public static float speed = 1f;
    public static float speedModifier = 0.05f;
    public static float playerSpeed = 0.25f;
    
    public static Grid grid;
    public static Cell playerCell;
    public static Cell ai_cell;
    
    public static int score;
    public static int highscore;
    
    public static Game game;
    public static Cell nextPlayerCell;
    
    public static void main(String[] args) {
        score = 0;
        highscore = 0;
        
        game = new Game(500,500);

        setUpGrid();
        
        Sprite playerSprite = new Sprite("resources/mario.jpg", player_pos, new Vector2(25, 25));
        Sprite enemySprite = new Sprite("resources/pacmanghost.png", ai_pos, new Vector2(25, 25));
        
        JTextField seedInput = new JTextField(20);
        seedInput.setBounds(100, 10, 250, 25);
        seedInput.addActionListener(e -> {
           String in = seedInput.getText();
           
           try {
               long n = java.lang.Long.parseLong(in);
               game.setSeed(n);
               
               setUpGrid();
           } catch(Exception ex) {
               System.out.println("Not a number");
           }
        });
        game.addComponent(seedInput); 
        long seed = game.getSeed();
        
        seedInput.setText(java.lang.Long.toString(seed));
        
        game.Update((Game g, float deltaTime) -> {
            speed = (float) Math.log(score + 1) * speedModifier;
            
            if(g.isKeyDown("q")) {
                g.exit();
            }
            
            playerCell = grid.CellAt(grid.getCoords(player_pos));
            
            CellSet neighbours = grid.Neighbours((int) playerCell.coords.x, (int) playerCell.coords.y);
            
            if(g.isKeyDown("w")) {
                nextPlayerCell = neighbours.top();
            }
            
            if(g.isKeyDown("a")) {
                nextPlayerCell = neighbours.left();
            }
            
            if(g.isKeyDown("s")) {
                nextPlayerCell = neighbours.bottom();
            }
            
            if(g.isKeyDown("d")) {
                nextPlayerCell = neighbours.right();
            }
            
            if(nextPlayerCell != null && nextPlayerCell.wall) {
                nextPlayerCell = playerCell;
            }
            
            if(nextPlayerCell != null) {
                Vector2 playerMove = player_pos.subtract(nextPlayerCell.transform.position);
                float player_distance = playerMove.length();
                
                if(player_distance <= 1) {
                    player_pos = nextPlayerCell.transform.position;
                } else {
                    player_pos = player_pos.add(playerMove.scale(playerSpeed));
                }
                
                
                
                moveCounter = moveDelay;
                
                score++;
                
                if(playerMove.length() > 1 && playerCell != nextPlayerCell) {
                    calculatePath();
                }
            }
            
            moveCounter--;
            
            if(path != null && !path.isEmpty()) {
                if(currentNode == null) {
                    currentNode = path.getFirst();
                }
                
                Cell currentCell = currentNode.Cell();
                Vector2 currentCellPos = currentCell.transform.position;
                Vector2 moveVector = ai_pos.subtract(currentCellPos);
                float distance = moveVector.length();
                
                
                if(distance <= 1) {
                    if(currentCell == goalCell) {
                        path.clear();
                        currentNode = null;
                        a = null;
                        ai_pos = goalCell.transform.position;
                        startCell = goalCell;
                        
                        if(score > highscore) {
                            highscore = score;
                        }
                        score = 0;
                    } else {
                        currentNode = currentNode.next();
                    }
                } else {
                    ai_pos = ai_pos.add(moveVector.scale(speed));
                    ai_cell = currentNode.Cell();
                }
            }
        });
        
        game.Draw((Graphics g, float deltaTime) -> {
            grid.Draw(g);
            
            g.setColor(Color.BLUE);
            g.drawString("Score: " + score, 10, 10);
            g.drawString("Highscore: " + highscore, 10, 30);
            
            g.drawString("AI Speed: " + speed, 10, 50);
            
            playerSprite.SetPosition(player_pos);
            playerSprite.Draw(g);
            
            enemySprite.SetPosition(ai_pos);
            enemySprite.Draw(g);
        });
        
        game.onMouse(new MouseCallback() {
            public void click(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                
                Vector2 mousepos = new Vector2(x, y);
                
                Cell clickedOn = grid.CellAt(grid.getCoords(mousepos));

                if(clickedOn != startCell && a == null) {
                    /*System.out.println("Start thread");
                    clickedOn.color = Color.GREEN;
                    goalCell = clickedOn;
                    path.clear();

                    new Thread() {
                        public void run() {
                            // Start the pathfinding here from startcell to goalcell
                            a = new AStar(startCell, goalCell);
                            path = a.findPath();
                        }
                    }.start();*/
                }
            }
        });
        
        game.run();
    }
    
    public static void calculatePath() {
        if(path_thread != null && path_thread.isAlive()) {
            return;
        }
        
        if(playerCell == null || ai_cell == null) {
            return;
        }
        
        path_thread = new Thread() {
            public void run() {
                goalCell = playerCell;
                a = new AStar(ai_cell, playerCell);
                path = a.findPath();
            }
        };
        
        path_thread.start();
    }
    
    public static void setUpGrid() {
        grid = new Grid(game.width(), game.height(), 20, 20);
        
        // Implement A* function to run from grid... makes sense
        AStarNode.setGrid(grid);
        
        startCell = grid.RandomCell();
        goalCell = null;
        
        player_pos = grid.RandomCell().transform.position;
        
        a = null;
        
        while(startCell.wall) {
            startCell = grid.RandomCell();
        }
        startCell.color = Color.RED;
        
        path = new LinkedList<AStarNode>();
        
        x = 0;
        y = 10;
        
        System.out.println(startCell.transform.position.x);
        
        ai_pos = new Vector2(startCell.transform.position.x, startCell.transform.position.y);
        ai_cell = grid.CellAt(grid.getCoords(ai_pos));
        
        currentNode = null;
        
        Sprite[] wallSprites = new Sprite[2];
        wallSprites[0] = new Sprite("resources/rock1.jpg");
        wallSprites[1] = new Sprite("resources/tree1.png");
        
        grid.setGroundSprite(new Sprite("resources/grass_sprite.png"));
        grid.setWallSprite(wallSprites);
    }
}
