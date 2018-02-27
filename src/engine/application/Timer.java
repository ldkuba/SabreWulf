package engine.application;

public class Timer
{
	private long startTime;
	
	private long timePerTick;
	private long currentTime;
	
	public Timer(float tickRate)
	{
		timePerTick = (long) ((1.0f/tickRate)*1000.0f);
		this.startTime = System.currentTimeMillis();
		
		currentTime = startTime;
	}
	
	public void waitForTick() throws InterruptedException
	{
		currentTime = System.currentTimeMillis();
		long delta = currentTime - startTime;
		
		if(delta >= timePerTick)
		{
			startTime = currentTime;
			return;
		}
		
		long timeToSleep = timePerTick - delta;
		
		startTime = currentTime;
		
		Thread.sleep(timeToSleep);
	}
	
	public void setTickRate(float tickRate)
	{
		timePerTick = (long) ((1.0f/tickRate)*1000.0f);
	}
}