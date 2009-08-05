/*
 * Main.java
 *
 * Created on 27. marts 2007, 14:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package iso2avi;

/**
 *
 * @author Rolf
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
        Encoder enc = new Encoder();

/*
 * Test

        EncoderOptions eo = new EncoderOptions("D:\\DVDRip\\DEAD_POETS_SOCIETY.ISO", "test.avi");
        enc.addJob(eo);
        enc.runQueue(1);
*/
        
        MainGUI gui = new MainGUI(enc);
        gui.setVisible(true);
        gui.setBounds(300, 300, gui.getWidth(), gui.getHeight());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Main();
    }
    
}
