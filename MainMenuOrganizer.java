package Evolution;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Container to Organize the Main Menu
 *
 */
public class MainMenuOrganizer {

	private BorderPane _root;

	/**
	 * Constructor 
	 */
	public MainMenuOrganizer(Stage stage) {
		_root = new BorderPane();
		Text topText = new Text("Machine Learning Flappy Bird");
		Text tagLine = new Text("A final project by REDACTED");
		topText.setFont(Constants.getFlappyFont(120));
		tagLine.setFont(Constants.getFlappyFont(50));
		VBox top = new VBox();
		top.setSpacing(8);
		top.getChildren().addAll(topText, tagLine);
		top.setPadding(new Insets(40));
		top.setAlignment(Pos.CENTER);
		_root.setTop(top);;
		this.setupMenu(stage);
	}

	/**
	 *  Sets up the menu
	 */
	private void setupMenu(Stage stage) {
		VBox menu = new VBox();
		_root.setCenter(menu);

		Button singlePlayer = new Button("Single Player Game");

		singlePlayer.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				SinglePlayerGameOrganizer organizer = new SinglePlayerGameOrganizer(stage, new GameSettings());
				Scene newScene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				stage.setScene(newScene);
			}
		});

		Button aiGame = new Button("AI Game");

		aiGame.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AIMenuOne menuone = new AIMenuOneSingleGame(stage);
				Scene newScene = new Scene(menuone.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				stage.setScene(newScene);
			}
		});

		Button aiMultiGame = new Button("AI Multi-Game");

		aiMultiGame.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				MultiGameScreen organizer = new MultiGameScreen(stage);
				Scene newScene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				stage.setScene(newScene);
			}
		});

		Button quit = new Button("Quit");

		quit.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.exit(0);
			}
		});

		menu.getChildren().addAll(singlePlayer, aiGame, aiMultiGame, quit);
		menu.setSpacing(10);
		menu.setAlignment(Pos.CENTER);
	}

	/**
	 * Gets the Root Pane
	 */
	public Pane getRootPane() {
		return _root;
	}
}
