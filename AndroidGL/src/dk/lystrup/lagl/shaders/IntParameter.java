/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders;

import android.opengl.GLES20;

/**
 *
 * @author cavi
 */
public class IntParameter extends ShaderParameter<Integer> {

    private int value;
    
    public IntParameter(String parameterName, int v) {
        super(parameterName);
        this.value = v;
    }
    
    @Override
    protected void setParameter() {
        GLES20.glUniform1i(parameterLocation, value);
    }
    
    public void setValue(int v) {
        value = v;
    }
    
    public Integer getValue() {
        return value;
    }

    @Override
    protected boolean isEqual(Integer lastValue, Integer currentValue) {
        return lastValue.equals(currentValue);
    }

    @Override
    protected Integer doClone(Integer currentValue) {
        return currentValue;
    }
}
