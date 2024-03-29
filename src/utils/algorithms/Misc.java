/** @file Misc.java
 * MISCELLANEOUS
 *  @author Fabio Caraffini
*/
package utils.algorithms; 
import static utils.MatLab.max;
import static utils.MatLab.min; 
import java.util.Arrays; 
import utils.random.RandUtils;

/**
 * This class contains useful miscellaneous methods.
 */
public class Misc {

	/**
	 * Saturation on bounds of the search space.
	 * 
	 * @param x      solution to be saturated.
	 * @param bounds search space boudaries.
	 * @return x_tor corrected solution.
	 */
	public static double[] saturate(double[] x, double[][] bounds) {
		int n = x.length;
		double[] x_sat = new double[n];
		for (int i = 0; i < n; i++)
			x_sat[i] = min(max(x[i], bounds[i][0]), bounds[i][1]);
		return x_sat;
	}

	/**
	 * Clone a solution.
	 * 
	 * @param x solution to be duplicated.
	 * @return xc cloned solution.
	 */
	public static double[] clone(double[] x) {
		int n = x.length;
		double[] xc = new double[n];
		for (int i = 0; i < n; i++)
			xc[i] = x[i];
		return xc;
	}

	public static double[][] getArrayCopy(double[][] array) {
		return Arrays.copyOf(array, array.length);
	}

	public static double[] getArrayCopy(double[] array) {
		return Arrays.copyOf(array, array.length);
	}

	public static double[] getRadius(double[][] bounds, float percent) {
		int len = bounds.length;
		float divVal = percent / 100;
		double[] r = new double[len];
		for (int i = 0; i < len; i++) {
			double upperBound = bounds[i][1];
			double lowerBound = bounds[i][0];
			r[i] = (upperBound - lowerBound) * divVal;
		}
		return r;
	}

	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (hyper-parallelepiped).
	 * @param n      problam dimension.
	 * @return r randomly generated point.
	 */
	public static double[] getRadius(double[] bounds, float percent) {
		int len = bounds.length;
		double[] r = new double[len];
		for (int i = 0; i < len; i++) {
			r[i] = (bounds[1] - bounds[0]) * (percent / 100);
		}
		return r;

	}

	public static double[] divideArrayItemsBy(double[] array, int divideBy) {
		int len = array.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = array[i] / divideBy;
		}
		return result;
	}

	/**
	 * Return random number between given range (min , max).
	 */
	public static double getRandomNumber(double min, double max) {
		return (double) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (general case).
	 * @param n      problem dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[][] bounds, int n) {
		double[] r = new double[n];
		for (int i = 0; i < n; i++) {
			r[i] = getRandomNumber(bounds[i][0], bounds[i][1]);
		}
		return r;
	}

	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (hyper-parallelepiped).
	 * @param n      problam dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[] bounds, int n) {
		double[] r = new double[n];
		for (int i = 0; i < n; i++) {
			r[i] = getRandomNumber(bounds[0], bounds[1]);
		}
		return r;

	}

	/**
	 * Rounds x to the nearest integer towards zero.
	 */
	public static int fix(double x) {
		return (int) ((x >= 0) ? Math.floor(x) : Math.ceil(x));
	}

	/**
	 * Toroidal correction within the search space
	 * 
	 * @param x      solution to be corrected.
	 * @param bounds search space boundaries (hyper-parallelepiped).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[] bounds) {
		int n = x.length;
		double[] x_cor = new double[n];
		for (int i = 0; i < n; i++) {
			x_cor[i] = (x[i] - bounds[0]) / (bounds[1] - bounds[0]);
			if (x_cor[i] > 1) {
				x_cor[i] = x_cor[i] - fix(x_cor[i]);
			} else if (x_cor[i] < 0) {
				x_cor[i] = 1 - Math.abs(x_cor[i] - fix(x_cor[i]));
			} else {
				x_cor[i] = bounds[0] + x_cor[i] * (bounds[1] - bounds[0]);
			}
		}
		return x_cor;
	}

	/**
	 * Toroidal correction within search space
	 * 
	 * @param x      solution to be corrected.
	 * @param bounds search space boundaries (general case).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[][] bounds) {
		int n = x.length;
		double[] x_cor = new double[n];
		for (int i = 0; i < n; i++) {
			x_cor[i] = (x[i] - bounds[i][0]) / (bounds[i][1] - bounds[i][0]);
			if (x_cor[i] > 1) {
				x_cor[i] = x_cor[i] - fix(x_cor[i]);
			} else if (x_cor[i] < 0) {
				x_cor[i] = 1 - Math.abs(x_cor[i] - fix(x_cor[i]));
			} else {
				x_cor[i] = bounds[i][0] + x_cor[i] * (bounds[i][1] - bounds[i][0]);
			}
		}
		return x_cor;
	}

	public static double[] crossOver(double[] x, double[] y) {
		int n = x.length;
		int index = RandUtils.randomInteger(n - 1);
		double[] newX = getArrayCopy(x);

		do {
			if (index >= n) {
				break;
			}
			newX[index] = y[index];
			index++;
		} while ((RandUtils.random() <= 1.00));

		return newX;
	}

}
