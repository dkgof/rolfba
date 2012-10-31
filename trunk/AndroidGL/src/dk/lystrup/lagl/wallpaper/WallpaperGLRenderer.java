/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.wallpaper;

import android.content.Context;
import dk.lystrup.lagl.Display;
import dk.lystrup.lagl.LAGLRenderer;
import dk.lystrup.lagl.Scene;
import dk.lystrup.lagl.wallpaper.GLWallpaperService;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public class WallpaperGLRenderer implements GLWallpaperService.Renderer {

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

    public void onDrawFrame(GL10 unused) {
        laglRenderer.drawFrame();
    }

    public void setContext(Context ctx) {
        Display.singleton().setContext(ctx);
    }
    
}
