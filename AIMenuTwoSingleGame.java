package Evolution;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Subclass of AIMenuTwo used for single games
 *
 */
public class AIMenuTwoSingleGame extends AIMenuTwo {

	/**
	 * Constructor for AIMenuTwoSingleGame
	 */
	public AIMenuTwoSingleGame(Stage stage, ArrayList<AIInputNodes> inputs, ArrayList<FitnessCalcFactor> factors) {
		super(stage, inputs, factors);
	}

	/**
	 * called when play game button is clicked
	 */
	@Override
	protected void playGame() {
		AIGameOrganizer organizer = new AISingleGameOrganizer(_stage, new AISettings(_factors, 50, _inputs,
				_neuralEditor.getLayerConfig(), Constants.OPTIMIZED_SELECTION_SETTINGS), new GameSettings());
		Scene newScene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		_stage.setScene(newScene);

	}

	/**
	 * called when back menu button is clicked
	 */
	@Override
	protected void backMenu() {
		_neuralEditor.saveFitnessAsPng();
		AIMenuOne menuOne = new AIMenuOneSingleGame(_stage);
		Scene newScene = new Scene(menuOne.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		_stage.setScene(newScene);

	}
	
	

}
