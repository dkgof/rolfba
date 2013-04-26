/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelauncherpro.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rolf
 */
public class XMLSaver {
    private final File xmlFile;
    private StringBuilder xml;
    
    private String indent;
    
    public XMLSaver(File file) throws IOException {
        this.xmlFile = file;
        xml = new StringBuilder();
        indent = "";
        
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    }
    
    public void appendln(String s) {
        xml.append(indent).append(s).append("\n");
    }
    
    public void increaseIndent() {
        indent += "\t";
    }
    
    public void decreaseIndent() {
        indent = indent.substring(0, indent.length()-1);
    }
    
    public String getXML() {
        return xml.toString();
    }
    
    public void writeFile() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(xmlFile, false);
            writer.write(xml.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
