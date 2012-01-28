/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.lystrup.lagl;

import android.content.Context;

/**
 * Display models the core features of the Android display for usage
 * in other parts of AndroidGL
 * @author Rolf
 */
public class Display {
    private static Display singleton;

    private int width;
    private int height;

    private Context context;
    
    public static Display singleton() {
        if(singleton == null) {
            singleton = new Display();
        }

        return singleton;
    }

    public void setResolution(int w, int h) {
        this.width = w;
        this.height = h;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(Context context) {
        this.context = context;
    }
}
