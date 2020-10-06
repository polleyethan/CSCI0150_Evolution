package Evolution;

import java.util.ArrayList;

/**
 *
 * Represents the AiSettings of a given game. Stores the input nodes, fitness factors, neural net configuration,  and selectionr rates.
 */
public class AISettings {

	private ArrayList<AIInputNodes> _inputs;
	private ArrayList<Integer> _neuralNetConfig;
	private ArrayList<FitnessCalcFactor> _fitnessFactors;
	private int _initialPopSize;
	// private double _keepRate;
	// private double _directSelectRate;
	// private double _directmutateRate;
	// private double _crossoverRate;
	// private double _mutateProb;
	// private double _decreaseRate;
	private double[] _crossoverSelectionRates;

	/**
	 * Constructor for completely custom AISettings
	 */
	public AISettings(ArrayList<FitnessCalcFactor> fitnessFactors, int popsize, ArrayList<AIInputNodes> inputs,
			ArrayList<Integer> neuralNetConfig, double[] crossoverSelectionRates) {
		_fitnessFactors = fitnessFactors;
		_initialPopSize = popsize;
		_inputs = inputs;
		_neuralNetConfig = neuralNetConfig;
		_crossoverSelectionRates = crossoverSelectionRates;
	}

	/**
	 * Constructor for default AISettings
	 */
	public AISettings() {
		_fitnessFactors = Constants.BEST_FITNESS_FACTORS;
		_initialPopSize = Constants.INITIAL_POP_SIZE;
		_inputs = Constants.OPTIMIZED_INPUTS;
		_neuralNetConfig = Constants.DEFAULT_NEURAL_NET;
		_neuralNetConfig.add(0, _inputs.size());
		_crossoverSelectionRates = Constants.OPTIMIZED_SELECTION_SETTINGS;
	}

	/**
	 * Constructor for AIsettings with given inputnodes and neural net configuration
	 */
	public AISettings(ArrayList<AIInputNodes> inputs, ArrayList<Integer> neuralNetConfig) {
		_fitnessFactors = Constants.BEST_FITNESS_FACTORS;
		_initialPopSize = Constants.INITIAL_POP_SIZE;
		_inputs = inputs;
		_neuralNetConfig = neuralNetConfig;
		_crossoverSelectionRates = Constants.OPTIMIZED_SELECTION_SETTINGS;
	}

	/**
	 * Returns the initial population size
	 */
	public int getInitialPopSize() {
		return _initialPopSize;
	}

	/**
	 * returns an arraylist with the selected AIInput Nodes
	 */
	public ArrayList<AIInputNodes> getInputs() {
		return _inputs;
	}

	/**
	 * Returns an array list representing the neural nets configuration
	 */
	public ArrayList<Integer> getNetConfig() {
		return _neuralNetConfig;
	}

	/**
	 * returns an array with the selection settings
	 */
	public double[] getSelectionSettings() {
		return _crossoverSelectionRates;
	}

	/**
	 * gets the fitness of a bird given the next pipe and the bird
	 */
	public double getFitness(Pipe next, Bird b) {
		double toReturn = 0;
		for (FitnessCalcFactor f : _fitnessFactors) {
			toReturn += f.getCalculation(next, b);
		}
		return toReturn;
	}
}
