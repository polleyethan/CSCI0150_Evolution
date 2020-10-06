package Evolution;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 *  Subclass of game representing a Single Player, non-ai Game
 *
 */
public class SinglePlayerGame extends Game {

	private Bird _bird;

	/**
	 * Constructor 
	 */
	public SinglePlayerGame(GameSettings settings) {
		super(settings);
		_bird = new HumanBird(this);
		_gamePane.setOnKeyPressed(new KeyHandler());
		_gamePane.requestFocus();
	}

	/**
	 *Checks if the game is over
	 */
	@Override
	protected void checkGameOver() {
		if (_bird.isDead()) {
			_isOver = true;
		}
	}

	/**
	 *checks for collisions
	 */
	@Override
	protected void checkCollisions() {
		if (this.checkBirdCollision(_bird)) {
			_bird.die();
		}

	}

	/**
	 * updates the birds
	 */
	@Override
	protected void updateBirds() {
		if (!_bird.isDead()) {
			_bird.update();
		} else if (!_bird.hasFallen()) {
			_bird.update();
		} else {
			_bird.removeGraphically(_gamePane);
		}

	}

	/**
	 *called on game over
	 */
	@Override
	protected void onGameOver() {
		System.out.println("GAME OVER");

	}

	/**
	 * Key handler to allow inputs
	 *
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			if (!_isOver) {
				switch (e.getCode()) {
				case SPACE: {
					if (_hasStarted) {
						_bird.bounce();
						break;
					} else {
						_hasStarted = true;
						_timeline.play();
						_bird.bounce();
						break;
					}
				}
				case UP: {
					System.out.println("TST");
					break;
				}
				default:
					break;
				}
			}
			e.consume();
		}

	}

	/**
	 * called when game ends
	 */
	@Override
	protected void endScreen() {
		_timeline.stop();

	}

	/**
	 * checks if timeline should end
	 */
	@Override
	protected boolean stopTimeline() {
		return _bird.hasFallen();
	}

}
