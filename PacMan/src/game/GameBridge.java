package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
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
 * Class responsible for the connection between the GameEngine and the GameComponents
 * 
 * @author ZoltanMate
 */
public class GameBridge {

	private LevelLoader levelLoader;
	private TileField tileField;
	private PacManComponent pacMan;
	private Score score;

	private GhostComponent ghost;
	private LinkedList<GhostComponent> ghostList;
	private Iterator<GhostComponent> ghostIterator;

	private Food food;
	private LinkedList<Food> foodList;
	private Iterator<Food> foodIterator;

	private Random random;
	private int previousDirection;

	private Image[] tileImages;

	private Image[] pacManWalkImages;
	private Image[] pacManMortalImages;

	private Image[][] ghostWalkImages;
	private Image[][] ghostMortalImages;

	private Image[] foodImages;

	private Image livesBgImage;
	private Image[] livesImage;
	private Image gameOverImage;

	public GameBridge() {
		levelLoader = new LevelLoader();
		tileField = new TileField();
		pacMan = new PacManComponent();
		score = new Score();

		ghost = new GhostComponent();
		ghostList = new LinkedList<GhostComponent>();

		food = new Food();
		foodList = new LinkedList<Food>();

		random = new Random();

		loadComponentsImages();
	}

	public void startNewGame() {
		levelLoader.loadLevel();
		levelLoader.initTileField(tileField);
		food.initFood(foodList, tileField);
		pacMan.initPacMan(pacMan);
		ghost.initGhosts(ghostList, random);
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- TileField -----------------------------------------------------------
	// ***************************************************************************************************************************

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
	// --------------------------------------------------- Score/Lives -----------------------------------------------------------
	// ***************************************************************************************************************************

	public void renderScore(Graphics g) {
		Font f = new Font("Arial Black", Font.BOLD, 27);
		g.setFont(f);
		g.setColor(Color.YELLOW);

		g.drawImage(livesBgImage, 0, 600, null);
		g.drawString(score.getScore(), 105, 626);

		if (score.getLives() == 3) {
			g.drawImage(livesImage[3], 442, 600, null);
		} else if (score.getLives() == 2) {
			g.drawImage(livesImage[2], 442, 600, null);
		} else if (score.getLives() == 1) {
			g.drawImage(livesImage[1], 442, 600, null);
		} else if (score.getLives() == 0) {
			g.drawImage(livesImage[0], 442, 600, null);
			g.drawImage(gameOverImage, 190, 600, null);
		}
	}

	// ***************************************************************************************************************************
	// --------------------------------------------------- PacMan --------------------------------------------------------------
	// ***************************************************************************************************************************

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
		if (pacMan.isLive()) {

			if (pacMan.isDirectionSetTo(PacManComponent.UP) && isPossibleToMoveUp(pacMan)) {
				pacMan.move(PacManComponent.UP);

				previousDirection = PacManComponent.UP;
				clearUnnecessaryDirections();
			} else if (pacMan.isDirectionSetTo(PacManComponent.RIGHT) && isPossibleToMoveRight(pacMan)) {
				pacMan.move(PacManComponent.RIGHT);

				previousDirection = PacManComponent.RIGHT;
				clearUnnecessaryDirections();
			} else if (pacMan.isDirectionSetTo(PacManComponent.DOWN) && isPossibleToMoveDown(pacMan)) {
				pacMan.move(PacManComponent.DOWN);

				previousDirection = PacManComponent.DOWN;
				clearUnnecessaryDirections();
			} else if (pacMan.isDirectionSetTo(PacManComponent.LEFT) && isPossibleToMoveLeft(pacMan)) {
				pacMan.move(PacManComponent.LEFT);

				previousDirection = PacManComponent.LEFT;
				clearUnnecessaryDirections();
			} else {
				switch (previousDirection) {
				case PacManComponent.UP: {
					if (isPossibleToMoveUp(pacMan)) {
						if (previousDirection != -1) {
							pacMan.move(previousDirection);
						}
					}
				}
					break;
				case PacManComponent.RIGHT: {
					if (isPossibleToMoveRight(pacMan)) {
						if (previousDirection != -1) {
							pacMan.move(previousDirection);
						}
					}
				}
					break;
				case PacManComponent.DOWN: {
					if (isPossibleToMoveDown(pacMan)) {
						if (previousDirection != -1) {
							pacMan.move(previousDirection);
						}
					}
				}
					break;
				case PacManComponent.LEFT: {
					if (isPossibleToMoveLeft(pacMan)) {
						if (previousDirection != -1) {
							pacMan.move(previousDirection);
						}
					}
				}
					break;
				}

				clearUnnecessaryDirections();
			}

		} else if (pacMan.isMortal()) {
			pacMan.updateMortalState();
		}
	}


	public void renderPacMan(Graphics g) {
		if (pacMan.isLive()) {
			g.drawImage(pacManWalkImages[pacMan.getMoveImageIndex()], (int) pacMan.getX(), (int) pacMan.getY(), null);
		} else if (pacMan.isMortal()) {
			g.drawImage(pacManMortalImages[pacMan.getMortalImageIndex()], (int) pacMan.getX(), (int) pacMan.getY(), null);
		}
	}

	private boolean isPossibleToMoveUp(PacManComponent pacMan) {
		int nextUpLeftX = pacMan.getUpLeftPoint().x / Tile.WIDTH;
		int nextUpLeftY = (pacMan.getUpLeftPoint().y - pacMan.getPacManSpeed()) / Tile.HEIGHT;
		int nextUpRightX = pacMan.getUpRightPoint().x / Tile.WIDTH;

		for (int i = nextUpLeftX; i <= nextUpRightX; i++) {
			if (!tileField.isPossibleToMoveIn(i, nextUpLeftY)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveRight(PacManComponent pacMan) {
		int nextUpRightX = (pacMan.getUpRightPoint().x + pacMan.getPacManSpeed()) / Tile.WIDTH;
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
		int nextDownLeftY = (pacMan.getDownLeftPoint().y + pacMan.getPacManSpeed()) / Tile.HEIGHT;
		int nextDownRightX = pacMan.getDownRightPoint().x / Tile.WIDTH;

		for (int i = nextDownLeftX; i <= nextDownRightX; i++) {
			if (!tileField.isPossibleToMoveIn(i, nextDownLeftY)) {
				return false;
			}
		}
		return true;
	}

	private boolean isPossibleToMoveLeft(PacManComponent pacMan) {
		int nextUpLeftX = (pacMan.getUpLeftPoint().x - pacMan.getPacManSpeed()) / Tile.WIDTH;
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

	// a szellemek frissitese, valamint pacMan kovetesenek implementalasa
	// Ha valamelyk szellem utkozik pacMannel, pacman allapota atvalt MORTAL-ra
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

			if (pacMan.isLive() && ghost.contains(pacMan.getCenterPoint())) {
				if (score.getLives() > 1) {
					pacMan.setState(PacManComponent.MORTAL);
					score.decLives();
					ghost.initGhosts(ghostList, random);
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
			} else if (pacMan.isMortal()) {
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
	// --------------------------------------------------- FOOD --------------------------------------------------------------
	// ***************************************************************************************************************************

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
	// --------------------------------------------------- IMAGES --------------------------------------------------------------
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

		pacManMortalImages = new Image[16];
		pacManMortalImages[0] = new ImageIcon("resources/images/pacman_dead_up_0.png").getImage();
		pacManMortalImages[1] = new ImageIcon("resources/images/pacman_dead_up_1.png").getImage();
		pacManMortalImages[2] = new ImageIcon("resources/images/pacman_dead_up_2.png").getImage();
		pacManMortalImages[3] = new ImageIcon("resources/images/pacman_dead_up_3.png").getImage();
		pacManMortalImages[4] = new ImageIcon("resources/images/pacman_dead_down_0.png").getImage();
		pacManMortalImages[5] = new ImageIcon("resources/images/pacman_dead_down_1.png").getImage();
		pacManMortalImages[6] = new ImageIcon("resources/images/pacman_dead_down_2.png").getImage();
		pacManMortalImages[7] = new ImageIcon("resources/images/pacman_dead_down_3.png").getImage();
		pacManMortalImages[8] = new ImageIcon("resources/images/pacman_dead_right_0.png").getImage();
		pacManMortalImages[9] = new ImageIcon("resources/images/pacman_dead_right_1.png").getImage();
		pacManMortalImages[10] = new ImageIcon("resources/images/pacman_dead_right_2.png").getImage();
		pacManMortalImages[11] = new ImageIcon("resources/images/pacman_dead_right_3.png").getImage();
		pacManMortalImages[12] = new ImageIcon("resources/images/pacman_dead_left_0.png").getImage();
		pacManMortalImages[13] = new ImageIcon("resources/images/pacman_dead_left_1.png").getImage();
		pacManMortalImages[14] = new ImageIcon("resources/images/pacman_dead_left_2.png").getImage();
		pacManMortalImages[15] = new ImageIcon("resources/images/pacman_dead_left_3.png").getImage();

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

		livesImage = new Image[4];
		livesImage[0] = new ImageIcon("resources/images/lives_0.png").getImage();
		livesImage[1] = new ImageIcon("resources/images/lives_1.png").getImage();
		livesImage[2] = new ImageIcon("resources/images/lives_2.png").getImage();
		livesImage[3] = new ImageIcon("resources/images/lives_3.png").getImage();

		gameOverImage = new ImageIcon("resources/images/game_over.png").getImage();
	}
}
