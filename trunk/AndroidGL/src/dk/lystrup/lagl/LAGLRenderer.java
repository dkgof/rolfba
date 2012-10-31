/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.opengl.GLES20;
import android.util.Log;

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

    public void surfaceCreated() {
        // Set the background color to black ( rgba ).
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);    // The type of depth testing to do
        
        LAGLUtil.checkGlError("Init");
        
        renderScene.init();
        
        timer.start();
    }

    public void surfaceChanged(int width, int height) {
        // Sets the current view port to the new size.
        GLES20.glViewport(0, 0, width, height);
        LAGLUtil.checkGlError("glViewport");

        Display.singleton().setResolution(width, height);
    }

    private float frameTime;
    
    public void drawFrame() {
        if(!paused) {
            //Get delta time in seconds since last frame
            delta = timer.getDelta();

            //If we see more than 10sec / frame, guess we are during loading and reset
            if(delta > 10) {
                delta = 0;
            }
            
            frameTime += delta;
            
            //Clear the screen color and depth buffer
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            LAGLUtil.checkGlError("glClear");
            
            // Reset the model matrix
            LAGLMatrix.singleton().resetToIdentity(LAGLMatrix.MatrixType.MODEL);

            //Debug framerate to logcat
            if(frameTime > 1) {
                float fps = count / frameTime;
                Log.i("LAGL", "Framerate: "+fps);
                count = 0;
                frameTime = 0;
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
        
        if(!paused) {
            //We are being unpaused, reset timer
            timer.reset();
        }
    }
}
