
package rge.importing;

/**
 *
 * @author Rolf
 */
public interface ModelImporter {

    /**
     * The extension supported by this model importer
     * @return the supported extension (excluding .)
     */
    public String getSupportedExtension();

    /**
     * Imports the given modelfile
     * @param modelFile the file holding the model to import
     * @param objectName the name of the object to import from the model file
     * @return the ModelData of the imported model
     */
    public ModelData importModel(String modelFile, String objectName);
}
