/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.helloworld;

import dk.lystrup.androidgl.GLRenderer;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import dk.lystrup.androidgl.Display;

/**
 *
 * @author Rolf
 */
public class MainActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        GLSurfaceView glView = new GLSurfaceView(this);

        glView.setRenderer(new GLRenderer(new HelloWorldScene()));

        Display.singleton().setContext(this);

        setContentView(glView);
    }
}
