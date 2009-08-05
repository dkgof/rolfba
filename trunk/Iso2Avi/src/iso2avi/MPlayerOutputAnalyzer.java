/*
 * OutputAnalyzer.java
 *
 * Created on 6. januar 2006, 05:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package iso2avi;

import java.io.*;

/**
 *
 * @author Rolf Bagge
 */
public class MPlayerOutputAnalyzer extends Thread {
    
    private static final int COUNT_VAR = 100;
    
    private InputStream in;
    private EncoderGui enc;
    
    /** Creates a new instance of OutputAnalyzer */
    public MPlayerOutputAnalyzer(InputStream in, EncoderGui enc) {
        this.in = in;
        this.enc = enc;
    }
    
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line=null;
            int count = 0;
            while ( (line = br.readLine()) != null) {
                
                //System.out.println(""+line);
                
                //Pos:   0.2s      7f ( 0%)  0.00fps Trem:   0min  11mb  A-V:0.020 [0:0]
                if( line.startsWith("Pos:") ) {
                    if( count == 0 ) {
                        int start = line.indexOf("(");
                        int stop = line.indexOf(")");
                        String percent = line.substring(start+1,stop-1).trim();
                        int p = Integer.parseInt(percent);

                        String fps = line.substring(stop+2, line.indexOf("fps")).trim();
                        String trem = line.substring(line.indexOf("Trem")+5, line.indexOf("min")).trim();
                        String size = line.substring(line.indexOf("min")+3, line.indexOf("mb")).trim();

                        if( trem.equals("0") ) {
                            trem = "n/a";
                        }
                        else {
                            trem += " min";
                        }

                        enc.getJProgress().setValue(p);
                        enc.getJFps().setText(fps);
                        enc.getJTime().setText(trem);
                        enc.getJSize().setText(size + "mb");
                    }
                    count ++;
                    count = count % COUNT_VAR;
                }
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();  
        }
    }
}
