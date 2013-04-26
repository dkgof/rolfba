/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelauncherpro.config;

import gamelauncherpro.ui.ShowGamePanel;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Marianne
 */
public class GameDB {
    private static GameDB singleton;
    
    private final ExecutorService executor;
    private final List<GameConfig> games;
    
    private GameDB() {
        executor = Executors.newCachedThreadPool();
        games = new LinkedList<>();
        loadGames();
    }
    
    public synchronized static GameDB singleton() {
        if(singleton == null) {
            singleton = new GameDB();
        }
        
        return singleton;
    }

    private void loadGames() {
        try {
            XMLLoader xml = new XMLLoader(new File("gamedb.xml"));
            List<Node> gameNodes = xml.getElements("/games/game");
            
            for(Node game : gameNodes) {
                NamedNodeMap attributes = game.getAttributes();
                String name = attributes.getNamedItem("name").getTextContent();
                String launchPath = attributes.getNamedItem("launchPath").getTextContent();
                String coverPath = attributes.getNamedItem("coverPath").getTextContent();
                String screenshotPath = attributes.getNamedItem("screenshotPath").getTextContent();
                
                GameConfig gc = new GameConfig(name);
                gc.setCoverPath(coverPath);
                gc.setLaunchPath(launchPath);
                gc.setScreenshotPath(screenshotPath);
                
                games.add(gc);
            }            
        } catch (XPathExpressionException | ParserConfigurationException | IOException | SAXException ex) {
            //Error loading games
            System.out.println("Error loading games: "+ex);
        }
    }
    
    public void save() {
        try {
            XMLSaver xml = new XMLSaver(new File("gamedb.xml"));
            xml.appendln("<games>");
            xml.increaseIndent();
            
            for(GameConfig gc : games) {
                gc.saveXML(xml);
            }
            
            xml.decreaseIndent();
            xml.appendln("</games>");
            
            xml.writeFile();
        } catch (IOException ex) {
            Logger.getLogger(GameDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addGame(GameConfig gc) {
        games.add(gc);
    }
    
    public void launchGame(GameConfig gc) {
        executor.submit(new RunCommand(gc));
    }

    public List<GameConfig> getGames() {
        return games;
    }

    public class RunCommand implements Runnable {

        private GameConfig gameConfig;
        
        public RunCommand(GameConfig gc) {
            this.gameConfig = gc;
        }
        
        @Override
        public void run() {
            try {
                System.out.println("Starting: "+gameConfig.getName());
                ProcessBuilder pb = new ProcessBuilder(gameConfig.getLaunchPath());
                Process gameProcess = pb.start();
                gameProcess.waitFor();
                System.out.println("Closed: "+gameConfig.getName());
            } catch (InterruptedException ex) {
                Logger.getLogger(ShowGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ShowGamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
