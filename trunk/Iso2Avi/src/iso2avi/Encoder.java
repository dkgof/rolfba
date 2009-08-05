/*
 * Encoder.java
 *
 * Created on 27. marts 2007, 14:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package iso2avi;

import java.util.*;

/**
 *
 * @author Rolf
 */
public class Encoder {
    
    private List<EncoderOptions> queue;
    private int runningThreads;
    private static final int MAX_THREADS = 1; //x264 is multithreaded itself..
    
    /** Creates a new instance of Encoder */
    public Encoder() {
        queue = new ArrayList<EncoderOptions>();
        
        runningThreads = 0;
    }
    
    public void addJob(EncoderOptions e) {
        queue.add(e);
    }
    
    public void removeJob(EncoderOptions e) {
        queue.remove(e);
    }
    
    public void runJob() {
        if( runningThreads < MAX_THREADS ) {
            incRunning();
            
            if( queue.size() > 0 ) {
                EncoderOptions eo = queue.remove(0);
                Thread t = new Thread( new EncoderThread(eo, this) );
                t.start();
                runJob();
            }
        }
    }
    
    public void incRunning() {
        runningThreads ++;
    }

    public void decRunning() {
        runningThreads --;
        runJob();
    }
    
    public List<EncoderOptions> getQueue() {
        return queue;
    }
}