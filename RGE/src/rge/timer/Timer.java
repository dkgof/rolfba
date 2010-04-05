
package rge.timer;

/**
 *
 * @author Rolf
 */
public class Timer {

    private long start, lastDelta;
    private boolean started;

    public Timer() {
        started = false;
        lastDelta = 0;
    }

    public void start() {
        if(!started) {
            start = System.nanoTime();
            started = true;
        }
    }

    public void reset() {
        if(started) {
            start = System.nanoTime();
            lastDelta = 0;
        }
    }

    public double getTime() {
        if(started) {
            return nanoToSeconds(System.nanoTime() - start);
        }

        throw new IllegalStateException("Timer must be started before use");
    }

    public double getDelta() {
        long now = System.nanoTime();
        double delta = nanoToSeconds(now-lastDelta);
        lastDelta = now;
        return delta;
    }

    private double nanoToSeconds(long nanoTime) {
        return nanoTime / 1000000000.0;
    }
}
