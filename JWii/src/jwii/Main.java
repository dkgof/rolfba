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

    private static final int BUTTON_PRESS_DELAY = 300;
    private static final int MOTION_DELAY = 300;
    private static final float idleThreshold = 0.1f;
    private static final int IDLE_TIMER = 1000 * 60 * 1;

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
    private boolean idle;

    public void start(int width, int height) {
        try {
            System.out.println("Starting up...");

            delay = 0;
            windowsMode = false;
            idle = false;

            setupTray();

            findMote();

            robo = new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());

            try {
                Thread.sleep(500);
            }
            catch(Exception e) {
                System.out.println("Interupted!");
            }

            deactivate();

            foundMote.setSensorBarBelowScreen();
            foundMote.setVirtualResolution(width, height);
            foundMote.setIrSensitivity(5);
            
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

        if( active ) {
            if( e.isButtonHomeJustPressed() ) {
                if( windowsMode ) {
                    deactivate();
                    WiiUseApiManager.definitiveShutdown();
                    try { Thread.sleep(1000); } catch(Exception ex){ }
                    System.exit(0);
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
                robo.keyPress(KeyEvent.VK_CONTROL);
                robo.keyPress(KeyEvent.VK_F);
                robo.keyRelease(KeyEvent.VK_F);
                robo.keyRelease(KeyEvent.VK_CONTROL);
            }

            if( e.isButtonMinusJustPressed() ) {
                robo.keyPress(KeyEvent.VK_CONTROL);
                robo.keyPress(KeyEvent.VK_B);
                robo.keyRelease(KeyEvent.VK_B);
                robo.keyRelease(KeyEvent.VK_CONTROL);
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

            if( e.isButtonTwoJustPressed() ) {
                windowsMode = true;
                foundMote.setLeds(true, false, false, true);
            }

            if( e.isButtonOneJustPressed() ) {
                windowsMode = false;
                foundMote.getStatus();
            }
        }

        if( e.isButtonOnePressed() && e.isButtonTwoPressed() ) {
            if( active ) {
                active = false;
                deactivate();
            }
            else {
                active = true;
                activate();
            }
        }
    }

    private boolean canSee;

    private int avgX = 0;
    private int avgY = 0;
    private int count = 0;

    private static final int SMOOTH_VALUE = 3;

    public void onIrEvent(IREvent e) {

        canSee = (e.getIRPoints().length > 0);

        if( canSee && System.currentTimeMillis() >= delay ) {

            if( count < SMOOTH_VALUE ) {
                count++;
            }

            avgX += e.getX();
            avgY += e.getY();

            avgX /= count;
            avgY /= count;

            robo.mouseMove(avgX, avgY);
        }
    }

    //private long motionDelay;

    public void onMotionSensingEvent(MotionSensingEvent e) {
        /*
        if( System.currentTimeMillis() > motionDelay && !idle) {
            GForce force = e.getGforce();

            float x = force.getX();
            float z = force.getZ() - 1;

            if( Math.abs(x) > Math.abs(z) ) {
                if( Math.abs(x) > 3 ) {
                    if( x < 0 ) {
                        System.out.println("Right!");
                        robo.keyPress(KeyEvent.VK_RIGHT);
                        robo.keyRelease(KeyEvent.VK_RIGHT);
                    }
                    else {
                        System.out.println("Left!");
                        robo.keyPress(KeyEvent.VK_LEFT);
                        robo.keyRelease(KeyEvent.VK_LEFT);
                    }
                    motionDelay = System.currentTimeMillis() + MOTION_DELAY;
                }
            }
            else {
                if( Math.abs(z) > 2 ) {
                    if( z < 0 ) {
                        System.out.println("Down!");
                        robo.keyPress(KeyEvent.VK_DOWN);
                        robo.keyRelease(KeyEvent.VK_DOWN);
                    }
                    else {
                        System.out.println("Up!");
                        robo.keyPress(KeyEvent.VK_UP);
                        robo.keyRelease(KeyEvent.VK_UP);
                    }
                    motionDelay = System.currentTimeMillis() + MOTION_DELAY;
                }
            }
        }
        */

        pauseMoteIfIdle(e.getGforce());
    }

    public void onExpansionEvent(ExpansionEvent e) {
    }

    private boolean led1, led2, led3, led4;
    private float batteryLevel;

    public void onStatusEvent(StatusEvent e) {
        if( !windowsMode && active ) {
            batteryLevel = e.getBatteryLevel() * 100;

            led1 = false;
            led2 = false;
            led3 = false;
            led4 = false;

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
        boolean blink = true;

        while(true) {
            try {
                if( !windowsMode && active && !idle) {
                    foundMote.getStatus();
                }

                if( idle ) {
                    foundMote.setLeds(false, false, false, blink);
                    blink = !blink;
                }

                Thread.sleep(2000);
            }
            catch(Exception e) {
                
            }
        }
    }

    private void activate() {
        foundMote.activateIRTRacking();
        foundMote.activateMotionSensing();
        foundMote.activateSmoothing();

        if( windowsMode ) {
            foundMote.setLeds(true, false, false, true);
        }
        else {
            foundMote.getStatus();
        }

        foundMote.activateRumble();
        try {
            Thread.sleep(500);
        }
        catch(Exception ex) {

        }
        foundMote.deactivateRumble();
    }

    private void deactivate() {
        foundMote.deactivateContinuous();
        foundMote.deactivateIRTRacking();
        foundMote.deactivateMotionSensing();
        foundMote.deactivateRumble();
        foundMote.deactivateSmoothing();
        foundMote.setLeds(false, false, false, false);
    }

    private long lastMovement = Long.MAX_VALUE;

    private void pauseMoteIfIdle(GForce gforce) {
        float x = Math.abs(gforce.getX());
        float y = Math.abs(gforce.getY());
        float z = Math.abs(gforce.getZ() - 1);

        long now = System.currentTimeMillis();

        if( x > idleThreshold || y > idleThreshold || z > idleThreshold ) {
            lastMovement = now;
        }

        if(now > lastMovement + IDLE_TIMER) {
            if( !idle ) {
                idle = true;
                foundMote.deactivateContinuous();
                foundMote.deactivateIRTRacking();
                foundMote.deactivateRumble();
                foundMote.setLeds(false, false, false, false);
            }
        }
        else {
            if( idle ) {
                idle = false;
                foundMote.activateIRTRacking();
            }
        }
    }

    private void setupTray() {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("tray.png");
            ActionListener exitListener = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    deactivate();
                    WiiUseApiManager.definitiveShutdown();
                    try { Thread.sleep(1000); } catch(Exception ex){ }
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
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void findMote() {
        Wiimote[] motes = WiiUseApiManager.getWiimotes(1, false);
        for (Wiimote mote : motes) {
            System.out.println("Found mote: " + mote);
            foundMote = mote;
            break;
        }

        if( foundMote == null ) {
            WiiUseApiManager.definitiveShutdown();
            try { Thread.sleep(1000); } catch(Exception ex){ }
            System.exit(0);
        }

        foundMote.addWiiMoteEventListeners(this);
    }
}
