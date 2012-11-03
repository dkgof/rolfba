/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.nodes;

import android.opengl.GLES20;
import dk.lystrup.lagl.Display;
import dk.lystrup.lagl.textures.DynamicTexture;

/**
 *
 * @author Rolf Bagge
 */
public class CompositeNode extends AbstractNode {
    private static final int MAX_HANDLERS = 2;

    private DynamicTexture[] renderToTextureOutputTextures;
    
    private int renderToTextureFBO;

    private Node[] textureQuads;
    
    private ChildResolution[] resolutions;
    
    public CompositeNode() {
        renderToTextureOutputTextures = new DynamicTexture[MAX_HANDLERS];
        textureQuads = new Node[MAX_HANDLERS];
        resolutions = new ChildResolution[MAX_HANDLERS];
        renderToTextureFBO = -1;
    }

    @Override
    public void recursiveUpdate(float deltaTime) {
        super.recursiveUpdate(deltaTime);
    }

    @Override
    public void recursiveRender() {
        render();
    }
    
    @Override
    public void render() {
        load();
    }

    private void load() {
        if(renderToTextureFBO == -1) {
            int[] fbos = new int[1];
            GLES20.glGenFramebuffers(fbos.length, fbos, 0);
            renderToTextureFBO = fbos[0];
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderToTextureFBO);
            
            int[] texs = new int[renderToTextureOutputTextures.length];
            GLES20.glGenTextures(texs.length, texs, 0);
            
            for(int i = 0; i<renderToTextureOutputTextures.length; i++) {
                renderToTextureOutputTextures[i] = new DynamicTexture(texs[i]);
                renderToTextureOutputTextures[i].bind();
                
                int texWidth = Display.singleton().getWidth();
                int texHeight = Display.singleton().getHeight();
                
                if(resolutions[i] != null) {
                    texWidth = resolutions[i].getWidth();
                    texHeight = resolutions[i].getHeight();
                }
                
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, texWidth, texHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
                
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            }
            
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        }
    }
    
    public class ChildResolution {
        private int width;
        private int height;
        
        public ChildResolution(int w, int h) {
            width = w;
            height = h;
        }

        /**
         * @return the width
         */
        public int getWidth() {
            return width;
        }

        /**
         * @param width the width to set
         */
        public void setWidth(int width) {
            this.width = width;
        }

        /**
         * @return the height
         */
        public int getHeight() {
            return height;
        }

        /**
         * @param height the height to set
         */
        public void setHeight(int height) {
            this.height = height;
        }
    }
}
