package Evolution;

/**
 *
 * Enumeration representing all of the fitness calculation factors
 */
public enum FitnessCalculationFactors {
	TOTAL_X_TRAVELED("Total X Dist"), X_DIST_NEXT_PIPE("X Dist to Next Pipe"),
	Y_DIST_PIPE_MIDDLE("Y Dist to Center of Next Pipe");

	private String _name;

	/**
	 * Constructor. Takes in a string representation of the fitness calc factor
	 */
	private FitnessCalculationFactors(String s) {
		_name = s;
	}

	/**
	 * Calculates the fitness of this factor given the bird and next pipe
	 */
	public double calculateFitness(Pipe p, Bird b) {
		switch (this) {
		case TOTAL_X_TRAVELED: {
			return Math.abs(b.getX());
		}
		case X_DIST_NEXT_PIPE: {
			return Math.abs(p.getX() - b.getX());
		}
		case Y_DIST_PIPE_MIDDLE: {
			return Math.abs((((p.getTopY() - p.getBottomY()) / 2) + p.getTopY()) - b.getY());
		}
		default:
			return 0;
		}
	}

	/**
	 * returns the string representation of this factor
	 */
	public String getName() {
		return _name;
	}
}
