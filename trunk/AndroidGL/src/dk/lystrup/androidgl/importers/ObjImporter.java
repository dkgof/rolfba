/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.androidgl.importers;

import dk.lystrup.androidgl.nodes.ModelData;

/**
 *
 * @author Rolf
 */
public class ObjImporter implements ModelImporter {

    public ModelData importModel(String modelFile, String modelName) {
        ModelData data = null;
        
        return data;
    }

    public boolean canImportModel(String model) {
        if(model.endsWith(".obj")) {
            return true;
        }
        
        return false;
    }
    
}
