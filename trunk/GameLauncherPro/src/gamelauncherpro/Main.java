/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelauncherpro;

import gamelauncherpro.ui.GameLauncherPro;
import javax.swing.UIManager;

/**
 *
 * @author Marianne
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            System.out.println("Error setting look and feel: "+e);
        }
        
        GameLauncherPro ui = new GameLauncherPro();
        ui.setVisible(true);
        ui.setLocationRelativeTo(null);
    }
}
