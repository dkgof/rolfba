/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl;

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
    
    public GLRenderer(Scene renderScene) {
        this.renderScene = renderScene;
        
        timer = new Timer();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig glConfig) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        renderScene.init();
        
        timer.start();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);

        Display.singleton().setResolution(width, height);
    }

    private int count = 0;
    private float delta;

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();

        delta = timer.getDelta();

        if(count % 100 == 0) {
            float fps = 1.0f / delta;
            Log.i("AndroidGL", "Framerate: "+fps);
        }

        count++;

        renderScene.update(delta);

        gl.glFrontFace(GL10.GL_CCW);

        renderScene.render(gl);
    }
}
