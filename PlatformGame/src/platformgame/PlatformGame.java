/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformgame;

import jgame.*;
/**
 *
 * @author thmun
 */
public class PlatformGame {
    static Game game;
    
    static Vector2 gravity;
    static Vector2 jumpVector;
    static Vector2 player_pos;
    static Sprite player_model;
    static float moveSpeed = 5.0f;
    static boolean jumping;
    static int ground;
    static float jumpSpeed = -15.0f;
    
    public static void main(String[] args) {
        game            = new Game();
        gravity         = new Vector2(0, 1.0f);
        jumpVector      = new Vector2(0, jumpSpeed);
        player_pos      = new Vector2(0, -25);
        player_model    = new Sprite("resources/mario.jpg");
        player_model.SetSize(new Vector2(50, 50));
        jumping         = false;
        
        SetupActions();
        
        game.setOrigin(new Vector2(game.width() / 2, game.height() - 100));
        game.useConsole();
        game.replaceStdOut();
        
        
        game.Update((g, deltaTime) -> {
            if(game.isKeyDown("d")) {
                game.runBoundAction("moveright");
            }
            if(game.isKeyDown("a")) {
                game.runBoundAction("moveleft");
            }
            
            if(jumping && player_pos.y < -24) {
                jumpVector = jumpVector.add(gravity);
                player_pos = player_pos.add(jumpVector);
                player_pos.constrainY(-Float.MAX_VALUE, -player_model.height() / 2);
            } else {
                player_pos.y = -player_model.height() / 2;
                jumping = false;
                jumpVector.y = jumpSpeed;
            }
        });
        
        game.Draw((g, deltaTime) -> {
            g.Sprite(player_model, player_pos);
            
            g.Line(new Vector2(-game.width() / 2, 0), new Vector2(game.width() / 2, 0));
            g.Text(player_pos.toString(), new Vector2(-100, -100));
        });
        
        game.run();
    }
    
    static void SetupActions() {
        game.addCommand("moveright", g -> {
            Vector2 moveVector = new Vector2(1,0);
            player_pos = player_pos.add(moveVector.scale(moveSpeed));
        });
        
        game.addCommand("moveleft", g -> {
            Vector2 moveVector = new Vector2(-1,0);
            player_pos = player_pos.add(moveVector.scale(moveSpeed));
        });
        
        game.addCommand("jump", g -> {
            if(!jumping) {
                jumping = true;
            }
        });
        
        //game.bindKey('d', "moveright");
        //game.bindKey('a', "moveleft");
        game.bindKey(' ', "jump");
    }
}
