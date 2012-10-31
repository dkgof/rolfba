/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.wallpaper;

import android.content.Context;
import android.util.Log;
import dk.lystrup.lagl.Display;
import dk.lystrup.lagl.LAGLRenderer;
import dk.lystrup.lagl.Scene;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public class WallpaperGLRenderer implements GLWallpaperService.Renderer {

    private float fps;
    private LAGLRenderer laglRenderer;
    
    public WallpaperGLRenderer(Scene s) {
        laglRenderer = new LAGLRenderer(s);
    }
    
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        laglRenderer.surfaceCreated();
    }

    public void onSurfaceChanged(GL10 unused, int w, int h) {
        laglRenderer.surfaceChanged(w, h);
    }

    private long lastFrame = -1;
    
    public void onDrawFrame(GL10 unused) {
        long now = System.currentTimeMillis();

        if(lastFrame != -1) {
            long diff = now - lastFrame;
            
            long targetFrameTime = (long)(1000.0 / fps);
            
            long waitTime = Math.max(targetFrameTime - diff, 0);
            
            if(waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException ex) {
                    Log.e("LAGL", "Error sleeping in frame onDrawFrame");
                }
            }
        }
        
        laglRenderer.drawFrame();
        
        lastFrame = System.currentTimeMillis();
    }

    public void setContext(Context ctx) {
        Display.singleton().setContext(ctx);
    }
    
    public void setFps(float fps) {
        this.fps = fps;
    }
}
