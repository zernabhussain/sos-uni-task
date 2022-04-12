package algorithms;

import static utils.algorithms.Misc.*;
import utils.RunAndStore.FTrend;
import interfaces.Algorithm;
import interfaces.Problem;

public class GA_SA_EA_MEME extends Algorithm {
	@Override
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception {

		SAlg sAlgo = new SAlg();
		CMAES cmes = new CMAES();
		FTrend FT = new FTrend();

		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();

		int initMaxEvaluations = maxEvaluations / problemDimension;

		double[] best;
		double fBest;

		int i = 0;
		if (initialSolution != null) {
			best = initialSolution;
			fBest = initialFitness;
		} else {
			best = generateRandomSolution(bounds, problemDimension);
			fBest = problem.f(best);
			i++;
		}

		FT.add(i, fBest);

		FTrend sAlgoFt = null;
		FTrend sCMESFt = null;
		double[] xTemp;
		double fTemp;

		while (i < maxEvaluations) {

			xTemp = generateRandomSolution(bounds, problemDimension);
			xTemp = crossOver(best, xTemp);
			fTemp = problem.f(xTemp);
			i++;

			sAlgo.setInitialSolution(xTemp);
			sAlgo.setInitialFitness(fTemp);

			cmes.setInitialSolution(xTemp);
			cmes.setInitialFitness(fTemp);
			
			if (fTemp < fBest) {
				fBest = fTemp;
				best = getArrayCopy(xTemp);
				FT.add(i, fBest);
			}

			sAlgoFt = sAlgo.execute(problem, initMaxEvaluations);
			i += initMaxEvaluations;

			xTemp = sAlgo.getFinalBest();
			fTemp = sAlgoFt.getF(sAlgoFt.size() - 1);

			if (fTemp < fBest) {
				fBest = fTemp;
				best = getArrayCopy(xTemp);
			}
			
			sCMESFt = cmes.execute(problem, initMaxEvaluations);
			i += initMaxEvaluations;

			xTemp = sAlgo.getFinalBest();
			fTemp = sCMESFt.getF(sCMESFt.size() - 1);

			if (fTemp < fBest) {
				fBest = fTemp;
				best = getArrayCopy(xTemp);
			}
		}

		FT.add(i, fBest);
		finalBest = best;
		return FT;

	}

}