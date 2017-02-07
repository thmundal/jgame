/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvastest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author thmun
 */
public class Grid {
    private Transform transform;
    private Vector2 size;
    private Vector2 cell_size;
    private Cell[][] grid_data;
    
    public Grid() {
        
    }
    
    public Grid(int width, int height, int cell_width, int cell_height) {
        size = new Vector2(width, height);
        cell_size = new Vector2(cell_width, cell_height);
        
        MakeGridData();
        //GenerateMaze();
        //GenerateMaze(CellAt(0, 0));
        GenerateMaze(0);
    }
    
    public void MakeGridData() {
        grid_data = new Cell[(int) (size.x / cell_size.x)][(int) (size.y / cell_size.y)];
        
        for(int x=0; x < (int) (size.x / cell_size.x); x++) {
            for(int y=0; y < (int) (size.y / cell_size.y); y++) {
                grid_data[x][y] = new Cell(new Vector2((int) cell_size.x, (int) cell_size.y), new Vector2(x * (int) cell_size.x, y * (int) cell_size.y), Color.white);
            }
        }
    }
    
    public boolean GenerateMaze() {
        for(Cell[] a : grid_data) {
            for(Cell cell : a) {
                if(cell.visited) continue;
                
                boolean r = Game.random.nextBoolean();
                
                cell.visited = true;
                CellSet n = Neighbours((int) cell.coords.x, (int) cell.coords.y);
                
                if(n.top() != null && r) {
                    n.bottom().wall = false;
                    n.bottom().visited = true;
                } else if(n.left() != null && !r) {
                    n.right().wall = false;
                    n.right().visited = true;
                }
                 
            }
        }
        
        return true;
    }
    
    public boolean GenerateMaze(int y) {
        if(y == grid_data.length - 1) {
            return true;
        }
        
        for(int x=0; x<grid_data.length; x++) {
            Cell cell = CellAt(x, y);
            CellSet n = Neighbours((int) cell.coords.x, (int) cell.coords.y);
            
            if(Game.random.nextBoolean()) {
                // Go right
                n.right().wall = false;
            } else {
                // Go dow
                n.bottom().wall = false;
            }
        }
        return GenerateMaze(y + 1);
        
    }
    
    public boolean GenerateMaze(Cell cell) {
        if(cell.coords.y >= grid_data.length - 1 || cell.coords.x >= grid_data.length - 1) {
            return true;
        }
        
        if(cell.visited) {
            // Need to check that there is a wall between
            GenerateMaze(RandomCell());
            return true;
        }
        
        /*for(int y=0; y<grid_data.length; y++) {
            for(int x=0; x<grid_data.length;x++) {
                // Cell c = CellAt(x, y);
            }
        }*/
        CellSet n = Neighbours((int) cell.coords.x, (int) cell.coords.y);
        
        int dir = Game.random.nextInt(4);
        Cell nextCell;
        
        switch(dir) {
            default:
            case 0:
                // Go right
                nextCell = n.right();
                break;
            case 1:
                // Go down
                nextCell = n.bottom();
                break;
            case 2:
                // Go left
                nextCell = n.left();
                break;
            case 3:
                // Go up
                nextCell = n.top();
                break;
        }

        nextCell.wall = false;
        GenerateMaze(nextCell);
        cell.visited = true;
        
        return true;
    }

    public void Draw(Graphics g) {
        for(int x=0; x < (int) (size.x / cell_size.x); x++) {
            for(int y=0; y < (int) (size.y / cell_size.y); y++) {
                grid_data[x][y].Draw(g);
            }
        }
    }
    
    public Cell CellAt(int x, int y) {
        if(x < 0) {
            x = 0;
        }
        if(y < 0) {
            y = 0;
        }
        if(x > grid_data.length - 1) {
            x = grid_data.length - 1;
        }
        
        if(y > grid_data[0].length - 1) {
            y = grid_data[0].length - 1;
        }
        return grid_data[x][y];
    }
    
    public Cell RandomCell() {
        Random rand = Game.random;
        return grid_data[rand.nextInt(grid_data.length - 1)][rand.nextInt(grid_data[0].length - 1)];
    }
    
    public CellSet Neighbours(int x, int y) {
        Cell[] n = new Cell[9];
        
        int count = 0;
        for(int _y = y - 1; _y <= y+1; _y++) {
            for(int _x = x - 1; _x <= x+1; _x++) {
                n[count] = CellAt(_x, _y);
                count++;
            }
        }
        
        return new CellSet(n);
    }
}
