package Evolution;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * Abstract GameOrganizer class
 */
public abstract class GameOrganizer {

	protected Pane _root;
	protected Game _game;
	protected AISettings _aiSettings;
	protected GameSettings _gameSettings;
	protected Stage _stage;

	/**
	 * Constructor for Game organizer
	 */
	public GameOrganizer(Stage stage, AISettings settings, GameSettings gamesettings) {
		_stage = stage;
		_aiSettings = settings;
		_gameSettings = gamesettings;

		this.setupVisually();
	}

	/**
	 * sets up the visuals
	 */
	private void setupVisually() {
		this.setupGame();
	}

	/**
	 * abstract method that sets up the game
	 */
	protected abstract void setupGame();

	/**
	 * returns the root pane
	 */
	public Pane getRootPane() {
		return _root;
	}

	/**
	 * returns the ai settings
	 */
	public AISettings getAISettings() {
		return _aiSettings;
	}

}
