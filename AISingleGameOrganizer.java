package Evolution;

import java.util.ArrayList;
import java.util.Collections;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * Subclass of AIGameOrganizer representing a single game.
 */
public class AISingleGameOrganizer extends AIGameOrganizer {

	private ArrayList<BirdView> _birdViews;
	private GridPane _panelRight;
	private VBox _controlPanel;
	private VBox _infoPanel;
	private Text _generationText;
	private Text _birdsLeftText;
	private Text _previousMaxFitText;
	private Text _previousAvgFitText;
	private VBox _leftPane;

	/**
	 * Constructor for AISingleGameOrganizer.
	 */
	public AISingleGameOrganizer(Stage stage, AISettings settings, GameSettings gamesettings) {
		super(stage, settings, gamesettings);
		this.setupPanelLeft();
		this.setupPanelRight();

	}

	/**
	 * Sets up the initial game.
	 */
	@Override
	protected void setupGame() {

		_root = new BorderPane();

		_game = new AIGame(this, _gameSettings);

		this.setupInitialPopulation();

		_game.setPopulation(_populations.get(0));

		((BorderPane) _root).setCenter(_game.getGamePane());

		_generations = new ArrayList<Integer>();

		_fitnessHistory = new ArrayList<Integer>();

	}

	/**
	 * Sets up the intiial population
	 */
	protected void setupInitialPopulation() {

		_generation = 0;

		_populations = new ArrayList<Population>();

		_gameData = new ArrayList<CompletedGameData>();

		_populations.add(new Population(_game, _aiSettings, 0, 0));

	}

	/**
	 * Sets up the left panel (allows user to alter settings and view data.
	 */
	protected void setupPanelLeft() {

		_leftPane = new VBox();

		((BorderPane) _root).setLeft(_leftPane);

		this.setupControlsPanel();

		this.setupInfoPanel();

		TitledPane info = new TitledPane("Info", _infoPanel);
		TitledPane controls = new TitledPane("Controls", _controlPanel);

		_leftPane.getChildren().addAll(info, controls);

	}

	/**
	 * Sets up the controls panel
	 */
	private void setupControlsPanel() {

		_controlPanel = new VBox();

		// TIMELINE SPEED SLIDER
		_controlPanel.getChildren().add(new Label("Timeline Speed:"));

		Slider speedSlider = new Slider(Constants.TIMELINE_DELAY_BOUNDS[0], Constants.TIMELINE_DELAY_BOUNDS[1],
				Constants.TIMELINE_DELAY_BOUNDS[2]);

		speedSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_game.changeDelay(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(speedSlider);

		// GRAVITY SLIDER
		_controlPanel.getChildren().add(new Label("Gravity:"));

		Slider gravitySlider = new Slider(Constants.GRAVITY_BOUNDS[0], Constants.GRAVITY_BOUNDS[1],
				Constants.GRAVITY_BOUNDS[2]);

		gravitySlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_game.getSettings().setGravity(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(gravitySlider);

		// PIPE DIFF SLIDER
		_controlPanel.getChildren().add(new Label("Pipe Difference:"));

		Slider pipeDiffSlider = new Slider(Constants.MAX_PIPE_DIFF_BOUNDS[0], Constants.MAX_PIPE_DIFF_BOUNDS[1],
				Constants.MAX_PIPE_DIFF_BOUNDS[2]);

		pipeDiffSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_game.getSettings().setMaxPipeDiff(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(pipeDiffSlider);

		// PIPE GAP SLIDER
		_controlPanel.getChildren().add(new Label("Pipe Gap:"));

		Slider pipeGapSlider = new Slider(Constants.PIPE_GAP_BOUNDS[0], Constants.PIPE_GAP_BOUNDS[1],
				Constants.PIPE_GAP_BOUNDS[2]);

		pipeGapSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_game.getSettings().setPGap(newvalue.doubleValue());
			_game.changePipeGap();
		});

		_controlPanel.getChildren().add(pipeGapSlider);

		// PIPE SEP SLIDER
		_controlPanel.getChildren().add(new Label("Pipe Seperation:"));

		Slider pipeSepSlider = new Slider(Constants.PIPE_SEP_BOUNDS[0], Constants.PIPE_SEP_BOUNDS[1],
				Constants.PIPE_SEP_BOUNDS[2]);

		pipeSepSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_game.getSettings().setSep(newvalue.doubleValue());
			_game.changePipeSeperation();
		});

		_controlPanel.getChildren().add(pipeSepSlider);

		// X VEL SLIDER
		_controlPanel.getChildren().add(new Label("X Velocity:"));

		Slider xVelSlider = new Slider(Constants.X_VEL_BOUNDS[0], Constants.X_VEL_BOUNDS[1], Constants.X_VEL_BOUNDS[2]);

		xVelSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_game.getSettings().setXVel(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(xVelSlider);

		// Bounce Slider
		_controlPanel.getChildren().add(new Label("Bird Bounce Velocity:"));

		Slider birdBounceSlider = new Slider(Constants.BIRD_BOUNCE_BOUNDS[0], Constants.BIRD_BOUNCE_BOUNDS[1],
				Constants.BIRD_BOUNCE_BOUNDS[2]);

		birdBounceSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_game.getSettings().setBounce(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(birdBounceSlider);

		Button showEvolution = new Button("Show Progress");

		showEvolution.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				_longTermVisualizer.showGraph(AISingleGameOrganizer.this.getGameData());
			}
		});

		_controlPanel.getChildren().add(showEvolution);

		Button backtohome = new Button("Back Home");

		backtohome.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AISingleGameOrganizer.this.getGame().stopT();
				MainMenuOrganizer organizer = new MainMenuOrganizer(_stage);
				Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
			}
		});

		_controlPanel.getChildren().add(backtohome);

		Button quit = new Button("Quit");

		quit.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.exit(0);
			}
		});

		_controlPanel.getChildren().add(quit);
	}

	/**
	 * Returns arraylist of completed game data for all of the played games
	 */
	@Override
	public ArrayList<CompletedGameData> getGameData() {
		return _gameData;
	}

	/**
	 * Sets up the information panel
	 */
	private void setupInfoPanel() {
		_infoPanel = new VBox();

		Text gen = new Text("Current Generation:");
		_generationText = new Text(_generation + "");

		Text bLeft = new Text("Birds Alive:");
		_birdsLeft = _populations.get(_generation).getSize();
		_birdsLeftText = new Text(_birdsLeft + "/" + _populations.get(_generation).getSize());

		Text prevMaxFit = new Text("Last Gen Max Fitness:");
		_previousMaxFitText = new Text();

		Text prevAvgFit = new Text("Last Gen Avg Fitness:");
		_previousAvgFitText = new Text();

		_infoPanel.getChildren().addAll(gen, _generationText, bLeft, _birdsLeftText, prevMaxFit, _previousMaxFitText,
				prevAvgFit, _previousAvgFitText);
	}

	/**
	 * 
	 */
	protected void setupPanelRight() {
		_birdViews = new ArrayList<BirdView>();
		_panelRight = new GridPane();
		((BorderPane) _root).setRight(_panelRight);

		_panelRight.setMaxWidth(Constants.BIRD_VIEW_WIDTH);
		_panelRight.setMinWidth(Constants.BIRD_VIEW_WIDTH);
		_panelRight.setPrefWidth(Constants.BIRD_VIEW_WIDTH);
		_panelRight.setHgap(15);

		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.CENTER);
		_panelRight.getColumnConstraints().add(column0);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.LEFT);
		_panelRight.getColumnConstraints().add(column1);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setHalignment(HPos.CENTER);
		_panelRight.getColumnConstraints().add(column2);

		ColumnConstraints column3 = new ColumnConstraints();
		column3.setHalignment(HPos.CENTER);
		column3.setMaxWidth(115 + 100 / Constants.NUM_INPUT_NODES);
		column3.setMinWidth(115 + 100 / Constants.NUM_INPUT_NODES);
		column3.setPrefWidth(115 + 100 / Constants.NUM_INPUT_NODES);
		_panelRight.getColumnConstraints().add(column3);

		ColumnConstraints column4 = new ColumnConstraints();
		column4.setHalignment(HPos.CENTER);
		_panelRight.getColumnConstraints().add(column4);

		Label place = new Label("#");
		_panelRight.add(place, 0, 0);

		Label info = new Label("Info");
		_panelRight.add(info, 1, 0);

		Label image = new Label("Image");
		_panelRight.add(image, 2, 0);

		Label insouts = new Label("Inputs / Outputs");
		_panelRight.add(insouts, 3, 0);

		Label actions = new Label("Actions");
		_panelRight.add(actions, 4, 0);

		_panelRight.getRowConstraints().add(new RowConstraints(15));

		for (int i = 0; i < 5; i++) {
			BirdView view = new BirdView(i, this, _panelRight, _aiSettings);
			_birdViews.add(view);
			_panelRight.getRowConstraints().add(new RowConstraints(Constants.BIRD_VIEW_HEIGHT));
		}

	}

	/**
	 * Returns the right gridpane
	 */
	public GridPane getRightPane() {
		return _panelRight;
	}

	/**
	 * returns the neural net visualizer
	 */
	@Override
	public NeuralNetVisualizer getVisualizer() {
		return _visualizer;
	}

	/**
	 * Called when a game ends. Creates a new game in its place.
	 */
	@Override
	public void createNewGame() {

		_game = new AIGame(this, _game.getSettings());

		Population last = _populations.get(_generation - 1);
		Collections.sort(last.getBirds());

		if (last.getBirds().get(0).getFitness() < -400) {

			_populations.add(new Population(_game, _aiSettings, _generation, last.getCurrentID()));
		} else {
			_populations.add(new Population(_game, _populations.get(_generation - 1), _aiSettings));
		}
		// _populations.add(new Population(_populations.get(_ _game, _aiSettings));
		_game.setPopulation(_populations.get(_generation));

		_generationText.setText(_generation + "");

		_birdsLeft = _populations.get(_generation).getSize();
		_birdsLeftText.setText(_birdsLeft + "/" + _populations.get(_generation).getSize());

		_previousMaxFitText.setText("" + last.getBirds().get(0).getFitness());
		_previousAvgFitText.setText("" + last.getAvgFitness());

		((BorderPane) _root).setCenter(_game.getGamePane());

	}

	/**
	 * Updates the bird panels
	 */
	@Override
	public void updatePanel() {
		Collections.sort(_game.getBirds());
		for (int i = 0; i < 5; i++) {
			_game.getBirds().get(i).setBirdView(_birdViews.get(i));
			if (_game.getBirds().get(i).isDead()) {
				_birdViews.get(i).blackOutImage();
				_birdViews.get(i).unflash();
			}
		}
	}

	/**
	 * Called when a bird dies
	 */
	@Override
	public void birdDied() {
		super.birdDied();
		_birdsLeftText.setText(_birdsLeft + "/" + _populations.get(_generation).getSize());
	}

}