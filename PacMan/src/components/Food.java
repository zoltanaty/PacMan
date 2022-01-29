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

}
