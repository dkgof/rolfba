/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 *
 * @author Rolf
 */
public class LAGLSurfaceView extends GLSurfaceView {
    public LAGLSurfaceView(Context context, Scene scene) {
        super(context);
        
        setEGLContextClientVersion(2);
        
        LAGLRenderer renderer = new LAGLRenderer(scene);
        
        setRenderer(renderer);
        
        Display.singleton().setRenderer(renderer);
    }
}
