package game.common.classes;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteAnimationComponent;
import engine.entity.component.SpriteComponent;
import engine.gui.components.Sprite;
import engine.maths.Vec3;
import engine.maths.Vec4;
import game.common.inventory.Item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Caravan {

    private Entity entity;
    private Queue<Vec3> destinationBuffer = new LinkedList<Vec3>();
    private SpriteAnimationComponent sprite;
    private ArrayList<Item> items;

    public Caravan(Application app) {
        entity = new Entity("Caravan");
        entity.addComponent(new NetTransformComponent());
        if(!app.isHeadless()) {
            sprite = new SpriteAnimationComponent(app.getAssetManager().getTexture("res/actos/caravan/sprite.png"), 4,
                    0, 0, 5.0f, 5.0f, 2);
            entity.addComponent(sprite);
        }
    }

    public Entity getEntity() {
        return entity;
    }

}
