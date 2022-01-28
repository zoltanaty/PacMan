package components;

import java.awt.Rectangle;

/**
 * Represents one block of the map
 * 
 * @author Zoltan Mate
 */
public class Tile extends Rectangle {
	
	private static final long serialVersionUID = -8330063285647502006L;
	
	public static final int WIDTH  = 20;
	public static final int HEIGHT = 20;
	
	public static final int ROAD = 0;
	public static final int CORNER = 2;
	public static final int WALL = 1;
	
	public static final int NUMBER_OF_BLOCK_TYPES = 3;
	
	private int tileID;
	
	public Tile(int x, int y) {
		this.setLocation(x, y);
		this.setSize(WIDTH, HEIGHT);
	}
	
	public void setTileID(int tileID) {
		this.tileID = tileID;
	}
	
	public int getTileID() {
		return this.tileID;
	}	
}
