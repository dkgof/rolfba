/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl;

import static android.opengl.GLES10.*;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLRenderer is the Core behind the AndroidGL system. It sets up the OpenGL context
 * and delegates the rendering work to a Scene object
 * @author Rolf
 */
public class GLRenderer implements Renderer {

    private final Timer timer;

    private final Scene renderScene;
    
    private boolean paused;
    
    public GLRenderer(Scene renderScene) {
        this.renderScene = renderScene;
        
        timer = new Timer();
        
        paused = false;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig glConfig) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // Enable Smooth Shading, default not really needed.
        glShadeModel(GL_SMOOTH);
        // Depth buffer setup.
        glClearDepthf(1.0f);
        // Enables depth testing.
        glEnable(GL_DEPTH_TEST);
        // The type of depth testing to do.
        glDepthFunc(GL_LEQUAL);
        // Really nice perspective calculations.
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        //Setup front face direction to counter clockwise
        glFrontFace(GL_CCW);
        
        renderScene.init();
        
        timer.start();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        glViewport(0, 0, width, height);

        Display.singleton().setResolution(width, height);
    }

    private int count = 0;
    private float delta;

    public void onDrawFrame(GL10 gl) {
        if(!paused) {
            //Get delta time in seconds since last frame
            delta = timer.getDelta();

            //Clear the screen color and depth buffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Reset the modelview matrix
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

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
            renderScene.render(gl);
        }
    }
    
    public void setPaused(boolean p) {
        paused = p;
    }
}
