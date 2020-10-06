package Evolution;

/**
 * Subclass of AILayer. Represents a Hidden Layer in the Neural Network.
 *
 */
public class AIHiddenLayer extends AILayer {

	/**
	 * Constructor for a random Hidden layer
	 */
	public AIHiddenLayer(int numNodes, int previousNumNodes) {
		super(numNodes, previousNumNodes);
	}

	/**
	 * Constructor for a hidden layer with given weights
	 */
	public AIHiddenLayer(double[][] weights, int numNodes) {
		super(weights, numNodes);
	}

	/**
	 * Calculates the value of this layer.
	 */
	@Override
	public double[] calculateLayer(double[] previous, NeuralNetVisualizer visual, int num) {
		double[] thislayer = new double[_numNodes];
		for (int h = 0; h < _weights.length; h++) {
			double val = 0;
			for (int i = 0; i < _weights[h].length; i++) {
				val += previous[i] * _weights[h][i];
			}
			thislayer[h] = activationFunc(val);
		}
		if (visual != null) {
			visual.showLiveUpdate(thislayer, num);
		}
		return _nextLayer.calculateLayer(thislayer, visual, num + 1);
	}

}
