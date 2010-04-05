
package rge.importing.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rge.importing.Face;
import rge.importing.FacePoint;
import rge.importing.ModelData;
import rge.importing.ModelImporter;
import rge.math.Vector3;

/**
 *
 * @author Rolf
 */
public class ObjImporter implements ModelImporter {

    @Override
    public String getSupportedExtension() {
        return "obj";
    }

    @Override
    public ModelData importModel(String modelFile, String objectName) {
        ModelData data = new ModelData();

        Pattern vertexPattern = Pattern.compile("v (.+?) (.+?) (.+?)");
        Pattern normalPattern = Pattern.compile("vn (.+?) (.+?) (.+?)");
        Pattern facePattern = Pattern.compile("f (\\d*)/(\\d*)/(\\d*) (\\d*)/(\\d*)/(\\d*) (\\d*)/(\\d*)/(\\d*)");

        try {
            File objFile = new File(modelFile);
            BufferedReader reader = new BufferedReader(new FileReader(objFile));

            String line = reader.readLine();

            while(line != null) {

                line = line.trim();

                Matcher vertexMatcher = vertexPattern.matcher(line);
                if(vertexMatcher.matches()) {
                    double x = Double.parseDouble(vertexMatcher.group(1));
                    double y = Double.parseDouble(vertexMatcher.group(2));
                    double z = Double.parseDouble(vertexMatcher.group(3));

                    data.addVertex(new Vector3(x,y,z));
                }

                Matcher normalMatcher = normalPattern.matcher(line);
                if(normalMatcher.matches()) {
                    double x = Double.parseDouble(normalMatcher.group(1));
                    double y = Double.parseDouble(normalMatcher.group(2));
                    double z = Double.parseDouble(normalMatcher.group(3));

                    data.addNormal(new Vector3(x,y,z));
                }

                Matcher faceMatcher = facePattern.matcher(line);
                if(faceMatcher.matches()) {
                    Face f = new Face();

                    int v1 = Integer.parseInt(faceMatcher.group(1));
                    int n1 = Integer.parseInt(faceMatcher.group(3));
                    f.addFacePoint(new FacePoint(v1,n1));

                    int v2 = Integer.parseInt(faceMatcher.group(4));
                    int n2 = Integer.parseInt(faceMatcher.group(6));
                    f.addFacePoint(new FacePoint(v2,n2));

                    int v3 = Integer.parseInt(faceMatcher.group(7));
                    int n3 = Integer.parseInt(faceMatcher.group(9));
                    f.addFacePoint(new FacePoint(v3,n3));

                    //TODO: add texture coords parsing

                    data.addFace(f);
                }

                line = reader.readLine();
            }

            return data;

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error loading model ["+objectName+"] from file ["+modelFile+"]", ex);
            System.exit(-1);
        }

        return null;
    }

}
