package Evolution;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Represents a bird view in the AISingleGameOrganizer
 *
 */
public class BirdView {

	private GridPane _parent;
	private AIBird _bird;
	private Rectangle _outputRect;
	private ArrayList<Rectangle> _inputRects;
	private Text _fitnessLbl;
	private Text _idLbl;
	private Text _genLbl;
	private AISettings _aiSettings;
	private ImageView _birdImg;
	private int _place;
	private VBox _left;
	private int _flash;
	private Boolean _flashActive;

	/**
	 * Constructor for a bird view
	 */
	public BirdView(int place, AIGameOrganizer organizer, GridPane parent, AISettings aiSettings) {
		_aiSettings = aiSettings;
		_parent = parent;
		_place = place;
		_flash = 0;
		_flashActive = false;
		this.setupViews(place, organizer);
	}

	/**
	 * sets up the view
	 */
	private void setupViews(int place, AIGameOrganizer organizer) {
		Pane inputRoot = new Pane();
		_inputRects = new ArrayList<Rectangle>(_aiSettings.getInputs().size());
		for (int i = 0; i < _aiSettings.getInputs().size() + 1; i++) {
			Rectangle r = new Rectangle(i * (100 / _aiSettings.getInputs().size()), Constants.BIRD_VIEW_HEIGHT / 2,
					100 / (_aiSettings.getInputs().size()), 0);
			r.setFill(Color.rgb(0, 0, i * (255 / _aiSettings.getInputs().size())));
			_inputRects.add(r);
			inputRoot.getChildren().add(r);
		}

		Pane outputRoot = new Pane();
		_outputRect = new Rectangle(110, Constants.BIRD_VIEW_HEIGHT / 2, 100 / _aiSettings.getInputs().size(), 0);
		_outputRect.setFill(Color.rgb(0, 255, 0));
		outputRoot.getChildren().add(_outputRect);

		Pane weights = new Pane();
		Line divider = new Line(105, 20, 105, Constants.BIRD_VIEW_HEIGHT - 20);

		weights.getChildren().addAll(inputRoot, divider, outputRoot);

		_birdImg = new ImageView();
		_birdImg.setScaleX(1.25);
		_birdImg.setScaleY(1.25);

		_fitnessLbl = new Text("Fitness: ");
		_genLbl = new Text("Gen: ");
		_idLbl = new Text("ID: ");

		Text placeLbl = new Text((place + 1) + ".");

		_left = new VBox(_fitnessLbl, _idLbl, _genLbl);
		_left.setMaxWidth(100);
		_left.setMinWidth(100);
		_left.setPrefWidth(100);

		_left.setAlignment(Pos.CENTER);

		Button killbird = new Button("Kill");

		killbird.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (!BirdView.this.getBird().isDead()) {
					organizer.birdDied();
				}
				BirdView.this.getBird().die();

			}
		});

		Button showWeightsLive = new Button("Live");

		showWeightsLive.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				organizer.getVisualizer().showLiveInit();
				organizer.getVisualizer().setNet(_bird.getNet());

			}
		});

		Button showWeightsStatic = new Button("Static");

		showWeightsStatic.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				organizer.getVisualizer().setNet(null);
				organizer.getVisualizer().showStaticInit();
				organizer.getVisualizer().showStatic(_bird.getNet().getLayers());
			}
		});

		Button jumpButton = new Button("Jump");

		jumpButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				BirdView.this.getBird().bounce();
			}
		});
		HBox weightsButtons = new HBox(showWeightsLive, showWeightsStatic);
		VBox buttons = new VBox(jumpButton, killbird, weightsButtons);

		buttons.setAlignment(Pos.CENTER);

		_parent.add(placeLbl, 0, place + 1);
		_parent.add(_left, 1, place + 1);
		_parent.add(_birdImg, 2, place + 1);
		_parent.add(weights, 3, place + 1);
		_parent.add(buttons, 4, place + 1);

	}

	/**
	 * gets the bird associated with the view
	 */
	public AIBird getBird() {
		return _bird;
	}

	/**
	 * sets the bird associated with the view
	 */
	public void setBird(AIBird bird) {
		_bird = bird;
		_birdImg.setImage(_bird.getImg());
		_idLbl.setText("ID: " + bird.getID());
		_genLbl.setText("Gen: " + bird.getGenNum());
		_fitnessLbl.setText("Fitness: " + (int) bird.getFitness());
	}

	/**
	 * flashes the bird view when the bird jumps
	 */
	public void flash() {
		_flashActive = true;
		_flash = 0;
		_left.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
	}

	/**
	 * unflashes the bird view
	 */
	public void unflash() {
		_left.setBackground(new Background(new BackgroundFill(null, null, null)));
		_flashActive = false;
		_flash = 0;
	}

	/**
	 * updates the birdview
	 */
	public void update(double[] inputs, double out) {
		if (_flashActive) {
			if (_flash > 3) {
				this.unflash();
			} else {
				_flash += 1;
			}
		}

		_birdImg.setEffect(null);

		for (int i = 0; i < _aiSettings.getInputs().size(); i++) {
			if (inputs[i] > 0) {
				_inputRects.get(i).setHeight(30 * inputs[i]);
				_inputRects.get(i).setY(Constants.BIRD_VIEW_HEIGHT / 2 - (30 * inputs[i]));
			} else {
				_inputRects.get(i).setHeight(-30 * inputs[i]);
				_inputRects.get(i).setY(Constants.BIRD_VIEW_HEIGHT / 2);
			}
		}
		_fitnessLbl.setText("Fitness: " + (int) _bird.getFitness());
		if (out > 0.5) {
			_outputRect.setHeight(50 * out);
			_outputRect.setY(Constants.BIRD_VIEW_HEIGHT / 2);
			_outputRect.setFill(Color.RED);
		} else {
			_outputRect.setHeight(50 * out);
			_outputRect.setY(Constants.BIRD_VIEW_HEIGHT / 2 - (50 * out));
			_outputRect.setFill(Color.GREEN);
			this.flash();
		}
	}

	/**
	 * blacks out the image when the bird dies
	 */
	public void blackOutImage() {
		ColorAdjust greyOutDead = new ColorAdjust();
		greyOutDead.setSaturation(-1);
		_birdImg.setEffect(greyOutDead);
	}

}
