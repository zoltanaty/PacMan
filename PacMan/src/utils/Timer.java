package utils;

/**
 * Timer used for several things in the game
 * 
 * @author ZoltanMate
 */
public class Timer {

	private long delay;
	private long lastTime;
	private long currentTime;

	/**
	 * Constructor sets the delay
	 * 
	 * @param delay - delay in ms
	 */
	public Timer(long delay) {
		this.delay = delay * 1000000L;
	}

	public void initTimer() {
		lastTime = System.nanoTime();
	}

	public void setDelay(long delay) {
		this.delay = delay * 1000000L;
	}

	public boolean isEllapsed() {
		currentTime = System.nanoTime();
		if (System.nanoTime() - lastTime >= delay) {
			lastTime = currentTime;
			return true;
		}

		return false;
	}
}
