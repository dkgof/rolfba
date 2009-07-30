/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems;

/**
 *
 * @author Rolf
 */
public interface Problem {
    public void init();
    public void run();
    public long getResult();
    public int problemNumber();
    public String getUser();
    public void setResult(long r);
}
