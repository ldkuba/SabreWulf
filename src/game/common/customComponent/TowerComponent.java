package game.common.customComponent;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.AbstractComponent;
import engine.maths.Vec3;

public class TowerComponent extends AbstractComponent{

    private Entity entity;
    private Application app;


    //Tells which team controls this tower
    private int controlledBy;
    //Progress?

    public TowerComponent(Application app, Entity entity) {
        this.entity = entity;
        this.app = app;
        controlledBy = 3;   //Neutral team
    }

    public void update() {
        //color overlay depending in team.
    }
}
