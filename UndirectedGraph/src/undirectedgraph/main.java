/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

import MinSpantree.MSNode;
import MinSpantree.MinSpanTree;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import valuepacks.Vector;
import jgame.*;

/**
 *
 * @author thmun
 */
public class main {
    static UndirectedGraph<Vector> graph;
    static Game game;
    static boolean console_toggle;
    static Vector2 mouse;
    static int radius;
    
    static MinSpanTree minspan_tree;
    static boolean display_tree;
    
    static UndirectedGraph<Vector> draw_graph;
    
    public static void main(String[] args) {
        graph = new UndirectedGraph<Vector>();
        game = new Game();
        
        game.useConsole();
        game.replaceStdOut();
        
        // You can change the random seed here to always get the same graph
        //game.setSeed(1);
        
        // Set the graph to use same random generator as the gameloop so that is affected by the same seed
        graph.setRandom(game.random);
        
        // Set up some variables
        console_toggle = true;
        radius = 30;
        mouse = new Vector2(0, 0);
        minspan_tree = null;
        display_tree = false;
        draw_graph = null;
        
        game.log("Welcome to the Undirected Graph project. Press \"|\" to toggle this output console");
        
        // Create random nodes
        /*for(int i=0; i<20; i++) {
            Vector pos = new Vector(game.random.nextFloat() * game.width(), game.random.nextFloat() * game.height());
            graph.addNode(pos);
        }
        
        // Create random edges
        for(int i=0; i<10; i++) {
            graph.createEdge(graph.randomNode(), graph.randomNode());
        }*/
        
        UGNode<Vector> n1 = graph.addNode(new Vector(500, 500));
        UGNode<Vector> n2 = graph.addNode(new Vector(710, 550));
        UGNode<Vector> n3 = graph.addNode(new Vector(450, 600));
        UGNode<Vector> n4 = graph.addNode(new Vector(600, 600));
        
        graph.createEdge(n1, n2);
        graph.createEdge(n2, n3);
        graph.createEdge(n3, n4);
        graph.createEdge(n1, n3);
        
        // Extract all the nodes 
        ArrayList<UGNode<Vector>> nodes = graph.nodes();
        game.log("Number of nodes: " + nodes.size());
        
        // Extract all edges
        ArrayList<UGEdge<Vector>> edges = graph.edges();
        
        game.onKeyboard(new KeyboardCallback() {
            public void down(KeyEvent e) {
                if(console_toggle) {
                    game.hideConsole();
                    console_toggle = false;
                } else {
                    game.showConsole();
                    console_toggle = true;
                }
            }
        });
        
        game.onMouse(new MouseCallback() {
            boolean focus;
            
            public void click(MouseEvent e) {
                focus = false;
                mouse.x = e.getX();
                mouse.y = e.getY();
                
                nodes.forEach(node -> {
                    if(mouse.subtract(node.val()).length() < radius && display_tree == false) {
                        game.log("Calculate minimal spanning tree from node with value" + node.val().toString());
                        focus = true;
                        display_tree = true;
                        minspan_tree = new MinSpanTree<Vector>(graph, node);
                        
                        return;
                    }
                    
                });
                
                if(!focus) {
                    game.log("I clicked the background, go back to displaying the graph");
                    minspan_tree = null;
                    display_tree = false;
                }
            }
        });
        
        game.Update((g, deltaTime) -> {
        });
        
        game.Draw((g, deltaTime) -> {
            // Display the graph unless we clicked a node to create a minimal spanning tree from
            if(!display_tree) {
                draw_graph = graph;
            } else {
                if(minspan_tree != null)
                    draw_graph = minspan_tree.graph();
            }
            // Draw nodes
            draw_graph.nodes().forEach(node -> {
                g.Circle(node.val(), radius);
                g.Text(node.val().toString(), new Vector2(node.val().x, node.val().y + radius + 12));
            });

            // Draw edges
            draw_graph.edges().forEach(edge -> {
                g.Line(edge.a().val(), edge.b().val());
                
                Vector2 distance = edge.a().val().subtract(edge.b().val());
                Vector2 tpos = new Vector2(edge.a().val().x + distance.x / 2, edge.a().val().y + distance.y / 2);
                g.Text(Float.toString(edge.weight()), tpos);
            });
        });
        
        game.run();
    }
    
    public static void drawTree(MSNode<UGNode<Vector>> n, GameGraphics g) {
        n.children().forEach(node -> {
            g.Circle(node.val().val(), radius);
            g.Line(node.val().val(), node.parent().val().val());
            drawTree(node, g);
        });
    }
    
}
