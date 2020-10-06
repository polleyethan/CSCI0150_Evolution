package Evolution;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * Subclass of game organizer for a singleplayer game
 */
public class SinglePlayerGameOrganizer extends GameOrganizer {

	/**
	 *  Constructor 
	 */
	public SinglePlayerGameOrganizer(Stage stage, GameSettings gamesettings) {
		super(stage, null, gamesettings);

	}

	/**
	 * Sets up the game
	 */
	@Override
	protected void setupGame() {
		_root = new BorderPane();
		_game = new SinglePlayerGame(_gameSettings);
		((BorderPane) _root).setCenter(_game.getGamePane());
		this.setupLeftPanel();
	}

	/**
	 * Sets up the left panel
	 */
	protected void setupLeftPanel() {

		VBox leftPanel = new VBox();

		Button backtohome = new Button("Back Home");

		backtohome.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				MainMenuOrganizer organizer = new MainMenuOrganizer(_stage);
				Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
			}
		});

		leftPanel.getChildren().add(backtohome);

		Button quit = new Button("Quit");

		quit.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.exit(0);
			}
		});

		leftPanel.getChildren().add(quit);

		((BorderPane) _root).setLeft(leftPanel);
	}

}
