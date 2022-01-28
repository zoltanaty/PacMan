package game;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import components.PacManComponent;

/**
 * @author ZoltanMate
 */
public class GameEngine {
	
	private GameBridge gameBridge;
	private Graphics   gameGraphics;
	private KeyAdapter keyAdapter;
	
	public GameEngine(){
		gameBridge = new GameBridge();
		keyAdapter = createKeyHandler();
		gameBridge.startNewGame();
	}
	
	public KeyAdapter getKeyAdapter(){
		return this.keyAdapter;
	}
	
	private KeyAdapter createKeyHandler() {
		return new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_UP){
					gameBridge.movePacMan(PacManComponent.UP);
				}

				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					gameBridge.movePacMan(PacManComponent.DOWN);
				}

				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					gameBridge.movePacMan(PacManComponent.RIGHT);
				}

				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					gameBridge.movePacMan(PacManComponent.LEFT);
				}
			}	
		};
	}
	
	public void setGameGraphics(BufferedImage gameGraphics){
		this.gameGraphics = gameGraphics.getGraphics();
	}
	
	public void updateGameComponents(){
		gameBridge.updatePacMan();
		gameBridge.updateFood();
		gameBridge.updateGhosts();
	}
	
	public void renderGameComponents(){
		gameBridge.renderTileField(gameGraphics);
		gameBridge.renderScore(gameGraphics);
		gameBridge.renderFoods(gameGraphics);
		gameBridge.renderGhosts(gameGraphics);
		gameBridge.renderPacMan(gameGraphics);
	}	
}
