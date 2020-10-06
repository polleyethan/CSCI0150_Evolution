package Evolution;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * Abstract class for AIMenuTwo. AIMenuTwo allows users to select the setup of the neural network for their birds
 */
public abstract class AIMenuTwo {

	protected BorderPane _root;
	protected ArrayList<AIInputNodes> _inputs;
	protected NeuralNetEditor _neuralEditor;
	protected ArrayList<FitnessCalcFactor> _factors;
	protected Stage _stage;
	protected VBox _centerBox;

	/**
	 * Abstract Constructor for AIMenuTwo
	 */
	public AIMenuTwo(Stage stage, ArrayList<AIInputNodes> inputs, ArrayList<FitnessCalcFactor> factors) {
		_stage = stage;
		_inputs = inputs;
		_factors = factors;
		_root = new BorderPane();
		this.setupNetVisuals();
		this.setupBottom();
	}

	/**
	 * Sets up the Neural Net Editor
	 */
	private void setupNetVisuals() {

		_neuralEditor = new NeuralNetEditor(_inputs.size());

		Pane neuralPane = _neuralEditor.getRoot();

		VBox neuralBox = new VBox();

		Button defaultConfig = new Button("Defaults");

		Text netHeader = new Text("Neural Network Configuration");
		netHeader.setFont(Constants.getFlappyFont(70));

		defaultConfig.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				_neuralEditor.setDefault(_inputs.size());
			}
		});

		neuralBox.getChildren().addAll(new Rectangle(0, 0, 0, 0), netHeader, defaultConfig, neuralPane);
		neuralBox.setSpacing(10);
		neuralBox.setAlignment(Pos.CENTER);
		Pane neuralContainer = new Pane(neuralBox);
		neuralContainer.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(20), new BorderWidths(1))));
		neuralContainer.setMaxWidth(Constants.SCENE_WIDTH / 2);
		neuralContainer.setMinWidth(Constants.SCENE_WIDTH / 2);
		neuralContainer.setPrefWidth(Constants.SCENE_WIDTH / 2);

		_centerBox = new VBox(neuralContainer);

		_centerBox.setAlignment(Pos.CENTER);

		_centerBox.setSpacing(15);

		_root.setCenter(_centerBox);
	}

	/**
	 * Sets up the bottom buttons
	 */
	private void setupBottom() {

		Button playGame = new Button("Play Game");

		playGame.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AIMenuTwo.this.playGame();
			}
		});

		Button backToOne = new Button("Back To AI Settings 1");

		backToOne.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AIMenuTwo.this.backMenu();
			}
		});

		HBox bottom = new HBox(backToOne, playGame);
		bottom.setAlignment(Pos.CENTER);
		_centerBox.getChildren().add(bottom);

	}

	/**
	 * Called when playgame button is pressed
	 */
	protected abstract void playGame();

	/**
	 * Called when backmenu button is pressed
	 */
	protected abstract void backMenu();

	/**
	 * gets the root pane
	 */
	public Pane getRoot() {
		return _root;
	}

}