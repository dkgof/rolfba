/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.thor;

import projecteuler.problems.*;

/**
 *
 * @author Rolf
 */
public abstract class ProblemThor implements Problem {
    protected long result;

    public String getUser() {
        return "thor";
    }

    public long getResult() {
        return result;
    }

    public void setResult(long r) {
        result = r;
    }
}
