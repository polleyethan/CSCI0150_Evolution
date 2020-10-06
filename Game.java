package Evolution;

import java.util.LinkedList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Abstract class representing a Flappy Bird Game
 *
 */
public abstract class Game {

	protected Pane _gamePane;
	protected double _xVel;
	protected double _x;
	protected Timeline _timeline;
	protected Boolean _isOver;
	protected Boolean _isPaused;
	protected Boolean _hasStarted;
	protected LinkedList<Pipe> _pipes;
	protected GameSettings _settings;
	protected double _timer;

	/**
	 * Constructor taking in game setttings
	 */
	public Game(GameSettings settings) {
		_settings = settings;
		_hasStarted = false;
		_isOver = false;
		_isPaused = false;
		_x = 0;
		_timer = 0;
		this.setupTimeline();
		this.setupVisually();
		this.setupPipes();
	}

	/**
	 * returns the game pane
	 */
	public Pane getGamePane() {
		return _gamePane;
	}

	/**
	 * changes the timeline delay
	 */
	public void changeDelay(double del) {
		_settings.setTDelay(del);
		_timeline.pause();
		_timeline.getKeyFrames().set(0, new KeyFrame(Duration.seconds(_settings.getDelay()), new TimeHandler()));
		_timeline.playFromStart();
	}

	/**
	 * updates the game; called when timeline updates
	 */
	public void update() {
		_timer += _settings.getDelay();
		this.checkCollisions();
		this.checkGameOver();
		if (!_isOver) {
			this.updateBirds();
			this.updateBackground();
			this.updatePipes();
		} else {
			if (this.stopTimeline()) {
				this.endScreen();
			} else {
				this.updateBirds();
				this.onGameOver();
			}
		}
	}

	/**
	 * Abstract method calling the endscreen
	 */
	protected abstract void endScreen();

	/**
	 * abstract method checking if the timeline should stop
	 */
	protected abstract boolean stopTimeline();

	/**
	 * Abstract method checking if the game is over
	 */
	protected abstract void checkGameOver();

	/**
	 * Abstract method checking for collisions
	 */
	protected abstract void checkCollisions();

	/**
	 * Abstract method to update the birds
	 */
	protected abstract void updateBirds();

	/**
	 * Abstract method called when the game is over
	 */
	protected abstract void onGameOver();

	/**
	 * Method checking if a bird has collided with any pipes or the top and bottom of the screen.
	 */
	protected Boolean checkBirdCollision(Bird bird) {
		if (bird.getY() > _settings.getHeight() || bird.getY() < 0) {
			return true;
		} else {
			for (Pipe p : _pipes) {
				if (p.didCollideWith(bird)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Updates the pipes
	 */
	private void updatePipes() {
		for (int n = 0; n < _pipes.size(); n++) {
			double translatedX = this.getTranslateX(_pipes.get(n).getX());
			if (translatedX + _settings.getPWidth() > 0) {
				_pipes.get(n).update(translatedX);
			} else {
				_pipes.get(n).removeGraphically(_gamePane);
				_pipes.remove(n);
				_pipes.add(this.createPipe());
			}
		}
	}

	/**
	 * Returns the relative x of the screen.
	 */
	public double getTranslateX(double xToTranslate) {
		return xToTranslate - _x;
	}

	/**
	 * Updates the background
	 */
	private void updateBackground() {
		_xVel = _settings.getXVel();
		_x += _xVel;
	}

	/**
	 * creates a new random pipe
	 */
	private Pipe createPipe() {
		double difference = getRandomNumber(_settings.getMaxPipeDiff(), -1 * _settings.getMaxPipeDiff());
		if (_pipes.getLast().getTopY() + difference < 0) {
			double topY = 0;
			double x = _pipes.getLast().getX() + _settings.getPWidth() + _settings.getSep();
			double width = _settings.getPWidth();
			return new Pipe(topY, x, this);
		} else if (_pipes.getLast().getBottomY() + difference > _settings.getHeight()) {
			double topY = _settings.getHeight() - _settings.getPGap();
			double x = _pipes.getLast().getX() + _settings.getPWidth() + _settings.getSep();
			return new Pipe(topY, x, this);
		} else {
			double topY = _pipes.getLast().getTopY() + difference;
			double x = _pipes.getLast().getX() + _settings.getPWidth() + _settings.getSep();
			double width = _settings.getPWidth();
			return new Pipe(topY, x, this);
		}
	}

	/**
	 * gets a random number given a max and min
	 */
	public static double getRandomNumber(double min, double max) {
		return (((max - min) * Math.random()) + min);
	}

	/**
	 * sets up the timeline
	 */
	public void setupTimeline() {
		KeyFrame kf = new KeyFrame(Duration.seconds(_settings.getDelay()), new TimeHandler());
		_timeline = new Timeline(kf);
		_timeline.setCycleCount(Animation.INDEFINITE);
	}

	/**
	 * sets up the game visually
	 */
	private void setupVisually() {
		_gamePane = new Pane();
		_gamePane.setPrefHeight(_settings.getHeight());
		_gamePane.setMinHeight(_settings.getHeight());
		_gamePane.setMaxHeight(_settings.getHeight());
		_gamePane.setPrefWidth(_settings.getWidth());
		_gamePane.setMinWidth(_settings.getWidth());
		_gamePane.setMaxWidth(_settings.getWidth());
		Rectangle clippedRect = new Rectangle(0, 0, _settings.getWidth(), _settings.getHeight());
		_gamePane.setClip(clippedRect);
		_gamePane.requestFocus();
		_gamePane.setFocusTraversable(true);
		_gamePane.setBackground(new Background(new BackgroundFill(Color.rgb(69, 179, 224), null, null)));
	}

	/**
	 * returns the Games settings
	 */
	public GameSettings getSettings() {
		return _settings;
	}

	/**
	 * sets up the initial pipes
	 */
	private void setupPipes() {
		_pipes = new LinkedList<Pipe>();
		_pipes.add(new Pipe(_settings.getHeight() / 2 - _settings.getPGap() / 2, 1000, this));
		for (int n = 0; n < 4; n++) {
			_pipes.add(this.createPipe());
		}

	}

	/**
	 * changes the pipe gap
	 */
	public void changePipeGap() {
		for (int n = 0; n < _pipes.size(); n++) {
			_pipes.get(n).changeGap(this);
		}
	}

	/**
	 * changes the pipe seperation
	 */
	public void changePipeSeperation() {
		for (int n = 1; n < _pipes.size(); n++) {
			_pipes.get(n).changeSep(this, _pipes.get(n - 1).getX() + _settings.getSep());
		}

	}

	/**
	 * changes the pipe width
	 */
	public void changePipeWidth() {
		for (int n = 0; n < _pipes.size(); n++) {
			double translatedX = this.getTranslateX(_pipes.get(n).getX());
			if (translatedX + _settings.getPWidth() > 0) {
				_pipes.get(n).update(translatedX);
			} else {
				_pipes.get(n).removeGraphically(_gamePane);
				_pipes.remove(n);
				_pipes.add(this.createPipe());
			}
		}
	}

	/**
	 * Timehandler to update the game based on timeline
	 *
	 */
	private class TimeHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			update();
		}
	}

}
