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
public abstract class ShaderParameter<T> {
    
    private String parameterName;
    protected int parameterLocation;
    private T lastValue;
    
    public ShaderParameter(String parameterName) {
        this.parameterName = parameterName;
        lastValue = null;
    }
    
    public void findParameterLocation(int shaderProgram) {
        parameterLocation = GLES20.glGetUniformLocation(shaderProgram, parameterName);
    }
    
    public void updateParameter() {
        T currentValue = getValue();
        
        if(lastValue == null || !isEqual(lastValue, currentValue)) {
            lastValue = doClone(currentValue);
            setParameter();
        }
    }
    
    protected abstract boolean isEqual(T lastValue, T currentValue);

    protected abstract void setParameter();
    
    public abstract T getValue();

    public void reset() {
        lastValue = null;
    }

    protected abstract T doClone(T currentValue);
}
