/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.wallpapers;

import android.util.Log;
import dk.lystrup.lagl.actuators.Actuator;
import dk.lystrup.lagl.math.Vector3;
import dk.lystrup.lagl.nodes.Node;
import dk.lystrup.lagl.shaders.Shader;
import dk.lystrup.lagl.shaders.glsl.FullscreenMandelBrot;

/**
 *
 * @author Rolf Bagge
 */
 public class MandelbrotActuator implements Actuator {
    
     private Vector3 dir = new Vector3(0.1f,0,0);
     
     public void update(Node node, float delta) {
        Shader s = node.getShader();

        if(s instanceof FullscreenMandelBrot) {
            FullscreenMandelBrot mandelbrot = (FullscreenMandelBrot) s;
            
            float[] pan = mandelbrot.getPan();
            
            Vector3 currentPan = new Vector3(pan[0], pan[1], 0);
            
            currentPan = currentPan.add(dir.scale(delta));
            
            if(currentPan.getX() >= 1) {
                currentPan = new Vector3(1, currentPan.getY(), 0);
                dir = new Vector3(-0.1f,-0.1f,0);
            }
            
            if(currentPan.getX() <= 0) {
                currentPan = new Vector3(0, -1, 0);
                dir = new Vector3(0.1f,0.1f,0);
            }
            
            mandelbrot.setPan(new float[]{currentPan.getX(), currentPan.getY()});
        }
    }
}
