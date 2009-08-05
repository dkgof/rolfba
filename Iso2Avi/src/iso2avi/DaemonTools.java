/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iso2avi;

/**
 *
 * @author Rolf
 */
public class DaemonTools {
    private static final String PATH = "C:/Program Files (x86)/DAEMON Tools Lite/daemon.exe";

    public static void mountIso(String isoPath) {
        String command = PATH+" -mount 0,"+isoPath;

        try {
            Process p = Runtime.getRuntime().exec(command);

            StreamEater.eatStream(p.getErrorStream());
            StreamEater.eatStream(p.getInputStream());

            p.waitFor();
            p.destroy();
        }
        catch(Exception e) {
            System.out.println("Error mounting ["+isoPath+"]: "+e);
        }
    }
}
