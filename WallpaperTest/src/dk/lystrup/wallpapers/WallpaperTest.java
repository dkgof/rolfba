package dk.lystrup.wallpapers;

import android.util.Log;
import dk.lystrup.lagl.wallpaper.GLWallpaperService;
import dk.lystrup.lagl.wallpaper.WallpaperGLRenderer;

public class WallpaperTest extends GLWallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }
 
    private class MyEngine extends GLWallpaperService.GLEngine {

        public MyEngine() {
            Log.i("LAGL", "Creating WallPaper Engine");
            WallpaperGLRenderer renderer = new WallpaperGLRenderer(new WallpaperScene());
            renderer.setContext(getBaseContext());
            renderer.setFps(5);
            setRenderer(renderer);
        }
    }
}