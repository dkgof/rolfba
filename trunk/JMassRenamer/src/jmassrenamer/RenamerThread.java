/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jmassrenamer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rolf
 */
public class RenamerThread implements Runnable {
    private final Pattern fromPattern;
    private final String toPattern;
    private final File directory;
    private final boolean recursive;
    private final MassRenamerGUI gui;

    public RenamerThread(String fromPattern, String toPattern, File directory, boolean recursive, MassRenamerGUI gui) {
        this.fromPattern = Pattern.compile(fromPattern);
        this.toPattern = toPattern;
        this.directory = directory;
        this.recursive = recursive;
        this.gui = gui;
    }

    public void run() {
        doRecursiveRenaming(directory);
    }

    private void doRecursiveRenaming(File directory) {
        for(File f : directory.listFiles()) {
            if( f.isDirectory() ) {
                if( recursive ) {
                    doRecursiveRenaming(f);
                }
            }
            else {
                Matcher m = fromPattern.matcher(f.getName());
                if( m.matches() ) {
                    String newName = ""+toPattern;
                    for(int i = 1; i <= m.groupCount(); i++) {
                        String match = m.group(i);
                        newName = newName.replaceAll("%"+i, match);
                    }
                    System.out.print("Renaming ["+f.getName()+"] --> ["+newName+"]");
                    File toFile = new File(f.getParentFile().getAbsolutePath()+"/"+newName);
                    boolean ok = f.renameTo(toFile);
                    System.out.println(" - "+ok);
                }
            }
        }

        gui.enableEverything();
    }

}
