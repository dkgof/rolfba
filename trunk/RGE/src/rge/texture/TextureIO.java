package rge.texture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 *
 * @author Rolf
 */
public class TextureIO {

    /**
     * Loads texture from the given file
     *
     * @param textureFile Name of texture to load
     * @return Loaded texture or null
     */
    public static Texture loadTexture(String textureFile) {
        return TextureIO.loadTexture(textureFile, false);
    }

    /**
     * Loads texture from the given file
     *
     * @param textureFile Name of texture to load
     * @param flip Whether to flip image
     * @return Loaded texture or null
     */
    public static Texture loadTexture(String textureFile, boolean flip) {
        Texture texture = null;

        try {
            ImageIO.setUseCache(false);
            BufferedImage inputImage = ImageIO.read(new File(textureFile));

            BufferedImage convertedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            convertedImage.getGraphics().drawImage(inputImage, 0, 0, null);

            DataBufferByte pixelDataBuffer = (DataBufferByte) convertedImage.getRaster().getDataBuffer();
            ByteBuffer pixelData = BufferUtils.createByteBuffer(pixelDataBuffer.getSize());
            
            byte[] pixels = pixelDataBuffer.getData();

            //ABGR -> RGBA
            byte temp;
            for(int i = 0; i<pixels.length; i+=4) {
                temp = pixels[i];
                pixels[i] = pixels[i+3];
                pixels[i+3] = temp;

                temp = pixels[i+1];
                pixels[i+1] = pixels[i+2];
                pixels[i+2] = temp;
            }
            
            pixelData.put(pixels);
            pixelData.flip();

            IntBuffer textureIds = BufferUtils.createIntBuffer(1);

            GL11.glGenTextures(textureIds);
            int textureId = textureIds.get(0);

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL13.GL_COMPRESSED_RGBA, convertedImage.getWidth(), convertedImage.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelData);

            texture = new DynamicTexture(textureId);

        } catch (IOException ex) {
            Logger.getLogger(TextureIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return texture;
    }

    /**
     * Get the closest greater power of 2 to the fold number
     *
     * @param fold The target number
     * @return The power of 2
     */
    public static int getNextPowerOfTwo(int fold) {
        int pow = 2;
        while (pow < fold) {
            pow *= 2;
        }
        return pow;
    }
}
