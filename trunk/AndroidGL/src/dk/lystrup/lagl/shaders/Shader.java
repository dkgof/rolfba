/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders;

/**
 *
 * @author Rolf Bagge
 */
public interface Shader {
    public void bind();

    public void unbind();
    
    public int getVertexAttrib();
    public int getTexCoordAttrib();
}
