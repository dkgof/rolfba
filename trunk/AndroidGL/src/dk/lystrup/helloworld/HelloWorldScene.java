/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.helloworld;

import dk.lystrup.lagl.AbstractScene;
import dk.lystrup.lagl.actuators.Actuator;
import dk.lystrup.lagl.actuators.OrientationSensorActuator;
import dk.lystrup.lagl.nodes.CameraNode;
import dk.lystrup.lagl.nodes.Node;
import dk.lystrup.lagl.nodes.TextureQuad;
import dk.lystrup.lagl.textures.StaticTexture;
import dk.lystrup.lagl.textures.Texture;

/**
 *
 * @author Rolf
 */
public class HelloWorldScene extends AbstractScene {

    @Override
    protected void customInit() {

        CameraNode camera = new CameraNode();
        camera.setPosition(0,0,5);
        root.attachNode(camera);

        Texture floraTex = new StaticTexture(R.drawable.flora);

        Node model = new TextureQuad();
        model.setTexture(floraTex);
        model.setPosition(0,0,0);
        model.setScale(1f);
        
        Actuator act = new OrientationSensorActuator();
        model.setActuator(act);

        root.attachNode(model);
    }

}
