/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler;

import projecteuler.problems.rolf.ProblemRolf2;
import projecteuler.problems.rolf.ProblemRolf7;
import projecteuler.problems.rolf.ProblemRolf5;
import projecteuler.problems.rolf.ProblemRolf6;
import projecteuler.problems.rolf.ProblemRolf1;
import projecteuler.problems.rolf.ProblemRolf4;
import projecteuler.problems.rolf.ProblemRolf8;
import projecteuler.problems.rolf.ProblemRolf3;
import projecteuler.framework.ProjectEuler;

/**
 *
 * @author Rolf
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProjectEuler pe = ProjectEuler.singleton();

        pe.addProblem(new ProblemRolf1());
        pe.addProblem(new ProblemRolf2());
        pe.addProblem(new ProblemRolf3());
        pe.addProblem(new ProblemRolf4());
        pe.addProblem(new ProblemRolf5());
        pe.addProblem(new ProblemRolf6());
        pe.addProblem(new ProblemRolf7());
        pe.addProblem(new ProblemRolf8());

        pe.runProblems();
    }
}
