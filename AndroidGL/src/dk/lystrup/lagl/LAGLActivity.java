/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import dk.lystrup.lagl.sensors.SensorCore;

/**
 *
 * @author Rolf
 */
public abstract class LAGLActivity extends Activity {
    private LAGLSurfaceView glView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        
        Log.i("AndroidGL", "Creating GLActivity");

        SensorCore.create(this);

        glView = new LAGLSurfaceView(this, this.getScene());

        Display.singleton().setContext(this);

        setContentView(glView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorCore.singleton().pause();
        glView.onPause();
        Log.i("AndroidGL", "Paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorCore.singleton().resume();
        glView.onResume();
        Log.i("AndroidGL", "Resumed");
    }

    protected abstract Scene getScene();
}
