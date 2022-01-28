package components;

import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * This class represents the food of PacMan
 * 
 * @author ZoltanMate
 */
public class Food extends Rectangle {

	private static final long serialVersionUID = -6660510585266615183L;

	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;

	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int BIG = 2;

	public static final int NUMBER_OF_FOOD_TYPES = 3;
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;

	private int giftType;
	private int status;

	public Food() {
		this.width = WIDTH;
		this.height = HEIGHT;

		this.giftType = SMALL;
		this.status = INACTIVE;
	}

	public void setFoodType(int giftType) {
		this.giftType = giftType;
	}

	public int getFoodType() {
		return giftType;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isActive() {
		return status == ACTIVE;
	}

	/**
	 * Initializes the food, and fills the field with them
	 * 
	 * @param foodList
	 * @param tileField
	 */
	public void initFood(LinkedList<Food> foodList, TileField tileField) {
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
}
