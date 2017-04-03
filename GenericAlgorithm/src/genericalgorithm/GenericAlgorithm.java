/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericalgorithm;

import java.util.Random;

/**
 *
 * @author thmun
 */
public class GenericAlgorithm {
    private String[] items;
    private int population;
    private String target;
    private int generations = 0;
    private final String letters = "abcdefghijklmnopqrstuvwxyz ";
    // letters have ascii values from 63 to 122
    // spacebar is 32
    private int mutation_rate;
    Random rand;
    
    public GenericAlgorithm(String t) {
        target = t;
        
        rand = new Random();
        population = 5;
        
        items = new String[population];
        for(int i=0; i<population; i++) {
            items[i] = "";
            for(int j=0; j<target.length(); j++) {
                items[i] += letters.charAt(rand.nextInt(letters.length()));
            }
            System.out.println(items[i]);
        }
    }
}
