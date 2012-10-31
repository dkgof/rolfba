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
public class IntArrayParameter extends ShaderParameter<int[]> {

    private int[] value;
    private int arrayCount;
    
    public IntArrayParameter(String parameterName, int[] v) {
        this(parameterName, v, 1);
    }

    public IntArrayParameter(String parameterName, int[] v, int arrayCount) {
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
                GLES20.glUniform1iv(parameterLocation, arrayCount, value, 0);
                break;
            case 2:
                GLES20.glUniform2iv(parameterLocation, arrayCount, value, 0);
                break;
            case 3:
                GLES20.glUniform3iv(parameterLocation, arrayCount, value, 0);
                break;
            case 4:
                GLES20.glUniform4iv(parameterLocation, arrayCount, value, 0);
                break;
            case 0:
                break;
            default:
                System.out.println("ArrayCount: "+arrayCount);
                System.out.println("ArraySize/Count: "+(value.length/arrayCount));
                System.out.println("Array: "+Arrays.toString(value));
                System.out.println("Unsupported int array, size/count combination!");
                break;
        }
    }
    
    public void setValue(int[] v) {
        this.setValue(v, 1);
    }

    public void setValue(int[] v, int arrayCount) {
        value = v;
        this.arrayCount = arrayCount;
    }
    
    public int[] getValue() {
        return value;
    }

    @Override
    protected boolean isEqual(int[] lastValue, int[] currentValue) {
        return Arrays.equals(lastValue, currentValue);
    }

    @Override
    protected int[] doClone(int[] currentValue) {
        return Arrays.copyOf(currentValue, currentValue.length);
    }
}
