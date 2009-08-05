/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iso2avi;

import java.io.IOException;

/**
 *
 * @author Rolf
 */
public class DGIndex {
    /**
     * Runs the file specified by fileName through DGIndex
     * @param inputFile full path to the first .vob
     * @return the path to the d2v file created by DGIndex
     */
    public static String dgIndex(String inputFile, int idctAlgorithm) {
        System.out.println("Running DGIndex...");

        String outFile = "dgindexout";
        String command = "DGIndex -AIF=["+inputFile+"] -IA="+idctAlgorithm+" -OM=0 -OF=["+outFile+"] -EXIT";

        System.out.println("Command: ["+command+"]");

        try {
            Process p = Runtime.getRuntime().exec(command);

            StreamEater.eatStream(p.getInputStream());
            StreamEater.eatStream(p.getErrorStream());

            p.waitFor();
            p.destroy();

            return outFile;

        } catch (Exception e) {
            System.out.println("Error running DGIndex: "+e);
        }

        return null;
    }
}
