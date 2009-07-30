/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.thor;

/**
 *
 * @author Thor
 */
public class ProblemThor6 extends ProblemThor{

    public void init() {
        result = 0;
    }

    public void run() {
        int sumOfSquare = 0;
        int squareOfSum = 0;
        for(int i = 1; i < 101; i++){
            sumOfSquare += i*i;
            squareOfSum += i;
        }
        squareOfSum *= squareOfSum;
        result = Math.abs(squareOfSum - sumOfSquare);
    }

    public int problemNumber() {
        return 6;
    }

}
