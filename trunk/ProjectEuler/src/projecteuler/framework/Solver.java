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
    public static void solve(List<Problem> problems, String identifier) {
        System.out.println("Starting problem solver ("+identifier+")...");

        double slowest = 0;
        double fastest = Double.MAX_VALUE;

        Problem slowestProblem = null;
        Problem fastestProblem = null;

        Timer totalTimer = Timer.startTimer();
        for(Problem p : problems) {
            Timer unitTimer = Timer.startTimer();
            p.init();
            p.run();
            p.printResult();
            System.out.println("\tTime: "+unitTimer.printTime()+"\n");

            double unitTime = unitTimer.time();

            if( unitTime < fastest ) {
                fastest = unitTime;
                fastestProblem = p;
            }
            else if(unitTime > slowest) {
                slowest = unitTime;
                slowestProblem = p;
            }
        }

        double time = totalTimer.time();
        double avg = time / problems.size();

        System.out.println("Done: "+totalTimer.printTime()+" Avg time / problem: "+String.format("%.4f", avg)+" seconds");

        System.out.println("Fastest problem was: "+fastestProblem+" ("+String.format("%.4f", fastest)+") seconds");
        System.out.println("Slowest problem was: "+slowestProblem+" ("+String.format("%.4f", slowest)+") seconds");
    }
}
