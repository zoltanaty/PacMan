package components;

import java.awt.Point;
import java.awt.Rectangle;

import utils.Timer;

/**
 * This class represents Pac-Man
 * 
 * @author ZoltanMate
 */
public class PacManComponent extends Rectangle {

	private static final long serialVersionUID = -3836175742422428064L;

	/**
	 * the size of Pac-Man
	 */
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;

	/**
	 * the directions of PacMan
	 */
	public static final int UP = 0;
	public static final int DOWN = 4;
	public static final int RIGHT = 8;
	public static final int LEFT = 12;

	/**
	 * it's useful for calculating the current image of PacMan it tells us how many
	 * images PacMan has in a specific direction
	 */
	public static final int NUMBER_OF_SPRITES = 4;

	/**
	 * the possible states of PacMan
	 */
	public static final int LIVE = 1;
	public static final int DYING = 2;
	public static final int DEAD = 3;

	/**
	 * the delay values for the image-timers
	 */
	public static final int IMAGE_UPDATE_DELAY = 60;
	public static final int MORTAL_STATE_DELAY = 200;

	/**
	 * the special points of PacMan, used in order avoid PacMan to enter in walls,
	 * and to immediately detect when PacMan intersects with a Ghost
	 */
	private Point upLeftPoint;
	private Point upRightPoint;
	private Point downLeftPoint;
	private Point downRightPoint;
	private Point centerPoint;

	/**
	 * initial position of PacMan
	 */
	private Point pacManInitialPosition;

	/**
	 * speed of PacMan
	 */
	private int speed;

	/**
	 * state of PacMan
	 */
	private int state;

	/**
	 * direction and lastDirection of PacMan
	 */
	private int direction;
	private int lastDirection;

	/**
	 * directions of PacMan
	 */
	private boolean up;
	private boolean right;
	private boolean down;
	private boolean left;

	/**
	 * image indexes of PacMan
	 */
	private int moveImageIndex = 0;
	private int mortalImageIndex = 0;

	/**
	 * imageTimers of PacMan
	 */
	private Timer imageTimer;
	private Timer mortalTimer;

	public PacManComponent() {
		upLeftPoint = new Point();
		upRightPoint = new Point();
		downLeftPoint = new Point();
		downRightPoint = new Point();
		centerPoint = new Point();

		setPacManInitialPosition(new Point(13, 17));

		this.width = WIDTH;
		this.height = HEIGHT;

		setSpeed(0);

		direction = UP;
		lastDirection = UP;

		up = false;
		right = false;
		down = false;
		left = false;

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

	public Point getCenterPoint() {
		centerPoint.x = x + width / 2;
		centerPoint.y = y + height / 2;

		return centerPoint;
	}

	public void setDirection(int direction, boolean status) {
		if (isLive()) {
			switch (direction) {
			case UP: {
				up = status;
				if (status)
					lastDirection = direction;

			}
				break;

			case DOWN: {
				down = status;
				if (status)
					lastDirection = direction;

			}
				break;

			case RIGHT: {
				right = status;
				if (status)
					lastDirection = direction;

			}
				break;

			case LEFT: {
				left = status;
				if (status)
					lastDirection = direction;

			}
				break;
			}
		}
	}

	public boolean isDirectionSetTo(int direction) {
		switch (direction) {
		case UP: {
			return up;
		}

		case DOWN: {
			return down;
		}

		case RIGHT: {
			return right;
		}

		case LEFT: {
			return left;
		}
		}

		return false;
	}

	public int getLastDirection() {
		return lastDirection;
	}

	/**
	 * Moves PacMan into the given direction with speed pixels
	 * 
	 * @param direction
	 */
	public void move(int direction) {
		if (isLive()) {
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

			this.direction = direction;
		}
	}

	public void updateMortalState() {
		if (isMortal()) {
			if (mortalTimer.isEllapsed()) {
				if (mortalImageIndex < (direction + NUMBER_OF_SPRITES - 1) && imageTimer.isEllapsed()) {
					mortalImageIndex = mortalImageIndex + 1;
				} else {
					setState(DEAD);
					resetPacMan();
				}
			}
		}
	}

	public int getMoveImageIndex() {
		return direction + moveImageIndex;
	}

	public int getDyingImageIndex() {
		return mortalImageIndex;
	}

	public void setState(int state) {
		this.state = state;

		if (state == LIVE) {
			up = false;
			right = true;
			down = false;
			left = false;

			direction = RIGHT;
			speed = 0;
			lastDirection = RIGHT;
			moveImageIndex = 3;
		} else if (state == DYING) {
			mortalTimer.initTimer();
			mortalImageIndex = direction;
		}
	}

	public boolean isLive() {
		return state == LIVE;
	}

	public boolean isMortal() {
		return state == DYING;
	}

	public boolean isDead() {
		return state == DEAD;
	}
	
	private void resetPacMan() {
		this.setLocation(Tile.WIDTH * getPacManInitialPosition().x + (Tile.WIDTH - PacManComponent.WIDTH / 2) / 2,
				Tile.HEIGHT * getPacManInitialPosition().y + (Tile.HEIGHT - PacManComponent.HEIGHT / 2) / 2);
		this.setState(PacManComponent.LIVE);
	}

	public Point getPacManInitialPosition() {
		return pacManInitialPosition;
	}

	public void setPacManInitialPosition(Point pacManInitialPosition) {
		this.pacManInitialPosition = pacManInitialPosition;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
