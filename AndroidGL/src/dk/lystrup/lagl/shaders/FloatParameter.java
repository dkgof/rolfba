/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders;

import android.opengl.GLES20;
import android.util.Log;

/**
 *
 * @author cavi
 */
public class FloatParameter extends ShaderParameter<Float> {

    private float value;
    
    public FloatParameter(String parameterName, float v) {
        super(parameterName);
        this.value = v;
    }
    
    @Override
    protected void setParameter() {
        GLES20.glUniform1f(parameterLocation, value);
    }
    
    public void setValue(float v) {
        value = v;
    }
    
    public Float getValue() {
        return value;
    }

    @Override
    protected boolean isEqual(Float lastValue, Float currentValue) {
        return lastValue.equals(currentValue);
    }

    @Override
    protected Float doClone(Float currentValue) {
        return currentValue;
    }
}
