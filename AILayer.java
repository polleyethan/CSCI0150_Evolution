package Evolution;

/**
 *
 * Abstract class for an AILayer. Held in Neural Network
 */
public abstract class AILayer {

	protected double[][] _weights;
	protected int _numNodes;
	protected AILayer _nextLayer;

	/**
	 * Constructor for random AILayer
	 */
	public AILayer(int numNodes, int previousNumNodes) {
		_numNodes = numNodes;
		_weights = new double[_numNodes][previousNumNodes];
		this.createRandomWeights();
	}

	/**
	 * Constructor for AILayer with given weights
	 */
	public AILayer(double[][] weights, int numNodes) {
		_weights = weights;
		_numNodes = numNodes;
	}

	/**
	 * sets the next layer for a given AILayer
	 */
	public void setNext(AILayer next) {
		_nextLayer = next;
	}

	/**
	 * returns the number of nodes on a given ailayer
	 */
	public int getNumNodes() {
		return _numNodes;
	}

	/**
	 * creates random weights for this ailayer
	 */
	private void createRandomWeights() {
		for (int h = 0; h < _weights.length; h++) {
			for (int i = 0; i < _weights[h].length; i++) {
				_weights[h][i] = getRandomNumber(-1, 1);
			}
		}
	}

	/**
	 * Abstract method to calcualte the layers value
	 */
	public abstract double[] calculateLayer(double[] previous, NeuralNetVisualizer visual, int num);

	/**
	 * activation function (sigmoid)
	 */
	public static double activationFunc(double val) {
		return (1 / (1 + Math.pow(Math.E, (-1 * val))));
	}

	/**
	 * gets a random number given a max and min
	 */
	public static double getRandomNumber(double min, double max) {
		return (((max - min) * Math.random()) + min);
	}

	/**
	 * returns the weights for this layer
	 */
	public double[][] getWeights() {
		return _weights;
	}

	/**
	 * returns the mutated weights for this layer given a mutation rate
	 */
	public double[][] getMutatedWeights(double mutateRate) {
		double[][] toReturn = _weights;
		for (int h = 0; h < _weights.length; h++) {
			for (int i = 0; i < _weights[h].length; i++) {
				toReturn[h][i] = mutate(_weights[h][i], mutateRate);
			}
		}
		return toReturn;
	}

	/**
	 * murates a specfic value given a val and mutate rate
	 */
	public static double mutate(double val, double mutateRate) {
		if (Math.random() < mutateRate) {
			return val + getRandomNumber(-1 * Constants.MUTATION_MAX_AMOUNT, Constants.MUTATION_MAX_AMOUNT);
		} else {
			return val;
		}
	}

	/**
	 * randomly chooses between two weights
	 */
	public static double getMomOrDad(double mom, double dad) {
		if (Math.random() < 0.5) {
			return mutate(mom, 0);
		} else {
			return mutate(dad, 0);
		}
	}

	/**
	 * randomly chooses between two arrays of weights
	 */
	public static double[][] getMomOrDadArray(double[][] mom, double[][] dad) {
		if (Math.random() < 0.5) {
			return mom;
		} else {
			return dad;
		}
	}

}
