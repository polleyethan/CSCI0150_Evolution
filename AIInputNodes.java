package Evolution;

/**
 *
 * Enumerationb representing the different possible input nodes.
 */
public enum AIInputNodes {

	Y_DIST_TOP_PIPE("Y Distance To Top Pipe"), Y_DIST_BOTTOM_PIPE("Y Distance To Bottom Pipe"),
	X_DISTANCE_PIPE("X Distance To Pipe"), Y_VEL("Y Velocity"), Y_DIST_TOP_SCREEN("Y Distance To Top of Screen"),
	Y_DIST_BOTTOM_SCREEN("Y Distance To Bottom of Screen"), X_VEL("X Velocity");

	private String _stringrep;

	/**
	 * Private constructor for an Input node taking in a string representation of the input.
	 */
	private AIInputNodes(String s) {
		_stringrep = s;
	}

	/**
	 * Calculates the value of the input node given the bird and next pipe.
	 */
	public double calculateVal(Pipe p, Bird b) {
		switch (this) {
		case Y_DIST_TOP_PIPE: {
			return b.getY() - p.getTopY();
		}
		case Y_DIST_BOTTOM_PIPE: {
			return p.getBottomY() - b.getY();
		}
		case X_DISTANCE_PIPE: {
			return p.getX() - b.getX();
		}
		case Y_VEL: {
			return b.getYVel();
		}
		case Y_DIST_TOP_SCREEN: {
			return b.getY();
		}
		case Y_DIST_BOTTOM_SCREEN: {
			return b.getGame().getSettings().getHeight() - b.getY();
		}
		case X_VEL: {
			return b.getGame().getSettings().getXVel();
		}
		default: {
			return 0;
		}
		}
	}

	/**
	 * Calculates the normalized value of the input node given the bird and next pipe.
	 */
	public double calculateNormalizedVal(Pipe p, Bird b) {
		switch (this) {
		case Y_DIST_TOP_PIPE: {
			return normalize(this.calculateVal(p, b), b.getGame().getSettings().getHeight(), 0);
		}
		case Y_DIST_BOTTOM_PIPE: {
			return normalize(this.calculateVal(p, b), b.getGame().getSettings().getHeight(), 0);
		}
		case X_DISTANCE_PIPE: {
			return normalize(this.calculateVal(p, b), b.getGame().getSettings().getSep(), 0);
		}
		case Y_VEL: {
			return normalize(this.calculateVal(p, b), b.getGame().getSettings().getGravity() * 20,
					b.getGame().getSettings().getGravity() * -15);
		}
		case Y_DIST_TOP_SCREEN: {
			return normalize(this.calculateVal(p, b), b.getGame().getSettings().getHeight(), 0);
		}
		case Y_DIST_BOTTOM_SCREEN: {
			return normalize(this.calculateVal(p, b), b.getGame().getSettings().getHeight(), 0);
		}
		case X_VEL: {
			return normalize(this.calculateVal(p, b), Constants.X_VEL_BOUNDS[1], Constants.X_VEL_BOUNDS[0]);
		}
		default: {
			return 0;
		}
		}
	}

	/**
	 * Normalizes a value
	 */
	public static double normalize(double val, double max, double min) {
		return (val - min) / (max - min);
	}

	/**
	 * returns the string representation of an input node.
	 */
	public String getString() {
		return _stringrep;

	}

}
