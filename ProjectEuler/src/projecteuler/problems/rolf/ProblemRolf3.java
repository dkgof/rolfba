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
public class ProblemRolf3 extends ProblemRolf {

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

        while( number % 2 == 0 ) {
            number /= 2;
        }
        
        for(long f = 3; f < number / 2; f+=2) {
            while( number % f == 0 ) {
                number /= f;
            }
        }

        result = number;

    }

    public int problemNumber() {
        return 3;
    }
}
