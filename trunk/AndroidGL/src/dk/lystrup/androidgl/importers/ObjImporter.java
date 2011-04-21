/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl.importers;

import android.util.Log;
import dk.lystrup.androidgl.nodes.ModelData;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rolf
 */
public class ObjImporter implements ModelImporter {

    public ModelData importModel(String modelFile, String modelName) {
        ModelData data = null;
        
        try {
            InputStream objStream = ObjImporter.class.getClassLoader().getResourceAsStream(modelFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(objStream));
            String line = reader.readLine();
            
            Pattern vertexPattern = Pattern.compile("v (\\S+) (\\S+) (\\S+)");
            Pattern facePattern = Pattern.compile("f (\\d?)/(\\d?)/(\\d?) (\\d?)/(\\d?)/(\\d?) (\\d?)/(\\d?)/(\\d?)");

            List<Float> vertexList = new LinkedList<Float>();
            List<Short> indicesList = new LinkedList<Short>();
            
            while(line != null) {
                Log.d("ObjImporter", line);
                
                Matcher vertexMatcher = vertexPattern.matcher(line);
                if(vertexMatcher.matches()) {
                    float x = Float.parseFloat(vertexMatcher.group(1));
                    float y = Float.parseFloat(vertexMatcher.group(2));
                    float z = Float.parseFloat(vertexMatcher.group(3));
                    vertexList.add(x);
                    vertexList.add(y);
                    vertexList.add(z);
                    Log.i("OnjImporter", "Adding vertex: "+x+","+y+","+z);
                }
                
                Matcher faceMatcher = facePattern.matcher(line);
                if(faceMatcher.matches()) {
                    short a = Short.parseShort(faceMatcher.group(1));
                    short b = Short.parseShort(faceMatcher.group(4));
                    short c = Short.parseShort(faceMatcher.group(7));
                    indicesList.add(a);
                    indicesList.add(b);
                    indicesList.add(c);
                    Log.i("OnjImporter", "Adding face: "+a+","+b+","+c);
                }
                
                line = reader.readLine();
            }
            
            float[] vertices = new float[vertexList.size()];
            short[] indices = new short[indicesList.size()];
            float[] texcoordinates = null;
            
            int i = 0;
            for(float f : vertexList) {
                vertices[i] = f;
                i++;
            }
            
            i = 0;
            for(short s : indicesList) {
                indices[i] = (short)(s-1);
                i++;
            }

            //TODO expand vertices list to include all vertices so they match 1:1 with texturecoordinates
            
            data = new ModelData(indices, vertices, texcoordinates);
            
        } catch (Exception ex) {
            Log.e("ObjImporter", "Error loading model: "+ex);
        }
        
        return data;
    }

    public boolean canImportModel(String model) {
        if(model.endsWith(".obj")) {
            return true;
        }
        
        return false;
    }
    
}
