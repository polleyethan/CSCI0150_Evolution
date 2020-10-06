package Evolution;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Subclass of AIMenuOne use for a single AIGame
 *
 */
public class AIMenuOneSingleGame extends AIMenuOne {

	/**
	 * Constructor for AIMenuOneSingleGame
	 */
	public AIMenuOneSingleGame(Stage stage) {
		super(stage);

	}

	/**
	 * Called when next button is clicked
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
			AIMenuTwo menutwo = new AIMenuTwoSingleGame(_stage, selectedInputs, selectedFitnessFactors);
			Scene newScene = new Scene(menutwo.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
			_stage.setScene(newScene);
		}

	}

	/**
	 * Called when back button is clicked
	 */
	@Override
	protected void backMenu() {
		MainMenuOrganizer mainmenu = new MainMenuOrganizer(_stage);
		Scene newScene = new Scene(mainmenu.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		_stage.setScene(newScene);

	}

}
