/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders;

import android.opengl.GLES20;
import android.util.Log;
import dk.lystrup.lagl.LAGLMatrix;
import java.util.Arrays;

/**
 *
 * @author Rolf Bagge
 */
public class MVPMatrixParameter extends ShaderParameter<float[]> {

    public MVPMatrixParameter() {
        super("uMVPMatrix");
    }
    
    @Override
    protected void setParameter() {
        GLES20.glUniformMatrix4fv(parameterLocation, 1, false, getValue(), 0);
    }

    @Override
    public float[] getValue() {
        return LAGLMatrix.singleton().getMVP();
    }

    @Override
    protected boolean isEqual(float[] lastValue, float[] currentValue) {
        return Arrays.equals(lastValue, currentValue);
    }

    @Override
    protected float[] doClone(float[] currentValue) {
        return Arrays.copyOf(currentValue, currentValue.length);
    }
    
}
