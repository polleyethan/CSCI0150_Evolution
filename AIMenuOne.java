package Evolution;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * Abstract class representing the first menu for an AIGame
 */
public abstract class AIMenuOne {

	protected BorderPane _root;
	protected Text _inputsWarning;
	protected VBox _inputChecks;
	protected Text _fitnessWarning;
	protected VBox _fitnessChecks;
	protected VBox _centerBox;
	protected Stage _stage;

	/**
	 * Constructor for AIMenuOne
	 */
	public AIMenuOne(Stage stage) {
		_stage = stage;
		_root = new BorderPane();
		this.setupMenu();
	}

	/**
	 * sets up the menu
	 */
	protected void setupMenu() {
		_centerBox = new VBox();
		_centerBox.setSpacing(20);
		this.setupInputs();
		this.setupFitness();
		this.setupBottomButtons();
		_root.setCenter(_centerBox);

	}

	/**
	 * sets up the inputs container and checkboxes
	 */
	private void setupInputs() {

		VBox inputs = new VBox();

		_inputsWarning = new Text("");
		_inputsWarning.setFill(Color.RED);

		_inputChecks = new VBox();

		for (int i = 0; i < AIInputNodes.values().length; i++) {

			CheckBox c = new CheckBox(AIInputNodes.values()[i].getString());

			_inputChecks.getChildren().add(c);

			if (Constants.OPTIMIZED_INPUTS.contains(AIInputNodes.values()[i])) {
				c.setSelected(true);
			}

		}

		Button inputDefault = new Button("Default Inputs");

		inputDefault.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				for (int i = 0; i < AIInputNodes.values().length; i++) {
					if (Constants.OPTIMIZED_INPUTS.contains(AIInputNodes.values()[i])) {
						((CheckBox) (_inputChecks.getChildren().get(i))).setSelected(true);
					} else {
						((CheckBox) (_inputChecks.getChildren().get(i))).setSelected(false);
					}

				}
			}
		});

		inputs.getChildren().add(_inputChecks);

		inputs.setAlignment(Pos.CENTER);

		VBox inputsContainer = new VBox();

		Text inputsHead = new Text("Neural Network Inputs");
		inputsHead.setFont(Constants.getFlappyFont(60));

		inputsContainer.getChildren().addAll(inputsHead, inputDefault, inputs, _inputsWarning);

		inputsContainer.setSpacing(10);
		inputsContainer.setAlignment(Pos.CENTER);

		inputsContainer.setPadding(new Insets(20));

		inputsContainer.setMaxWidth(400);
		inputsContainer.setMinWidth(400);
		inputsContainer.setPrefWidth(400);

		inputsContainer.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(20), new BorderWidths(1))));

		_centerBox.getChildren().add(inputsContainer);
		_centerBox.setAlignment(Pos.CENTER);

	}

	/**
	 * setsup the fitness container, checkboxes, and text input boxes
	 */
	private void setupFitness() {

		VBox fitness = new VBox();

		_fitnessWarning = new Text("");
		_fitnessWarning.setFill(Color.RED);

		_fitnessChecks = new VBox();

		for (int i = 0; i < FitnessCalculationFactors.values().length; i++) {

			CheckBox c = new CheckBox(FitnessCalculationFactors.values()[i].getName());
			TextField t = new TextField();
			c.setMaxWidth(250);
			c.setMinWidth(250);
			c.setPrefWidth(250);
			t.setMaxWidth(50);
			t.setMinWidth(50);
			t.setPrefWidth(50);
			t.setDisable(true);
			c.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldval, Boolean newval) {
					t.setDisable(!newval);
				}
			});
			HBox box = new HBox(c, t);
			_fitnessChecks.getChildren().add(box);

			for (FitnessCalcFactor f : Constants.BEST_FITNESS_FACTORS) {
				if (f.getFactor() == FitnessCalculationFactors.values()[i]) {
					c.setSelected(true);
					t.setText(f.getMultiplier() + "");
				}
			}

		}

		Button fitnessDefault = new Button("Default Factors");

		fitnessDefault.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ArrayList<FitnessCalculationFactors> factors = new ArrayList<FitnessCalculationFactors>();
				for (int i = 0; i < FitnessCalculationFactors.values().length; i++) {
					HBox box = ((HBox) _fitnessChecks.getChildren().get(i));
					CheckBox checkbox = (CheckBox) box.getChildren().get(0);
					TextField t = (TextField) box.getChildren().get(1);
					for (FitnessCalcFactor f : Constants.BEST_FITNESS_FACTORS) {
						if (f.getFactor() == FitnessCalculationFactors.values()[i]) {
							factors.add(FitnessCalculationFactors.values()[i]);
							checkbox.setSelected(true);
							t.setText(f.getMultiplier() + "");
						} else if (!factors.contains(FitnessCalculationFactors.values()[i])) {
							checkbox.setSelected(false);
							t.setText("");
						}
					}
				}
			}
		});

		fitness.getChildren().add(_fitnessChecks);

		fitness.setAlignment(Pos.CENTER);

		VBox fitnessContainer = new VBox();

		Text fitnessHead = new Text("Fitness Factors");
		fitnessHead.setFont(Constants.getFlappyFont(60));

		fitnessContainer.getChildren().addAll(fitnessHead, fitnessDefault, fitness, _fitnessWarning);

		fitnessContainer.setSpacing(10);
		fitnessContainer.setAlignment(Pos.CENTER);

		fitnessContainer.setPadding(new Insets(20));

		fitnessContainer.setMaxWidth(400);
		fitnessContainer.setMinWidth(400);
		fitnessContainer.setPrefWidth(400);

		fitnessContainer.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, new CornerRadii(20), new BorderWidths(1))));

		_centerBox.getChildren().add(fitnessContainer);
		_centerBox.setAlignment(Pos.CENTER);

	}

	/**
	 * Sets up the buttons on the bottom of the screen
	 */
	private void setupBottomButtons() {
		Button nextSettings = new Button("Next");

		nextSettings.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AIMenuOne.this.nextMenu();

			}

		});

		Button backToMain = new Button("Back To Main Menu");

		backToMain.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AIMenuOne.this.backMenu();
			}
		});
		HBox bottom = new HBox(backToMain, nextSettings);
		bottom.setAlignment(Pos.CENTER);
		_centerBox.getChildren().add(bottom);
	}

	/**
	 * abstract method called when clicking the back button
	 */
	protected abstract void backMenu();

	/**
	 * returns an array with the selected fitness factors
	 */
	public ArrayList<FitnessCalcFactor> getFitnessFactors() {
		ArrayList<FitnessCalcFactor> fitFactors = new ArrayList<FitnessCalcFactor>();
		for (int i = 0; i < _fitnessChecks.getChildren().size(); i++) {
			HBox box = ((HBox) _fitnessChecks.getChildren().get(i));
			CheckBox checkbox = (CheckBox) box.getChildren().get(0);
			TextField t = (TextField) box.getChildren().get(1);
			if (checkbox.isSelected()) {
				if ((t.getText() != null && !t.getText().isEmpty())) {
					try {
						double multi = Double.parseDouble(t.getText());
						fitFactors.add(new FitnessCalcFactor(FitnessCalculationFactors.values()[i], multi));
					} catch (NumberFormatException e) {
						return null;
					}
				} else {
					return null;
				}

			}
		}
		return fitFactors;
	}

	/**
	 * returns an array with the selected inputs
	 */
	public ArrayList<AIInputNodes> getInputs() {
		ArrayList<AIInputNodes> selectedInputs = new ArrayList<AIInputNodes>();
		for (int i = 0; i < AIInputNodes.values().length; i++) {
			if (((CheckBox) (_inputChecks.getChildren().get(i))).isSelected()) {
				selectedInputs.add(AIInputNodes.values()[i]);
			}
		}
		return selectedInputs;
	}

	/**
	 * gets the root pane
	 */
	public Pane getRootPane() {
		return _root;
	}

	/**
	 * abstract method called when next button is clicked.
	 */
	public abstract void nextMenu();
}