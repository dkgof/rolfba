/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.thor;

/**
 *
 * @author Thor
 */
public class ProblemThor5 extends ProblemThor{

    public void init() {
        result = 0;
    }

    public void run() {
        boolean found = false;
        int i = 20;
        while(!found){
            for(int a = 1; a < 21; a++){
                if(i%a != 0){
                    break;
                }
                if(a == 20){
                    found = true;
                }
            }
            i += 20;
        }
        result = i-20;
    }

    public int problemNumber() {
        return 5;
    }

}
