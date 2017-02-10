/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    public static Vector2 player_pos;
    public static AStarNodeInterface currentCell;
    
    public static LinkedList<AStarNode> path;
    
    public static void main(String[] args) {
        System.out.println("Test");
        
        Game game = new Game(500,500);
        Grid grid = new Grid(game.width(), game.height(), 20, 20);
        
        // Implement A* function to run from grid... makes sense
        AStarNode.setGrid(grid);
        
        startCell = grid.RandomCell();
        goalCell = null;
        
        a = null;
        
        while(startCell.wall) {
            startCell = grid.RandomCell();
        }
        startCell.color = Color.RED;
        
        path = new LinkedList<AStarNode>();
        
        x = 0;
        y = 10;
        
        player_pos = new Vector2(startCell.transform.position.x, startCell.transform.position.y);
        float speed = 0.5f;
        
        currentCell = null;
        game.Update(new UpdateCallback() {
            public void run(Game g, float deltaTime) {
                if(x < game.width() - 10)
                    x++;
                
                if(g.isKeyDown("h")) {
                    g.exit();
                }
                
                if(path != null && !path.isEmpty()) {
                    System.out.println("Got a path");
                    if(currentCell == null) {
                        currentCell = path.getFirst();
                    }
                    
                    AStarNode nextCell = (AStarNode) currentCell.next();
                    
                    if(nextCell != null) {
                        System.out.println("Found next cell..");
                        if(player_pos.x == nextCell.Cell().transform.position.x &&
                           player_pos.y == nextCell.Cell().transform.position.y) {
                            // currentCell.reset();
                            currentCell = nextCell;
                            System.out.println("Going to next cell");
                        } else {
                            System.out.println("Moving...");
                            Vector2 moveVector = new Vector2(nextCell.transform().position.x - player_pos.x, nextCell.transform().position.y - player_pos.y);
                            player_pos = player_pos.add(moveVector.scale(speed));
                        }
                    } else {
                        System.out.println("Reached goal, resetting");
                        path = null;
                        /*
                        startCell = goalCell;
                        player_pos = startCell.transform().position;
                        goalCell = null;
                        a = null;*/
                    }
                }
            }
        });
        
        game.Draw(new DrawCallback() {
            public void run(Graphics g, float deltaTime) {
                
                if(a != null && path != null) {
                    /*System.out.println("Draw path");
                    AStarNodeInterface n = path.getFirst();

                    while(n.next() != null) {
                        // System.out.println("Draw path");
                        n.next().setColor(Color.red);
                        n = n.next();
                    }*/
                }
                
                grid.Draw(g);
                // g.drawString("this is a test", x, y);
                
                Color c = g.getColor();
                g.setColor(Color.BLUE);
                g.fillRect((int) player_pos.x, (int) player_pos.y, 25, 25);
                g.setColor(c);
            }
        });
        
        game.onMouse(new MouseCallback() {
            public void click(MouseEvent e) {
                System.out.println("I clicked the mouse at position: ");
                System.out.print(e.getX());
                System.out.print(",");
                System.out.println(e.getY());
                
                x = e.getX();
                y = e.getY();
                
                Vector2 mousepos = new Vector2(x, y);
                
                if(goalCell == null) {
                    Cell clickedOn = grid.CellAt(grid.getCoords(mousepos));
                    
                    if(clickedOn != startCell && a == null) {
                        System.out.println("Start thread");
                        clickedOn.color = Color.GREEN;
                        goalCell = clickedOn;
                        
                        new Thread() {
                            public void run() {
                                // Start the pathfinding here from startcell to goalcell
                                a = new AStar(startCell, goalCell);
                                path = a.findPath();
                                this.stop();
                            }
                        }.start();
                    }
                }
                
            }
        });
        
        game.run();
    }
}
