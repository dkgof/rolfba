/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.rolf;

import projecteuler.problems.*;

/**
 *
 * @author Rolf
 */
public class ProblemRolf2 extends ProblemRolf {

    /**
     * Each new term in the Fibonacci sequence is generated by adding the
     * previous two terms. By starting with 1 and 2, the first 10 terms will be:
     * 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
     *
     * Find the sum of all the even-valued terms in the sequence which do not
     * exceed four million.
     */

    public void init() {
        result = 0;
    }

    public void run() {
        int a = 1;
        int b = 2;
        int c;

        while( b <= 4000000 ) {
            if( b % 2 == 0 ) {
                result += b;
            }

            c = a;
            a = b;
            b = c+a;
        }
    }

    public int problemNumber() {
        return 2;
    }
}
