package Evolution;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * Abstract class representing a bird.
 */
public abstract class Bird {

	private Boolean _isDead;
	protected double _x;
	protected double _y;
	protected double _yVel;
	private ImageView _birdGraphic;
	private Image _birdImg;
	protected double _score;
	protected Boolean _hasFallen;
	protected Color _color;
	protected Game _game;

	/**
	 * Abstract constructor for a bird
	 */
	public Bird(Game game, Color color) {
		_game = game;
		_isDead = false;
		_color = color;
		_hasFallen = false;
		_y = game.getSettings().getHeight() / 2;
		_x = game.getSettings().getWidth() / 2;

		this.createBird(game.getGamePane());
	}

	/**
	 * returns whether or not the bird is dead
	 */
	public Boolean isDead() {
		return _isDead;
	}

	/**
	 * returns whether or not the bird has fallen
	 */
	public Boolean hasFallen() {
		return _hasFallen;
	}

	/**
	 * bounces the bird
	 */
	public void bounce() {
		_yVel = _game.getSettings().getBounce();
	}

	/**
	 * returns the birds y val
	 */
	public double getY() {
		return _y;
	}

	/**
	 * returns the birds y velocity
	 */
	public double getYVel() {
		return _yVel;
	}

	/**
	 * returns the birds x val
	 */
	public double getX() {
		return _x;
	}

	/**
	 * updates the bird
	 */
	public void update() {
		this.updateLogically();
		this.updateGraphically();
	}

	/**
	 * updates the bird logically
	 */
	public void updateLogically() {
		if (!_isDead) {
			_x += _game.getSettings().getXVel();
			_score = _x;
			_yVel += _game.getSettings().getGravity();
			_y += _yVel;
		} else if (!_hasFallen) {
			_yVel += Constants.GRAVITY_BOUNDS[2] * Constants.TIMELINE_MOVEMENT_RATIO;
			;
			_y += _yVel;
			if (_y - _game.getSettings().getBRadius() > _game.getSettings().getHeight()) {
				_hasFallen = true;
			}
		}
	}

	/**
	 * updates the bird graphically
	 */
	public void updateGraphically() {
		if (!_isDead) {
			_birdGraphic.setX(_game.getSettings().getWidth() / 2);
			_birdGraphic.setY(_y);
			_birdGraphic.setRotate(Math.toDegrees(Math.PI * _yVel / 16));
		} else if (!_hasFallen) {
			_birdGraphic.setX(_game.getSettings().getWidth() / 2);
			_birdGraphic.setY(_y);
			_birdGraphic.setRotate(Math.toDegrees(Math.PI * _yVel / 16));
		}
	}

	/**
	 * creates the bird graphically.
	 */
	private void createBird(Pane gamePane) {
		Image importedImg = new Image(getClass().getResource("TEST.png").toExternalForm(), _game.getSettings().getBRadius(), _game.getSettings().getBRadius(),
				true, false);
		PixelReader pixelReader = importedImg.getPixelReader();

		int width = (int) importedImg.getWidth();
		int height = (int) importedImg.getHeight();

		WritableImage writableImg = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImg.getPixelWriter();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color clr = pixelReader.getColor(x, y);
				if ((int) (clr.getRed() * 255) == 33 && (int) (clr.getGreen() * 255) == 150
						&& (int) (clr.getBlue() * 255) == 243) {
					pixelWriter.setColor(x, y, _color);
				} else {
					pixelWriter.setColor(x, y, clr);
				}
			}
		}

		_birdImg = writableImg;
		_birdGraphic = new ImageView(_birdImg);
		_birdGraphic.setX(_x);
		_birdGraphic.setY(_y);
		gamePane.getChildren().add(_birdGraphic);
	}

	/**
	 * called when bird dies
	 */
	public void die() {
		_isDead = true;
	}

	/**
	 * returns the birds radius
	 */
	public double getRadius() {
		return _game.getSettings().getBRadius();
	}

	/**
	 * gets the birds score
	 */
	public double getScore() {
		return _score;
	}

	/**
	 * gets the graphics for the bird
	 */
	public ImageView getGraphics() {
		return _birdGraphic;
	}

	/**
	 * gets the image for the bird
	 */
	public Image getImg() {
		return _birdImg;
	}

	/**
	 * gets the game that the bird is in
	 */
	public Game getGame() {
		return _game;
	}

	/**
	 * removes the bird graphically
	 */
	public void removeGraphically(Pane gamePane) {

		gamePane.getChildren().remove(_birdGraphic);
	}

	/**
	 * gets the color of the bird
	 */
	public Color getColor() {
		return _color;
	}
}
