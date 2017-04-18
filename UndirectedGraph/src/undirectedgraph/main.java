/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirectedgraph;

import MinSpantree.MinSpanTree;
import java.awt.event.MouseEvent;
import valuepacks.Vector;
import jgame.*;

/**
 * The Undirected Graph project
 * @author Thomas Mundal
 */
public class main {
    static UndirectedGraph<Vector> graph;
    static Game game;
    static boolean console_toggle;
    static Vector2 mouse;
    static int radius;
    
    static MinSpanTree minspan_tree;
    static boolean display_tree;
    static boolean node_dancing;
    
    static UndirectedGraph<Vector> draw_graph;
    
    /**
     * Main function
     * @param args 
     */
    public static void main(String[] args) {
        graph = new UndirectedGraph<Vector>();      // Initialize an undirected graph that contains nodes with a vector as value
        
        // Initialize the graphical interface
        game = new Game();
        game.useConsole();
        game.replaceStdOut();
        game.showConsole();
        
        // Set up some variables
        console_toggle  = true;
        radius          = 10;
        mouse           = new Vector2(0, 0);
        minspan_tree    = null;
        display_tree    = false;
        draw_graph      = null;
        node_dancing    = false;
        
        // A welcome message ;)
        game.log("Welcome to the Undirected Graph project. Press \""+game.getKeyForAction("console")+"\" to toggle this output console");
        game.log("To calculate MST, click on a node, press \"p\" on the keyboard, or type \"prim\" in this console");
        game.log("To go back to displaying the graph, click anywhere but on a node inside this application");
        
        // Create nodes and put them in the graph
        UGNode<Vector> n1 = graph.addNode(new Vector(300, 200));
        UGNode<Vector> n2 = graph.addNode(new Vector(710, 550));
        UGNode<Vector> n3 = graph.addNode(new Vector(450, 600));
        UGNode<Vector> n4 = graph.addNode(new Vector(600, 600));
        UGNode<Vector> n5 = graph.addNode(new Vector(900, 600));
        UGNode<Vector> n6 = graph.addNode(new Vector(900, 200));
        
        // Create edges between nodes
        graph.createEdge(n1, n2);
        graph.createEdge(n2, n3);
        graph.createEdge(n3, n4);
        graph.createEdge(n1, n3);
        graph.createEdge(n4, n5);
        graph.createEdge(n4, n1);
        graph.createEdge(n2, n6);
        
        // A fun command for making the nodes have a dancing party!
        game.addCommand("dance", game -> {
            node_dancing = !node_dancing;
            game.log("Dancing toggled");
        });
        
        // A command for running the mst calculation
        game.addCommand("prim", game -> {
            MST();
        });
        
        // Bind the prim calculation to "p"
        game.bindKey('p', "prim");
        
        // Mouse events
        game.onMouse(new MouseCallback() {
            boolean focus;
            
            public void click(MouseEvent e) {
                focus = false;
                mouse.x = e.getX();
                mouse.y = e.getY();
                
                // If we clicked on a node
                graph.nodes().forEach(node -> {
                    if(mouse.subtract(node.val()).length() < radius && display_tree == false) {
                        game.log("Calculate minimal spanning tree from node with value" + node.val().toString());
                        focus = true;
                        main.MST(node);
                        return;
                    }
                    
                });
                
                if(!focus) {
                    // Clicked on the background, display the original graph
                    minspan_tree = null;
                    display_tree = false;
                }
            }
        });
        
        game.Update((g, deltaTime) -> {
            // Decide here what graph to draw. The original graph, or the MST
            if(!display_tree) {
                draw_graph = graph;
            } else {
                if(minspan_tree != null)
                    draw_graph = minspan_tree.graph();
            }
            
            // The dance party here! :D
            if(node_dancing) {
                draw_graph.nodes().forEach(node -> {
                    Vector newval = new Vector(node.val().x + (-1 + game.random.nextInt(3)), node.val().y + (-1 + game.random.nextInt(3)));
                    node.setVal(newval);
                });
            }
        });
        
        game.Draw((g, deltaTime) -> {
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
        
        // Start game loop
        game.run();
    }
    
    /**
     * A function for calling the MST calculation
     * @param node 
     */
    public static void MST(UGNode<Vector> node) {
        game.log("Calculate MST");
        display_tree = true;
        minspan_tree = new MinSpanTree<Vector>(graph, node);
    }
    
    /**
     * Calculate MST from a random node
     */
    public static void MST() {
        MST(graph.randomNode());
    }
}
