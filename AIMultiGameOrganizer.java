package Evolution;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * Subclass of AIGameOrganizer. Used for multiscreen games.
 */
public class AIMultiGameOrganizer extends AIGameOrganizer {

	private GameView _panel;
	private int _x;
	private int _y;

	/**
	 * Constructor for AIMultiGameOrganizer. 
	 */
	public AIMultiGameOrganizer(GameView panel, Stage stage, AISettings settings, GameSettings gamesettings, int x,int y) {
		super(stage, settings, gamesettings);
		_x = x;
		_y = y;
		_panel = panel;
	}

	/**
	 * Sets up the initial game for this mini game.
	 */
	@Override
	protected void setupGame() {

		_root = new Pane();

		_game = new AIMiniGame(this, _gameSettings);

		this.setupInitialPopulation();

		_game.setPopulation(_populations.get(0));

		_root.getChildren().add(_game.getGamePane());

		_generations = new ArrayList<Integer>();

		_fitnessHistory = new ArrayList<Integer>();

	}

	/**
	 * Creates an initial population for the Game
	 */
	protected void setupInitialPopulation() {

		_generation = 0;

		_populations = new ArrayList<Population>();

		_gameData = new ArrayList<CompletedGameData>();

		_populations.add(new Population(_game, _aiSettings, 0, 0));

		_birdsLeft = _populations.get(0).getBirds().size();

	}

	/**
	 * Called when a game ends. Creates a new game and adds it to the screen visually. Saves data from the last game.
	 */
	@Override
	public void createNewGame() {

		_game = new AIGame(this, _game.getSettings());

		Population last = _populations.get(_generation - 1);
		Collections.sort(last.getBirds());

		if (last.getBirds().get(0).getFitness() < -400) {

			_populations.add(new Population(_game, _aiSettings, _generation, last.getCurrentID()));
		} else {
			_populations.add(new Population(_game, _populations.get(_generation - 1), _aiSettings));
		}
		_game.setPopulation(_populations.get(_generation));

		_panel.getGenerationText().setText(_generation + "");

		_birdsLeft = _populations.get(_generation).getSize();
		_panel.getBirdsLeftText().setText(_birdsLeft + "/" + _populations.get(_generation).getSize());

		_root.getChildren().clear();
		_root.getChildren().add(_game.getGamePane());
		_panel.updateInfoPanel();

	}

	/**
	 * Updates panel
	 */
	@Override
	protected void updatePanel() {
		// TODO Auto-generated method stub

	}

	/**
	 * Called when a bird dies
	 */
	@Override
	public void birdDied() {
		super.birdDied();
		_panel.getBirdsLeftText().setText(_birdsLeft + "/" + _populations.get(_generation).getSize());
	}

	/**
	 * Gets the X location of this minigame on the MultiScreen GridPane.
	 */
	public int getX() {
		return _x;
	}

	/**
	 * Gets the Y location of this minigame on the MultiScreen GridPane.
	 */
	public int getY() {
		return _y;
	}

}