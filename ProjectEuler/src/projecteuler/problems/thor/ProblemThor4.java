/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.problems.thor;

/**
 *
 * @author Thor
 */
public class ProblemThor4 extends ProblemThor{

    public void init() {
        result = 0;
    }

    public void run(){
        for(int a = 100; a < 1000; a++){
            for(int b = 100; b < 1000; b++){
                int c = a * b;

                boolean ok = checkPalindrome(c);

                if(ok) {
                    result = Math.max(result, c);
                }
            }
        }
    }

    public int problemNumber() {
        return 4;
    }

    private boolean checkPalindrome(int c) {
        String pal = ""+c;

        String h = "";
        String v = "";

        if(pal.length()%2 == 0){
            v = pal.substring(0, pal.length()/2);
            h = pal.substring(pal.length()/2);
        }

        else{
            v = pal.substring(0, pal.length()/2);
            h = pal.substring(pal.length()/2 + 1);
        }
        
        for(int i = 0; i < v.length(); i++){
            if (v.charAt(i) != h.charAt(h.length()-i-1)){
                return false;
            }
        }

        return true;
    }

}
