package Evolution;

import java.util.ArrayList;
import cs015.fnl.EvolutionSupport.CS15NetworkVisualizer;
import javafx.stage.Stage;

/**
 * Abstract super class for AI Game Organizers. Extends Super class Game Organizer
 *
 */
public abstract class AIGameOrganizer extends GameOrganizer {

	protected ArrayList<CompletedGameData> _gameData;
	protected ArrayList<Population> _populations;
	protected ArrayList<Integer> _generations;
	protected ArrayList<Integer> _fitnessHistory;
	protected int _generation;
	protected AIGame _game;
	protected int _birdsLeft;
	protected CS15NetworkVisualizer _visualizer2;
	protected NeuralNetVisualizer _visualizer;
	protected LongTermVisualizer _longTermVisualizer;

	/**
	 * Constructor for AI Game Organizer
	 */
	public AIGameOrganizer(Stage stage, AISettings settings, GameSettings gamesettings) {
		super(stage, settings, gamesettings);
		// _visualizer2 = new
		// CS15NetworkVisualizer(Constants.NUM_INPUT_NODES,Constants.NUM_HIDDEN_NODES);
		_visualizer = new NeuralNetVisualizer(_aiSettings.getNetConfig());
		_longTermVisualizer = new LongTermVisualizer(this);
	}

	/**
	 * Abstract method to setup the game
	 */
	@Override
	protected abstract void setupGame();

	/**
	 * returns this Game Organizer's game
	 */
	public AIGame getGame() {
		return _game;
	}

	/**
	 * Returns this games gamedata
	 */
	public ArrayList<CompletedGameData> getGameData() {
		return _gameData;
	}

	/**
	 * Called when game ends. Creates a new game and saves data from the last game.
	 */
	public void onGameEnd() {

		CompletedGameData data = new CompletedGameData(_game, _populations.get(_generation), _generation);

		_gameData.add(data);

		_longTermVisualizer.addNewGame(data, _generation);

		_generations.add(_generation);

		_fitnessHistory.add((int) _populations.get(_generation).getAvgFitness());

		if (_generation < Constants.MAX_GENERATIONS) {

			_generation += 1;

			this.createNewGame();
		} else {

		}
	}

	/**
	 * Abstract method to create a new game
	 */
	public abstract void createNewGame();

	/**
	 * abstract method to update the panels
	 */
	protected abstract void updatePanel();

	/**
	 * called when a bird dies in the game
	 */
	public void birdDied() {
		_birdsLeft -= 1;
	}

	/**
	 * returns number of birds remaining alive
	 */
	public int getBirdsLeft() {
		return _birdsLeft;
	}

	/**
	 * gets the generation number for the current game
	 */
	public int getGen() {
		return _generation;
	}

	/**
	 * gets the current population
	 */
	public ArrayList<Population> getPopulations() {
		return _populations;
	}

	/**
	 * gets the visualizer for the neural networks
	 */
	public NeuralNetVisualizer getVisualizer() {
		return _visualizer;
	}

}
