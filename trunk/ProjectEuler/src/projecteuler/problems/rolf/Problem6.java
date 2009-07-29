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
public class Problem6 implements Problem {

    private long result;

    /**
     * The sum of the squares of the first ten natural numbers is,
     *
     * 12 + 22 + ... + 102 = 385
     * The square of the sum of the first ten natural numbers is,
     *
     * (1 + 2 + ... + 10)2 = 552 = 3025
     * Hence the difference between the sum of the squares of the first ten
     * natural numbers and the square of the sum is 3025  385 = 2640.
     *
     * Find the difference between the sum of the squares of the first one
     * hundred natural numbers and the square of the sum.
     */

    public void init() {
        result = 0;
    }

    public void run() {
        long sumofsquares = 0;
        long squareofsum = 0;

        for(int i = 1; i<=100; i++) {
            sumofsquares += i*i;
            squareofsum += i;
        }

        squareofsum *= squareofsum;

        result = Math.abs(squareofsum - sumofsquares);
    }

    public void printResult() {
        System.out.println("\tResult of problem 6 ["+result+"]");
    }
}
