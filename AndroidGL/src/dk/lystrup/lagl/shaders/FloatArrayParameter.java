/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders;

import android.opengl.GLES20;
import java.util.Arrays;

/**
 *
 * @author cavi
 */
public class FloatArrayParameter extends ShaderParameter<float[]> {

    private float[] value;
    private int arrayCount;
    
    public FloatArrayParameter(String parameterName, float[] v) {
        this(parameterName, v, 1);
    }

    public FloatArrayParameter(String parameterName, float[] v, int arrayCount) {
        super(parameterName);
        this.value = v;
        this.arrayCount = arrayCount;
    }
    
    @Override
    protected void setParameter() {
        int length = 0;
        if(arrayCount > 0) {
            length = value.length / arrayCount;
        }
        switch(length) {
            case 1:
                GLES20.glUniform1fv(parameterLocation, arrayCount, value, 0);
                break;
            case 2:
                GLES20.glUniform2fv(parameterLocation, arrayCount, value, 0);
                break;
            case 3:
                GLES20.glUniform3fv(parameterLocation, arrayCount, value, 0);
                break;
            case 4:
                GLES20.glUniform4fv(parameterLocation, arrayCount, value, 0);
                break;
            case 0:
                break;
            default:
                System.out.println("ArrayCount: "+arrayCount);
                System.out.println("ArraySize/Count: "+(value.length/arrayCount));
                System.out.println("Array: "+Arrays.toString(value));
                System.out.println("Unsupported float array, size/count combination!");
                break;
        }
    }
    
    public void setValue(float[] v) {
        this.setValue(v, 1);
    }

    public void setValue(float[] v, int arrayCount) {
        value = v;
        this.arrayCount = arrayCount;
    }
    
    public float[] getValue() {
        return value;
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
