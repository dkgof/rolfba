/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems;

/**
 *
 * @author Rolf
 */
public abstract class ProblemRolf implements Problem {
    protected long result;

    public String getUser() {
        return "rolf";
    }

    public long getResult() {
        return result;
    }

    public void setResult(long r) {
        result = r;
    }
}
