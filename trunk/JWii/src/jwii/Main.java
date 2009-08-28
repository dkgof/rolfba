/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jwii;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.values.GForce;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

/**
 *
 * @author Rolf
 */
public class Main implements wiiusej.wiiusejevents.utils.WiimoteListener, Runnable {

    public static final int BUTTON_PRESS_DELAY = 100;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().start(1360, 768);
    }
    
    private Robot robo;
    private Wiimote foundMote;
    private long delay;
    private boolean windowsMode;
    private TrayIcon trayIcon;
    private boolean active;

    public void start(int width, int height) {
        try {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().getImage("tray.png");

            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    WiiUseApiManager.definitiveShutdown();
                    System.exit(0);
                }
            };

            PopupMenu pop = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);

            pop.add(defaultItem);

            trayIcon = new TrayIcon(image, "JWii", pop);
            trayIcon.setImageAutoSize(true);

            tray.add(trayIcon);

            System.out.println("Starting up...");

            robo = new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
            
            Wiimote[] motes = WiiUseApiManager.getWiimotes(1, true);
            for (Wiimote mote : motes) {
                System.out.println("Found mote: " + mote);
                foundMote = mote;
                break;
            }

            delay = 0;
            windowsMode = false;

            if( foundMote == null ) {
                WiiUseApiManager.definitiveShutdown();
                System.exit(0);
            }

            foundMote.addWiiMoteEventListeners(this);

            try {
                Thread.sleep(500);
            }
            catch(Exception e) {
                System.out.println("Interupted!");
            }

            foundMote.setSensorBarBelowScreen();
            foundMote.setVirtualResolution(width, height);
            
            activate();
            active = true;

            Thread powerThread = new Thread(this);
            powerThread.setDaemon(true);
            powerThread.start();

        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onButtonsEvent(WiimoteButtonsEvent e) {
        if( e.isButtonHomeJustPressed() ) {
            if( windowsMode ) {
                WiiUseApiManager.definitiveShutdown();
            }
            else {
                robo.keyPress(KeyEvent.VK_WINDOWS);
                robo.keyPress(KeyEvent.VK_ALT);
                robo.keyPress(KeyEvent.VK_ENTER);

                robo.keyRelease(KeyEvent.VK_ENTER);
                robo.keyRelease(KeyEvent.VK_ALT);
                robo.keyRelease(KeyEvent.VK_WINDOWS);
            }
        }

        if( e.isButtonAJustReleased() ) {
            robo.mouseRelease(InputEvent.BUTTON1_MASK);
        }

        if( e.isButtonAJustPressed() ) {
            robo.mousePress(InputEvent.BUTTON1_MASK);
            delay = System.currentTimeMillis() + BUTTON_PRESS_DELAY;
        }

        if( e.isButtonBJustPressed() ) {
            if( windowsMode ) {
                robo.mousePress(InputEvent.BUTTON3_MASK);
                delay = System.currentTimeMillis() + BUTTON_PRESS_DELAY;
            }
            else {
                robo.keyPress(KeyEvent.VK_BACK_SPACE);
                robo.keyRelease(KeyEvent.VK_BACK_SPACE);
            }
        }
        if( e.isButtonBJustReleased() ) {
            if( windowsMode ) {
                robo.mouseRelease(InputEvent.BUTTON3_MASK);
            }
        }

        if( e.isButtonPlusJustPressed() ) {
            robo.keyPress(KeyEvent.VK_PAGE_UP);
            robo.keyRelease(KeyEvent.VK_PAGE_UP);
        }

        if( e.isButtonMinusJustPressed() ) {
            robo.keyPress(KeyEvent.VK_PAGE_DOWN);
            robo.keyRelease(KeyEvent.VK_PAGE_DOWN);
        }

        if( e.isButtonUpJustPressed() ) {
            robo.keyPress(KeyEvent.VK_UP);
            robo.keyRelease(KeyEvent.VK_UP);
        }

        if( e.isButtonDownJustPressed() ) {
            robo.keyPress(KeyEvent.VK_DOWN);
            robo.keyRelease(KeyEvent.VK_DOWN);
        }

        if( e.isButtonLeftJustPressed() ) {
            robo.keyPress(KeyEvent.VK_LEFT);
            robo.keyRelease(KeyEvent.VK_LEFT);
        }

        if( e.isButtonRightJustPressed() ) {
            robo.keyPress(KeyEvent.VK_RIGHT);
            robo.keyRelease(KeyEvent.VK_RIGHT);
        }

        if( e.isButtonOneJustPressed() ) {
            windowsMode = false;
            foundMote.getStatus();
        }

        if( e.isButtonTwoJustPressed() ) {
            windowsMode = true;
            foundMote.setLeds(true, false, false, true);
        }

        if( e.isButtonOneHeld() ) {
            if( active ) {
                deactivate();
                active = false;
                foundMote.setLeds(false, false, false, false);
            }
            else {
                activate();
                active = true;
                foundMote.getStatus();
            }
        }
    }

    public void onIrEvent(IREvent e) {

        System.out.println(""+e);

        boolean canSee = false;

        canSee = (e.getIRPoints().length > 0);

        if( canSee && System.currentTimeMillis() >= delay ) {
            robo.mouseMove(e.getX(), e.getY());
        }
    }

    public void onMotionSensingEvent(MotionSensingEvent e) {
        GForce force = e.getGforce();

        if( force.getX() > 2 ) {
            robo.keyPress(KeyEvent.VK_LEFT);
            robo.keyRelease(KeyEvent.VK_LEFT);
        }
        else if( force.getX() < -2 ) {
            robo.keyPress(KeyEvent.VK_RIGHT);
            robo.keyRelease(KeyEvent.VK_RIGHT);
        }

        if( force.getY() > 2 ) {
            robo.keyPress(KeyEvent.VK_UP);
            robo.keyRelease(KeyEvent.VK_UP);
        }
        else if( force.getY() < -2 ) {
            robo.keyPress(KeyEvent.VK_DOWN);
            robo.keyRelease(KeyEvent.VK_DOWN);
        }
    }

    public void onExpansionEvent(ExpansionEvent e) {
    }

    public void onStatusEvent(StatusEvent e) {
        if( !windowsMode && active ) {
            float batteryLevel = e.getBatteryLevel() * 100;

            boolean led1 = false;
            boolean led2 = false;
            boolean led3 = false;
            boolean led4 = false;

            if(batteryLevel >= 20) {
                led1 = true;
            }
            if(batteryLevel >= 40) {
                led2 = true;
            }
            if(batteryLevel >= 60) {
                led3 = true;
            }
            if(batteryLevel >= 80) {
                led4 = true;
            }

            foundMote.setLeds(led1, led2, led3, led4);
        }
    }

    public void onDisconnectionEvent(DisconnectionEvent e) {
    }

    public void onNunchukInsertedEvent(NunchukInsertedEvent e) {
    }

    public void onNunchukRemovedEvent(NunchukRemovedEvent e) {
    }

    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent e) {
    }

    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent e) {
    }

    public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent e) {
    }

    public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent e) {
    }

    public void run() {
        while(true) {
            try {
                foundMote.getStatus();
                Thread.sleep(5000);
            }
            catch(Exception e) {
                
            }
        }
    }

    private void activate() {
        foundMote.activateIRTRacking();
        foundMote.activateMotionSensing();
    }

    private void deactivate() {
        foundMote.deactivateContinuous();
        foundMote.deactivateIRTRacking();
        foundMote.deactivateMotionSensing();
        foundMote.deactivateRumble();
        foundMote.deactivateSmoothing();

    }
}
