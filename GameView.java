package Evolution;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * Represents a game view in the multi screen game
 */
public class GameView {

	private AIMultiGameOrganizer _organizer;
	private MultiGameScreen _parent;
	private TitledPane _root;
	private HBox _infoPanel;
	private Text _generationText;
	private Text _birdsLeftText;
	private Text _previousMaxFitText;
	private Text _previousAvgFitText;
	private int _gamenum;
	private Text _emptyText;

	/**
	 * Constructor for a game view
	 */
	public GameView(int gamenum, MultiGameScreen parent) {
		_gamenum = gamenum;
		_parent = parent;
		this.setupInfoPanel();
	}

	/**
	 * adds a game to the gameview
	 */
	public void addGame(AIMultiGameOrganizer organizer) {
		_organizer = organizer;
		this.updateInfoPanel();
	}

	/**
	 * removes a game from the game view
	 */
	public void removeGame() {
		// _parent.addButton()
		_organizer = null;
		_root.setContent(_emptyText);
	}

	/**
	 * sets up the info panel
	 */
	private void setupInfoPanel() {
		_emptyText = new Text("Game " + _gamenum + " slot EMPTY");

		VBox info = new VBox();

		Text gen = new Text("Current Generation:");
		_generationText = new Text("");

		Text bLeft = new Text("Birds Alive:");

		_birdsLeftText = new Text("");

		Text prevMaxFit = new Text("Last Gen Max Fitness:");
		_previousMaxFitText = new Text("");

		Text prevAvgFit = new Text("Last Gen Avg Fitness:");
		_previousAvgFitText = new Text("");

		info.getChildren().addAll(gen, _generationText, bLeft, _birdsLeftText, prevMaxFit, _previousMaxFitText,
				prevAvgFit, _previousAvgFitText);

		Button deleteGame = new Button("Delete");

		deleteGame.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				_organizer.getGame().stopT();
				_parent.deleteGame(_organizer);
				GameView.this.removeGame();
			}
		});

		_infoPanel = new HBox(info, deleteGame);

		_infoPanel.setSpacing(15);

		_root = new TitledPane("Game " + _gamenum, _emptyText);
	}

	/**
	 * updates the info panel
	 */
	public void updateInfoPanel() {

		_generationText.setText(_organizer.getGen() + "");

		_birdsLeftText.setText(
				_organizer.getBirdsLeft() + "/" + _organizer.getPopulations().get(_organizer.getGen()).getSize());

		if (_organizer.getGen() > 0) {
			_previousMaxFitText.setText(
					"" + _organizer.getPopulations().get(_organizer.getGen() - 1).getBirds().get(0).getFitness());
			_previousAvgFitText.setText("" + _organizer.getPopulations().get(_organizer.getGen() - 1).getAvgFitness());
		} else {

			_previousMaxFitText.setText("N/A");
			_previousAvgFitText.setText("N/A");

		}

		_root.setContent(_infoPanel);
	}

	/**
	 * gets the rootpane
	 */
	public TitledPane getRoot() {
		return _root;
	}

	/**
	 * gets the birds remaining text node
	 */
	public Text getBirdsLeftText() {
		return _birdsLeftText;
	}

	/**
	 * gets the current generation text node
	 */
	public Text getGenerationText() {
		return _generationText;
	}

	/**
	 * gets the AIGameORganizer
	 */
	public AIGameOrganizer getGameOrganizer() {
		return _organizer;
	}
}
