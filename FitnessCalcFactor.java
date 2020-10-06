package Evolution;

/**
 * Container class that calculates a given fitness factor
 *
 */
public class FitnessCalcFactor {

	private FitnessCalculationFactors _factor;
	private double _multiplier;

	/**
	 * Constructor for fitnesscalcfactor. 
	 */
	public FitnessCalcFactor(FitnessCalculationFactors factor, double multiplier) {
		_factor = factor;
		_multiplier = multiplier;
	}

	/**
	 * Gets the factors value by multiplying the actual value with the associated weight assigned to this factor
	 */
	public double getCalculation(Pipe p, Bird b) {
		return _multiplier * _factor.calculateFitness(p, b);
	}

	/**
	 * gets the actual factor
	 */
	public FitnessCalculationFactors getFactor() {
		return _factor;
	}

	/**
	 * gets the weight associated with this factor.
	 */
	public double getMultiplier() {
		return _multiplier;
	}
}
