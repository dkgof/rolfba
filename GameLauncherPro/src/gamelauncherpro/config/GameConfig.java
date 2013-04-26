/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelauncherpro.config;

/**
 *
 * @author Marianne
 */
public class GameConfig {
    private String name;
    private String launchPath;
    private String coverPath;
    private String screenshotPath;
    
    public GameConfig(String name) {
        this.name = name;
        launchPath = null;
        coverPath = null;
        screenshotPath = null;
    }
    
    public void saveXML(XMLSaver xml) {
        xml.appendln("<game name=\""+name+"\" launchPath=\""+launchPath+"\" coverPath=\""+coverPath+"\" screenshotPath=\""+screenshotPath+"\" />");
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the launchPath
     */
    public String getLaunchPath() {
        return launchPath;
    }

    /**
     * @param launchPath the launchPath to set
     */
    public void setLaunchPath(String launchPath) {
        this.launchPath = launchPath;
    }

    /**
     * @return the coverPath
     */
    public String getCoverPath() {
        return coverPath;
    }

    /**
     * @param coverPath the coverPath to set
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    /**
     * @return the screenshotPath
     */
    public String getScreenshotPath() {
        return screenshotPath;
    }

    /**
     * @param screenshotPath the screenshotPath to set
     */
    public void setScreenshotPath(String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }
}
