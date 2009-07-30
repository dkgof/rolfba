/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.rolf;

import projecteuler.problems.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rolf
 */
public class ProblemRolf10 extends ProblemRolf {

    /**
     * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can
     * see that the 6th prime is 13.
     *
     * What is the 10001st prime number?
     */

    public void init() {
        result = 2;
    }

    public void run() {
        List<Integer> primes = new LinkedList<Integer>();

        primes.add(2);

        int testNumber = 3;
        int halfTestNumber;
        boolean isPrime;

        while(testNumber <= 2000000) {

            isPrime = true;

            halfTestNumber = testNumber / 2;

            for(int f : primes) {
                if( f > halfTestNumber) {
                    break;
                }
                if(testNumber%f == 0) {
                    isPrime = false;
                    break;
                }
            }

            if(isPrime) {
                primes.add(testNumber);
                result += testNumber;
            }

            testNumber++;
        }
    }

    public int problemNumber() {
        return 10;
    }
}
