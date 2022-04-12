package algorithms;

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;
import static utils.algorithms.Misc.*;

public class SAlg extends Algorithm {
	@Override
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception {

		FTrend FT = new FTrend();
		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();

		double[] elite = null;
		double fElite;

		double[] temp = null;
		double fTemp;

		int i = 0;
		if (initialSolution != null) {
			elite = initialSolution;
			fElite = initialFitness;
		} else {
			elite = generateRandomSolution(bounds, problemDimension);
			fElite = problem.f(elite);
			i++;
			FT.add(i, fElite);
		}

		int internalBudget = 150;
		// main loop
		while (i < maxEvaluations) {

			double[] radius = getRadius(bounds, 40);
			temp = getArrayCopy(elite);
			fTemp = fElite;

			for (int k = 0; k < internalBudget && i < maxEvaluations; k++) {
				double[] xShort = getArrayCopy(temp);

				for (int j = 0; j < problemDimension && i < maxEvaluations; j++) {

					xShort[j] = temp[j] - radius[j];
					double[] toroCorr = toro(xShort, bounds);
					double fitness = problem.f(toroCorr);
					i++;

					if (fitness < fElite) {
						fElite = fitness;
						elite = getArrayCopy(xShort);
						FT.add(i, fitness);

					} else {
						xShort = getArrayCopy(temp);
						xShort[j] = temp[j] + ((double) radius[j] / (double) 2);
						toroCorr = toro(xShort, bounds);
						fitness = problem.f(toroCorr);
						i++;

						if (fitness < fElite) {
							fElite = fitness;
							elite = getArrayCopy(xShort);
							FT.add(i, fitness);

						}
					}

				}

				if (fElite < fTemp) {
					fTemp = fElite;
					temp = getArrayCopy(elite);

				} else {
					radius = divideArrayItemsBy(radius, 2);
				}
			}
		}
		finalBest = elite;
		FT.add(i, fElite);

		return FT; // return the fitness trend
	}
}
