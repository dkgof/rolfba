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
public class ProblemRolf5 extends ProblemRolf {

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

    int[] divisors = {20,19,18,17,16,15,14,13,12,11};

    public void run() {
        int number = 20;
        boolean found;

        while(true) {
            number += 20;

            found = true;

            for(int f : divisors) {
                if(number%f != 0) {
                    found = false;
                    break;
                }
            }

            if( found ) {
                result = number;
                break;
            }
        }
    }

    public int problemNumber() {
        return 5;
    }
}
