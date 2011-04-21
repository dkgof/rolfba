/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl.importers;

import android.util.Log;
import dk.lystrup.androidgl.nodes.ModelData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rolf
 */
public class ModelImportFactory {
    private static ModelImportFactory singleton;
    
    private final List<ModelImporter> importers;
    
    private ModelImportFactory() {
        importers = new ArrayList<ModelImporter>();
        importers.add(new ObjImporter());
    }
    
    public static ModelImportFactory singleton() {
        if(singleton == null) {
            singleton = new ModelImportFactory();
        }
        
        return singleton;
    }
    
    public static ModelData importModel(String modelFile, String modelName) {
        return singleton().importModelInside(modelFile, modelName);
    }
    
    private ModelData importModelInside(String modelFile, String modelName) {
        for(ModelImporter importer : importers) {
            if(importer.canImportModel(modelFile)) {
                return importer.importModel(modelFile, modelName);
            }
        }
        
        Log.w("ModelImportFactory", "No importer that can handle ["+modelFile+"] avaliable");
        
        return null;
    }
}
