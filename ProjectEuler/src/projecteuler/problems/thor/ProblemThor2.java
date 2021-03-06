/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.thor;

/**
 *
 * @author Thor
 */
public class ProblemThor2 extends ProblemThor {

    public void init() {
       result = 2;
    }

    /*Each new term in the Fibonacci sequence is generated by adding the
      previous two terms. By starting with 1 and 2, the first 10 terms will be:

      1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...

      Find the sum of all the even-valued terms in the sequence which do not
      exceed four million.
     */
    public void run() {
        int a = 1;
        int b = 2;
        int c = a + b;
        while(c < 4000000){
            a = b;
            b = c;
            c = a + b;
            if(c%2 == 0){
                result += c;
            }
        }
    }

    public int problemNumber() {
       return 2;
    }

}
