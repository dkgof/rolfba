/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import dk.lystrup.lagl.math.Quaternion;

/**
 * This class handles everything with sensors and sensor events
 * @author Rolf
 */
public class SensorCore implements SensorEventListener {
    private static SensorCore singleton;
    
    private final SensorManager sensorManager;
    private final Sensor rotationVector;

    private final float[] quaternionArray;
    private Quaternion currentRotation;
    
    private SensorCore(Context context) {
        //Retrieve the sensor manager
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        
        //Retrieve sensors
        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        
        quaternionArray = new float[4];
        currentRotation = new Quaternion();
    }

    /**
     * Get the singleton of this class, you must have called create() first
     * @return the singleton of this class
     */
    public static SensorCore singleton() {
        if(singleton == null) {
            Log.wtf("SensorCore", "SensorCore accessed before creation!");
        }
        return singleton;
    }

    /**
     * Creates a new SensorCore
     * @param context the context of your application
     */
    public static void create(Context context) {
        if(singleton == null) {
            singleton = new SensorCore(context);
        } else {
            Log.i("SensorCore", "SensorCore already created!");
        }
    }

    public void pause() {
        sensorManager.unregisterListener(this, rotationVector);
    }

    public void resume() {
        sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onSensorChanged(SensorEvent event) {
        SensorManager.getQuaternionFromVector(quaternionArray, event.values);
        currentRotation = new Quaternion(quaternionArray[0], quaternionArray[1], quaternionArray[2], quaternionArray[3]);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i("SensorCore", "Accuracy of sensor "+sensor+" changed to "+accuracy);
    }

    /**
     * Get the current rotation of the device as a quaternion
     * @return the currentRotation
     */
    public Quaternion getCurrentRotation() {
        return currentRotation;
    }
}
