package game.player;

import engine.AI.Path;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.texture.Texture;
import engine.maths.Mat4;
import game.classes.AbstractClasses;
import game.classes.AbstractClasses.ClassType;
import game.classes.AbstractClasses.StatType;
import game.classes.melee.MeleeDPS;
import game.classes.melee.MeleeTank;
import game.classes.range.RangeDPS;
import game.classes.range.RangeHealer;

//Will contain an Entity object with components and holds data of it's own profile (health, status, position etc)
public class Player {

	private int id;
	private String name;
	private AbstractClasses charClass;
	private int vitality;
	private int energy;

	private float movementSpeed = 2.0f;
	String status;
	private boolean isLocal;

	private Path path;

	public Entity player;
	public TransformComponent transform;
	public SpriteComponent sprite;

	public Player(int id, String name, boolean isLocal, Texture texture, ClassType selectedClass,
			StatType selectedStat) {
		this.id = id;
		this.name = name;
		this.isLocal = isLocal;

		switch (selectedClass) {
		case RANGEDPS:
			charClass = new RangeDPS(selectedStat);
		case RANGEHEALER:
			charClass = new RangeHealer(selectedStat);
		case MELEEDPS:
			charClass = new MeleeDPS(selectedStat);
		case MELEETANK:
			charClass = new MeleeTank(selectedStat);
		}

		this.vitality = charClass.getVitality();
		this.energy = charClass.getEnergy();

		player = new Entity(0, "Player " + name);

		sprite = new SpriteComponent(null, texture, id, id);
		transform = new TransformComponent();
		player.addComponent(sprite);
		player.addComponent(transform);
	}

	public AbstractClasses getCharClass() {
		return charClass;
	}

	public void setVitality(int hp) {
		vitality = hp;
	}

	public int getVitality() {
		return vitality;
	}

	public void setEnergy(int mana) {
		energy = mana;
	}

	public int getEnergy() {
		return energy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String newStatus) {
		status = newStatus;
	}

	public boolean checkLocal() {
		return isLocal;
	}

	public int checkID() {
		return id;
	}

	public void render(Renderer2D renderer, Mat4 transformation) {
		sprite.submit(renderer, transformation);
	}

	public void update() {
		// Follow the path
	}

	public Entity getEntity() {
		return player;
	}
}
