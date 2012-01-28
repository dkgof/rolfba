/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.opengl.Matrix;
import dk.lystrup.lagl.math.AxisAngle;
import dk.lystrup.lagl.math.Quaternion;
import dk.lystrup.lagl.math.Vector3;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Rolf
 */
public class LAGLMatrix {
    private static LAGLMatrix singleton;
    
    public static LAGLMatrix singleton() {
        if(singleton == null) {
            singleton = new LAGLMatrix();
        }
        
        return singleton;
    }
    
    public enum MatrixType {
        MODEL,
        VIEW,
        PROJECTION,
    }
    
    private Map<MatrixType, LinkedList<float[]>> matrixStack;
    private float[] mvpMatrix;
    
    public LAGLMatrix() {
        matrixStack = new EnumMap<MatrixType, LinkedList<float[]>>(MatrixType.class);
        
        mvpMatrix = new float[16];
        
        matrixStack.put(MatrixType.MODEL, new LinkedList<float[]>());
        matrixStack.put(MatrixType.VIEW, new LinkedList<float[]>());
        matrixStack.put(MatrixType.PROJECTION, new LinkedList<float[]>());
        
        matrixStack.get(MatrixType.MODEL).add(new float[16]);
        matrixStack.get(MatrixType.VIEW).add(new float[16]);
        matrixStack.get(MatrixType.PROJECTION).add(new float[16]);
    }
    
    public void pushMatrix(MatrixType type) {
        float[] matrix = matrixStack.get(type).getFirst();
        
        matrixStack.get(type).addFirst(Arrays.copyOf(matrix, matrix.length));
    }
    
    public void popMatrix(MatrixType type) {
        matrixStack.get(type).removeFirst();
    }

    public float[] getMVP() {
        float[] modelMatrix = matrixStack.get(MatrixType.MODEL).getFirst();
        float[] viewMatrix = matrixStack.get(MatrixType.VIEW).getFirst();
        float[] projMatrix = matrixStack.get(MatrixType.PROJECTION).getFirst();
        
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projMatrix, 0, mvpMatrix, 0);
        
        return mvpMatrix;
    }
    
    public void resetToIdentity(MatrixType type) {
        Matrix.setIdentityM(matrixStack.get(type).getFirst(), 0);
    }
    
    public void rotate(MatrixType type, Quaternion rotation) {
        AxisAngle axisAngle = rotation.toAxisAngle();
        Vector3 axis = axisAngle.getAxis();
        
        Matrix.rotateM(matrixStack.get(type).getFirst(), 0, axisAngle.getAngle(), axis.getX(), axis.getY(), axis.getZ());
    }
    
    public void translate(MatrixType type, Vector3 direction) {
        Matrix.translateM(matrixStack.get(type).getFirst(), 0, direction.getX(), direction.getY(), direction.getZ());
    }

    public void scale(MatrixType type, Vector3 scalar) {
        Matrix.scaleM(matrixStack.get(type).getFirst(), 0, scalar.getX(), scalar.getY(), scalar.getZ());
    }
    
    public float[] getMatrix(MatrixType type) {
        return matrixStack.get(type).getFirst();
    }
}
