package game;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * The frame of the game
 * 
 * @author ZoltanMate
 */
public class GameFrame extends JFrame{
	
	private static final long serialVersionUID = -5001146727791405693L;
	
	public static final int WIDTH = 556;
	public static final int HEIGHT = 670;
	
	/**
	 * 	The game will draw on this panel
	 */
	private GamePanel gamePanel;

	public GameFrame(){
		setUpFrame();
		setUpComponents();
	}
	
	/**
	 * 	Set-up the frame properties
	 */
	private void setUpFrame(){
		this.setTitle("Pac-Man");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.WHITE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		this.setVisible(true);
	}

	/**
	 * 	Creates a new GamePanel and adds it to the contentPane on the frame
	 */
	private void setUpComponents(){	
		gamePanel = new GamePanel();
		this.getContentPane().add(gamePanel);	
	}
	
	public BufferedImage getFrameBufferedImage(){
		return gamePanel.getGameBufferedImage();
	}
}
