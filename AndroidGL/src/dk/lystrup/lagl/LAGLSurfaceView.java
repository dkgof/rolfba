/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

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
        
        GLRenderer renderer = new GLRenderer(scene);
        
        setRenderer(renderer);
    }
}
