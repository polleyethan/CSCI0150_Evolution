package Evolution;

import java.util.ArrayList;

/**
 *
 * Representation of a neural network.
 */
public class NeuralNetwork {

	private ArrayList<AILayer> _layers;
	// private CS15NetworkVisualizer _visualizer;
	private AISettings _settings;
	private NeuralNetVisualizer _visualizer;

	/**
	 * Constructor taking in ai settings. Creates a random neural net
	 */
	public NeuralNetwork(AISettings aiSettings) {
		_settings = aiSettings;
		_layers = new ArrayList<AILayer>();
		int numlayers = _settings.getNetConfig().size();
		_layers.add(new AIInputLayer(_settings.getNetConfig().get(0)));
		for (int n = 1; n < numlayers - 1; n++) {
			AIHiddenLayer newlayer = new AIHiddenLayer(_settings.getNetConfig().get(n),
					_layers.get(n - 1).getNumNodes());
			_layers.add(newlayer);
			_layers.get(n - 1).setNext(newlayer);
		}
		AIOutputLayer outlayer = new AIOutputLayer(_layers.get(numlayers - 2).getNumNodes());
		_layers.add(outlayer);
		_layers.get(numlayers - 2).setNext(outlayer);
	}

	/**
	 * Constructor taking in a neural net to be copied
	 */
	public NeuralNetwork(NeuralNetwork net, AISettings aiSettings) {
		_settings = aiSettings;
		_layers = new ArrayList<AILayer>();
		int numlayers = _settings.getNetConfig().size();
		_layers.add(new AIInputLayer(_settings.getNetConfig().get(0)));
		for (int n = 1; n < numlayers - 1; n++) {
			AIHiddenLayer newlayer = new AIHiddenLayer(net.getLayers().get(n).getWeights(),
					_settings.getNetConfig().get(n));
			_layers.add(newlayer);
			_layers.get(n - 1).setNext(newlayer);
		}
		AIOutputLayer outlayer = new AIOutputLayer(net.getLayers().get(numlayers - 1).getWeights());
		_layers.add(outlayer);
		_layers.get(numlayers - 2).setNext(outlayer);
	}

	/**
	 *  Constructor taking in a neural net to be mutated
	 */
	public NeuralNetwork(NeuralNetwork net, double mutateRate, AISettings aiSettings) {
		_settings = aiSettings;
		_layers = new ArrayList<AILayer>();
		int numlayers = _settings.getNetConfig().size();
		_layers.add(new AIInputLayer(_settings.getNetConfig().get(0)));
		for (int n = 1; n < numlayers - 1; n++) {
			AIHiddenLayer newlayer = new AIHiddenLayer(net.getLayers().get(n).getMutatedWeights(mutateRate),
					_settings.getNetConfig().get(n));
			_layers.add(newlayer);
			_layers.get(n - 1).setNext(newlayer);
		}
		AIOutputLayer outlayer = new AIOutputLayer(net.getLayers().get(numlayers - 1).getMutatedWeights(mutateRate));
		_layers.add(outlayer);
		_layers.get(numlayers - 2).setNext(outlayer);
	}

	/**
	 * Constructor taking in a mom and dad neural net
	 */
	public NeuralNetwork(NeuralNetwork mom, NeuralNetwork dad, double mutateRate, AISettings aiSettings) {
		_settings = aiSettings;
		_layers = new ArrayList<AILayer>();
		int numlayers = _settings.getNetConfig().size();
		_layers.add(new AIInputLayer(_settings.getNetConfig().get(0)));
		for (int n = 1; n < numlayers - 1; n++) {
			double[][] layerweights = crossoverParents(mom.getLayers().get(n), mom.getLayers().get(n), mutateRate);
			AIHiddenLayer newlayer = new AIHiddenLayer(layerweights, _settings.getNetConfig().get(n));
			_layers.add(newlayer);
			_layers.get(n - 1).setNext(newlayer);
		}
		double[][] layerweights = crossoverParents(mom.getLayers().get(numlayers - 1),
				mom.getLayers().get(numlayers - 1), mutateRate);
		AIOutputLayer outlayer = new AIOutputLayer(layerweights);
		_layers.add(outlayer);
		_layers.get(numlayers - 2).setNext(outlayer);
	}

	/**
	 * Crosses over the neural net of two parents
	 */
	public static double[][] crossoverParents(AILayer mom, AILayer dad, double mutateRate) {
		int crossoverpoint = (int) getRandomNumber(0, mom.getWeights().length * mom.getWeights()[0].length);
		int counter = 0;
		double[][] toReturn = new double[mom.getWeights().length][mom.getWeights()[0].length];
		for (int i = 0; i < toReturn.length; i++) {
			for (int j = 0; j < toReturn[i].length; j++) {
				if (counter < crossoverpoint) {
					counter += 1;
					toReturn[i][j] = mom.getWeights()[i][j];
				} else {
					toReturn[i][j] = dad.getWeights()[i][j];
				}
			}
		}

		return toReturn;
	}

	/**
	 * returns a random number given a max and min
	 */
	public static double getRandomNumber(double min, double max) {
		return (((max - min) * Math.random()) + min);
	}

	/**
	 * sets the visualizer for this neural network
	 */
	public void setVisualizer(NeuralNetVisualizer visual) {
		_visualizer = visual;
	}

	/**
	 * removes the visualizer for this neural network 
	 */
	public void removeVisualizer() {
		_visualizer = null;
	}

	/**
	 * calculates the output for this network
	 */
	public double[] calculateMove(double[] input) {
		return _layers.get(0).calculateLayer(input, _visualizer, 0);
	}

	/**
	 * returns arraylist of layers
	 */
	public ArrayList<AILayer> getLayers() {
		return _layers;
	}

}