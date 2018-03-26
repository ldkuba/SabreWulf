package game.common.abilities.effects;

import game.common.actors.Actor;

public class LinkSprintEffect extends Effect
{
	/* Percentage increase in movement speed */
	private final float moveSpeedIncrease = 1.2f;

	public LinkSprintEffect(float duration, Actor target)
	{
		super(duration, target);
	}

	@Override
	public void applyEffect()
	{
		target.setMovementSpeed(target.getMovementSpeed() * moveSpeedIncrease);
	}

	@Override
	public void removeEffect()
	{
		target.setMovementSpeed(target.getMovementSpeed() / moveSpeedIncrease);
	}
}
