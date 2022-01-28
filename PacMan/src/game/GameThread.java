package game;

/**
 * The game thread, helps managing the update-interval
 * 
 * @author ZoltanMate
 */
public class GameThread extends Thread {

	public static final int FPS = 120;
	public static final int updateInterval = 1000 / FPS;

	public GameThread(Runnable runnable) {
		super(runnable);
	}

	public void synchronizeFrames() {
		try {
			Thread.sleep(updateInterval);
		} catch (Exception e) {}
	}
}
