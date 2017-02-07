/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
/**
 *
 * @author Thomas Mundal<thomas@munso.no>
 */
public class CanvasTest {
    /**
     * @param args the command line arguments
     */
    
    public static Vector2 pos = new Vector2(1f, 1f);
    public static Vector2 speed = new Vector2(0f, 0f);
    public static Sprite dude;
    public static Grid grid;
    
    public static void main(String[] args) {
        final Game game = new Game(800, 600);
        final float moveSpeed = 500.0f;
        
        final Vector2 gravity = new Vector2(0, 100);
        grid = new Grid(game.height(), game.width(), 32, 32);
        
        grid.CellAt(0, 0).color = Color.black;
        grid.RandomCell().color = Color.green;
        
        game.Update(new UpdateCallback() {
            public void run(Game g, float deltaTime) {
                if(game.isKeyDown("a")) {
                    speed.x = -moveSpeed;
                }
                
                if(game.isKeyDown("d")) {
                    speed.x = moveSpeed;
                }
                
                if(game.isKeyDown("w")) {
                    speed.y = -moveSpeed;
                }
                
                if(game.isKeyDown("s")) {
                    speed.y = moveSpeed;
                }
                
                speed = speed.scale(deltaTime);
                pos = pos.add(speed); //.add(gravity.scale(deltaTime));
                
                
            }
        });
        
        game.Draw(new DrawCallback() {
            public void run(Graphics g, float deltaTime) {
                g.clearRect(0, 0, game.width(), game.height());
                grid.Draw(g);
                
                g.setColor(Color.red);
                g.drawOval(pos.intX(), pos.intY(), 60, 60); //FOR CIRCLE
            }
        });
        
        game.run();
    }
}