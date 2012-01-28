/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLRenderer is the Core behind the AndroidGL system. It sets up the OpenGL context
 * and delegates the rendering work to a Scene object
 * @author Rolf
 */
public class LAGLRenderer {

    private final Timer timer;

    private final Scene renderScene;
    
    private boolean paused;

    private int count = 0;
    
    private float delta;
    
    public LAGLRenderer(Scene renderScene) {
        this.renderScene = renderScene;
        
        timer = new Timer();
        
        paused = false;
    }

    public void surfaceCreated(EGLConfig glConfig) {
        // Set the background color to black ( rgba ).
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        renderScene.init();
        
        timer.start();
    }

    public void surfaceChanged(int width, int height) {
        // Sets the current view port to the new size.
        GLES20.glViewport(0, 0, width, height);

        Display.singleton().setResolution(width, height);
    }

    public void drawFrame() {
        if(!paused) {
            //Get delta time in seconds since last frame
            delta = timer.getDelta();

            //Clear the screen color and depth buffer
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            // Reset the model matrix
            LAGLMatrix.singleton().resetToIdentity(LAGLMatrix.MatrixType.MODEL);

            //Debug framerate to logcat
            if(count == 100) {
                float fps = 1.0f / delta;
                Log.i("AndroidGL", "Framerate: "+fps);
                count = 0;
            }
            count++;

            //Update scenegraph
            renderScene.update(delta);
            
            //Render scenegraph
            renderScene.render();
        }
    }
    
    public void setPaused(boolean p) {
        paused = p;
    }
}
