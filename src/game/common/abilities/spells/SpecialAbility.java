package game.common.abilities.spells;

import game.common.abilities.AbstractAbility;
import game.common.items.attributes.Attribute;
import game.common.items.attributes.main.Damage;
import game.common.items.attributes.main.Energy;
import game.common.items.attributes.main.MovementSpeed;
import game.common.items.attributes.main.Resistance;

import java.util.ArrayList;

public class SpecialAbility extends AbstractAbility{

    /**
     * Percentage values (%) that will affect the attributes of the target player.
     */
    private float affectResistance = 1.0f;
    private float affectMovementSpeed = 1.0f;
    private float affectEnergy = 1.0f;
    private float affectDamage = 1.0f;

    private float energyCost;

    private float duration;

    /**
     * Creates a special ability for the target player
     * @param name
     * @param cooldown
     * @param extraDamage
     */
    public SpecialAbility(String name, float cooldown, float extraDamage) {
        super(name,cooldown,extraDamage);
    }

    /** 
     * Sets the affected resistance of the target player
     * @param res
     */
    public void setResistance(float res) {
        affectResistance = res;
    }

    /**
     * Sets the affected movement of the target player
     * @param move
     */
    public void setMovement(float move) {
        affectMovementSpeed = move;
    }

    /**
     * Sets the affected energy of the target player
     * @param energy
     */
    public void setEnergy(float energy) {
        affectEnergy = energy;
    }

    /**
     * Sets the affected damage of the target player
     */
    public void setDamage(float dmg) {
        affectDamage = dmg;
    }

    /**
     * Sets the duration of the ability
     */
    public void setDuration(float dur) {
        duration = dur;
    }

    /**
     * Sets the energy cost to use the ability
     * @param cost
     */
    public void setEnergyCost(float cost) {
        energyCost = cost;
    }

    @Override
    public float dealDamage(float damage) {
        return damage + extraDamage;
    }

    /**
     * Returns an array of attributes that affects the target player
     * @return
     */
    public ArrayList<Attribute> affectAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        if(affectResistance < 1.0f) {
            Resistance attribute = new Resistance(affectResistance);
            attributes.add(attribute);
        }
        if(affectMovementSpeed < 1.0f) {
            MovementSpeed attribute = new MovementSpeed(affectMovementSpeed);
            attributes.add(attribute);
        }
        if(affectEnergy < 1.0f) {
            Energy attribute = new Energy(affectEnergy);
            attributes.add(attribute);
        }
        if(affectDamage < 1.0f) {
            Damage attribute = new Damage(affectDamage);
            attributes.add(attribute);
        }
        return attributes;
    }

}
