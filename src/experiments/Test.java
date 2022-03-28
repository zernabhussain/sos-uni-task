package experiments;

import algorithms.ISPO;
import algorithms.SAlg;
import algorithms.CMAES;
//import benchmarks.BaseFunctions.Ackley;
//import benchmarks.BaseFunctions.Alpine;
//import benchmarks.BaseFunctions.Michalewicz;
//import benchmarks.BaseFunctions.Rastigin;
//import benchmarks.BaseFunctions.Schwefel;
//import benchmarks.BaseFunctions.Rosenbrock;
//import benchmarks.BaseFunctions.Sphere;
import benchmarks.BaseFunctions.*;
import interfaces.Algorithm;
import interfaces.Experiment;
import interfaces.Problem;

public class Test extends Experiment
{
	
	public Test(int probDim)
	{
		super(probDim,"TEST");
			
		Algorithm a;// ///< A generic optimiser.
	    Problem p;// ///< A generic problem.

		a = new ISPO();
		a.setParameter("p0", 1.0);
		a.setParameter("p1", 10.0);
		a.setParameter("p2", 2.0);
		a.setParameter("p3", 4.0);
		a.setParameter("p4", 1e-5);
		a.setParameter("p5", 30.0);
		add(a); //add it to the list

		a = new CMAES(); //N.B. this algorithm makes use of "generateRandomSolution" that has still to be implemented.
		add(a);
 	
		a = new SAlg();  
		add(a);
 	
		
		p= new Sphere(probDim);
		add(p);
		
		p= new Schwefel(probDim);
		add(p);
 
		p= new Rastigin(probDim);
		add(p);
// 
		p= new Michalewicz(probDim);
		add(p);
 		 
		p = new Ackley(probDim);
		add(p);//add it to the list
		p = new Alpine(probDim);
		add(p);
		p = new Rosenbrock(probDim);
		add(p);

	}
}
