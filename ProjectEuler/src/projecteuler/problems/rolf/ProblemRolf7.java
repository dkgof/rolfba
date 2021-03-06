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
public class ProblemRolf7 extends ProblemRolf {

    /**
     * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can
     * see that the 6th prime is 13.
     *
     * What is the 10001st prime number?
     */

    public void init() {
        result = 0;
    }

    public void run() {
        List<Integer> primes = new LinkedList<Integer>();

        int max = 105000;
        int count = 0;

        primes.add(2);

        for(int i = 3; i <= max; i+=2) {
            primes.add(i);
        }

        while( count < 10001 ) {
            count++;

            int p = primes.get(0);

            result = p;

            Iterator<Integer> it = primes.iterator();
            while(it.hasNext()) {
                int i = it.next();

                if( i % p == 0 ) {
                    it.remove();
                }
            }
        }
    }

    public int problemNumber() {
        return 7;
    }
}
