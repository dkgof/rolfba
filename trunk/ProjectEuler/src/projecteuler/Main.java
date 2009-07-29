/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler;

import projecteuler.problems.rolf.Problem2;
import projecteuler.problems.rolf.Problem7;
import projecteuler.problems.rolf.Problem5;
import projecteuler.problems.rolf.Problem6;
import projecteuler.problems.rolf.Problem1;
import projecteuler.problems.rolf.Problem4;
import projecteuler.problems.rolf.Problem8;
import projecteuler.problems.rolf.Problem3;
import java.util.ArrayList;
import java.util.List;
import projecteuler.problems.*;
import projecteuler.utils.Timer;

/**
 *
 * @author Rolf
 */
public class Main {

    public Main() {
        List<Problem> problemsRolf = new ArrayList<Problem>();

        problemsRolf.add(new Problem1());
        problemsRolf.add(new Problem2());
        problemsRolf.add(new Problem3());
        problemsRolf.add(new Problem4());
        problemsRolf.add(new Problem5());
        problemsRolf.add(new Problem6());
        problemsRolf.add(new Problem7());
        problemsRolf.add(new Problem8());

        solve(problemsRolf, "Rolf");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
    }

    private void solve(List<Problem> problems, String identifier) {
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
