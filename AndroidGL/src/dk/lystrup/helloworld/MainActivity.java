/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.helloworld;

import dk.lystrup.androidgl.LAGLActivity;
import dk.lystrup.androidgl.Scene;

/**
 *
 * @author Rolf
 */
public class MainActivity extends LAGLActivity {

    @Override
    protected Scene getScene() {
        return new HelloWorldScene();
    }
}
