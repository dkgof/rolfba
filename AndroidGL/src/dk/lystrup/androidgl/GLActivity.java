/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.androidgl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

/**
 *
 * @author Rolf
 */
public abstract class GLActivity extends Activity {
    private GLRenderer renderer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        Log.i("AndroidGL", "Creating GLActivity");

        SensorCore.create(this);

        GLSurfaceView glView = new GLSurfaceView(this);

        renderer = new GLRenderer(getScene());
        
        glView.setRenderer(renderer);

        Display.singleton().setContext(this);

        setContentView(glView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorCore.singleton().pause();
        renderer.setPaused(true);
        Log.i("AndroidGL", "Paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorCore.singleton().resume();
        renderer.setPaused(false);
        Log.i("AndroidGL", "Resumed");
    }

    protected abstract Scene getScene();
}
