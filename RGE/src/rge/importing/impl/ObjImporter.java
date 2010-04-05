
package rge.importing.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rge.importing.Face;
import rge.importing.FacePoint;
import rge.importing.ModelData;
import rge.importing.ModelImporter;
import rge.importing.ModelNotFoundException;
import rge.importing.Point2;
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
    public ModelData importModel(String modelFile, String modelName) {
        List<String> foundModels = new ArrayList<String>();

        ModelData data = new ModelData();

        Pattern vertexPattern = Pattern.compile("v (.+?) (.+?) (.+?)");
        Pattern normalPattern = Pattern.compile("vn (.+?) (.+?) (.+?)");
        Pattern textureCoordPattern = Pattern.compile("vt (.+?) (.+?)");
        Pattern facePattern = Pattern.compile("f (\\d*)/(\\d*)/(\\d*) (\\d*)/(\\d*)/(\\d*) (\\d*)/(\\d*)/(\\d*)");
        Pattern modelPattern = Pattern.compile("o (.+)");

        boolean insideCorrectModel = false;
        boolean modelFound = false;

        if(modelName == null) {
            insideCorrectModel = true;
        }

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

                Matcher textureCoordsMatcher = textureCoordPattern.matcher(line);
                if(textureCoordsMatcher.matches()) {
                    double s = Double.parseDouble(textureCoordsMatcher.group(1));
                    double t = Double.parseDouble(textureCoordsMatcher.group(2));

                    data.addTextureCoord(new Point2(s,t));
                }

                if(insideCorrectModel) {
                    Matcher faceMatcher = facePattern.matcher(line);
                    if(faceMatcher.matches()) {
                        Face f = new Face();

                        int v1 = Integer.parseInt(faceMatcher.group(1));
                        int t1 = Integer.parseInt(faceMatcher.group(2));
                        int n1 = Integer.parseInt(faceMatcher.group(3));
                        FacePoint fp = new FacePoint(v1,n1);
                        fp.addTextureCoord(t1);
                        f.addFacePoint(fp);

                        int v2 = Integer.parseInt(faceMatcher.group(4));
                        int t2 = Integer.parseInt(faceMatcher.group(5));
                        int n2 = Integer.parseInt(faceMatcher.group(6));
                        fp = new FacePoint(v2,n2);
                        fp.addTextureCoord(t2);
                        f.addFacePoint(fp);

                        int v3 = Integer.parseInt(faceMatcher.group(7));
                        int t3 = Integer.parseInt(faceMatcher.group(8));
                        int n3 = Integer.parseInt(faceMatcher.group(9));
                        fp = new FacePoint(v3,n3);
                        fp.addTextureCoord(t3);
                        f.addFacePoint(fp);

                        data.addFace(f);
                    }
                }

                Matcher modelMatcher = modelPattern.matcher(line);
                if(modelMatcher.matches()) {
                    foundModels.add(modelMatcher.group(1));

                    insideCorrectModel = modelMatcher.group(1).equalsIgnoreCase(modelName);
                    if(insideCorrectModel) {
                        modelFound = true;
                    }
                }

                line = reader.readLine();
            }

            if(!modelFound && modelName != null) {
                throw new ModelNotFoundException("Unable to find model ["+modelName+"] in file ["+modelFile+"], avaliable models in file are "+foundModels);
            }

            return data;

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error loading model ["+modelName+"] from file ["+modelFile+"]", ex);
            System.exit(-1);
        }

        return null;
    }

}
