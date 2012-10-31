/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.wallpapers;

import dk.lystrup.lagl.AbstractScene;
import dk.lystrup.lagl.nodes.CameraNode;
import dk.lystrup.lagl.nodes.FullscreenQuad;
import dk.lystrup.lagl.nodes.Node;
import dk.lystrup.lagl.shaders.glsl.FullscreenMandelBrot;
import dk.lystrup.lagl.textures.StaticTexture;
import dk.lystrup.lagl.textures.Texture;

/**
 *
 * @author Rolf
 */
public class WallpaperScene extends AbstractScene {

    @Override
    protected void customInit() {
        CameraNode camera = new CameraNode();
        camera.setPosition(0,0,5);
        root.attachNode(camera);

        Texture colorTex = new StaticTexture(dk.lystrup.helloworld.R.drawable.colors);

        Node model = new FullscreenQuad();
        //model.setTexture(colorTex);
        model.setPosition(0,0,0);
        model.setScale(1f);
        
        FullscreenMandelBrot mandelbrot = new FullscreenMandelBrot();
        
        model.setShader(mandelbrot);
        model.setActuator(new MandelbrotActuator());
        
        root.attachNode(model);
    }
    
}
