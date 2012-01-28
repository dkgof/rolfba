/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.importers;

import android.util.Log;
import dk.lystrup.lagl.nodes.ModelData;
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
            Pattern facePattern = Pattern.compile("f (\\S+) (\\S+) (\\S+)");

            List<Float> vertexList = new LinkedList<Float>();
            List<Short> indicesList = new LinkedList<Short>();
            
            while(line != null) {
                Matcher vertexMatcher = vertexPattern.matcher(line);
                if(vertexMatcher.matches()) {
                    float x = Float.parseFloat(vertexMatcher.group(1));
                    float y = Float.parseFloat(vertexMatcher.group(2));
                    float z = Float.parseFloat(vertexMatcher.group(3));
                    vertexList.add(x);
                    vertexList.add(y);
                    vertexList.add(z);
                }
                
                Matcher faceMatcher = facePattern.matcher(line);
                if(faceMatcher.matches()) {
                    String[] splitA = faceMatcher.group(1).split("/");
                    String[] splitB = faceMatcher.group(2).split("/");
                    String[] splitC = faceMatcher.group(3).split("/");
                    
                    short a = Short.parseShort(splitA[0]);
                    short b = Short.parseShort(splitB[0]);
                    short c = Short.parseShort(splitC[0]);
                    indicesList.add(a);
                    indicesList.add(b);
                    indicesList.add(c);
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
