/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.lagl.importers;

import dk.lystrup.lagl.nodes.ModelData;

/**
 *
 * @author Rolf
 */
public interface ModelImporter {
    public ModelData importModel(String modelFile, String modelName);
    public boolean canImportModel(String model);
}
