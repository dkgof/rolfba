/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import projecteuler.problems.Problem;
import projecteuler.utils.Timer;

/**
 *
 * @author Rolf
 */
public class Solver {
    public static Map<String,Double> solve(List<Problem> problems) {
        Map<String,Double> times = new HashMap<String,Double>();

        System.out.println("");

        long avgSum = 0;

        for(Problem p : problems) {
            Timer unitTimer = Timer.startTimer();
            p.init();
            p.run();
            double time = unitTimer.time();
            System.out.println("\tProblem "+p.problemNumber()+" - "+p.getUser()+": ["+p.getResult()+"] - "+time);

            avgSum += p.getResult();
            times.put(p.getUser(), time);
        }

        avgSum /= problems.size();

        if( avgSum != problems.get(0).getResult() ) {
            System.out.println("!!! Mismatch in results !!!");
        }

        

        return times;
    }
}
