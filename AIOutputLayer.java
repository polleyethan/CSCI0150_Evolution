package Evolution;

/**
 *
 * Subclass of AILayer. Represents the output layer for a Neural Network
 */
public class AIOutputLayer extends AILayer {

	/**
	 * Constructor for a random AIOutputLayer.
	 */
	public AIOutputLayer(int previousNumNodes) {
		super(1, previousNumNodes);
	}

	/**
	 * Constructor for an AIOutputLayer with given weights.
	 */
	public AIOutputLayer(double[][] weights) {
		super(weights, 1);
	}

	/**
	 * Calculates the value of this layer and returns the output, allowing the bird to make a decision.
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
		return thislayer;
	}

}
