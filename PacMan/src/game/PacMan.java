package game;

/**
 * The starting point of the game
 * Implemets Runnable, so the run method will handle:
 * 	- updating the game components
 *  - rendering the game components
 *  - calling the repaint() method of the frame
 *  - it will synchronize the thread to the expected FPS
 * 
 * @author ZoltanMate
 */
public class PacMan implements Runnable{

	private GameFrame gameFrame;
	private GameThread gameThread;
	private GameEngine gameEngine;
	
	public PacMan(){
		setUpComponents();
		gameThread.start();
	}
	
	private void setUpComponents(){
		gameFrame = new GameFrame();
		gameEngine = new GameEngine();
		gameThread = new GameThread(this);
		
		gameFrame.addKeyListener(gameEngine.getKeyAdapter());
		gameEngine.setGameGraphics(gameFrame.getFrameBufferedImage());
	}

	@Override
	public void run() {
		while(true){
			gameEngine.updateGameComponents();
			gameEngine.renderGameComponents();
			gameFrame.repaint();
			gameThread.synchronizeFrames();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new PacMan();
	}
}
