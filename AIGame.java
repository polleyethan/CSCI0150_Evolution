package Evolution;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * AIGame subclass of Game class. Used for AIGames
 */
public class AIGame extends Game {

	protected Population _population;
	private AIGameOrganizer _organizer;
	private double[] _averageSettings;
	private double _avgTimer;

	/**
	 * Constructor for AIGame 
	 */
	public AIGame(AIGameOrganizer organizer, GameSettings settings) {
		super(settings);
		_organizer = organizer;
		_averageSettings = new double[7];
		_avgTimer = 0;
		_gamePane.requestFocus();
		_hasStarted = true;
		_timeline.play();
	}

	/**
	 * sets the population for this game
	 */
	public void setPopulation(Population pop) {
		_population = pop;
	}

	/**
	 * overrides the update method for AIGames
	 */
	@Override
	public void update() {
		super.update();
		this.calculateAverages();
		_organizer.updatePanel();

	}

	/**
	 * calculates the average Settings values for this game
	 */
	private void calculateAverages() {
		_avgTimer += 1;
		_averageSettings[0] += _settings.getGravity();
		_averageSettings[1] += _settings.getMaxPipeDiff();
		_averageSettings[2] += _settings.getPGap();
		_averageSettings[3] += _settings.getSep();
		_averageSettings[4] += _settings.getXVel();
		_averageSettings[5] += _settings.getBounce();
		_averageSettings[6] += _settings.getPWidth();
	}

	/**
	 * returns the average settings values for this game
	 */
	public double[] getAvgSettings() {
		double[] toReturn = new double[7];
		for (int n = 0; n < _averageSettings.length; n++) {
			toReturn[n] = _averageSettings[n] / _avgTimer;
		}
		return toReturn;
	}

	/**
	 * checks if the game is over
	 */
	@Override
	protected void checkGameOver() {
		if (!this.anyBirdsAlive()) {
			_isOver = true;
		}
	}

	/**
	 * Returns whether or not any birds are still alive
	 */
	protected Boolean anyBirdsAlive() {
		for (AIBird b : _population.getBirds()) {
			if (!b.isDead()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks for collisions between birds
	 */
	@Override
	protected void checkCollisions() {
		for (AIBird b : _population.getBirds()) {
			if (!b.isDead()) {
				if (this.checkBirdCollision(b)) {
					b.die();
					_organizer.birdDied();
				}
			}
		}
	}

	/**
	 * Updates all the birds in the game. Also determines what move each bird will make and updates each birds birdview.
	 */
	@Override
	protected void updateBirds() {
		for (AIBird b : _population.getBirds()) {
			if (!b.isDead()) {
				b.update();
				Pipe next = this.getNextPipe(b);
				double move = b.calculateMove(next);
				b.updateView(next, move);
			} else if (!b.hasFallen()) {
				b.update();
			} else {
				b.removeGraphically(_gamePane);
			}
		}

	}

	/**
	 * gets the next pipe that the birds must face
	 */
	protected Pipe getNextPipe(Bird b) {
		for (int n = 0; n < _pipes.size(); n++) {
			if (_pipes.get(n).getX() + _settings.getPWidth() > b.getX()) {

				return _pipes.get(n);
			}
		}
		return _pipes.getLast();
	}

	/**
	 * Override. Called when game over
	 */
	@Override
	protected void onGameOver() {
		// System.out.println("GAME OVER");
	}


	/**
	 * Displays the end screen when the game is over
	 */
	@Override
	protected void endScreen() {
		_timeline.stop();
		_population.calculateFitness();
		_organizer.onGameEnd();

	}

	/**
	 * stops the timeline
	 */
	protected void stopT() {
		_timeline.stop();
	}

	/**
	 * returns if the timeline shoudl stop or not
	 */
	@Override
	protected boolean stopTimeline() {
		for (AIBird b : _population.getBirds()) {
			if (!b.hasFallen()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * gets this games population
	 */
	public ArrayList<AIBird> getBirds() {
		return _population.getBirds();
	}

}
