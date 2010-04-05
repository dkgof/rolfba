
package rge.animators;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rolf
 */
public abstract class Animator implements Runnable {
    private double fps;
    private boolean paused;

    private Thread thread;

    public Animator(double fps) {
        this.fps = fps;
    }

    public void start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        double start, diff;

        double sleepTime = Math.round(1000.0 / fps);

        while(true) {
            while(paused) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Error waiting", ex);
                }
            }

            start = System.nanoTime();

            animate();

            diff = System.nanoTime() - start;

            try {
                Thread.sleep(Math.max(0,  Math.round(sleepTime - (diff / 1000000.0))));
            }
            catch(Exception ex) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Error sleeping", ex);
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        this.notifyAll();
    }

    protected abstract void animate();
}
