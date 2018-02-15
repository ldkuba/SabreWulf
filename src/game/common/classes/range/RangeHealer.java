package game.classes.range;

/*
 * Original Intelligence: 90
 * RangeHealer Intelligence: 170
 */

public class RangeHealer extends RangeClass{

	private int exIntell = 80;
	private int exStrength;
	private int exDefence;
	privagte int exHealth;
	
	
	
	public RangeHealer(StatType type)
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExHealth(exHealth);
		setStatType(type);
	}
	
}
