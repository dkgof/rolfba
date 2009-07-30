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
        System.out.println("\nStarting problem solver for problem "+problems.get(0).problemNumber()+"...");

        double slowest = 0;
        double fastest = Double.MAX_VALUE;

        Problem slowestProblem = null;
        Problem fastestProblem = null;

        Timer totalTimer = Timer.startTimer();
        for(Problem p : problems) {
            Timer unitTimer = Timer.startTimer();
            p.init();
            p.run();
            System.out.println("\tProblem "+p.problemNumber()+" - "+p.getUser()+": ["+p.getResult()+"] - "+unitTimer.printTime());

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

        double time = totalTimer.time();
        double avg = time / problems.size();

        System.out.println("\n\tFastest: "+fastestProblem);
        System.out.println("\tSlowest: "+slowestProblem);
        System.out.println("Done: "+totalTimer.printTime()+" Avg time / problem: "+String.format("%.4f", avg)+" seconds");
    }
}
