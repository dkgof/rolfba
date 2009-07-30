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
            for(int j = 100; j<i; j++) {
                int t = i * j;

                if(checkPalindrome(t)) {
                    result = Math.max(result, t);
                }
            }
        }
    }

    private boolean checkPalindrome(int t) {
        String palin = ""+t;

        String sub1;
        String sub2;

        if( palin.length() % 2 == 0) {
            sub1 = palin.substring(0, palin.length()/2);
            sub2 = palin.substring(palin.length()/2);
        }
        else {
            sub1 = palin.substring(0, palin.length()/2);
            sub2 = palin.substring(palin.length()/2+1);
        }

        for(int i = 0; i<sub1.length();i++) {
            if( sub1.charAt(i) != sub2.charAt(sub2.length()-i-1) ) {
                return false;
            }
        }

        return true;
    }

    public int problemNumber() {
        return 4;
    }
}
