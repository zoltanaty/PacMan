package utils;

import java.io.File;
import java.util.Scanner;

import components.TileField;

/**
 * Class responsible for loading the map from the text file.
 * 
 * @author ZoltanMate
 */
public class LevelLoader {

	private File levelFile;
	private Scanner fileReader;

	private int[][] levelMap;

	public LevelLoader() {
		setLevelMap(new int[TileField.HEIGHT][TileField.WIDTH]);
	}

	public void loadLevel() {
		levelFile = new File("resources/levels/level.lev");

		try {
			fileReader = new Scanner(levelFile);

			for (int i = 0; i < TileField.HEIGHT; i++) {
				for (int j = 0; j < TileField.WIDTH; j++) {
					getLevelMap()[i][j] = fileReader.nextInt();
				}
			}
		} catch (Exception e) {
			System.out.println("Error: reading level file.");
		} finally {
			fileReader.close();
		}
	}

	public int[][] getLevelMap() {
		return levelMap;
	}

	public void setLevelMap(int[][] levelMap) {
		this.levelMap = levelMap;
	}
	
}
