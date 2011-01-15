
package dk.lystrup.androidgl;

/**
 * Timer models a nano precision timer for messuring time between frames and other
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

    public float getTime() {
        if(started) {
            return nanoToSeconds(System.nanoTime() - start);
        }

        throw new IllegalStateException("Timer must be started before use");
    }

    public float getDelta() {
        long now = System.nanoTime();
        float delta = nanoToSeconds(now-lastDelta);
        lastDelta = now;
        return delta;
    }

    private float nanoToSeconds(long nanoTime) {
        return (float) (nanoTime / 1000000000.0);
    }
}
