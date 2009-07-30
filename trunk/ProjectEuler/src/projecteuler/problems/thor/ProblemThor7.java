/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.thor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thor
 */
public class ProblemThor7 extends ProblemThor{

    public void init() {
        result = 0;
    }

    public void run() {
        int i = 3;
        boolean isPrime;

        List<Integer> primes = new ArrayList<Integer>();
        primes.add(2);

        while(primes.size() < 10001){
            isPrime = true;
            for(int prime : primes) {
                if(i%prime == 0){
                    isPrime = false;
                    break;
                }
            }

            if(isPrime){
                primes.add(i);
            }
            i += 2;
        }
        result = primes.get(primes.size()-1);
    }

    public int problemNumber() {
        return 7;
    }

}
