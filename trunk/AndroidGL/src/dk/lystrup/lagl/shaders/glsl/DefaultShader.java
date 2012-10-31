/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders.glsl;

import dk.lystrup.lagl.shaders.IntParameter;

/**
 *
 * @author Rolf Bagge
 */
public class DefaultShader extends GLSLShader {

    public DefaultShader() {
        parameters.add(new IntParameter("vTextureCoord", 0));
    }
    
    @Override
    protected String getVertexShader() {
        return "default.vert";
    }

    @Override
    protected String getFragmentShader() {
        return "default.frag";
    }
    
}
