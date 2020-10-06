package Evolution;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;

import javafx.scene.text.Font;

/**
 *
 * Constants class
 */
public class Constants {

	public static final double SCENE_WIDTH = 1200;
	public static final double SCENE_HEIGHT = 700;
	public static final double POPUP_SCENE_WIDTH = 600;
	public static final double POPUP_SCENE_HEIGHT = 600;
	public static final double DEFAULT_TIMELINE_DELAY = .005;
	public static final double TIMELINE_MOVEMENT_RATIO = .0035;
	public static final double DEFAULT_GAME_HEIGHT = 700;
	public static final double DEFAULT_GAME_WIDTH = 600;
	public static final double BIRD_VIEW_WIDTH = 500;
	public static final double BIRD_VIEW_HEIGHT = (DEFAULT_GAME_HEIGHT - 15) / 5;
	public static final double DEFAULT_BIRD_RADIUS = 40;
	public static final double MOVE_THRESHOLD = .4;

	public static final double MINI_GAME_SIZE_DEFALTOR = 1;
	public static final double MAX_GENS = 100;
	public static final double MAX_X = 100000;

	public static final String[] INPUT_NAMES = { "Y Dist to Top Pipe", "Y Dist to Bottom Pipe", "Y Actual",
			"Y Relative", "Y Velocity" };

	public static final String[] SETTINGS_NAMES = { "Gravity", "Max Gap Difference", "Gap Size", "Pipe Seperation",
			"X Velocity", "Bird Bounce Velocity", "Pipe Width" };
	public static final String[] FITNESS_ORDER = { "Average", "Best", "Worst" };
	public static final double[] TIMELINE_DELAY_BOUNDS = { 1, 250, 50 };
	public static final double[] GRAVITY_BOUNDS = { 1, 50, 10 };
	public static final double[] MAX_PIPE_DIFF_BOUNDS = { 200, 1200, 500 };
	public static final double[] PIPE_GAP_BOUNDS = { 50, 500, 200 };
	public static final double[] PIPE_SEP_BOUNDS = { 100, 1000, 300 };
	public static final double[] X_VEL_BOUNDS = { 25, 500, 200 };
	public static final double[] BIRD_BOUNCE_BOUNDS = { 200, 1250, 650 };
	public static final double[] PIPE_WIDTH_BOUNDS = { 25, 150, 75 };

	public static final int MAX_NUM_LAYERS = 7;
	public static final int MIN_NUM_LAYERS = 3;
	public static final int MAX_NUM_NODES = 20;
	public static final int MIN_NUM_NODES = 2;

	// public static final ArrayList<Integer> DEFAULT_NEURAL_NET = new
	// ArrayList<>(List.of(6,2,1));
	public static final ArrayList<Integer> DEFAULT_NEURAL_NET = new ArrayList<>(Arrays.asList(8, 4, 1));
	public static final ArrayList<AIInputNodes> OPTIMIZED_INPUTS = new ArrayList<AIInputNodes>(
			Arrays.asList(AIInputNodes.Y_DIST_TOP_PIPE, AIInputNodes.Y_DIST_BOTTOM_PIPE, AIInputNodes.X_DISTANCE_PIPE));
	public static final double[] OPTIMIZED_SELECTION_SETTINGS = {.4, .2, .4, .4, .25, 1 };
	public static final ArrayList<FitnessCalcFactor> BEST_FITNESS_FACTORS = new ArrayList<FitnessCalcFactor>(
			Arrays.asList(new FitnessCalcFactor(FitnessCalculationFactors.TOTAL_X_TRAVELED, 1),
					new FitnessCalcFactor(FitnessCalculationFactors.X_DIST_NEXT_PIPE, -1),
					new FitnessCalcFactor(FitnessCalculationFactors.Y_DIST_PIPE_MIDDLE, -3)));
	/*
	 * private double _keepRate; private double _directSelectRate; private double
	 * _directmutateRate; private double _crossoverRate; private double _mutateProb;
	 * private double _decreateRate;
	 */

	public static final int NUM_INPUT_NODES = 5;
	public static final int NUM_HIDDEN_NODES = 12;

	public static final int MAX_GENERATIONS = 1000;
	public static final int INITIAL_POP_SIZE = 50;

	public static final double MUTATION_RATE = .15;
	public static final double MUTATION_MAX_AMOUNT = .1;

	// public static final double DIRECT_SELECTION_RATE = .4;
	public static final double TOP_TWO_DESCENDANT_RATE = .2;
	public static final double RANDOM_WINNER_DESCENDANT_RATE = .5;
	public static final double WINNER_RATE = .4;
	public static final double RANDOM_WINNER_RATE = .1;
	public static final double RANDOM_RATE = .1;

	/**
	 * Constructor
	 */
	public Constants() {

	}

	/**
	 * Gets the Flappy font with a given size
	 */
	public static Font getFlappyFont(int size) {
		return Font.loadFont(new File("FlappyBirdy.ttf").toURI().toString(), size);
	}

}
