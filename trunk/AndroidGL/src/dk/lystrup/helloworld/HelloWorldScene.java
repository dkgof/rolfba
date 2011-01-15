/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.helloworld;

import dk.lystrup.androidgl.AbstractScene;
import dk.lystrup.androidgl.actuators.Actuator;
import dk.lystrup.androidgl.actuators.ConstantRotationActuator;
import dk.lystrup.androidgl.math.AxisAngle;
import dk.lystrup.androidgl.math.Vector3;
import dk.lystrup.androidgl.nodes.CameraNode;
import dk.lystrup.androidgl.nodes.ModelData;
import dk.lystrup.androidgl.nodes.ModelNode;
import dk.lystrup.androidgl.nodes.Node;
import dk.lystrup.androidgl.nodes.TextureQuad;
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

        Node model = new TextureQuad();
        
        Texture floraTex = new StaticTexture(R.drawable.flora);

        Actuator act = new ConstantRotationActuator(new AxisAngle(90, Vector3.UnitX));
        model.setActuator(act);
        model.setTexture(floraTex);
        model.setPosition(-1, 0, 0);

        Node model2 = new TextureQuad();
        model2.setTexture(floraTex);
        model2.setPosition(1,0,0);
        Actuator act2 = new ConstantRotationActuator(new AxisAngle(45, Vector3.UnitY));
        model2.setActuator(act2);

        root.attachNode(model);
        model.attachNode(model2);
    }

}
