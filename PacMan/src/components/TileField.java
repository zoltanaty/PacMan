package components;

/**
 * The so called Tile-Field (field of tiles) forms the map of the game
 * 
 * @author Zoltan Mate
 */
public class TileField {
	
	public static final int WIDTH  = 27;
	public static final int HEIGHT = 30;

	private Tile[][] fieldTile;

	/**
	 *  Creates the field(map) by creating the tiles and positioning them
	 */
	public TileField() {
		fieldTile = new Tile[HEIGHT][WIDTH];

		for(int i = 0; i < HEIGHT; i++)
		{
			for(int j = 0; j < WIDTH; j++)
			{
				fieldTile[i][j] = new Tile(j * Tile.HEIGHT, i * Tile.WIDTH);
			}
		}
	}
	
	public void setTileID(int newID, int i, int j){
		fieldTile[i][j].setTileID(newID);
	}
	
	public int getTileID(int i, int j){
		return fieldTile[i][j].getTileID();
	}

	public  Tile getTileItem(int i, int j){
		return fieldTile[i][j];
	}
	
	public Tile[][] getFieldTile(){
		return this.fieldTile;
	}
	
	/**
	 * @param i - coordinate
	 * @param j - coordinate
	 * @return true in case it is possible to move on tile[i][j]
	 */
	public boolean isPossibleToMoveIn(int i, int j){	
		switch(fieldTile[j][i].getTileID()) {	
			case Tile.ROAD:
				return true;
			case Tile.CORNER:
				return true;
			case Tile.WALL:
				return false;
			default: 
				return false;
		}
	}
}
