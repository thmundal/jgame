/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

/**
 *
 * @author Thomas
 */
@FunctionalInterface
public interface CommandInterface {
    public void run(Game g);
}
