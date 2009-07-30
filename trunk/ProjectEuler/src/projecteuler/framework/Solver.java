/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.framework;

import java.util.List;
import projecteuler.problems.Problem;
import projecteuler.utils.Timer;

/**
 *
 * @author Rolf
 */
public class Solver {
    public static void solve(List<Problem> problems) {
        System.out.println("");

        //System.out.println("Starting problem solver for problem "+problems.get(0).problemNumber()+"...");

        double slowest = 0;
        double fastest = Double.MAX_VALUE;

        Problem slowestProblem = null;
        Problem fastestProblem = null;

        long avgSum = 0;

        Timer totalTimer = Timer.startTimer();
        for(Problem p : problems) {
            Timer unitTimer = Timer.startTimer();
            p.init();
            p.run();
            System.out.println("\tProblem "+p.problemNumber()+" - "+p.getUser()+": ["+p.getResult()+"] - "+unitTimer.printTime());

            avgSum += p.getResult();

            double unitTime = unitTimer.time();

            if( unitTime < fastest ) {
                fastest = unitTime;
                fastestProblem = p;
            }

            if(unitTime > slowest) {
                slowest = unitTime;
                slowestProblem = p;
            }
        }

        avgSum /= problems.size();

        if( avgSum != problems.get(0).getResult() ) {
            System.out.println("!!! Mismatch in results !!!");
        }

        double time = totalTimer.time();
        double avg = time / problems.size();

        /*
        System.out.println("\n\tFastest: "+fastestProblem);
        System.out.println("\tSlowest: "+slowestProblem);
        System.out.println("Done: "+totalTimer.printTime()+" Avg time / problem: "+String.format("%.4f", avg)+" seconds");
        */
    }
}
