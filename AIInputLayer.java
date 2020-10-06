package Evolution;

/**
 * Subclass of abstract AILayer class. Represents the input layer for a neural network.
 *
 */
public class AIInputLayer extends AILayer {

	/**
	 * Constructor for Input layer
	 */
	public AIInputLayer(int numInputNodes) {
		super(numInputNodes, 0);
	}

	/**
	 * Calculates the value of the Layer
	 */
	@Override
	public double[] calculateLayer(double[] previous, NeuralNetVisualizer visual, int num) {
		if (visual != null) {
			visual.showLiveUpdate(previous, num);
		}
		return _nextLayer.calculateLayer(previous, visual, num + 1);
	}

}
