/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl;

import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author Rolf
 */
public abstract class LAGLWallpaper extends WallpaperService {

    private static final Semaphore sEglSemaphore = new Semaphore(1);
    private final Scene scene;
    private final int fps;
    private SurfaceHolder mHolder;

    public LAGLWallpaper(Scene s, int fps) {
        this.scene = s;
        this.fps = fps;
    }

    @Override
    public Engine onCreateEngine() {
        return new LAGLEngine();
    }

    protected abstract void onTouchEvent(MotionEvent event);

    private class LAGLEngine extends Engine {

        private GLThread glThread;

        public LAGLEngine() {
            glThread = new GLThread(new LAGLRenderer(scene));
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            mHolder = surfaceHolder;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            //Send touch event to parent class
            LAGLWallpaper.this.onTouchEvent(event);

            super.onTouchEvent(event);
        }
    }

    private class EglHelper {

        public EglHelper() {
        }

        /**
         * Initialize EGL for a given configuration spec.
         *
         * @param configSpec
         */
        public void start(int[] configSpec) {
            /*
             * Get an EGL instance
             */
            mEgl = (EGL10) EGLContext.getEGL();

            /*
             * Get to the default display.
             */
            mEglDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

            /*
             * We can now initialize EGL for that display
             */
            int[] version = new int[2];
            mEgl.eglInitialize(mEglDisplay, version);

            EGLConfig[] configs = new EGLConfig[1];
            int[] num_config = new int[1];
            mEgl.eglChooseConfig(mEglDisplay, configSpec, configs, 1,
                    num_config);
            mEglConfig = configs[0];

            /*
             * Create an OpenGL ES context. This must be done only once, an
             * OpenGL context is a somewhat heavy object.
             */
            mEglContext = mEgl.eglCreateContext(mEglDisplay, mEglConfig,
                    EGL10.EGL_NO_CONTEXT, null);

            mEglSurface = null;
        }

        /*
         * Create and return an OpenGL surface
         */
        public GL createSurface(SurfaceHolder holder) {
            /*
             * The window size has changed, so we need to create a new surface.
             */
            if (mEglSurface != null) {

                /*
                 * Unbind and destroy the old EGL surface, if there is one.
                 */
                mEgl.eglMakeCurrent(mEglDisplay, EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                mEgl.eglDestroySurface(mEglDisplay, mEglSurface);
            }

            /*
             * Create an EGL surface we can render into.
             */
            mEglSurface = mEgl.eglCreateWindowSurface(mEglDisplay,
                    mEglConfig, holder, null);

            /*
             * Before we can issue GL commands, we need to make sure the context
             * is current and bound to a surface.
             */
            mEgl.eglMakeCurrent(mEglDisplay, mEglSurface, mEglSurface,
                    mEglContext);

            return mEglContext.getGL();
        }

        /**
         * Display the current render surface.
         *
         * @return false if the context has been lost.
         */
        public boolean swap() {
            mEgl.eglSwapBuffers(mEglDisplay, mEglSurface);

            /*
             * Always check for EGL_CONTEXT_LOST, which means the context and
             * all associated data were lost (For instance because the device
             * went to sleep). We need to sleep until we get a new surface.
             */
            return mEgl.eglGetError() != EGL11.EGL_CONTEXT_LOST;
        }

        public void finish() {
            if (mEglSurface != null) {
                mEgl.eglMakeCurrent(mEglDisplay, EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_CONTEXT);
                mEgl.eglDestroySurface(mEglDisplay, mEglSurface);
                mEglSurface = null;
            }
            if (mEglContext != null) {
                mEgl.eglDestroyContext(mEglDisplay, mEglContext);
                mEglContext = null;
            }
            if (mEglDisplay != null) {
                mEgl.eglTerminate(mEglDisplay);
                mEglDisplay = null;
            }
        }
        EGL10 mEgl;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
    }

    /**
     * A generic GL Thread. Takes care of initializing EGL and GL. Delegates to
     * a Renderer instance to do the actual drawing.
     *
     */
    class GLThread extends Thread {

        GLThread(LAGLRenderer renderer) {
            super();
            mDone = false;
            mWidth = 0;
            mHeight = 0;
            mRenderer = renderer;
            setName("GLThread");
        }

        @Override
        public void run() {
            /*
             * When the android framework launches a second instance of an
             * activity, the new instance's onCreate() method may be called
             * before the first instance returns from onDestroy().
             *
             * This semaphore ensures that only one instance at a time accesses
             * EGL.
             */
            try {
                try {
                    sEglSemaphore.acquire();
                } catch (InterruptedException e) {
                    return;
                }
                guardedRun();
            } catch (InterruptedException e) {
                // fall thru and exit normally
            } finally {
                sEglSemaphore.release();
            }
        }

        private void guardedRun() throws InterruptedException {
            mEglHelper = new EglHelper();
            /*
             * Specify a configuration for our opengl session and grab the first
             * configuration that matches is
             */
            int[] configSpec = {
                EGL10.EGL_NONE
            };
            mEglHelper.start(configSpec);

            GL10 gl = null;
            boolean tellRendererSurfaceCreated = true;
            boolean tellRendererSurfaceChanged = true;

            /*
             * This is our main activity thread's loop, we go until asked to
             * quit.
             */
            while (!mDone) {

                /*
                 * Update the asynchronous state (window size)
                 */
                int w, h;
                boolean changed;
                boolean needStart = false;
                synchronized (this) {
                    Runnable r;
                    while ((r = getEvent()) != null) {
                        r.run();
                    }
                    if (mPaused) {
                        mEglHelper.finish();
                        needStart = true;
                    }
                    if (needToWait()) {
                        while (needToWait()) {
                            wait();
                        }
                    }
                    if (mDone) {
                        break;
                    }
                    changed = mSizeChanged;
                    w = mWidth;
                    h = mHeight;
                    mSizeChanged = false;
                }
                if (needStart) {
                    mEglHelper.start(configSpec);
                    tellRendererSurfaceCreated = true;
                    changed = true;
                }
                if (changed) {
                    gl = (GL10) mEglHelper.createSurface(mHolder);
                    tellRendererSurfaceChanged = true;
                }
                if (tellRendererSurfaceCreated) {
                    mRenderer.surfaceCreated();
                    tellRendererSurfaceCreated = false;
                }
                if (tellRendererSurfaceChanged) {
                    mRenderer.surfaceChanged(w, h);
                    tellRendererSurfaceChanged = false;
                }
                if ((w > 0) && (h > 0)) {
                    /*
                     * draw a frame here
                     */
                    mRenderer.drawFrame();

                    /*
                     * Once we're done with GL, we need to call swapBuffers() to
                     * instruct the system to display the rendered frame
                     */
                    mEglHelper.swap();
                }
            }

            /*
             * clean-up everything...
             */
            mEglHelper.finish();
        }

        private boolean needToWait() {
            return (mPaused || (!mHasFocus) || (!mHasSurface) || mContextLost)
                    && (!mDone);
        }

        public void surfaceCreated() {
            synchronized (this) {
                mHasSurface = true;
                mContextLost = false;
                notify();
            }
        }

        public void surfaceDestroyed() {
            synchronized (this) {
                mHasSurface = false;
                notify();
            }
        }

        public void onPause() {
            synchronized (this) {
                mPaused = true;
            }
        }

        public void onResume() {
            synchronized (this) {
                mPaused = false;
                notify();
            }
        }

        public void onWindowFocusChanged(boolean hasFocus) {
            synchronized (this) {
                mHasFocus = hasFocus;
                if (mHasFocus == true) {
                    notify();
                }
            }
        }

        public void onWindowResize(int w, int h) {
            synchronized (this) {
                mWidth = w;
                mHeight = h;
                mSizeChanged = true;
            }
        }

        public void requestExitAndWait() {
            // don't call this from GLThread thread or it is a guaranteed
            // deadlock!
            synchronized (this) {
                mDone = true;
                notify();
            }
            try {
                join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        /**
         * Queue an "event" to be run on the GL rendering thread.
         *
         * @param r the runnable to be run on the GL rendering thread.
         */
        public void queueEvent(Runnable r) {
            synchronized (this) {
                mEventQueue.add(r);
            }
        }

        private Runnable getEvent() {
            synchronized (this) {
                if (mEventQueue.size() > 0) {
                    return mEventQueue.remove(0);
                }

            }
            return null;
        }
        private boolean mDone;
        private boolean mPaused;
        private boolean mHasFocus;
        private boolean mHasSurface;
        private boolean mContextLost;
        private boolean mSizeChanged;
        private int mWidth;
        private int mHeight;
        private LAGLRenderer mRenderer;
        private ArrayList<Runnable> mEventQueue = new ArrayList();
        private EglHelper mEglHelper;
    }
}
