/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public class GLRenderer implements GLSurfaceView.Renderer {

    private LAGLRenderer laglRenderer;
    
    public GLRenderer(Scene s) {
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
    
}
