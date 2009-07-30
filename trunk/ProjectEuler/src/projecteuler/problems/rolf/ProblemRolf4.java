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
public class ProblemRolf4 extends ProblemRolf {

    /**
     * A palindromic number reads the same both ways. The largest palindrome
     * made from the product of two 2-digit numbers is 9009 = 91  99.
     * 
     * Find the largest palindrome made from the product of two 3-digit numbers.
     */

    public void init() {
        result = 0;
    }

    public void run() {
        for(int i = 100; i<1000; i++) {
            for(int j = 100; j<1000; j++) {
                int t = i * j;

                if(checkPalindrome(t)) {
                    result = Math.max(result, t);
                }
            }
        }
    }

    private boolean checkPalindrome(int t) {
        String palin = ""+t;

        if( palin.length() % 2 != 0) {
            return false;
        }

        String sub1 = palin.substring(0, palin.length()/2);
        String sub2 = palin.substring(palin.length()/2);

        String sub22 = "";

        for(int i = sub2.length()-1; i>=0; i--) {
            sub22 += sub2.charAt(i);
        }

        return sub22.equals(sub1);
    }

    public int problemNumber() {
        return 4;
    }
}
