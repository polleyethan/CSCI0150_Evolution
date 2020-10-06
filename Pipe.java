package Evolution;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Representation of a Pipe in Flappy bird
 *
 */
public class Pipe {

	private double _topY;
	private double _bottomY;
	private double _x;
	private ImageView _topPipe;
	private ImageView _bottomPipe;
	private Boolean _topPipeExists;
	private Boolean _bottomPipeExists;
	private double _width;

	/**
	 * Constructor for a pipe
	 */
	public Pipe(double topY, double x, Game game) {
		_width = game.getSettings().getPWidth();
		_topY = topY;
		_bottomY = _topY + game.getSettings().getPGap();
		if (_topY <= 0) {
			_topPipeExists = false;
		} else {
			_topPipeExists = true;
		}
		if (_topY + game.getSettings().getPGap() > game.getSettings().getHeight()) {
			_bottomPipeExists = false;
		} else {
			_bottomPipeExists = true;
		}
		_x = x;
		this.createGraphically(game);
	}

	/**
	 * Returns if a pipe collided with a given bird
	 */
	public Boolean didCollideWith(Bird b) {
		if (_topPipeExists && _bottomPipeExists) {
			return this.didCollideBottomPipe(b) || this.didCollideTopPipe(b);
		} else if (_topPipeExists) {
			return this.didCollideTopPipe(b);
		} else {
			return this.didCollideBottomPipe(b);
		}
	}

	/**
	 * checks if both the top and bottom pipe exist
	 */
	public void checkBothExist(Game game) {
		if (_topY <= 0) {
			if (_topPipeExists) {
				game.getGamePane().getChildren().remove(_topPipe);
			}
			_topPipeExists = false;
		} else {
			_topPipeExists = true;
		}
		if (_topY + game.getSettings().getPGap() > game.getSettings().getHeight()) {
			if (_bottomPipeExists) {
				game.getGamePane().getChildren().remove(_bottomPipe);
			}
			_bottomPipeExists = false;
		} else {
			_bottomPipeExists = true;
		}
	}

	/**
	 * Checks if a bird did collide with the top pipe
	 */
	public Boolean didCollideTopPipe(Bird b) {
		if (_topPipe.intersects(b.getGraphics().getBoundsInLocal())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a bird did collide with the bottom pipe
	 */
	public Boolean didCollideBottomPipe(Bird b) {
		if (_bottomPipe.intersects(b.getGraphics().getBoundsInLocal())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * updates the pipe
	 */
	public void update(double graphicX) {
		this.updateGraphically(graphicX);
	}

	/**
	 * updates the pipe graphically
	 */
	public void updateGraphically(double graphicX) {
		if (_topPipeExists) {
			_topPipe.setX(graphicX);
		}
		if (_bottomPipeExists) {
			_bottomPipe.setX(graphicX);
		}

	}

	/**
	 * creates the pipe graphically
	 */
	public void createGraphically(Game game) {
		if (_topPipeExists) {
			this.createTopPipe(game);
		}
		if (_bottomPipeExists) {
			this.createBottomPipe(game);
		}
	}

	/**
	 * creates the top pipe
	 */
	public void createTopPipe(Game game) {
		_topPipe = new ImageView(new Image(getClass().getResource("PIPE2.png").toExternalForm()));
		_topPipe.setX(game.getTranslateX(_x));
		_topPipe.setY(0);
		_topPipe.setFitWidth(game.getSettings().getPWidth());
		_topPipe.setFitHeight(_topY);
		_topPipe.maxHeight(_topY);
		_topPipe.minHeight(_topY);
		_topPipe.prefHeight(_topY);
		game.getGamePane().getChildren().add(_topPipe);
	}

	/**
	 * creates the bottom pipe
	 */
	public void createBottomPipe(Game game) {
		_bottomPipe = new ImageView(new Image(getClass().getResource("PIPE2.png").toExternalForm()));
		_bottomPipe.setFitWidth(game.getSettings().getPWidth());
		_bottomPipe.setX(game.getTranslateX(_x));
		_bottomPipe.setY(_bottomY);
		_bottomPipe.setFitHeight(game.getSettings().getHeight() - _bottomY);
		_bottomPipe.maxHeight(game.getSettings().getHeight() - _bottomY);
		_bottomPipe.minHeight(game.getSettings().getHeight() - _bottomY);
		_bottomPipe.prefHeight(game.getSettings().getHeight() - _bottomY);
		game.getGamePane().getChildren().add(_bottomPipe);
	}

	/**
	 * gets the top y
	 */
	public double getTopY() {
		return _topY;
	}

	/**
	 * gets the bottom y
	 */
	public double getBottomY() {
		return _bottomY;
	}

	/**
	 * gets the x loc
	 */
	public double getX() {
		return _x;
	}

	/**
	 * changes the pipe gap
	 */
	public void changeGap(Game game) {
		double cGap = _bottomY - _topY;
		double diff = game.getSettings().getPGap() - cGap;
		_topY -= diff / 2;
		_bottomY = _topY + game.getSettings().getPGap();
		this.checkBothExist(game);

		if (_topPipeExists && _bottomPipeExists) {
			this.updateTopGap(game);
			this.updateBottomGap(game);
		} else if (_topPipeExists) {
			this.updateTopGap(game);
		} else {
			this.updateBottomGap(game);
		}
	}

	/**
	 * updates the bottom gap
	 */
	public void updateBottomGap(Game game) {
		if (_bottomPipe != null) {
			_bottomPipe.setY(_bottomY);
			_bottomPipe.setFitHeight(game.getSettings().getHeight() - _bottomY);
			_bottomPipe.maxHeight(game.getSettings().getHeight() - _bottomY);
			_bottomPipe.minHeight(game.getSettings().getHeight() - _bottomY);
			_bottomPipe.prefHeight(game.getSettings().getHeight() - _bottomY);
		} else {
			this.createBottomPipe(game);
		}
	}

	/**
	 * updates the top gap
	 */
	public void updateTopGap(Game game) {
		if (_topPipe != null) {
			_topPipe.setFitHeight(_topY);
			_topPipe.maxHeight(_topY);
			_topPipe.minHeight(_topY);
			_topPipe.prefHeight(_topY);
		} else {
			this.createTopPipe(game);
		}
	}

	/**
	 * changes the pipes seperation
	 */
	public void changeSep(Game game, double x) {
		_x = x;
		if (_topPipeExists) {
			_topPipe.setX(game.getTranslateX(_x));
		}
		if (_bottomPipeExists) {
			_bottomPipe.setX(game.getTranslateX(_x));
		}
	}

	/**
	 * changes the pipes width
	 */
	public void changeWidth(Game game) {
		_width = game.getSettings().getPWidth();
		if (_topPipeExists) {
			_topPipe.setFitWidth(_width);
		}
		if (_bottomPipeExists) {
			_bottomPipe.setFitWidth(_width);
		}
	}

	/**
	 * removes the pipe graphically
	 */
	public void removeGraphically(Pane gamePane) {
		if (_topPipeExists) {
			gamePane.getChildren().remove(_topPipe);
		}
		if (_bottomPipeExists) {
			gamePane.getChildren().remove(_bottomPipe);
		}
	}
}
