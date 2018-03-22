package engine.application;

/**
 * Sets up a timer to be used by the engine
 * @author User
 *
 */
public class Timer {
	private long startTime;

	private long initialTime;
	
	private long elapsedTime;

	private long timePerTick;
	private long currentTime;
	
	/**
	 * Creates a new timer
	 * @param tickRate
	 */
	public Timer(float tickRate) {
		initialTime = System.currentTimeMillis();
		
		timePerTick = (long) ((1.0f/tickRate)*1000.0f);
		this.startTime = System.currentTimeMillis();
		
		currentTime = startTime;
	}

	/**
	 * Waits for the next tick on the timer
	 * @throws InterruptedException
	 */
	public void waitForTick() throws InterruptedException {
		currentTime = System.currentTimeMillis();
		long delta = currentTime - startTime;	
		elapsedTime = delta;
		if(delta >= timePerTick) {
			startTime = currentTime;
			return;
		}

		long timeToSleep = timePerTick - delta;

		startTime = currentTime;

		Thread.sleep(timeToSleep);
	}
	
	/**
	 * Sets the tick rate of the timer
	 * @param tickRate
	 */
	public void setTickRate(float tickRate) {
		timePerTick = (long) ((1.0f/tickRate)*1000.0f);
	}
	
	/**
	 * Gets the current time of the timer
	 * @return 
	 */
	public long getTime() {
		return currentTime - initialTime;
	}

	/**
	 * Gets the elapsed time of the timer
	 * @return
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}

}
