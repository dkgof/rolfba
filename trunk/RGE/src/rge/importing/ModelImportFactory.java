
package rge.importing;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rolf
 */
public class ModelImportFactory {

    private static Map<String, ModelImporter> importers = new HashMap<String, ModelImporter>();

    private static boolean first = true;

    public static ModelData importModel(String modelFile, String modelName) {

        if(first) {
            registerImporters();
            first = false;
        }

        ModelImporter importer = importers.get(getExtension(modelFile));

        if( importer != null ) {
            ModelData data = importer.importModel(modelFile, modelName);

            String importStats =
                    "\n********************\n" +
                    "* ["+modelFile+" - "+modelName+"] \n" +
                    data.getStats()+
                    "********************";

            Logger.getAnonymousLogger().log(Level.INFO, importStats);

            return data;
        }
        else {
            Logger.getAnonymousLogger().log(Level.WARNING, "No known ModelImporter for format: "+getExtension(modelFile));
            System.exit(-1);
        }
        
        return null;
    }

    public static void registerImporter(ModelImporter importer) {
        Logger.getAnonymousLogger().log(Level.INFO, "Registering importer for ["+importer.getSupportedExtension()+"]");
        importers.put(importer.getSupportedExtension().toLowerCase(), importer);
    }

    private static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
    }

    private static void registerImporters() {
        URL path = ModelImportFactory.class.getResource("/rge/importing/impl/");
        File dir = new File(path.getFile());

        if(dir.isDirectory()) {
            for(File f : dir.listFiles()) {
                if(f.getName().endsWith(".class")) {
                    try {
                        Object o = Class.forName("rge.importing.impl." + f.getName().substring(0, f.getName().lastIndexOf("."))).newInstance();
                        if( o instanceof ModelImporter ) {
                            registerImporter((ModelImporter) o);
                        }
                    } catch (Exception ex) {
                        Logger.getAnonymousLogger().log(Level.WARNING, "Unable to get class from file: "+f, ex);
                    }
                }
            }
        }
    }
}
