/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iso2avi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rolf
 */
public class StreamEater implements Runnable {

    private InputStream stream;

    private StreamEater(InputStream stream) {
        this.stream = stream;
    }

    public static void eatStream(InputStream stream) {
        new Thread(new StreamEater(stream)).start();
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line=null;
            while ( (line = br.readLine()) != null) {
                System.out.println(""+line);
            }
        } catch (Exception e) {
            System.out.print("Error eating stream: "+e);
        }
    }
}
