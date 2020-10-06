package Evolution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * Class represennting an editor for the neural network configuration
 */
public class NeuralNetEditor {

	private ArrayList<Integer> _layers;
	private Pane _root;
	private Circle[][] _nodeCircles;

	/**
	 * Constructor for NeuralNet Editor
	 */
	public NeuralNetEditor(int numInputs) {
		_root = new Pane();
		_root.setMaxHeight(Constants.SCENE_HEIGHT / 2);
		_root.setMinHeight(Constants.SCENE_HEIGHT / 2);
		_root.setPrefHeight(Constants.SCENE_HEIGHT / 2);
		_root.setMaxWidth(Constants.SCENE_WIDTH / 2);
		_root.setMinWidth(Constants.SCENE_WIDTH / 2);
		_root.setPrefWidth(Constants.SCENE_WIDTH / 2);
		_layers = new ArrayList<Integer>();
		_layers.add(0, numInputs);
		_layers.addAll(Constants.DEFAULT_NEURAL_NET);
		this.show();
	}

	/**
	 * Gets the root pane
	 */
	public Pane getRoot() {
		return _root;
	}

	/**
	 * Gets the layer configurations
	 */
	public ArrayList<Integer> getLayerConfig() {
		return _layers;
	}

	/**
	 * Sets the default Array
	 */
	public void setDefault(int numInputs) {
		_layers = new ArrayList<Integer>();
		_layers.add(0, numInputs);
		_layers.addAll(Constants.DEFAULT_NEURAL_NET);
		_root.getChildren().clear();
		this.show();
	}

	/**
	 * Shows the root
	 */
	public void show() {
		_nodeCircles = new Circle[_layers.size()][];
		for (int i = 0; i < _layers.size(); i++) {
			_nodeCircles[i] = new Circle[_layers.get(i)];
			double x = ((Constants.SCENE_WIDTH / 2 - 50) / (_layers.size() + 1)) * (i + 1);
			this.createEditLayer(i, _layers.get(i), x);
		}
		this.createDefaultLines();

	}

	/**
	 * Creates the default lines
	 */
	private void createDefaultLines() {
		for (int l = 0; l < _layers.size() - 1; l++) {
			for (int s = 0; s < _layers.get(l); s++) {
				for (int e = 0; e < _layers.get(l + 1); e++) {
					Line line = new Line(_nodeCircles[l][s].getCenterX(), _nodeCircles[l][s].getCenterY(),
							_nodeCircles[l + 1][e].getCenterX(), _nodeCircles[l + 1][e].getCenterY());
					_root.getChildren().add(line);
				}
			}
		}
	}

	/**
	 * Creates a new layer with editing
	 */
	private void createEditLayer(int layNum, int size, double x) {
		double spacingY = (Constants.SCENE_HEIGHT / 2 - 120) / (size + 1);
		double radius = spacingY / 2;
		if (radius > 20) {
			radius = 20;
		}
		for (int i = 0; i < size; ++i) {
			Circle node = new Circle(radius, Color.GREY);
			node.setCenterX(x);
			if (i == 0) {
				node.setCenterY(spacingY);

			} else {
				node.setCenterY(_nodeCircles[layNum][i - 1].getCenterY() + spacingY);

			}
			_root.getChildren().add(node);

			_nodeCircles[layNum][i] = node;

		}
		if (layNum != 0 && layNum != _layers.size() - 1) {
			Button addNode = new Button("Node+");
			addNode.setLayoutX(x - 25);
			addNode.setLayoutY((Constants.SCENE_HEIGHT / 2 - 120));

			addNode.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (_layers.get(layNum) < Constants.MAX_NUM_NODES) {
						NeuralNetEditor.this._root.getChildren().clear();
						_layers.set(layNum, _layers.get(layNum) + 1);
						NeuralNetEditor.this.show();
					}
				}
			});

			Button deleteNode = new Button("Node-");
			deleteNode.setLayoutX(x - 25);
			deleteNode.setLayoutY((Constants.SCENE_HEIGHT / 2 - 90));

			deleteNode.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (_layers.get(layNum) > Constants.MIN_NUM_NODES) {
						_layers.set(layNum, _layers.get(layNum) - 1);
						NeuralNetEditor.this._root.getChildren().clear();
						NeuralNetEditor.this.show();
					}
				}
			});

			Button addLayer = new Button("Layer+");
			addLayer.setLayoutX(x - 25);
			addLayer.setLayoutY((Constants.SCENE_HEIGHT / 2 - 60));

			addLayer.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (_layers.size() < Constants.MAX_NUM_LAYERS) {
						_layers.add(layNum, 2);
						NeuralNetEditor.this._root.getChildren().clear();
						NeuralNetEditor.this.show();
					}
				}
			});

			Button deleteLayer = new Button("Layer-");
			deleteLayer.setLayoutX(x - 25);
			deleteLayer.setLayoutY((Constants.SCENE_HEIGHT / 2 - 30));

			deleteLayer.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (_layers.size() > Constants.MIN_NUM_LAYERS) {
						_layers.remove(layNum);
						NeuralNetEditor.this._root.getChildren().clear();
						NeuralNetEditor.this.show();
					}
				}
			});

			_root.getChildren().addAll(addNode, deleteNode, addLayer, deleteLayer);
		}
	}
	
	public void saveFitnessAsPng() {
	    WritableImage image = _root.snapshot(new SnapshotParameters(), null);
	    String filename = "";
	    for(int p : this.getLayerConfig()) {
	    	filename +=p+"-";
	    	System.out.println(filename);
	    }
	    filename+="config_chart";
	    
	    File file = new File(filename+".png");
	    
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	    } catch (IOException e) {
	      
	    }
	}


}
