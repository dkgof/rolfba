/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.utils;

/**
 *
 * @author Rolf
 */
public class Timer {
    private long start;

    private Timer() {
        start = System.nanoTime();
    }

    public static Timer startTimer() {
        return new Timer();
    }

    /**
     * Retreive the time since the timer started in seconds
     * @return
     */
    public double time() {
        long diff = System.nanoTime() - start;
        double seconds = diff * 0.000000001;
        return seconds;
    }

    public String printTime() {
        double seconds = time();
        return String.format("%.4f", seconds)+" seconds";
    }
}
