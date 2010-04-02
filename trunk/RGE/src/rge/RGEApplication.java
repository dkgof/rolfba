package rge;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

/**
 * 
 * @author Rolf
 */
public class RGEApplication {
    //The scenes of this application
    private List<Scene> scenes;

    //The currently active scene
    private Scene currentScene;

    private boolean firstFrame;

    public RGEApplication() {
        scenes = new ArrayList<Scene>();
        firstFrame = true;
    }

    public void renderApplication() {
        currentScene.renderScene();
    }

    /**
     * Setup all the scenes of this application
     */
    public void setupScenes() {
        for(Scene s : scenes) {
            s.setupScene();
        }
    }

    public void addScene(Scene s) {
        scenes.add(s);
    }

    public void display() {
        if(firstFrame) {
            initApp();
            setupScenes();
            firstFrame = false;
        }

        renderApplication();
    }

    private void initApp() {
        if(scenes.size() == 0) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "At least 1 scene must be added to the application");
        }
        currentScene = scenes.get(0);

        GL11.glClearColor(0,0,0,0);
    }
}
