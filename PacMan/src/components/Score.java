package components;

import java.awt.Rectangle;

/**
 * Component to handle Score and Life
 * 
 * @author ZoltanMate
 */
public class Score extends Rectangle {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 540;
	public static final int HEIGHT = 30;
	
	private int lives;
	private int score;
	
	public Score(){
		this.setSize(WIDTH, HEIGHT);
		lives = 3;	
		score = 0;
	}
	
	public int getLives(){
		return lives;
	}
	
	public String getScore(){
		return score + "";
	}
	
	public void incScore(){
		score += 10;
	}
	
	public void decLives(){
		lives--;
	}
	
	public void incLives(){
		lives++;
	}
	
}
