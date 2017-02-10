/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statictest;

import java.util.Hashtable;

/**
 *
 * @author thmun
 */
public class StaticTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Static b = new Static();
        b.test();
        
        System.out.println(Static.geta());
    }
    
}

class Static {
    static int a;
    static Hashtable<Integer, String> hash = new Hashtable<Integer, String>();
    public Static() {
        
    }
    
    public void test() {
        a = a+4;
        
        Static.a = 8;
        
        String t = Static.getHash(1);
        
        System.out.println(Static.hash.size());
    }
    
    public static String getHash(Integer n) {
        String r = hash.get(n);
        
        if(r == null) {
            hash.put(n, "Inserted");
        }
        
        return r;
    }
    
    public static int geta() {
        return a;
    }
}