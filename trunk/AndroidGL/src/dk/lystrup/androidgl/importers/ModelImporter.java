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
public interface ModelImporter {
    public ModelData importModel(String modelFile, String modelName);
    public boolean canImportModel(String model);
}
