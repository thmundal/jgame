/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame;

import java.awt.Graphics;

/**
 *
 * @author Thomas
 */

@FunctionalInterface
public interface DrawCallback {
    public void run(Graphics g, float deltaTime);
}