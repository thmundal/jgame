/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.shapes;

import jgame.Transform;

/**
 *
 * @author Thomas Mundal <thomas@munso.no>
 */
public interface Entity {
    public Transform transform = new Transform();
    public int width = 0;
    public int height = 0;
}
