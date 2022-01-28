package game;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The game will draw on this panel
 * 
 * @author Zoltan Mate
 */
public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = -6251979586909122858L;
	
	public static final int WIDTH = 540;
	public static final int HEIGHT = 660;

	private BufferedImage bufferedImage;

	public GamePanel(){
		this.setSize(WIDTH, HEIGHT);
		bufferedImage = new  BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	public BufferedImage getGameBufferedImage(){
		return bufferedImage;
	}
	
	protected void paintComponent(Graphics g){
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
