/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.shaders.glsl;

import android.opengl.GLES20;
import android.util.Log;
import dk.lystrup.lagl.LAGLUtil;
import dk.lystrup.lagl.shaders.FloatArrayParameter;
import dk.lystrup.lagl.shaders.MVPMatrixParameter;
import dk.lystrup.lagl.shaders.Shader;
import dk.lystrup.lagl.shaders.ShaderParameter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rolf Bagge
 */
public abstract class GLSLShader implements Shader {

    private int program;
    
    protected List<ShaderParameter> parameters;
    
    public GLSLShader() {
        program = 0;
        parameters = new LinkedList<ShaderParameter>();
        
        parameters.add(new MVPMatrixParameter());
    }
    
    public void unbind() {
        GLES20.glUseProgram(0);
    }
    
    public void bind() {
        load();
        
        GLES20.glUseProgram(program);
        LAGLUtil.checkGlError("glUseProgram");
        
        for(ShaderParameter parameter : parameters) {
            parameter.updateParameter();
        }
    }
    
    private void createProgram() throws IOException {
        LAGLUtil.checkGlError("createProgram - pre");
        
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, loadSource(getVertexShader()));
        if(vertexShader == 0) {
            throw new IllegalStateException("Unable to load vertex shader!");
        }
        
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, loadSource(getFragmentShader()));
        if(fragmentShader == 0) {
            throw new IllegalStateException("Unable to load fragment shader!");
        }

        program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            LAGLUtil.checkGlError("glAttachShader - vertex");
            GLES20.glAttachShader(program, fragmentShader);
            LAGLUtil.checkGlError("glAttachShader - fragment");
            GLES20.glLinkProgram(program);
            LAGLUtil.checkGlError("glLinkProgram");
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e("LAGL", "Could not link program: ");
                Log.e("LAGL", GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
    }
    
    private int loadShader(int shaderType, String source) {
        Log.i("LAGL", "Compiling shader: ["+source+"]");
        int shader = GLES20.glCreateShader(shaderType);
        LAGLUtil.checkGlError("glCreateShader");
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            LAGLUtil.checkGlError("glShaderSource");
            GLES20.glCompileShader(shader);
            LAGLUtil.checkGlError("glCompileShader");
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e("LAGL", "Could not compile shader " + shaderType + ":");
                Log.e("LAGL", GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }
    
    private String loadSource(String shaderFile) throws IOException {
        BufferedReader sourceReader = new BufferedReader( new InputStreamReader(GLSLShader.class.getClassLoader().getResourceAsStream("dk/lystrup/lagl/shaders/glsl/impl/" + shaderFile)));
        
        String shaderSource = "";
        
        String line;
        
        while((line = sourceReader.readLine()) != null) {
            shaderSource += line + "\n";
        }
        
        return shaderSource;
    }
    
    @Override
    public int getVertexAttrib() {
        load();
        return GLES20.glGetAttribLocation(program, "aPosition");
    }

    @Override
    public int getTexCoordAttrib() {
        load();
        return GLES20.glGetAttribLocation(program, "aTextureCoord");
    }
    
    protected abstract String getVertexShader();
    protected abstract String getFragmentShader();

    private void load() {
        if(program == 0) {
            try {
                createProgram();
                
                for(ShaderParameter parameter : parameters) {
                    parameter.findParameterLocation(program);
                }
            } catch(IOException ex) {
                Log.wtf("LAGL", "Error creating shader", ex);
            }
        }
    }
}

