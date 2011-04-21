/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.helloworld;

import dk.lystrup.androidgl.AbstractScene;
import dk.lystrup.androidgl.actuators.Actuator;
import dk.lystrup.androidgl.actuators.OrientationSensorActuator;
import dk.lystrup.androidgl.importers.ModelImportFactory;
import dk.lystrup.androidgl.nodes.CameraNode;
import dk.lystrup.androidgl.nodes.ModelNode;
import dk.lystrup.androidgl.nodes.Node;
import dk.lystrup.androidgl.textures.StaticTexture;
import dk.lystrup.androidgl.textures.Texture;

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

        Node model = new ModelNode(ModelImportFactory.importModel("dk/lystrup/helloworld/models/total.obj", "Custom"));
        model.setTexture(floraTex);
        model.setPosition(0,0,0);
        model.setScale(0.5f);
        
        Actuator act = new OrientationSensorActuator();
        model.setActuator(act);

        root.attachNode(model);
    }

}
