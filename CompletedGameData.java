package Evolution;

import java.util.Collections;

/**
 * Container class holding data for completed game data
 *
 */
public class CompletedGameData {

	public AIGame _game;
	private Population _population;
	private int _genNum;
	private double _bestBirdFitness;
	private double _worstBirdFitness;
	private double _averageFitness;
	private double[] _averageSettings;

	/**
	 * Constructor for completed game data
	 */
	public CompletedGameData(AIGame game, Population pop, int genNum) {
		_game = game;
		_population = pop;
		_genNum = genNum;
		Collections.sort(_population.getBirds());
		_bestBirdFitness = _population.getBirds().get(0).getFitness();
		_worstBirdFitness = _population.getBirds().get(_population.getSize() - 1).getFitness();
		_averageFitness = _population.getAvgFitness();
		_averageSettings = game.getAvgSettings();

	}

	/**
	 * returns the gen num
	 */
	public int getGenerationNum() {
		return _genNum;
	}

	/**
	 * returns the average settings for the game
	 */
	public double[] getAvgSettings() {
		return _averageSettings;
	}

	/**
	 * returns the best fitness
	 */
	public double getBestFitness() {
		return _bestBirdFitness;
	}

	/**
	 * returns the worst fitness
	 */
	public double getWorstFitness() {
		return _worstBirdFitness;
	}

	/**
	 * returns the average fitness
	 */
	public double getAvgFitness() {
		return _averageFitness;
	}

}
