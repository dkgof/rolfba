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

    private Map<String, List<Problem>> problems;

    private ProjectEuler() {
        problems = new HashMap<String, List<Problem>>();
    }

    public static ProjectEuler singleton() {
        if( singleton == null ) {
            singleton = new ProjectEuler();
        }
        
        return singleton;
    }

    public void runProblems() {
        for(String key : problems.keySet()) {
            List<Problem> probs = problems.get(key);

            Solver.solve(probs, key);
        }
    }

    public void addProblem(String identifier, Problem p) {
        List<Problem> probs = problems.get(identifier);

        if( probs == null ) {
            probs = new ArrayList<Problem>();
            problems.put(identifier, probs);
        }

        probs.add(p);
    }
}
