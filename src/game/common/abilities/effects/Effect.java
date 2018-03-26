package game.common.abilities.effects;

import game.common.actors.Actor;

public abstract class Effect
{
	private float maxDuration;
	private float duration;
	protected Actor target;
	
	public Effect(float duration, Actor target)
	{
		this.duration = 0;
		this.maxDuration = duration;
		this.target = target;
		applyEffect();
		target.addEffect(this);
	}
	
	public abstract void applyEffect();
	public abstract void removeEffect();
	
	public void update(float deltaTime)
	{
		duration += deltaTime;
		
		if(duration >= maxDuration)
		{
			removeEffect();
			target.removeEffect(this);
		}
	}
}
