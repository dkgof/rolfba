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
public class Problem1 implements Problem {

    private int result;

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
        //All numbers that are multiples of 3 below 1000
        for(int i = 3; i<1000; i += 3) {
            if( i%5 != 0) {
                result += i;
            }
        }

        //All numbers that are multiples of 5 below 1000
        for(int i = 5; i<1000; i += 5) {
            result += i;
        }
    }

    public void printResult() {
        System.out.println("\tResult of problem 1 ["+result+"]");
    }
}
