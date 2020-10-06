package Evolution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Visualizes the long term data of a gameorganizer
 *
 */
public class LongTermVisualizer {

	private AIGameOrganizer _organizer;
	private ArrayList<CompletedGameData> _gameData;
	private Stage _stage;
	private LineChart<Number, Number> _fitnessChart;
	private LineChart<Number, Number> _settingsChart;
	private XYChart.Series _avgFitness;
	private XYChart.Series _bestFitness;
	private XYChart.Series _worstFitness;
	private XYChart.Series[] _settingsData;
	private ArrayList<Integer> _settingsDisplayed;
	private HBox _settingsBox;
	private ArrayList<Integer> _fitnessDisplayed;
	private HBox _fitnessBox;
	private VBox _mainbox;

	/**
	 * Constructor for LongTimeVisualizer
	 */
	public LongTermVisualizer(AIGameOrganizer organizer) {
		_organizer = organizer;
		_gameData = new ArrayList<CompletedGameData>();
		_stage = new Stage();
		_stage.setAlwaysOnTop(true);
		this.setupCharts();

	}

	/**
	 * sets up the charts 
	 */
	private void setupCharts() {
		this.setupFitnessChart();
		this.setupSettingsChart();
		Button close = new Button("Close");
		close.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				LongTermVisualizer.this.hideGraph();
			}

		});

		_mainbox = new VBox(close, _fitnessBox, _settingsBox);
		Scene scene = new Scene(_mainbox, 600, 800);

		_stage.setScene(scene);
	}

	/**
	 * sets up the settings chart
	 */
	private void setupSettingsChart() {
		_settingsDisplayed = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			_settingsDisplayed.add(i);
		}
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Generation #");

		_settingsChart = new LineChart<Number, Number>(xAxis, yAxis);

		_settingsChart.setTitle("Settings Data");

		_settingsData = new XYChart.Series[7];

		for (int n = 0; n < _settingsDisplayed.size(); n++) {
			_settingsData[_settingsDisplayed.get(n)] = new XYChart.Series();
			_settingsData[_settingsDisplayed.get(n)].setName(Constants.SETTINGS_NAMES[_settingsDisplayed.get(n)]);
			_settingsChart.getData().add(_settingsData[_settingsDisplayed.get(n)]);
		}

		VBox settingsChecks = new VBox();
		for (int i = 0; i < _settingsData.length; i++) {

			CheckBox c = new CheckBox(Constants.SETTINGS_NAMES[i] + " Data");
			int j = i;
			c.setSelected(true);
			c.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldval, Boolean newval) {
					// LongTermVisualizer.this.updateSettingsChart(j);
					if (newval) {
						if (!_settingsDisplayed.contains(j)) {
							_settingsDisplayed.add(j);
						}
					} else {
						_settingsDisplayed.remove((Object) j);
					}
					LongTermVisualizer.this.updateSettingsChart();
				}
			});

			settingsChecks.getChildren().add(c);

		}
		_settingsBox = new HBox(_settingsChart, settingsChecks);
		_settingsBox.setAlignment(Pos.CENTER);

	}

	/**
	 * sets up the fitness chart
	 */
	private void setupFitnessChart() {
		_fitnessDisplayed = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			_fitnessDisplayed.add(i);
		}
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Generation #");

		_fitnessChart = new LineChart<Number, Number>(xAxis, yAxis);

		_fitnessChart.setTitle("Fitness Data");

		_avgFitness = new XYChart.Series();
		_avgFitness.setName("Average Fitness");

		_bestFitness = new XYChart.Series();
		_bestFitness.setName("Best Fitness");

		_worstFitness = new XYChart.Series();
		_worstFitness.setName("Worst Fitness");

		_fitnessChart.getData().addAll(_avgFitness, _bestFitness, _worstFitness);
		VBox fitnessChecks = new VBox();
		for (int i = 0; i < 3; i++) {

			CheckBox c = new CheckBox(Constants.FITNESS_ORDER[i] + " Fitness");
			int j = i;
			c.setSelected(true);
			c.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldval, Boolean newval) {
					// LongTermVisualizer.this.updateSettingsChart(j);
					if (newval) {
						if (!_fitnessDisplayed.contains(j)) {
							_fitnessDisplayed.add(j);
						}
					} else {
						_fitnessDisplayed.remove((Object) j);
					}
					LongTermVisualizer.this.updateFitnessChart();
				}
			});

			fitnessChecks.getChildren().add(c);

		}
		_fitnessBox = new HBox(_fitnessChart, fitnessChecks);
		_fitnessBox.setAlignment(Pos.CENTER);

	}

	/**
	 * updates the settings chart
	 */
	private void updateSettingsChart() {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Generation #");

		_settingsChart = new LineChart<Number, Number>(xAxis, yAxis);

		_settingsChart.setTitle("Settings Data");
		for (int n = 0; n < _settingsDisplayed.size(); n++) {
			_settingsChart.getData().add(_settingsData[_settingsDisplayed.get(n)]);
		}
		_settingsBox.getChildren().set(0, _settingsChart);

	}

	/**
	 * updates the fitness chart
	 */
	private void updateFitnessChart() {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Generation #");

		_fitnessChart = new LineChart<Number, Number>(xAxis, yAxis);

		_fitnessChart.setTitle("Fitness Data");
		for (int n = 0; n < _fitnessDisplayed.size(); n++) {
			switch (_fitnessDisplayed.get(n)) {
			case 0: {
				_fitnessChart.getData().add(_avgFitness);
				break;

			}
			case 1: {
				_fitnessChart.getData().add(_bestFitness);
				break;

			}
			case 2: {
				_fitnessChart.getData().add(_worstFitness);
				break;
			}
			}

		}
		_fitnessBox.getChildren().set(0, _fitnessChart);
	}

	/**
	 * adds new game data to the visualizer
	 */
	public void addNewGame(CompletedGameData newGame, int genNum) {
		_gameData.add(newGame);
		_avgFitness.getData().add(new XYChart.Data<Integer, Double>(genNum, newGame.getAvgFitness()));
		_bestFitness.getData().add(new XYChart.Data<Integer, Double>(genNum, newGame.getBestFitness()));
		_worstFitness.getData().add(new XYChart.Data<Integer, Double>(genNum, newGame.getWorstFitness()));

		for (int n = 0; n < _settingsData.length; n++) {
			_settingsData[n].getData().add(new XYChart.Data<Integer, Double>(genNum, newGame.getAvgSettings()[n]));
		}
	}

	/**
	 * shows the graph
	 */
	public void showGraph(ArrayList<CompletedGameData> gameData) {
		_stage.show();

	}
	
	
	/**
	 * hides the graph
	 */
	public void hideGraph() {
		_stage.hide();
	}

}
