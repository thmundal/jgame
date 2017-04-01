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
public class Worker extends Thread {
    WorkerCallback cb;
    public Worker(WorkerCallback _cb) {
        cb = _cb;
    }
    
    public void run() {
        cb.run(this);
    }
    
    public void signal(WorkerCallback _cb2) {
        _cb2.run(this);
        
    }
}
