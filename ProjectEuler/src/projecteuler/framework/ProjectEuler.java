/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import projecteuler.problems.Problem;

/**
 *
 * @author Rolf
 */
public class ProjectEuler {

    private static ProjectEuler singleton;

    private Map<Integer, List<Problem>> problems;

    private ProjectEuler() {
        problems = new HashMap<Integer, List<Problem>>();
    }

    public static ProjectEuler singleton() {
        if( singleton == null ) {
            singleton = new ProjectEuler();
        }
        
        return singleton;
    }

    public void runProblems() {
        Map<Integer,Map<String,Double>> times = new HashMap<Integer, Map<String, Double>>();
        for(int key : problems.keySet()) {
            List<Problem> probs = problems.get(key);

            times.put(key, Solver.solve(probs));
        }

        System.out.println("\n");
    }

    public void addProblem(Problem p) {
        List<Problem> probs = problems.get(p.problemNumber());

        if( probs == null ) {
            probs = new ArrayList<Problem>();
            problems.put(p.problemNumber(), probs);
        }

        probs.add(p);
    }
}
