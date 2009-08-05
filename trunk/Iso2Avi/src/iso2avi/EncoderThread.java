/*
 * EncoderThread.java
 *
 * Created on 27. marts 2007, 14:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package iso2avi;

import java.io.*;

/**
 *
 * @author Rolf
 */
public class EncoderThread implements Runnable {
    
    private EncoderOptions options;
    private Encoder enc;
    private Process p;
    private boolean keepGoing = true;
    private boolean doneEncoding = false;
    private EncoderGui gui;
    
    private long timer;
    private MPlayerOutputAnalyzer inputAn;
    private MPlayerOutputAnalyzer errAn;
    
    /** Creates a new instance of EncoderThread */
    public EncoderThread(EncoderOptions eo, Encoder enc) {
        this.enc = enc;
        options = eo;
    }

    private float mark() {
        long now = System.currentTimeMillis();
        long diff = now - timer;
        timer = now;
        
        float minutes = diff / 1000.0f / 60.0f;
        int temp = (int) (minutes * 100);
        minutes = temp / 100.0f;
        
        return minutes;
    }
    
    public void run() {
        System.out.println("Job starting: "+this);
        
        //Do encoding
        try {
            System.out.println("\tRunning job: "+options.getInputFile()+"-->"+options.getOutputFile());
            
            Runtime rt = Runtime.getRuntime();
            
            //Create GUI
            gui = new EncoderGui(this);
            gui.setVisible(true);
            gui.setBounds(400, 400, gui.getWidth(), gui.getHeight());
            gui.setTitle(gui.getTitle()+" - "+options.getOutputFile());

            gui.getJAudioRate().setText(options.getAbitrate()+"kbit/s");
            gui.getJVideoRate().setText(options.getBitrate()+"kbit/s");
            gui.getJCropRate().setText("n/a");
            gui.getJCropOk().setText("");
            gui.getJPass1().setText("");
            gui.getJPass2().setText("");
            gui.getJSubOk().setText("");
            gui.getJRipSub().setText("");
            gui.getJAudio().setText("");

            //Find crop value
            mark();
            gui.getJCropOk().setText("Running");
            gui.getJNowEncoding().setText("Finding cropvalues: "+options.getInputFile());
            options.findCropValue(p, gui);
            gui.getJCropRate().setText(options.getCropValue());
            gui.getJCropOk().setText("Done "+mark()+"m");
                        
            if( !keepGoing ) {
                return;
            }

            //Find subtitles
            gui.getJSubOk().setText("Running");
            gui.getJNowEncoding().setText("Finding subtitles: "+options.getInputFile());
            options.findSubtitleTracks(p, gui);
            gui.getJSubOk().setText("Done "+mark()+"m");

            if( !keepGoing ) {
                return;
            }
            
            gui.getJNowEncoding().setText("Waiting for sublock: "+options.getInputFile());
            
            //Rip subtitles
            gui.getJRipSub().setText("Running");
            getSubtitleLock();
            int count = 0;
            for(int i : options.getSubtitleTracks() ) {
                count++;
                gui.getJRipSub().setText("Running "+count+"/"+options.getSubtitleTracks().size());
                gui.getJNowEncoding().setText("Ripping subtitle ( "+i+" ): "+options.getInputFile());
                
                String ripCmd = "mencoder -dvd-device "+options.getInputFile();
                ripCmd += " dvd://1 -ofps 25 -nosound -ovc frameno -o nul";
                ripCmd += " -sid "+i;
                ripCmd += " -vobsubout "+options.getOutputFile().substring(0,options.getOutputFile().length()-5)+"-"+i+"\"";
                
                System.out.println("\tRipcmd: "+ripCmd);
                
                p = rt.exec(ripCmd);
                
                inputAn = new MPlayerOutputAnalyzer(p.getInputStream(), gui);
                errAn = new MPlayerOutputAnalyzer(p.getErrorStream(), gui);
                
                inputAn.start();
                errAn.start();
                
                p.waitFor();
                
                p.destroy();
            }
            releaseSubtitleLock();
            gui.getJRipSub().setText("Done "+mark()+"m");

            if( !keepGoing ) {
                return;
            }
            //Now encode
            
            //Pass 1

            gui.getJPass1().setText("Running");
            gui.getJNowEncoding().setText("Now Encoding: "+options.getInputFile()+" Pass 1");

            p = rt.exec(options.getEncodex264CmdSinglePass());

            inputAn = new MPlayerOutputAnalyzer(p.getInputStream(), gui);
            errAn = new MPlayerOutputAnalyzer(p.getErrorStream(), gui);

            inputAn.start();
            errAn.start();

            p.waitFor();

            p.destroy();

            if( !keepGoing ) {
                return;
            }
            gui.getJPass1().setText("Done "+mark()+"m");
            gui.getJPass2().setText("Running");


            /*
             * Mencoder Pass
             */

            /*
            p = rt.exec(options.getEncodex264CmdPass1());
            
            inputAn = new MPlayerOutputAnalyzer(p.getInputStream(), gui);
            errAn = new MPlayerOutputAnalyzer(p.getErrorStream(), gui);

            inputAn.start();
            errAn.start();
            
            p.waitFor();
            
            p.destroy();

            if( !keepGoing ) {
                return;
            }
            gui.getJPass1().setText("Done "+mark()+"m");
            gui.getJPass2().setText("Running");
            
            //Pass 2
            gui.getJNowEncoding().setText("Now Encoding: "+options.getInputFile()+" Pass 2");

             */

            /*
             * Mencoder Pass
             */

            /*
            p = rt.exec(options.getEncodex264CmdPass2());
            
            inputAn = new MPlayerOutputAnalyzer(p.getInputStream(), gui);
            errAn = new MPlayerOutputAnalyzer(p.getErrorStream(), gui);

            inputAn.start();
            errAn.start();
            
            p.waitFor();
            
            p.destroy();
            
            gui.getJPass2().setText("Done "+mark()+"m");

            System.out.println("Job done: "+this);
             */
            
            doneEncoding = true;
            
            gui.getJCancel().setText("Close");
        }
        catch(Exception e) {
            //Sleep exception
            e.printStackTrace();
            System.exit(-1);
        }
        
        //Done
        enc.decRunning();
    }

    private void getSubtitleLock() {
        File f = new File("sublock.lock");
        try {
            while( f.exists() || !f.createNewFile() ) {
                f = new File("sublock.lock");
                try {
                    Thread.sleep(100);
                }
                catch(Exception e) {
                    System.out.println("Sleep exception: "+e);
                }
            }
        }
        catch(Exception ex) {
            System.out.println("Create file exception: "+ex);
        }
        
        System.out.println("\t"+this+" aquired subtitle lock!");
    }

    private void releaseSubtitleLock() {
        File f = new File("sublock.lock");
        if( f.exists() ) {
            while( !f.delete() ) {
                System.out.println("Cant delete lock?");
                try {
                    Thread.sleep(1000);
                }
                catch(Exception e) {

                }
            }
            System.out.println("\t"+this+" released subtitle lock!");
        }
    }

    public void cancelJob() {
        if( !doneEncoding) {
            keepGoing = false;
            if( p != null ) {
                p.destroy();
            }
            options.cancelJob();
            releaseSubtitleLock();
            System.exit(-1);
        }
        else {
            gui.setVisible(false);
            gui.dispose();
        }
    }
    
}
