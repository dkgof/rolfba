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
public class ProblemRolf3 implements Problem {

    private long result;

    /**
     * The prime factors of 13195 are 5, 7, 13 and 29.
     * 
     * What is the largest prime factor of the number 600851475143 ?
     */

    public void init() {
        result = 0;
    }

    public void run() {
        long number = 600851475143l;

        long factor = 0;

        for(long f = 2; f < number / 2; f++) {
            if( number % f == 0 ) {
                factor = Math.max(factor, f);
                number /= f;
            }
        }

        if( number != 1) {
            factor = Math.max(factor, number);
        }

        result = factor;

    }

    public void printResult() {
        System.out.println("\tResult of problem 3 ["+result+"]");
    }
}
