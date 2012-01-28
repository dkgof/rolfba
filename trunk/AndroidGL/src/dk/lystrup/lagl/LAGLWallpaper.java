/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 *
 * @author Rolf
 */
public class LAGLWallpaper extends WallpaperService {

    private final Scene scene;
    private final int fps;

    public LAGLWallpaper(Scene s, int fps) {
        this.scene = s;
        this.fps = fps;
    }

    @Override
    public Engine onCreateEngine() {
        return new LAGLEngine();
    }

    private class LAGLEngine extends Engine {

        private final LAGLRenderer renderer;
        private final Handler handler = new Handler();
        private Runnable drawFrameRunnable;
        private boolean visible;

        public LAGLEngine() {
            renderer = new LAGLRenderer(scene);

            drawFrameRunnable = new Runnable() {
                public void run() {
                    renderer.drawFrame();
                    handler.removeCallbacks(drawFrameRunnable);
                    if (visible) {
                        handler.postDelayed(drawFrameRunnable, 1000 / fps);
                    }
                }
            };
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            drawFrameRunnable.run();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            drawFrameRunnable.run();
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
            handler.removeCallbacks(drawFrameRunnable);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            this.visible = visible;
            
            if(visible) {
                drawFrameRunnable.run();
            } else {
                handler.removeCallbacks(drawFrameRunnable);
            }
        }
    }
}
