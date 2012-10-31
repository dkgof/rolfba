/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders.glsl;

import dk.lystrup.lagl.shaders.FloatArrayParameter;
import dk.lystrup.lagl.shaders.FloatParameter;
import dk.lystrup.lagl.shaders.IntParameter;

/**
 *
 * @author Rolf Bagge
 */
public class FullscreenMandelBrot extends GLSLShader {

    /*
    uniform vec2 Pan = vec2(0.5, 0);
    uniform float Zoom = 1.0;
    uniform float Aspect = 2.0;
    uniform vec3 ColorScale = vec3(4, 5, 6);
    uniform int Iterations = 8;
    */
    
    private IntParameter iterations;
    private FloatArrayParameter pan;
    private FloatArrayParameter colors;
    private FloatParameter zoom;
    private FloatParameter aspect;
    
    public FullscreenMandelBrot() {
        //parameters.add(new IntParameter("uTexture", 0));
        
        iterations = new IntParameter("Iterations", 32);
        pan = new FloatArrayParameter("Pan", new float[]{0, 0});
        colors = new FloatArrayParameter("ColorScale", new float[]{4, 5, 6});
        zoom = new FloatParameter("Zoom", 5f);
        aspect = new FloatParameter("Aspect", 1f);
        
        parameters.add(iterations);
        parameters.add(pan);
        parameters.add(colors);
        parameters.add(zoom);
        parameters.add(aspect);
    }
    
    @Override
    protected String getVertexShader() {
        return "fullscreen.vert";
    }

    @Override
    protected String getFragmentShader() {
        return "mandelbrot2.frag";
    }
    
    public void setIterations(int iterations) {
        this.iterations.setValue(iterations);
    }
    
    public void setZoom(float zoom) {
        this.zoom.setValue(zoom);
    }
    
    public void setAspect(float aspect) {
        this.aspect.setValue(aspect);
    }

    public void setPan(float[] pan) {
        this.pan.setValue(pan);
    }
    
    public void setColors(float[] colors) {
        this.colors.setValue(colors);
    }

    public float getZoom() {
        return zoom.getValue();
    }

    public float[] getPan() {
        return pan.getValue();
    }
}
