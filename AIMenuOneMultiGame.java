package Evolution;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Subclass of AIMenuOne used when playing a multi-game
 *
 */
public class AIMenuOneMultiGame extends AIMenuOne {

	private int _x;
	private int _y;

	private MultiGameScreen _organizer;

	/**
	 * Constructor for AIMenuOneMultiGame
	 */
	public AIMenuOneMultiGame(Stage stage, MultiGameScreen organizer, int x, int y) {
		super(stage);
		_organizer = organizer;
		_x = x;
		_y = y;

	}

	/**
	 * Sets up the menu on a new stage
	 */
	@Override
	protected void setupMenu() {
		super.setupMenu();
		Scene s = new Scene(_root, Constants.POPUP_SCENE_WIDTH, Constants.POPUP_SCENE_HEIGHT);
		_stage.setScene(s);
		_stage.setAlwaysOnTop(true);
		_stage.show();
	}

	/**
	 * Called when the next button is clicked
	 */
	@Override
	public void nextMenu() {
		ArrayList<AIInputNodes> selectedInputs = this.getInputs();
		ArrayList<FitnessCalcFactor> selectedFitnessFactors = this.getFitnessFactors();

		if (selectedInputs.size() == 0) {
			_inputsWarning.setText("MUST SELECT AT LEAST ONE INPUT");

		} else if (selectedFitnessFactors == null) {
			_fitnessWarning.setText("ALL FACTORS MUST HAVE A NUMERICAL MULTIPLIER");
		} else if (selectedFitnessFactors.size() == 0) {
			_fitnessWarning.setText("MUST SELECT FITNESS FACTORS");
		} else {
			for (FitnessCalcFactor f : selectedFitnessFactors) {
				System.out.println(f.getMultiplier());
			}
			AIMenuTwo menutwo = new AIMenuTwoMultiGame(_stage, selectedInputs, selectedFitnessFactors, _organizer, _x,
					_y);
			Scene newScene = new Scene(menutwo.getRoot(), Constants.POPUP_SCENE_WIDTH, Constants.POPUP_SCENE_HEIGHT);
			_stage.setScene(newScene);
		}

	}

	/**
	 * Called when the back button is clicked
	 */
	@Override
	protected void backMenu() {
		_stage.close();

	}

}
