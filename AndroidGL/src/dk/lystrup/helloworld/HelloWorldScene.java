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
import dk.lystrup.androidgl.textures.StaticTexture;
import dk.lystrup.androidgl.textures.Texture;

/**
 *
 * @author Rolf
 */
public class HelloWorldScene extends AbstractScene {

    private float vertices[] = {
        -0.5f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.5f, 0.5f, 0.0f,};

    private float textures[] = {
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 0.0f};

    private short[] indices = {0, 1, 2, 0, 2, 3};

    @Override
    protected void customInit() {
        ModelData data = new ModelData();
        data.setIndices(indices);
        data.setVertices(vertices);
        data.setTexturecoords(textures);

        CameraNode camera = new CameraNode();
        camera.setPosition(0,0,5);
        root.attachNode(camera);

        ModelNode model = new ModelNode(data);

        Texture floraTex = new StaticTexture(R.drawable.flora);

        Actuator act = new ConstantRotationActuator(new AxisAngle(90, Vector3.UnitX));
        model.setActuator(act);
        model.setTexture(floraTex);
        model.setPosition(-1, 0, 0);

        ModelNode model2 = new ModelNode(data);
        model2.setTexture(floraTex);
        model2.setPosition(1,0,0);
        Actuator act2 = new ConstantRotationActuator(new AxisAngle(45, Vector3.UnitY));
        model2.setActuator(act2);

        root.attachNode(model);
        model.attachNode(model2);
    }

}
