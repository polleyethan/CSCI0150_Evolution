package Evolution;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * Subclass of AIMenuTwo used for MultiGames
 */
public class AIMenuTwoMultiGame extends AIMenuTwo {

	private MultiGameScreen _organizer;
	private int _x;
	private int _y;

	/**
	 * Constructor for AIMenuTwoMultiGame
	 */
	public AIMenuTwoMultiGame(Stage stage, ArrayList<AIInputNodes> inputs, ArrayList<FitnessCalcFactor> factors,
			MultiGameScreen organizer, int x, int y) {
		super(stage, inputs, factors);
		_organizer = organizer;
		_x = x;
		_y = y;
	}

	/**
	 * Called when play game button is clicked
	 */
	@Override
	protected void playGame() {
		_organizer.addNewGame(new AISettings(_factors, 50, _inputs, _neuralEditor.getLayerConfig(),
				Constants.OPTIMIZED_SELECTION_SETTINGS), _x, _y);
		_stage.close();
	}

	/**
	 * Called when back menu button is clicked
	 */
	@Override
	protected void backMenu() {
		AIMenuOne menuOne = new AIMenuOneMultiGame(_stage, _organizer, _x, _y);
		Scene newScene = new Scene(menuOne.getRootPane(), Constants.POPUP_SCENE_WIDTH, Constants.POPUP_SCENE_HEIGHT);
		_stage.setScene(newScene);

	}

}
