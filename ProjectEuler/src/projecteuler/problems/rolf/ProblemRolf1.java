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
public class ProblemRolf1 extends ProblemRolf {

    /**
     * If we list all the natural numbers below 10 that are multiples of 3 or 5,
     * we get 3, 5, 6 and 9. The sum of these multiples is 23.
     * 
     * Find the sum of all the multiples of 3 or 5 below 1000.
     */

    public void init() {
        result = 0;
    }

    public void run() {
        int max = 1000 / 3;
        int t;

        //All numbers that are multiples of 3 below 1000
        for(int i = 1; i<=max; i += 1) {
            result += i*3;

            t = i*5;

            if( t<1000 && t%3 != 0 ) {
                result += t;
            }
        }
    }

    public int problemNumber() {
        return 1;
    }
}
