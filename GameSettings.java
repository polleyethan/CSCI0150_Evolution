package Evolution;

/**
 *
 * Container class representing the games settings
 */
public class GameSettings {

	public double _TIMELINE_DELAY;
	public double _GRAVITY_NOMINAL;
	public double _MAX_PIPE_DIFFERENCE;
	public double _PIPE_GAP;
	public double _PIPE_SEPERATION;
	public double _X_VELOCITY;
	public double _BIRD_BOUNCE_VELOCITY;
	public double _PIPE_WIDTH;

	public double _GAME_HEIGHT;
	public double _GAME_WIDTH;
	public double _BIRD_RADIUS;

	/**
	 * Constructor representing custom game settings
	 */
	public GameSettings(double del, double g, double pipediff, double pipegap, double pipesep, double xvel,
			double bouncevel, double pwidth) {
		_TIMELINE_DELAY = del;
		_GRAVITY_NOMINAL = g;
		_MAX_PIPE_DIFFERENCE = pipediff;
		_PIPE_GAP = pipegap;
		_PIPE_SEPERATION = pipesep;
		_X_VELOCITY = xvel;
		_BIRD_BOUNCE_VELOCITY = bouncevel;
		_PIPE_WIDTH = pwidth;
		_GAME_HEIGHT = Constants.DEFAULT_GAME_HEIGHT;
		_GAME_WIDTH = Constants.DEFAULT_GAME_WIDTH;
		_BIRD_RADIUS = Constants.DEFAULT_BIRD_RADIUS;
	}

	/**
	 * Constructor representing default game settings
	 */
	public GameSettings() {
		_TIMELINE_DELAY = Constants.TIMELINE_DELAY_BOUNDS[2];
		_GRAVITY_NOMINAL = Constants.GRAVITY_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_MAX_PIPE_DIFFERENCE = Constants.MAX_PIPE_DIFF_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_PIPE_GAP = Constants.PIPE_GAP_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_PIPE_SEPERATION = Constants.PIPE_SEP_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_X_VELOCITY = Constants.X_VEL_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_BIRD_BOUNCE_VELOCITY = Constants.BIRD_BOUNCE_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_PIPE_WIDTH = Constants.PIPE_WIDTH_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_GAME_HEIGHT = Constants.DEFAULT_GAME_HEIGHT / Constants.MINI_GAME_SIZE_DEFALTOR;
		_GAME_WIDTH = Constants.DEFAULT_GAME_WIDTH / Constants.MINI_GAME_SIZE_DEFALTOR;
		_BIRD_RADIUS = Constants.DEFAULT_BIRD_RADIUS / Constants.MINI_GAME_SIZE_DEFALTOR;
	}

	/**
	 * Construtors representing a default game with different height and width
	 */
	public GameSettings(double xConst, double yConst) {
		double deflate = Constants.DEFAULT_GAME_HEIGHT / yConst;
		_TIMELINE_DELAY = Constants.TIMELINE_DELAY_BOUNDS[2];
		_GRAVITY_NOMINAL = Constants.GRAVITY_BOUNDS[2] / deflate;
		_MAX_PIPE_DIFFERENCE = Constants.MAX_PIPE_DIFF_BOUNDS[2] / deflate;
		_PIPE_GAP = Constants.PIPE_GAP_BOUNDS[2] / deflate;
		_PIPE_SEPERATION = Constants.PIPE_SEP_BOUNDS[2] / deflate;
		_X_VELOCITY = Constants.X_VEL_BOUNDS[2] / deflate;
		_BIRD_BOUNCE_VELOCITY = Constants.BIRD_BOUNCE_BOUNDS[2] / deflate;
		_GAME_HEIGHT = yConst;
		_GAME_WIDTH = xConst;
		_BIRD_RADIUS = Constants.DEFAULT_BIRD_RADIUS / deflate;
		_PIPE_WIDTH = Constants.PIPE_WIDTH_BOUNDS[2] / deflate;

	}

	/**
	 * Constructor for default gamesettings with a faster timeline 
	 */
	public GameSettings(Boolean fast) {
		_TIMELINE_DELAY = Constants.TIMELINE_DELAY_BOUNDS[2];
		if (fast) {
			_TIMELINE_DELAY = Constants.TIMELINE_DELAY_BOUNDS[0];
		}
		_GRAVITY_NOMINAL = Constants.GRAVITY_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_MAX_PIPE_DIFFERENCE = Constants.MAX_PIPE_DIFF_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_PIPE_GAP = Constants.PIPE_GAP_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_PIPE_SEPERATION = Constants.PIPE_SEP_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_X_VELOCITY = Constants.X_VEL_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_BIRD_BOUNCE_VELOCITY = Constants.BIRD_BOUNCE_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_PIPE_WIDTH = Constants.PIPE_WIDTH_BOUNDS[2] / Constants.MINI_GAME_SIZE_DEFALTOR;
		_GAME_HEIGHT = Constants.DEFAULT_GAME_HEIGHT / Constants.MINI_GAME_SIZE_DEFALTOR;
		_GAME_WIDTH = Constants.DEFAULT_GAME_WIDTH / Constants.MINI_GAME_SIZE_DEFALTOR;
		_BIRD_RADIUS = Constants.DEFAULT_BIRD_RADIUS / Constants.MINI_GAME_SIZE_DEFALTOR;

	}

	/**
	 * returns the game height
	 */
	public double getHeight() {
		return _GAME_HEIGHT;
	}

	/**
	 * returns the game width
	 */
	public double getWidth() {
		return _GAME_WIDTH;
	}

	/**
	 * returns the bird radius
	 */
	public double getBRadius() {
		return _BIRD_RADIUS;
	}

	/**
	 * sets the nominal gravity
	 */
	public void setGravity(double g) {
		_GRAVITY_NOMINAL = g;
	}

	/**
	 * sets the max pipe difference
	 */
	public void setMaxPipeDiff(double diff) {
		_MAX_PIPE_DIFFERENCE = diff;
	}

	/**
	 * sets the pipe gap
	 */
	public void setPGap(double gap) {
		_PIPE_GAP = gap;
	}

	/**
	 * sets the pipe seperation
	 */
	public void setSep(double g) {
		_PIPE_SEPERATION = g;
	}

	/**
	 * sets the x velocity
	 */
	public void setXVel(double g) {
		_X_VELOCITY = g;
	}

	/**
	 * sets the birds bounce velocity
	 */
	public void setBounce(double g) {
		_BIRD_BOUNCE_VELOCITY = g;
	}

	/**
	 * sets the timeline delay
	 */
	public void setTDelay(double g) {
		_TIMELINE_DELAY = g;
	}

	/**
	 * sets the pipe width
	 */
	public void setPWidth(double g) {
		_PIPE_WIDTH = g;
	}

	/**
	 * returns the real gravity
	 */
	public double getGravity() {
		return _GRAVITY_NOMINAL * Constants.TIMELINE_MOVEMENT_RATIO;
	}

	/**
	 * returns the max pipe diff
	 */
	public double getMaxPipeDiff() {
		return _MAX_PIPE_DIFFERENCE;
	}

	/**
	 * returns the pipe gap
	 */
	public double getPGap() {
		return _PIPE_GAP;
	}

	/**
	 * returns the pipe seperation
	 */
	public double getSep() {
		return _PIPE_SEPERATION;
	}

	/**
	 * returns the x velocity
	 */
	public double getXVel() {
		return _X_VELOCITY * Constants.TIMELINE_MOVEMENT_RATIO;
	}

	/**
	 * returns the bird bounce velocity
	 */
	public double getBounce() {
		return -1 * _BIRD_BOUNCE_VELOCITY * Constants.TIMELINE_MOVEMENT_RATIO;
	}

	/**
	 * returns the timeline delay
	 */
	public double getDelay() {
		return _TIMELINE_DELAY / 10000;
	}

	/**
	 * returns the pipe width
	 */
	public double getPWidth() {
		return _PIPE_WIDTH;
	}

}
