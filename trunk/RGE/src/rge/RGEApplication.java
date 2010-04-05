package rge;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

/**
 * RGEApplication is the top most part of the engine. Scenes are added to the app
 * and can then be rendered.
 * @author Rolf
 */
public class RGEApplication {
    //The scenes of this application
    private List<Scene> scenes;

    //The currently active scene
    private Scene currentScene;

    private boolean firstFrame;

    /**
     * Create a new RGEAppliation
     */
    public RGEApplication() {
        scenes = new ArrayList<Scene>();
        firstFrame = true;
    }

    private void renderApplication() {
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

    /**
     * Add a scene to this app
     * @param s the scene to add
     */
    public void addScene(Scene s) {
        scenes.add(s);
    }

    /**
     * Render this app
     */
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
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }
}
