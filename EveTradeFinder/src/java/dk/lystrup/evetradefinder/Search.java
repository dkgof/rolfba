/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.evetradefinder;

/**
 *
 * @author Rolf
 */
public class Search {
    private String from;
    private String to;
    private String type;
    
    public Search(String from, String to, String type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
}
