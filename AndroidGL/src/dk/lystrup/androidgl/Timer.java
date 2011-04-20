
package dk.lystrup.androidgl;

/**
 * Timer models a nanosecond precision timer for measuring time between frames and other
 * timing that requires precise delta values.
 * @author Rolf
 */
public class Timer {

    private long start, lastDelta;
    private boolean started;

    public Timer() {
        started = false;
        lastDelta = 0;
    }

    /**
     * Start this timer
     */
    public void start() {
        if(!started) {
            start = System.nanoTime();
            started = true;
        }
    }

    /**
     * Reset this timer
     */
    public void reset() {
        if(started) {
            start = System.nanoTime();
            lastDelta = 0;
        }
    }

    /**
     * Return the time in seconds since this timer was started
     * @return seconds since timer was started
     */
    public float getTime() {
        if(started) {
            return nanoToSeconds(System.nanoTime() - start);
        }

        throw new IllegalStateException("Timer must be started before use");
    }

    /**
     * Get the time in seconds since last call to getDelta
     * @return seconds since last getDelta call
     */
    public float getDelta() {
        long now = System.nanoTime();
        float delta = nanoToSeconds(now-lastDelta);
        lastDelta = now;
        return delta;
    }

    /**
     * Convert nanoseconds to seconds
     * @param nanoTime the time in nanoseconds
     * @return the time in seconds
     */
    private float nanoToSeconds(long nanoTime) {
        return (float) (nanoTime / 1000000000.0);
    }
}
