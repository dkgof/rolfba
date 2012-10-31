/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.opengl.GLES20;
import android.util.Log;

/**
 *
 * @author Rolf Bagge
 */
public class LAGLUtil {
    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("LAGL", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
}
