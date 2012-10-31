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
public class BooleanParameter extends ShaderParameter<Boolean> {

    private boolean value;
    
    public BooleanParameter(String parameterName, boolean v) {
        super(parameterName);
        this.value = v;
    }
    
    @Override
    protected void setParameter() {
        GLES20.glUniform1i(parameterLocation, value?1:0);
    }
    
    public void setValue(boolean v) {
        value = v;
    }
    
    public Boolean getValue() {
        return value;
    }

    @Override
    protected boolean isEqual(Boolean lastValue, Boolean currentValue) {
        return lastValue.equals(currentValue);
    }

    @Override
    protected Boolean doClone(Boolean currentValue) {
        return currentValue;
    }
}
