package Evolution;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Represents a population of Birds
 *
 */
public class Population {

	private ArrayList<AIBird> _birds;
	private int _size;
	private double _averageFitness;
	private int _currentId;
	private int _genNumber;
	private AISettings _aiSettings;

	/**
	 * Constructor for a default Population
	 */
	public Population(Game game, AISettings settings, int genNumber, int currentId) {
		_aiSettings = settings;
		_currentId = currentId;
		_genNumber = genNumber;
		_size = _aiSettings.getInitialPopSize();
		_birds = new ArrayList<AIBird>();
		for (int i = 0; i < _size; i++) {
			_birds.add(new AIBird(_aiSettings, game, _currentId, _genNumber, getRandomColor()));
			_currentId += 1;
		}
	}

	/**
	 * constructor for a new Population with a given pop 
	 */
	public Population(Game game, Population previous, AISettings settings) {
		_aiSettings = settings;
		_currentId = previous.getCurrentID();
		_genNumber = previous.getGenNum() + 1;
		_size = (int) (previous.getBirds().size() * _aiSettings.getSelectionSettings()[5]);
		_birds = new ArrayList<AIBird>();
		for (int i = 0; i < _size * _aiSettings.getSelectionSettings()[1]; i++) {
			AIBird previousbird = previous.getBirds().get(i);
			_birds.add(new AIBird(_aiSettings, game, previousbird.getNet(), previousbird.getID(),
					previousbird.getGenNum(), previousbird.getColor()));
		}
		for (int i = 0; i < _size * _aiSettings.getSelectionSettings()[2]; i++) {
			int randomBirdIndex = (int) getRandomNumber(0, _size * _aiSettings.getSelectionSettings()[0]);
			NeuralNetwork oldnet = previous.getBirds().get(randomBirdIndex).getNet();
			NeuralNetwork newNet = new NeuralNetwork(oldnet, _aiSettings.getSelectionSettings()[4], _aiSettings);
			_birds.add(new AIBird(_aiSettings, game, newNet, _currentId, _genNumber, getRandomColor()));
			_currentId += 1;
		}
		for (int i = 0; i < _size * _aiSettings.getSelectionSettings()[3]; i++) {
			int randomBirdMomIndex = (int) getRandomNumber(0, _size * _aiSettings.getSelectionSettings()[0]);
			int randomBirdDadIndex = (int) getRandomNumber(0, _size * _aiSettings.getSelectionSettings()[0]);
			NeuralNetwork mom = previous.getBirds().get(randomBirdMomIndex).getNet();
			NeuralNetwork dad = previous.getBirds().get(randomBirdDadIndex).getNet();
			NeuralNetwork newNet = new NeuralNetwork(mom, dad, _aiSettings.getSelectionSettings()[4], _aiSettings);
			_birds.add(new AIBird(_aiSettings, game, newNet, _currentId, _genNumber, getRandomColor()));
			_currentId += 1;
		}

	}

	/**
	 * gets the current id
	 */
	public int getCurrentID() {
		return _currentId;
	}

	/**
	 * gets the generation number
	 */
	public int getGenNum() {
		return _genNumber;
	}

	
	/**
	 * gets size of the population
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * calculates the average fitness of the population
	 */
	public void calculateFitness() {
		double sum = 0;
		for (AIBird b : _birds) {
			sum += b.getFitness();
		}
		_averageFitness = sum / _size;
	}

	/**
	 * gets a random number with a given max and min
	 */
	public static double getRandomNumber(double min, double max) {
		return (((max - min) * Math.random()) + min);
	}

	/**
	 * gets array list of birds
	 */
	public ArrayList<AIBird> getBirds() {
		return _birds;
	}

	/**
	 * gets the average fitness
	 */
	public double getAvgFitness() {
		return _averageFitness;
	}

	/**
	 * gets a random color
	 */
	public static Color getRandomColor() {
		int r = (int) getRandomNumber(0, 255);
		int g = (int) getRandomNumber(0, 255);
		int b = (int) getRandomNumber(0, 255);
		return Color.rgb(r, g, b);
	}
}
