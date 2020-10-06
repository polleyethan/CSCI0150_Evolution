package Evolution;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Instantiate Menu Organizer and show it in the scene
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {
		//Creates a Main Menu
		MainMenuOrganizer organizer = new MainMenuOrganizer(stage);
		Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] argv) {
		// launch is a static method inherited from Application.
		launch(argv);
	}
}
