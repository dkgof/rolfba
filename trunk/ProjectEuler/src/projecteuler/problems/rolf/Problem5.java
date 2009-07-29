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
public class Problem5 implements Problem {

    private long result;

    /**
     * 2520 is the smallest number that can be divided by each of the numbers
     * from 1 to 10 without any remainder.
     *
     * What is the smallest number that is evenly divisible by all of the
     * numbers from 1 to 20?
     */

    public void init() {
        result = 0;
    }

    public void run() {
        int number = 20;
        boolean ok = true;

        while(true) {
            number += 20;

            ok = true;

            for(int f = 3; f < 21; f++) {
                ok = ok && (number % f == 0);
            }

            if( ok ) {
                result = number;
                break;
            }
        }
    }

    public void printResult() {
        System.out.println("\tResult of problem 5 ["+result+"]");
    }
}
