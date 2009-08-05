/*
 * EncoderOptions.java
 *
 * Created on 27. marts 2007, 14:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package iso2avi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.*;

/**
 *
 * @author Rolf
 */
public class EncoderOptions {
    
    private int bitrate;
    private int abitrate;

    private int keyint;
    private int subq;
    private int frameref;
    private int bframes;
    private boolean dct;
    private boolean b_pyramid;
    private boolean weight_b;
    private boolean partitions_all;

    private String inputFile;
    private String outputFile;
    
    private List<Integer> subtitles;
    
    private String cropValue = "";
    
    private boolean keepGoing = true;
    
    /** Creates a new instance of EncoderOptions */
    public EncoderOptions(String in, String out) {
        setBitrate(1500);
        setAbitrate(128);
        
        setInputFile(in);
        setOutputFile(out);
        
        subtitles = new ArrayList<Integer>();
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getAbitrate() {
        return abitrate;
    }

    public void setAbitrate(int abitrate) {
        this.abitrate = abitrate;
    }

    public String getInputFile() {
        return "\""+inputFile+"\"";
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return "\""+outputFile+"\"";
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
    
    public String toString() {
        return "In: "+inputFile+", Out: "+outputFile+" (V:"+getBitrate()+", A:"+getAbitrate()+")";
    }
    
    public void findCropValue(Process p, EncoderGui gui) {
        try {
            String command = "mplayer -v -nosound -vo null -benchmark -vf cropdetect=:2 -dvd-device "+this.getInputFile()+" dvd://1";
            
            System.out.println("\tCommand: "+command);

            Runtime rt = Runtime.getRuntime();
            
            p = rt.exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line = "";
            boolean read = false;
            
            int count = 0;
            
            while( true ) {

                read = false;
                line = "";
                
                if( !keepGoing ) {
                    break;
                }
                
                if( in2.ready() ) {
                    String temp = in2.readLine().trim();
                    
                    if(!temp.equals("")) {
                        line = temp;
                        read = true;
                    }
                    
                }

                if( in.ready() ) {
                    String temp = in.readLine().trim();
                    
                    if(!temp.equals("")) {
                        line = temp;
                        read = true;
                    }
                }
                
                if( read ) {
                    if( line.startsWith("[CROP]") ) {
                        int i = line.indexOf("-vf ");
                        cropValue = line.substring(i+4).trim();
                        cropValue = cropValue.substring(0, cropValue.indexOf(")"));
                        
                        count++;
                        
                        int percent = (int) (count / 1000.0f * 100.0f);
                        
                        gui.getJProgress().setValue(percent);
                        
                        if(count >= 1100) {
                            System.out.println("\tFound crop value: "+cropValue);
                            break;
                        }
                    }
                }
                else {
                    Thread.sleep(1);
                }
            }

            p.destroy();

        }
        catch( Exception e ) {
            System.out.println("Error running mplayer: "+e);
        }
    }
    
    public void findSubtitleTracks(Process p, EncoderGui gui) {
        try {
            Runtime rt = Runtime.getRuntime();

            String command = "mplayer -v -nosound -vo null -dvd-device "+this.getInputFile()+" dvd://1";

            System.out.println("Test: ["+this.getInputFile().toLowerCase()+"]");

            System.out.println("\tCommand: "+command);

            p = rt.exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line = "";
            boolean read = false;
            
            int count = 0;

            while( true ) {

                read = false;
                line = "";

                if( !keepGoing ) {
                    break;
                }
                
                if( in2.ready() ) {
                    String temp = in2.readLine().trim();
                    
                    if(!temp.equals("")) {
                        line = temp;
                        read = true;
                    }
                    
                }

                if( in.ready() ) {
                    String temp = in.readLine().trim();
                    
                    if(!temp.equals("")) {
                        line = temp;
                        read = true;
                    }
                }

                if( read ) {
                    if( line.startsWith("subtitle ( sid ):") ) {
                        String subtitle = line.substring(18).trim();
                        subtitle = subtitle.substring(0, subtitle.indexOf(" "));

                        System.out.println("\tFound subtitle: "+subtitle);

                        subtitles.add(Integer.parseInt(subtitle));
                    }
                    else if( line.startsWith("Starting playback...") ) {
                        break;
                    }
                }
                else {
                    Thread.sleep(1);
                }
            }

            p.destroy();

        }
        catch( Exception e ) {
            System.out.println("Error running mplayer: "+e);
        }
    }
    
    public List<Integer> getSubtitleTracks() {
        return subtitles;
    }

    private String x264Options = "keyint=2500:frameref=6:bframes=16:b_pyramid:weight_b:8x8dct:mixed_refs:crf=20:direct=auto:subme=5:trellis=1:no-fast-pskip";

    public String getEncodex264CmdPass1() {
        String cmd = "mencoder -dvd-device "+this.getInputFile()+" dvd://1 -nosound -o nul -ovc x264 -x264encopts "+x264Options+":pass=1:turbo=1:threads=auto" + " -vf "+cropValue;
                
        return cmd;
    }

    public String getEncodex264CmdPass2() {
        String cmd = "mencoder -dvd-device "+this.getInputFile()+" dvd://1 -of lavf -oac mp3lame -lameopts fast:preset=standard -o "+this.getOutputFile()+" -ovc x264 -x264encopts "+x264Options+":pass=2:threads=auto" + " -vf "+cropValue;
                
        return cmd;
    }

    public String getEncodex264CmdSinglePass() {
        String cmd = "mencoder -dvd-device "+this.getInputFile()+" dvd://1 -of lavf -oac mp3lame -lameopts fast:preset=standard -o "+this.getOutputFile()+" -ovc x264 -x264encopts "+x264Options+":threads=auto" + " -vf "+cropValue;

        return cmd;
    }
    
    public void cancelJob() {
        keepGoing = false;
    }
    
    public String getCropValue() {
        return cropValue.substring(5);
    }

    /**
     * @return the keyint
     */
    public int getKeyint() {
        return keyint;
    }

    /**
     * @param keyint the keyint to set
     */
    public void setKeyint(int keyint) {
        this.keyint = keyint;
    }

    /**
     * @return the subq
     */
    public int getSubq() {
        return subq;
    }

    /**
     * @param subq the subq to set
     */
    public void setSubq(int subq) {
        this.subq = subq;
    }

    /**
     * @return the frameref
     */
    public int getFrameref() {
        return frameref;
    }

    /**
     * @param frameref the frameref to set
     */
    public void setFrameref(int frameref) {
        this.frameref = frameref;
    }

    /**
     * @return the bframes
     */
    public int getBframes() {
        return bframes;
    }

    /**
     * @param bframes the bframes to set
     */
    public void setBframes(int bframes) {
        this.bframes = bframes;
    }

    /**
     * @return the dct
     */
    public boolean isDct() {
        return dct;
    }

    /**
     * @param dct the dct to set
     */
    public void setDct(boolean dct) {
        this.dct = dct;
    }

    /**
     * @return the b_pyramid
     */
    public boolean isB_pyramid() {
        return b_pyramid;
    }

    /**
     * @param b_pyramid the b_pyramid to set
     */
    public void setB_pyramid(boolean b_pyramid) {
        this.b_pyramid = b_pyramid;
    }

    /**
     * @return the weight_b
     */
    public boolean isWeight_b() {
        return weight_b;
    }

    /**
     * @param weight_b the weight_b to set
     */
    public void setWeight_b(boolean weight_b) {
        this.weight_b = weight_b;
    }

    /**
     * @return the partitions_all
     */
    public boolean isPartitions_all() {
        return partitions_all;
    }

    /**
     * @param partitions_all the partitions_all to set
     */
    public void setPartitions_all(boolean partitions_all) {
        this.partitions_all = partitions_all;
    }
}
