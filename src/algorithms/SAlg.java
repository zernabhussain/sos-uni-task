package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;
import static utils.algorithms.Misc.toro;
import static utils.algorithms.Misc.generateRandomSolution; //WARNING:  this method is incomplete and will affect the search. You will have to complete in Task 2
import static utils.algorithms.Misc.getRadius;

public class SAlg extends Algorithm {
	@Override
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception {

		FTrend FT = new FTrend();
		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();

		double[] elite = null;
		double[] bestTemp = null;
		double[] xShort = null;

		if (initialSolution != null) {  
			elite = initialSolution;
			bestTemp = initialSolution; 
		} else // if not inserted, we need to randomly sample the initial guess
		{
			elite = toro(generateRandomSolution(bounds, problemDimension), bounds);
			bestTemp = elite;
			xShort = elite;
		}

		// particle (the solution, i.e. "x")
//		double[] elite = toro(generateRandomSolution(bounds, problemDimension), bounds);
//
//		double[] bestTemp = elite;
//		double[] xShort = elite;

//		FT.add(0, problem.f(initGuess));

//		double[] radius = getRadius(bounds, 40);
		double radius = 0.40;
		int i = 0;
		// main loop
		while (i < maxEvaluations) {
			for (int j = 0; j < problemDimension; j++) {
				// init learning factor

				xShort[j] = elite[j] - radius;
				double[] toroCorr = toro(xShort, bounds);
				double fitness = problem.f(toroCorr);
				i++;
				if (fitness <= problem.f(bestTemp)) {
					bestTemp = toroCorr;
					FT.add(i, fitness);

				} else {
					xShort[j] = elite[j] + (radius / 2);
					toroCorr = toro(xShort, bounds);
					fitness = problem.f(toroCorr);
					i++;
					if (fitness <= problem.f(bestTemp)) {
						bestTemp = toroCorr;
						FT.add(i, fitness);

					}
//					xShort = bestTemp;

				}

			}

			if (problem.f(bestTemp) <= problem.f(elite)) {
				elite = bestTemp;
			} else {
				radius = radius / 2;
			}
//			i++;

		}
		finalBest = elite;
		double finalBest = problem.f(elite);
		i++;
////		// save the final best
		FT.add(i, finalBest);
//		i++;// add it to the txt file (row data)

		return FT; // return the fitness trend
	}
}
