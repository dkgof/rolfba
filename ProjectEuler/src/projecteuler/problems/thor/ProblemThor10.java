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
public class ProblemThor10 extends ProblemThor{

    public void init() {
        result = 2;
    }

    public void run() {

       int i = 3;
       boolean isPrime = true;

       List<Integer> primeNumbers = new ArrayList<Integer>();
       primeNumbers.add(2);

       while(i <= 2000000){
           isPrime = true;
           for(int a : primeNumbers){
               if(i%a == 0){
                   isPrime = false;
                   break;
               }
           }

           if(isPrime){
               primeNumbers.add(i);
               result += i;
           }
           i += 2;
       }
    }

    public int problemNumber() {
        return 10;
    }

}
