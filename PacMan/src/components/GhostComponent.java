package components;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import utils.Timer;

/**
 * This class represents the Ghost
 * 
 * @author Zoltan Mate
 */
public class GhostComponent extends Rectangle {

	private static final long serialVersionUID = -830456434179705506L;

	/**
	 * The size of the Ghost
	 */
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;

	/**
	 * The directions of the Ghost
	 */
	public static final int UP = 0;
	public static final int DOWN = 2;
	public static final int RIGHT = 4;
	public static final int LEFT = 6;

	/**
	 * The number of ghost types
	 */
	public static final int GHOST_TYPES = 4;

	/**
	 * It's useful for calculating the current image of the Ghost. It tells us how
	 * many images the Ghost has in a specific direction
	 */
	public static final int NUMBER_OF_SPRITES = 2;

	/**
	 * The number of directions of the Ghost
	 */
	public static final int NUMBER_OF_DIRECTIONS = 4;

	/**
	 * Possible states of the Ghost
	 */
	public static final int LIVE = 1;
	public static final int DYING = 2;

	/**
	 * Image timer delays
	 */
	public static final int IMAGE_UPDATE_DELAY = 80;
	public static final int MORTAL_STATE_DELAY = 3000;

	/**
	 * Type of the Ghost
	 */
	private int ghostType;

	/**
	 * State of the Ghost
	 */
	private int state = LIVE;

	/**
	 * Direction of the Ghost
	 */
	private int direction = DOWN;

	/**
	 * Random Generator for the Ghosts movement randomness
	 */
	private Random random;

	/**
	 * The image indexes of the Ghost
	 */
	private int moveImageIndex = 0;
	private int mortalImageIndex = 0;

	/**
	 * The timers of the Ghost
	 */
	private Timer imageTimer;
	private Timer mortalTimer;

	/**
	 * the special points of the Ghost, used in order avoid the Ghost to enter in walls
	 */
	private Point upLeftPoint;
	private Point upRightPoint;
	private Point downLeftPoint;
	private Point downRightPoint;

	/**
	 * The initial position of the Ghost
	 */
	private Point ghostInitialPosition;

	/**
	 * The speed of the ghost
	 */
	private int speed = 0;

	/**
	 * Default constructor
	 */
	public GhostComponent() {
	}

	public GhostComponent(int x, int y, Random random) {
		upLeftPoint = new Point();
		upRightPoint = new Point();
		downLeftPoint = new Point();
		downRightPoint = new Point();

		setGhostInitialPosition(new Point(x, y));

		this.width = WIDTH;
		this.height = HEIGHT;

		this.random = random;
		this.direction = NUMBER_OF_SPRITES * random.nextInt(4);

		imageTimer = new Timer(IMAGE_UPDATE_DELAY);
		mortalTimer = new Timer(MORTAL_STATE_DELAY);
	}

	public Point getUpLeftPoint() {
		upLeftPoint.x = this.x;
		upLeftPoint.y = this.y;

		return upLeftPoint;
	}

	public Point getUpRightPoint() {
		upRightPoint.x = this.x + width - 1;
		upRightPoint.y = this.y;

		return upRightPoint;
	}

	public Point getDownLeftPoint() {
		downLeftPoint.x = this.x;
		downLeftPoint.y = this.y + height - 1;

		return downLeftPoint;
	}

	public Point getDownRightPoint() {
		downRightPoint.x = this.x + width - 1;
		downRightPoint.y = this.y + height - 1;

		return downRightPoint;
	}

	public int getGhostSpeed() {
		return getSpeed();
	}

	public void setGhostType(int ghostType) {
		this.ghostType = ghostType;
	}

	public int getGhostType() {
		return ghostType;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setRandomDirection() {
		direction = NUMBER_OF_SPRITES * random.nextInt(4);
	}

	public boolean isDirectionSetTo(int direction) {
		switch (direction) {
		case UP: {
			return this.direction == UP;
		}
		case DOWN: {
			return this.direction == DOWN;
		}
		case RIGHT: {
			return this.direction == RIGHT;
		}
		case LEFT: {
			return this.direction == LEFT;
		}
		}

		return false;
	}

	public void move(int direction) {
		switch (direction) {
		case UP: {
			y -= getSpeed();
		}
			break;
		case DOWN: {
			y += getSpeed();
		}
			break;
		case RIGHT: {
			x += getSpeed();
		}
			break;
		case LEFT: {
			x -= getSpeed();
		}
			break;
		}

		if (imageTimer.isEllapsed()) {
			moveImageIndex = (moveImageIndex + 1) % NUMBER_OF_SPRITES;
		}
	}

	public int getMoveImageIndex() {
		return direction + moveImageIndex;
	}

	public int getMortalImageIndex() {
		return mortalImageIndex;
	}

	public void setState(int state) {
		this.state = state;

		if (state == LIVE) {
			moveImageIndex = 0;

			imageTimer.setDelay(IMAGE_UPDATE_DELAY);
			imageTimer.initTimer();
		} else if (state == DYING) {
			mortalTimer.initTimer();
			mortalImageIndex = 0;
		}
	}

	public boolean isLive() {
		return state == LIVE;
	}

	public boolean isDying() {
		return state == DYING;
	}

	public Point getGhostInitialPosition() {
		return ghostInitialPosition;
	}

	public void setGhostInitialPosition(Point ghostInitialPosition) {
		this.ghostInitialPosition = ghostInitialPosition;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
