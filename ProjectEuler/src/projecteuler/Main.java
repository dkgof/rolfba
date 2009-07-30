/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projecteuler;

import projecteuler.problems.rolf.*;
import projecteuler.framework.ProjectEuler;
import projecteuler.problems.thor.*;

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

        pe.addProblem(new ProblemThor1());
        pe.addProblem(new ProblemThor2());
        pe.addProblem(new ProblemThor3());
        pe.addProblem(new ProblemThor4());

        pe.runProblems();
    }
}
