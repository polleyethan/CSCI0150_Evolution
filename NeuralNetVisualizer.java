package Evolution;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Creates a visualizer for NeuralNetwork. Can either show Live values of the neural network or the set weights for the neural network.
 *
 */
public class NeuralNetVisualizer {

	private ArrayList<Integer> _layers;
	private Pane _root;
	private Circle[][] _nodeCircles;
	private Line[][][] _nodeLines;
	private Text[][] _liveValues;
	private Stage _stage;
	private NeuralNetwork _network;

	/**
	 * Constructor 
	 */
	public NeuralNetVisualizer(ArrayList<Integer> layers) {
		_root = new Pane();
		_layers = layers;
		this.initializeView();
		_stage = new Stage();
		Scene scene = new Scene(_root, Constants.SCENE_WIDTH / 2, Constants.SCENE_HEIGHT / 2);
		_stage.setScene(scene);
		_stage.setAlwaysOnTop(true);
	}

	/**
	 * Returns the root pane
	 */
	public Pane getRoot() {
		return _root;
	}

	/**
	 * Gets the layer configuration
	 */
	public ArrayList<Integer> getLayerConfig() {
		return _layers;
	}

	/**
	 * Initializes the view
	 */
	public void initializeView() {

		_nodeCircles = new Circle[_layers.size()][];
		for (int i = 0; i < _layers.size(); i++) {
			_nodeCircles[i] = new Circle[_layers.get(i)];
			double x = ((Constants.SCENE_WIDTH / 2) / (_layers.size() + 1)) * (i + 1);
			this.createCircleLayer(i, _layers.get(i), x);
		}
		this.createLines();
	}

	/**
	 * Creates the lines between nodes
	 */
	private void createLines() {
		_nodeLines = new Line[_layers.size() - 1][][];
		for (int l = 0; l < _layers.size() - 1; l++) {
			_nodeLines[l] = new Line[_layers.get(l)][];
			for (int s = 0; s < _layers.get(l); s++) {
				_nodeLines[l][s] = new Line[_layers.get(l + 1)];
				for (int e = 0; e < _layers.get(l + 1); e++) {
					Line line = new Line(_nodeCircles[l][s].getCenterX(), _nodeCircles[l][s].getCenterY(),
							_nodeCircles[l + 1][e].getCenterX(), _nodeCircles[l + 1][e].getCenterY());
					_nodeLines[l][s][e] = line;
					_root.getChildren().add(line);
				}
			}
		}
	}

	/**
	 * Creates a layer of circles
	 */
	private void createCircleLayer(int layNum, int size, double x) {
		double spacingY = (Constants.SCENE_HEIGHT / 2) / (size + 1);
		double radius = spacingY / 2;
		if (radius > 20) {
			radius = 20;
		}
		for (int i = 0; i < size; ++i) {
			Circle node = new Circle(radius, Color.BLACK);
			node.setCenterX(x);

			if (i == 0) {
				node.setCenterY(spacingY);

			} else {
				node.setCenterY(_nodeCircles[layNum][i - 1].getCenterY() + spacingY);

			}
			node.toFront();
			_root.getChildren().add(node);

			_nodeCircles[layNum][i] = node;

		}
	}

	/**
	 * Shows the static weights of the neural net
	 */
	public void showStatic(ArrayList<AILayer> layers) {
		for (int l = 0; l < _layers.size() - 1; l++) {
			for (int s = 0; s < _layers.get(l); s++) {
				for (int e = 0; e < _layers.get(l + 1); e++) {
					double weight = layers.get(l + 1).getWeights()[e][s];
					_nodeLines[l][s][e].setStrokeWidth(5 * Math.abs(weight));
					if (weight > 0) {
						_nodeLines[l][s][e].setStroke(Color.GREEN);
					} else {
						_nodeLines[l][s][e].setStroke(Color.RED);
					}
				}
			}
		}
		_stage.show();
	}

	/**
	 * Sets the net of this visualizer
	 */
	public void setNet(NeuralNetwork n) {
		if (_network != null) {
			_network.removeVisualizer();
		}
		if (n != null) {
			_network = n;
			_network.setVisualizer(this);
		} else {
			_network = null;
		}
	}

	/**
	 * resets the lines 
	 */
	public void resetlines() {
		for (int l = 0; l < _layers.size() - 1; l++) {
			for (int s = 0; s < _layers.get(l); s++) {
				for (int e = 0; e < _layers.get(l + 1); e++) {
					_nodeLines[l][s][e].setStrokeWidth(1);
					_nodeLines[l][s][e].setStroke(Color.BLACK);
				}
			}
		}
	}

	/**
	 * resets the circles
	 */
	public void resetCircles() {
		for (int l = 0; l < _layers.size(); l++) {
			for (int s = 0; s < _layers.get(l); s++) {
				_nodeCircles[l][s].setOpacity(1);
			}
		}
	}

	/**
	 * initializes showing live values
	 */
	public void showLiveInit() {
		this.resetlines();
		_stage.show();
	}

	/**
	 * updates showing live values
	 */
	public void showLiveUpdate(double[] values, int layer) {
		for (int s = 0; s < _layers.get(layer); s++) {
			_nodeCircles[layer][s].setOpacity(1.5 * values[s]);
		}
	}

	/**
	 * initializes showing static weights
	 */
	public void showStaticInit() {
		this.resetCircles();
		_stage.show();
	}
}
