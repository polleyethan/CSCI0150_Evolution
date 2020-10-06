package Evolution;

import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * Container for MultiGame Screen Game. Allows User to have Multiple Games playing in same screen at one time.
 */
public class MultiGameScreen {

	private ArrayList<AIMultiGameOrganizer> _gameOrganizers;
	private ArrayList<GameView> _gameViews;
	private BorderPane _root;
	private VBox _controlPanel;
	private GameSettings _settings;
	private Timeline _timeline;
	private AISettings _aiset;
	private GridPane _center;
	private Button _addGameButton;
	private int _xPanels;
	private int _yPanels;
	private Stage _stage;
	private VBox _panelRight;
	private int _numGames;

	/**
	 * Constructor for MultiGameScreen
	 */
	public MultiGameScreen(Stage stage) {
		_stage = stage;
		_xPanels = 3;
		_yPanels = 3;
		_numGames = _xPanels * _yPanels;
		_settings = new GameSettings((Constants.DEFAULT_GAME_WIDTH + 100) / _xPanels,
				Constants.DEFAULT_GAME_HEIGHT / _yPanels);
		_root = new BorderPane();
		_gameOrganizers = new ArrayList<AIMultiGameOrganizer>();

		this.setupScreen(stage);
	}

	/**
	 * sets up the screen
	 */
	private void setupScreen(Stage stage) {
		this.setupControlsPanel();
		this.setupPanelRight();
		_center = new GridPane();
		this.setXConstraints();
		this.setYConstraints();
		for (int n = 0; n < _xPanels; n++) {
			for (int g = 0; g < _yPanels; g++) {
				Button addGame = new Button("Add Game");
				int x = n;
				int y = g;
				addGame.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						AIMenuOne menu = new AIMenuOneMultiGame(new Stage(), MultiGameScreen.this, x, y);
					}
				});
				_center.add(addGame, x, y);
			}
		}
		_root.setCenter(_center);

	}

	/**
	 * Sets up the right panel with GameViews
	 */
	private void setupPanelRight() {
		_gameViews = new ArrayList<GameView>();
		_panelRight = new VBox();
		_root.setRight(_panelRight);

		_panelRight.setMaxWidth(Constants.BIRD_VIEW_WIDTH - 100);
		_panelRight.setMinWidth(Constants.BIRD_VIEW_WIDTH - 100);
		_panelRight.setPrefWidth(Constants.BIRD_VIEW_WIDTH - 100);

		for (int i = 0; i < _numGames; i++) {
			GameView view = new GameView(i + 1, this);
			_gameViews.add(view);
			_panelRight.getChildren().add(view.getRoot());
		}

	}

	/**
	 * Adds a new Game to the specified x y coordinate
	 */
	public void addNewGame(AISettings aisettings, int x, int y) {
		System.out.println("X:" + x);
		System.out.println("Y:" + y);
		AIMultiGameOrganizer game = new AIMultiGameOrganizer(_gameViews.get((y * _yPanels) + x), _stage, aisettings,
				_settings, x, y);
		_gameOrganizers.add(game);

		int numgames = _gameOrganizers.size() - 1;
		_center.getChildren().remove((x * _xPanels) + y);
		_gameViews.get((y * _yPanels) + x).addGame(game);

		this.setConstraintGame(_gameOrganizers.get(numgames));
		this.setConstraintGame(_gameOrganizers.get(numgames));
		_center.add(_gameOrganizers.get(numgames).getRootPane(), x, y);
	}
	
	
	

	/**
	 * Deletes a given Game
	 */
	public void deleteGame(AIMultiGameOrganizer organizer) {
		int x = organizer.getX();

		int y = organizer.getY();
		_center.getChildren().remove(organizer.getRootPane());
		_gameOrganizers.remove(organizer);
		Button addGame = new Button("Add Game");
		addGame.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AIMenuOne menu = new AIMenuOneMultiGame(new Stage(), MultiGameScreen.this, x, y);
			}
		});
		_center.add(addGame, x, y);
	}

	/**
	 * Sets X Constraints 
	 */
	private void setXConstraints() {
		double constraint = (Constants.DEFAULT_GAME_WIDTH + 100) / _xPanels;
		for (int x = 0; x < _xPanels; x++) {
			ColumnConstraints columnc = new ColumnConstraints();
			columnc.setHalignment(HPos.CENTER);
			columnc.setMaxWidth(constraint);
			columnc.setMinWidth(constraint);
			columnc.setPrefWidth(constraint);
			_center.getColumnConstraints().add(columnc);
		}

	}

	/**
	 * Sets Game Constraints
	 */
	private void setConstraintGame(AIMultiGameOrganizer game) {
		double constraintx = (Constants.DEFAULT_GAME_WIDTH + 100) / _xPanels;
		double constrainty = Constants.DEFAULT_GAME_HEIGHT / _yPanels;
		game.getRootPane().setMinSize(constraintx, constrainty);
		game.getRootPane().setMaxSize(constraintx, constrainty);
		game.getRootPane().setPrefSize(constraintx, constrainty);
	}

	/**
	 * Sets y Constraints
	 */
	private void setYConstraints() {
		double constraint = Constants.DEFAULT_GAME_HEIGHT / _yPanels;
		for (int y = 0; y < _yPanels; y++) {
			RowConstraints rowr = new RowConstraints();
			rowr.setValignment(VPos.CENTER);
			rowr.setMaxHeight(constraint);
			rowr.setMinHeight(constraint);
			rowr.setPrefHeight(constraint);
			_center.getRowConstraints().add(rowr);
		}
	}

	/**
	 * Sets up the Control Panel
	 */
	private void setupControlsPanel() {

		_controlPanel = new VBox();

		// TIMELINE SPEED SLIDER
		_controlPanel.getChildren().add(new Label("Timeline Speed:"));

		Slider speedSlider = new Slider(Constants.TIMELINE_DELAY_BOUNDS[0], Constants.TIMELINE_DELAY_BOUNDS[1],
				Constants.TIMELINE_DELAY_BOUNDS[2]);

		speedSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			this.changeDelay(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(speedSlider);

		// GRAVITY SLIDER
		_controlPanel.getChildren().add(new Label("Gravity:"));

		Slider gravitySlider = new Slider(Constants.GRAVITY_BOUNDS[0], Constants.GRAVITY_BOUNDS[1],
				Constants.GRAVITY_BOUNDS[2]);

		gravitySlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_settings.setGravity(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(gravitySlider);

		// PIPE DIFF SLIDER
		_controlPanel.getChildren().add(new Label("Pipe Difference:"));

		Slider pipeDiffSlider = new Slider(Constants.MAX_PIPE_DIFF_BOUNDS[0], Constants.MAX_PIPE_DIFF_BOUNDS[1],
				Constants.MAX_PIPE_DIFF_BOUNDS[2]);

		pipeDiffSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_settings.setMaxPipeDiff(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(pipeDiffSlider);

		// PIPE GAP SLIDER
		_controlPanel.getChildren().add(new Label("Pipe Gap:"));

		Slider pipeGapSlider = new Slider(Constants.PIPE_GAP_BOUNDS[0], Constants.PIPE_GAP_BOUNDS[1],
				Constants.PIPE_GAP_BOUNDS[2]);

		pipeGapSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_settings.setPGap(newvalue.doubleValue());
			this.changePipeGap();
		});

		_controlPanel.getChildren().add(pipeGapSlider);

		// PIPE SEP SLIDER
		_controlPanel.getChildren().add(new Label("Pipe Seperation:"));

		Slider pipeSepSlider = new Slider(Constants.PIPE_SEP_BOUNDS[0], Constants.PIPE_SEP_BOUNDS[1],
				Constants.PIPE_SEP_BOUNDS[2]);

		pipeSepSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_settings.setSep(newvalue.doubleValue());
			this.changePipeSeperation();
		});

		_controlPanel.getChildren().add(pipeSepSlider);

		// X VEL SLIDER
		_controlPanel.getChildren().add(new Label("X Velocity:"));

		Slider xVelSlider = new Slider(Constants.X_VEL_BOUNDS[0], Constants.X_VEL_BOUNDS[1], Constants.X_VEL_BOUNDS[2]);

		xVelSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_settings.setXVel(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(xVelSlider);

		// Bounce Slider
		_controlPanel.getChildren().add(new Label("Bird Bounce Velocity:"));

		Slider birdBounceSlider = new Slider(Constants.BIRD_BOUNCE_BOUNDS[0], Constants.BIRD_BOUNCE_BOUNDS[1],
				Constants.BIRD_BOUNCE_BOUNDS[2]);

		birdBounceSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			_settings.setBounce(newvalue.doubleValue());
		});

		_controlPanel.getChildren().add(birdBounceSlider);

		Button backtohome = new Button("Back Home");

		backtohome.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
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

		_controlPanel.setSpacing(5);
		_root.setLeft(_controlPanel);

	}

	/**
	 *  Changes the Pipes seperation
	 */
	private void changePipeSeperation() {
		for (AIMultiGameOrganizer g : _gameOrganizers) {
			g.getGame().changePipeSeperation();
		}

	}

	/**
	 * Changes the Pipe Gap
	 */
	private void changePipeGap() {
		for (AIMultiGameOrganizer g : _gameOrganizers) {
			g.getGame().changePipeGap();
		}
	}

	/**
	 * Changes the TImeline Delay
	 */
	public void changeDelay(double del) {
		for (AIMultiGameOrganizer g : _gameOrganizers) {
			g.getGame().changeDelay(del);
		}
	}

	/**
	 * returns the root pane
	 */
	public Pane getRootPane() {

		return _root;
	}

}
