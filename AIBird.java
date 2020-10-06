package Evolution;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * Subclass of Bird class. AI Bird makes its own decisions and has a NeuralNetwork
 */
public class AIBird extends Bird implements Comparable<AIBird>, Cloneable {

	private NeuralNetwork _neuralNet;
	private Pane _gamePane;
	private int _birdID;
	private int _genNumber;
	private double _fitness;
	private BirdView _birdView;
	private Boolean _aiOn;
	private AISettings _aiSettings;

	/**
	 * Constructor for a new, Random AIBird
	 */
	public AIBird(AISettings aiSettings, Game game, int birdID, int genNumber, Color color) {
		super(game, color);
		_aiSettings = aiSettings;
		_gamePane = game.getGamePane();
		_birdID = birdID;
		_genNumber = genNumber;
		_aiOn = true;
		_neuralNet = new NeuralNetwork(_aiSettings);
	}

	/**
	 * Constructor for a new AIBird with a preset neuralnetwork
	 */
	public AIBird(AISettings aiSettings, Game game, NeuralNetwork network, int birdID, int genNumber, Color color) {
		super(game, color);
		_aiSettings = aiSettings;
		_gamePane = game.getGamePane();
		_neuralNet = new NeuralNetwork(network, _aiSettings);
		_birdID = birdID;
		_aiOn = true;
		_genNumber = genNumber;
	}

	/**
	 * Constructor for an AIBird with a mom and dad
	 */
	public AIBird(AISettings aiSettings, Game game, AIBird mom, AIBird dad, int birdID, int genNumber, Color color) {
		super(game, color);
		_aiSettings = aiSettings;
		_gamePane = game.getGamePane();
		_birdID = birdID;
		_genNumber = genNumber;
		_aiOn = true;
		_neuralNet = new NeuralNetwork(_aiSettings);
		// _neuralNet = new NeuralNetwork(mom.getNet(),dad.getNet(),Math.random());
	}

	/**
	 * Calculates the move that this bird should make given the next pipe. Uses neural network to do so.
	 */
	public double calculateMove(Pipe next) {
		_fitness = _aiSettings.getFitness(next, this);
		double[] moves = _neuralNet.calculateMove(this.getInputs(next));
		if (moves[0] < 0.5 && _aiOn) {
			this.flash();
			this.bounce();
		}
		return moves[0];
	}

	/**
	 * Overrides the Birds die method.
	 */
	@Override
	public void die() {
		super.die();
		if (_birdView != null) {
			_birdView.blackOutImage();
			_birdView.unflash();
		}
	}

	/**
	 * Sets the birdview for this bird
	 */
	public void setBirdView(BirdView v) {
		if (v != _birdView) {
			_birdView = v;
			if (_birdView.getBird() != null && _birdView.getBird() != this) {
				_birdView.getBird().removeBirdView();
			}
			_birdView.setBird(this);
		}
	}

	/**
	 * retrieves the bird view for this bird
	 */
	public BirdView getBirdView() {
		return _birdView;
	}

	/**
	 * removes the bird view for this bird
	 */
	public void removeBirdView() {
		_birdView = null;
	}

	/**
	 * gets the neural network for this bird
	 */
	public NeuralNetwork getNet() {
		return _neuralNet;
	}

	/**
	 * gets the fitness for this bird
	 */
	public double getFitness() {
		return _fitness;
	}

	/**
	 * returns if the AI is active for this bird.
	 */
	public Boolean getAIOn() {
		return _aiOn;
	}

	/**
	 * toggles whether or not the ai is on for this bird
	 */
	public void toggleAIOn() {
		_aiOn = !_aiOn;
		System.out.println(_aiOn);
	}

	/**
	 * gets the generation number for this bird
	 */
	public int getGenNum() {
		return _genNumber;
	}

	/**
	 * gets the id for this bird
	 */
	public int getID() {
		return _birdID;
	}

	/**
	 * Implementation of the comparable interface. Orders them by fitness
	 */
	@Override
	public int compareTo(AIBird b) {
		if (_fitness > ((AIBird) b).getFitness()) {
			return -1;
		} else if (_fitness == ((AIBird) b).getFitness()) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Updates the birdview
	 */
	public void updateView(Pipe p, double move) {
		if (_birdView != null) {
			_birdView.update(this.getInputs(p), move);
		}

	}

	/**
	 * flashs the birdview
	 */
	public void flash() {
		if (_birdView != null) {
			_birdView.flash();
		}
	}

	/**
	 * gets the inputs for this bird.
	 */
	public double[] getInputs(Pipe p) {
		double[] toReturn = new double[_aiSettings.getInputs().size()];
		for (int n = 0; n < toReturn.length; n++) {
			toReturn[n] = _aiSettings.getInputs().get(n).calculateNormalizedVal(p, this);
		}
		return toReturn;
	}

}
