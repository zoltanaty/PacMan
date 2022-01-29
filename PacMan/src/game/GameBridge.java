package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import components.Food;
import components.GhostComponent;
import components.PacManComponent;
import components.Score;
import components.Tile;
import components.TileField;
import utils.LevelLoader;

/**
 * Class responsible for the connection between the GameEngine and the
 * GameComponents
 * 
 * @author ZoltanMate
 */
public class GameBridge {

	private LevelLoader levelLoader;
	private TileField tileField;
	private Score score;

	private PacManComponent pacMan;
	private int pacManPreviousDirection;

	private List<GhostComponent> ghostList;
	private Iterator<GhostComponent> ghostIterator;

	private List<Food> foodList;
	private Iterator<Food> foodIterator;

	private Random random;

	private Image[] tileImages;

	private Image[] pacManWalkImages;
	private Image[] pacManDyingImages;

	private Image[][] ghostWalkImages;
	private Image[][] ghostMortalImages;

	private Image[] foodImages;

	private Image livesBgImage;
	private Image[] livesImages;
	private Image gameOverImage;

	public GameBridge() {
		levelLoader = new LevelLoader();
		tileField = new TileField();
		foodList = new LinkedList<Food>();
		pacMan = new PacManComponent();
		ghostList = new LinkedList<GhostComponent>();
		score = new Score();
		random = new Random();
		loadComponentsImages();
	}

	public void startNewGame() {
		score = new Score();
		levelLoader.loadLevel();
		initTileField();
		initFood();
		initPacMan();
		initGhosts();
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- TileField
	// -----------------------------------------------------------
	// ***************************************************************************************************************************

	private void initTileField() {
		for (int i = 0; i < TileField.HEIGHT; i++) {
			for (int j = 0; j < TileField.WIDTH; j++) {
				tileField.setTileID(levelLoader.getLevelMap()[i][j], i, j);
			}
		}
	}

	public void renderTileField(Graphics g) {
		for (int i = 0; i < TileField.HEIGHT; i++) {
			for (int j = 0; j < TileField.WIDTH; j++) {
				switch (tileField.getTileID(i, j)) {
				case Tile.ROAD: {
					g.drawImage(tileImages[Tile.ROAD], (int) tileField.getTileItem(i, j).getX(),
							(int) tileField.getTileItem(i, j).getY(), null);

				}
					break;

				case Tile.CORNER: {
					g.drawImage(tileImages[Tile.ROAD], (int) tileField.getTileItem(i, j).getX(),
							(int) tileField.getTileItem(i, j).getY(), null);

				}
					break;

				case Tile.WALL: {
					g.drawImage(tileImages[Tile.WALL], (int) tileField.getTileItem(i, j).getX(),
							(int) tileField.getTileItem(i, j).getY(), null);

				}
					break;
				}
			}
		}
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- Score/Lives
	// -----------------------------------------------------------
	// ***************************************************************************************************************************

	public void renderScore(Graphics g) {
		Font f = new Font("Arial Black", Font.BOLD, 27);
		g.setFont(f);
		g.setColor(Color.YELLOW);

		g.drawImage(livesBgImage, 0, 600, null);
		g.drawString(score.getScore(), 105, 626);

		if (score.getLives() == 3) {
			g.drawImage(livesImages[3], 442, 600, null);
		} else if (score.getLives() == 2) {
			g.drawImage(livesImages[2], 442, 600, null);
		} else if (score.getLives() == 1) {
			g.drawImage(livesImages[1], 442, 600, null);
		} else if (score.getLives() == 0) {
			g.drawImage(livesImages[0], 442, 600, null);
			g.drawImage(gameOverImage, 190, 600, null);
		}
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- PacMan
	// --------------------------------------------------------------
	// ***************************************************************************************************************************

	/**
	 * Creates and initializes PacMan
	 * 
	 * @param pacMan
	 */
	private void initPacMan() {
		pacMan.setLocation(
				Tile.WIDTH * pacMan.getPacManInitialPosition().x + (Tile.WIDTH - PacManComponent.WIDTH / 2) / 2,
				Tile.HEIGHT * pacMan.getPacManInitialPosition().y + (Tile.HEIGHT - PacManComponent.HEIGHT / 2) / 2);
		pacMan.setState(PacManComponent.LIVE);
		pacMan.setSpeed(0);
	}

	public boolean isPacManMoving() {
		return pacMan.getSpeed() > 0;
	}

	public void startPacManMovement() {
		pacMan.setSpeed(1);
	}

	public void movePacMan(int direction) {
		pacMan.setDirection(direction, true);
	}

	public void clearUnnecessaryDirections() {
		switch (pacMan.getLastDirection()) {
		case PacManComponent.UP: {
			pacMan.setDirection(PacManComponent.DOWN, false);
			pacMan.setDirection(PacManComponent.LEFT, false);
			pacMan.setDirection(PacManComponent.RIGHT, false);
		}
			break;
		case PacManComponent.DOWN: {
			pacMan.setDirection(PacManComponent.UP, false);
			pacMan.setDirection(PacManComponent.LEFT, false);
			pacMan.setDirection(PacManComponent.RIGHT, false);
		}
			break;
		case PacManComponent.LEFT: {
			pacMan.setDirection(PacManComponent.DOWN, false);
			pacMan.setDirection(PacManComponent.UP, false);
			pacMan.setDirection(PacManComponent.RIGHT, false);
		}
			break;
		case PacManComponent.RIGHT: {
			pacMan.setDirection(PacManComponent.DOWN, false);
			pacMan.setDirection(PacManComponent.LEFT, false);
			pacMan.setDirection(PacManComponent.UP, false);
		}
			break;
		}
	}

	public void updatePacMan() {
		if (pacMan.isAlive()) {

			if (pacMan.isDirectionSetTo(PacManComponent.UP) && isPossibleToMoveUp(pacMan)) {
				pacMan.move(PacManComponent.UP);

				pacManPreviousDirection = PacManComponent.UP;
				clearUnnecessaryDirections();
			} else if (pacMan.isDirectionSetTo(PacManComponent.RIGHT) && isPossibleToMoveRight(pacMan)) {
				pacMan.move(PacManComponent.RIGHT);

				pacManPreviousDirection = PacManComponent.RIGHT;
				clearUnnecessaryDirections();
			} else if (pacMan.isDirectionSetTo(PacManComponent.DOWN) && isPossibleToMoveDown(pacMan)) {
				pacMan.move(PacManComponent.DOWN);

				pacManPreviousDirection = PacManComponent.DOWN;
				clearUnnecessaryDirections();
			} else if (pacMan.isDirectionSetTo(PacManComponent.LEFT) && isPossibleToMoveLeft(pacMan)) {
				pacMan.move(PacManComponent.LEFT);

				pacManPreviousDirection = PacManComponent.LEFT;
				clearUnnecessaryDirections();
			} else {
				switch (pacManPreviousDirection) {
				case PacManComponent.UP: {
					if (isPossibleToMoveUp(pacMan)) {
						if (pacManPreviousDirection != -1) {
							pacMan.move(pacManPreviousDirection);
						}
					}
				}
					break;
				case PacManComponent.RIGHT: {
					if (isPossibleToMoveRight(pacMan)) {
						if (pacManPreviousDirection != -1) {
							pacMan.move(pacManPreviousDirection);
						}
					}
				}
					break;
				case PacManComponent.DOWN: {
					if (isPossibleToMoveDown(pacMan)) {
						if (pacManPreviousDirection != -1) {
							pacMan.move(pacManPreviousDirection);
						}
					}
				}
					break;
				case PacManComponent.LEFT: {
					if (isPossibleToMoveLeft(pacMan)) {
						if (pacManPreviousDirection != -1) {
							pacMan.move(pacManPreviousDirection);
						}
					}
				}
					break;
				}

				clearUnnecessaryDirections();
			}

		} else if (pacMan.isDying()) {
			pacMan.updateDyingState();
		}
	}

	public void renderPacMan(Graphics g) {
		if (pacMan.isAlive()) {
			g.drawImage(pacManWalkImages[pacMan.getMoveImageIndex()], (int) pacMan.getX(), (int) pacMan.getY(), null);
		} else if (pacMan.isDying()) {
			g.drawImage(pacManDyingImages[pacMan.getDyingImageIndex()], (int) pacMan.getX(), (int) pacMan.getY(), null);
		}
	}

	private boolean isPossibleToMoveUp(PacManComponent pacMan) {
		int nextUpLeftX = pacMan.getUpLeftPoint().x / Tile.WIDTH;
		int nextUpLeftY = (pacMan.getUpLeftPoint().y - pacMan.getSpeed()) / Tile.HEIGHT;
		int nextUpRightX = pacMan.getUpRightPoint().x / Tile.WIDTH;

		for (int i = nextUpLeftX; i <= nextUpRightX; i++) {
			if (!tileField.isPossibleToMoveIn(i, nextUpLeftY)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveRight(PacManComponent pacMan) {
		int nextUpRightX = (pacMan.getUpRightPoint().x + pacMan.getSpeed()) / Tile.WIDTH;
		int nextUpRightY = pacMan.getUpRightPoint().y / Tile.HEIGHT;
		int nextDownRightY = pacMan.getDownRightPoint().y / Tile.HEIGHT;

		for (int i = nextUpRightY; i <= nextDownRightY; i++) {
			if (!tileField.isPossibleToMoveIn(nextUpRightX, i)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveDown(PacManComponent pacMan) {
		int nextDownLeftX = pacMan.getDownLeftPoint().x / Tile.WIDTH;
		int nextDownLeftY = (pacMan.getDownLeftPoint().y + pacMan.getSpeed()) / Tile.HEIGHT;
		int nextDownRightX = pacMan.getDownRightPoint().x / Tile.WIDTH;

		for (int i = nextDownLeftX; i <= nextDownRightX; i++) {
			if (!tileField.isPossibleToMoveIn(i, nextDownLeftY)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveLeft(PacManComponent pacMan) {
		int nextUpLeftX = (pacMan.getUpLeftPoint().x - pacMan.getSpeed()) / Tile.WIDTH;
		int nextUpLeftY = pacMan.getUpLeftPoint().y / Tile.HEIGHT;
		int nextDownLeftY = pacMan.getDownLeftPoint().y / Tile.HEIGHT;

		for (int i = nextUpLeftY; i <= nextDownLeftY; i++) {
			if (!tileField.isPossibleToMoveIn(nextUpLeftX, i)) {
				return false;
			}
		}
		return true;
	}

	public boolean isManDead() {
		return pacMan.isDead();
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- GHOSTS
	// --------------------------------------------------------------
	// ***************************************************************************************************************************

	/**
	 * Creates and initializes the 4 Ghosts
	 * 
	 * @param ghosts
	 * @param random
	 */
	private void initGhosts() {
		ghostList.clear();

		for (int i = 0; i < 4; i++) {
			GhostComponent ghost = new GhostComponent(2 * i + 10, 14, random);
			ghost.setGhostType(i);
			ghost.setLocation(
					Tile.WIDTH * ghost.getGhostInitialPosition().x + (Tile.WIDTH - GhostComponent.WIDTH / 2) / 2,
					Tile.HEIGHT * ghost.getGhostInitialPosition().y + (Tile.HEIGHT - GhostComponent.HEIGHT / 2) / 2);
			ghost.setSpeed(0);
			ghostList.add(ghost);
		}
	}

	public boolean areGhostsMoving() {
		for (int i = 0; i < 4; i++) {
			if (ghostList.get(i).getSpeed() == 0) {
				return false;
			}
		}

		return true;
	}

	public void startGhostsMovement() {
		for (int i = 0; i < 4; i++) {
			ghostList.get(i).setSpeed(1);
		}
	}

	public void updateGhosts() {
		ghostIterator = ghostList.listIterator();

		while (ghostIterator.hasNext()) {
			GhostComponent ghost = ghostIterator.next();

			if (ghost.isLive()) {
				if (ghostIsPossibleToChangeDirection(ghost)) {
					changeGhostDirection(ghost);
				}

				if (ghost.isDirectionSetTo(GhostComponent.UP) && isPossibleToMoveUp(ghost)) {
					ghost.move(GhostComponent.UP);
				} else if (ghost.isDirectionSetTo(GhostComponent.DOWN) && isPossibleToMoveDown(ghost)) {
					ghost.move(GhostComponent.DOWN);
				} else if (ghost.isDirectionSetTo(GhostComponent.LEFT) && isPossibleToMoveLeft(ghost)) {
					ghost.move(GhostComponent.LEFT);
				} else if (ghost.isDirectionSetTo(GhostComponent.RIGHT) && isPossibleToMoveRight(ghost)) {
					ghost.move(GhostComponent.RIGHT);
				} else {
					changeGhostDirection(ghost);
				}
			}

			if (pacMan.isAlive() && ghost.contains(pacMan.getCenterPoint())) {
				if (score.getLives() > 1) {
					pacMan.setState(PacManComponent.DYING);
					score.decLives();
					initGhosts();
					break;
				} else {
					pacMan.setState(PacManComponent.DEAD);
					score.decLives();
				}
			}
		}
	}

	private boolean ghostIsPossibleToChangeDirection(GhostComponent ghost) {
		int upLeftX = ghost.getUpLeftPoint().x / Tile.WIDTH;
		int upLeftY = ghost.getUpLeftPoint().y / Tile.HEIGHT;

		int xDiff = Math.abs(ghost.getUpLeftPoint().x - tileField.getTileItem(upLeftY, upLeftX).x);
		int yDiff = Math.abs(ghost.getUpLeftPoint().y - tileField.getTileItem(upLeftY, upLeftX).y);

		if ((tileField.getTileID(upLeftY, upLeftX) == Tile.CORNER) && xDiff <= 1 && yDiff <= 1) {
			return true;
		}

		return false;
	}

	private void changeGhostDirection(GhostComponent ghost) {
		int xDiff = (int) (Math.abs(pacMan.getLocation().getX() - ghost.getLocation().getX()));
		int yDiff = (int) (Math.abs(pacMan.getLocation().getY() - ghost.getLocation().getY()));

		if (xDiff > yDiff) {
			// vizszintesen megyunk
			if (ghost.getLocation().getX() < pacMan.getLocation().getX()) {
				if (isPossibleToMoveRight(ghost)) {
					ghost.setDirection(GhostComponent.RIGHT);
				} else {
					ghost.setRandomDirection();
				}
			} else if (isPossibleToMoveLeft(ghost)) {
				ghost.setDirection(GhostComponent.LEFT);

			} else {
				ghost.setRandomDirection();
			}
		} else {
			// fuggolegesen megyunk
			if (ghost.getLocation().getY() < pacMan.getLocation().getY()) {
				if (isPossibleToMoveDown(ghost)) {
					ghost.setDirection(GhostComponent.DOWN);
				} else {
					ghost.setRandomDirection();
				}
			} else if (isPossibleToMoveUp(ghost)) {
				ghost.setDirection(GhostComponent.UP);

			} else {
				ghost.setRandomDirection();
			}
		}
	}

	public void renderGhosts(Graphics g) {
		for (GhostComponent ghost : ghostList) {
			if (ghost.isLive()) {
				g.drawImage(ghostWalkImages[ghost.getGhostType()][ghost.getMoveImageIndex() % 4], (int) ghost.getX(),
						(int) ghost.getY(), null);
			} else if (pacMan.isDying()) {
				g.drawImage(ghostMortalImages[ghost.getGhostType()][ghost.getMortalImageIndex() % 4],
						(int) ghost.getX(), (int) ghost.getY(), null);
			}
		}
	}

	private boolean isPossibleToMoveUp(GhostComponent ghost) {
		int nextUpLeftX = ghost.getUpLeftPoint().x / Tile.WIDTH;
		int nextUpLeftY = (ghost.getUpLeftPoint().y - ghost.getGhostSpeed()) / Tile.HEIGHT;
		int nextUpRightX = ghost.getUpRightPoint().x / Tile.WIDTH;

		for (int i = nextUpLeftX; i <= nextUpRightX; i++) {
			if (!tileField.isPossibleToMoveIn(i, nextUpLeftY)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveRight(GhostComponent ghost) {
		int nextUpRightX = (ghost.getUpRightPoint().x + ghost.getGhostSpeed()) / Tile.WIDTH;
		int nextUpRightY = ghost.getUpRightPoint().y / Tile.HEIGHT;
		int nextDownRightY = ghost.getDownRightPoint().y / Tile.HEIGHT;

		for (int i = nextUpRightY; i <= nextDownRightY; i++) {
			if (!tileField.isPossibleToMoveIn(nextUpRightX, i)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveDown(GhostComponent ghost) {
		int nextDownLeftX = ghost.getDownLeftPoint().x / Tile.WIDTH;
		int nextDownLeftY = (ghost.getDownLeftPoint().y + ghost.getGhostSpeed()) / Tile.HEIGHT;
		int nextDownRightX = ghost.getDownRightPoint().x / Tile.WIDTH;

		for (int i = nextDownLeftX; i <= nextDownRightX; i++) {
			if (!tileField.isPossibleToMoveIn(i, nextDownLeftY)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveLeft(GhostComponent ghost) {
		int nextUpLeftX = (ghost.getUpLeftPoint().x - ghost.getGhostSpeed()) / Tile.WIDTH;
		int nextUpLeftY = ghost.getUpLeftPoint().y / Tile.HEIGHT;
		int nextDownLeftY = ghost.getDownLeftPoint().y / Tile.HEIGHT;

		for (int i = nextUpLeftY; i <= nextDownLeftY; i++) {
			if (!tileField.isPossibleToMoveIn(nextUpLeftX, i)) {
				return false;
			}
		}
		return true;
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- FOOD
	// --------------------------------------------------------------
	// ***************************************************************************************************************************

	/**
	 * Initializes the food, and fills the field with them
	 * 
	 * @param foodList
	 * @param tileField
	 */
	private void initFood() {
		foodList.clear();

		for (int i = 0; i < tileField.getFieldTile().length; i++) {
			for (int j = 0; j < tileField.getFieldTile()[i].length - 1; j++) {
				if (((tileField.getFieldTile()[i][j].getTileID() == Tile.ROAD
						|| tileField.getFieldTile()[i][j].getTileID() == Tile.CORNER)
						&& (tileField.getFieldTile()[i + 1][j].getTileID() == Tile.ROAD
								|| tileField.getFieldTile()[i + 1][j].getTileID() == Tile.CORNER)
						&& (tileField.getFieldTile()[i][j + 1].getTileID() == Tile.ROAD
								|| tileField.getFieldTile()[i][j + 1].getTileID() == Tile.CORNER)
						&& (tileField.getFieldTile()[i + 1][j + 1].getTileID() == Tile.ROAD
								|| tileField.getFieldTile()[i + 1][j + 1].getTileID() == Tile.CORNER))) {
					Food food = new Food();
					food.setFoodType(Food.SMALL);
					food.setLocation(tileField.getFieldTile()[i][j].getLocation());
					food.setStatus(Food.ACTIVE);
					foodList.add(food);
				}
			}
		}
	}

	public void updateFood() {
		foodIterator = foodList.listIterator();

		while (foodIterator.hasNext()) {

			Food food = foodIterator.next();
			if (food.isActive()) {
				if (food.contains(pacMan.getCenterPoint())) {
					food.setStatus(Food.INACTIVE);
					score.incScore();
				}
			}
		}
	}

	public void renderFoods(Graphics g) {
		for (Food food : foodList) {
			if (food.isActive()) {
				g.drawImage(foodImages[food.getFoodType()], (int) food.getX(), (int) food.getY(), null);
			}
		}
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- IMAGES
	// --------------------------------------------------------------
	// ***************************************************************************************************************************

	private void loadComponentsImages() {
		tileImages = new Image[Tile.NUMBER_OF_BLOCK_TYPES];
		tileImages[Tile.WALL] = new ImageIcon("resources/images/wall.png").getImage();
		tileImages[Tile.ROAD] = new ImageIcon("resources/images/road.png").getImage();

		pacManWalkImages = new Image[16];
		pacManWalkImages[0] = new ImageIcon("resources/images/pacman_up_0.png").getImage();
		pacManWalkImages[1] = new ImageIcon("resources/images/pacman_up_1.png").getImage();
		pacManWalkImages[2] = new ImageIcon("resources/images/pacman_up_2.png").getImage();
		pacManWalkImages[3] = new ImageIcon("resources/images/pacman_up_3.png").getImage();
		pacManWalkImages[4] = new ImageIcon("resources/images/pacman_down_0.png").getImage();
		pacManWalkImages[5] = new ImageIcon("resources/images/pacman_down_1.png").getImage();
		pacManWalkImages[6] = new ImageIcon("resources/images/pacman_down_2.png").getImage();
		pacManWalkImages[7] = new ImageIcon("resources/images/pacman_down_3.png").getImage();
		pacManWalkImages[8] = new ImageIcon("resources/images/pacman_right_0.png").getImage();
		pacManWalkImages[9] = new ImageIcon("resources/images/pacman_right_1.png").getImage();
		pacManWalkImages[10] = new ImageIcon("resources/images/pacman_right_2.png").getImage();
		pacManWalkImages[11] = new ImageIcon("resources/images/pacman_right_3.png").getImage();
		pacManWalkImages[12] = new ImageIcon("resources/images/pacman_left_0.png").getImage();
		pacManWalkImages[13] = new ImageIcon("resources/images/pacman_left_1.png").getImage();
		pacManWalkImages[14] = new ImageIcon("resources/images/pacman_left_2.png").getImage();
		pacManWalkImages[15] = new ImageIcon("resources/images/pacman_left_3.png").getImage();

		pacManDyingImages = new Image[16];
		pacManDyingImages[0] = new ImageIcon("resources/images/pacman_dead_up_0.png").getImage();
		pacManDyingImages[1] = new ImageIcon("resources/images/pacman_dead_up_1.png").getImage();
		pacManDyingImages[2] = new ImageIcon("resources/images/pacman_dead_up_2.png").getImage();
		pacManDyingImages[3] = new ImageIcon("resources/images/pacman_dead_up_3.png").getImage();
		pacManDyingImages[4] = new ImageIcon("resources/images/pacman_dead_down_0.png").getImage();
		pacManDyingImages[5] = new ImageIcon("resources/images/pacman_dead_down_1.png").getImage();
		pacManDyingImages[6] = new ImageIcon("resources/images/pacman_dead_down_2.png").getImage();
		pacManDyingImages[7] = new ImageIcon("resources/images/pacman_dead_down_3.png").getImage();
		pacManDyingImages[8] = new ImageIcon("resources/images/pacman_dead_right_0.png").getImage();
		pacManDyingImages[9] = new ImageIcon("resources/images/pacman_dead_right_1.png").getImage();
		pacManDyingImages[10] = new ImageIcon("resources/images/pacman_dead_right_2.png").getImage();
		pacManDyingImages[11] = new ImageIcon("resources/images/pacman_dead_right_3.png").getImage();
		pacManDyingImages[12] = new ImageIcon("resources/images/pacman_dead_left_0.png").getImage();
		pacManDyingImages[13] = new ImageIcon("resources/images/pacman_dead_left_1.png").getImage();
		pacManDyingImages[14] = new ImageIcon("resources/images/pacman_dead_left_2.png").getImage();
		pacManDyingImages[15] = new ImageIcon("resources/images/pacman_dead_left_3.png").getImage();

		ghostWalkImages = new Image[GhostComponent.GHOST_TYPES][GhostComponent.NUMBER_OF_DIRECTIONS
				* GhostComponent.NUMBER_OF_SPRITES];
		ghostWalkImages[0][0] = new ImageIcon("resources/images/ghost_blue_up_0.png").getImage();
		ghostWalkImages[0][1] = new ImageIcon("resources/images/ghost_blue_up_1.png").getImage();
		ghostWalkImages[0][2] = new ImageIcon("resources/images/ghost_blue_down_0.png").getImage();
		ghostWalkImages[0][3] = new ImageIcon("resources/images/ghost_blue_down_1.png").getImage();
		ghostWalkImages[0][4] = new ImageIcon("resources/images/ghost_blue_right_0.png").getImage();
		ghostWalkImages[0][5] = new ImageIcon("resources/images/ghost_blue_right_1.png").getImage();
		ghostWalkImages[0][6] = new ImageIcon("resources/images/ghost_blue_left_0.png").getImage();
		ghostWalkImages[0][7] = new ImageIcon("resources/images/ghost_blue_left_1.png").getImage();

		ghostWalkImages[1][0] = new ImageIcon("resources/images/ghost_green_up_0.png").getImage();
		ghostWalkImages[1][1] = new ImageIcon("resources/images/ghost_green_up_1.png").getImage();
		ghostWalkImages[1][2] = new ImageIcon("resources/images/ghost_green_down_0.png").getImage();
		ghostWalkImages[1][3] = new ImageIcon("resources/images/ghost_green_down_1.png").getImage();
		ghostWalkImages[1][4] = new ImageIcon("resources/images/ghost_green_right_0.png").getImage();
		ghostWalkImages[1][5] = new ImageIcon("resources/images/ghost_green_right_1.png").getImage();
		ghostWalkImages[1][6] = new ImageIcon("resources/images/ghost_green_left_0.png").getImage();
		ghostWalkImages[1][7] = new ImageIcon("resources/images/ghost_green_left_1.png").getImage();

		ghostWalkImages[2][0] = new ImageIcon("resources/images/ghost_purple_up_0.png").getImage();
		ghostWalkImages[2][1] = new ImageIcon("resources/images/ghost_purple_up_1.png").getImage();
		ghostWalkImages[2][2] = new ImageIcon("resources/images/ghost_purple_down_0.png").getImage();
		ghostWalkImages[2][3] = new ImageIcon("resources/images/ghost_purple_down_1.png").getImage();
		ghostWalkImages[2][4] = new ImageIcon("resources/images/ghost_purple_right_0.png").getImage();
		ghostWalkImages[2][5] = new ImageIcon("resources/images/ghost_purple_right_1.png").getImage();
		ghostWalkImages[2][6] = new ImageIcon("resources/images/ghost_purple_left_0.png").getImage();
		ghostWalkImages[2][7] = new ImageIcon("resources/images/ghost_purple_left_1.png").getImage();

		ghostWalkImages[3][0] = new ImageIcon("resources/images/ghost_red_up_0.png").getImage();
		ghostWalkImages[3][1] = new ImageIcon("resources/images/ghost_red_up_1.png").getImage();
		ghostWalkImages[3][2] = new ImageIcon("resources/images/ghost_red_down_0.png").getImage();
		ghostWalkImages[3][3] = new ImageIcon("resources/images/ghost_red_down_1.png").getImage();
		ghostWalkImages[3][4] = new ImageIcon("resources/images/ghost_red_right_0.png").getImage();
		ghostWalkImages[3][5] = new ImageIcon("resources/images/ghost_red_right_1.png").getImage();
		ghostWalkImages[3][6] = new ImageIcon("resources/images/ghost_red_left_0.png").getImage();
		ghostWalkImages[3][7] = new ImageIcon("resources/images/ghost_red_left_1.png").getImage();

		foodImages = new Image[Food.NUMBER_OF_FOOD_TYPES];
		foodImages[0] = new ImageIcon("resources/images/food_1.png").getImage();
		foodImages[1] = new ImageIcon("resources/images/food_2.png").getImage();
		foodImages[2] = new ImageIcon("resources/images/food_3.png").getImage();

		livesBgImage = new ImageIcon("resources/images/score_panel.png").getImage();

		livesImages = new Image[4];
		livesImages[0] = new ImageIcon("resources/images/lives_0.png").getImage();
		livesImages[1] = new ImageIcon("resources/images/lives_1.png").getImage();
		livesImages[2] = new ImageIcon("resources/images/lives_2.png").getImage();
		livesImages[3] = new ImageIcon("resources/images/lives_3.png").getImage();

		gameOverImage = new ImageIcon("resources/images/game_over.png").getImage();
	}
}
