package game.common.objects;

import engine.entity.Entity;
import engine.entity.component.ColliderComponent;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.net.common_net.NetworkManager;
import game.common.customComponent.CapturePointComponent;
import game.common.player.ActorManager;

public class Tower {

    private Entity entity;
    private NetDataComponent netData;
    private float maxProgress = 100.0f;

    //Client side
    public Tower(Vec3 location, Texture texture, int netId, NetworkManager networkManager, float width, float height) {

        //Set entity
        entity = new Entity("Tower");
        entity.addComponent(new TransformComponent());
        entity.getTransform().setPosition(location);
        entity.addComponent(new NetIdentityComponent(netId, networkManager));
        netData = new NetDataComponent();
        netData.addData("Progress", 0);
        netData.addData("Occupied", -1);    //No on controls the territory.

        entity.addComponent(netData);
        entity.addComponent(new SpriteComponent(new Vec4(0.0f,0.0f,0.0f,0.0f),texture,width, height));

        System.out.println("ID given: " + netId);

        ColliderComponent collider = new ColliderComponent(2.0f, false);
        entity.addComponent(collider);

    }

    //Server side
    public Tower(ActorManager actorManager, Vec3 location, int netId, NetworkManager network, float width, float height) {
        //Set up entity
        entity = new Entity("Tower");
        entity.addComponent(new TransformComponent());
        entity.getTransform().setPosition(location);
        entity.addComponent(new NetIdentityComponent(netId, network));
        netData = new NetDataComponent();
        netData.addData("Progress", 0);
        netData.addData("Occupied", -1);
        entity.addComponent(netData);

        System.out.println("ID given: " + netId);
        
        entity.addComponent(new CapturePointComponent(actorManager, entity));

        ColliderComponent collider = new ColliderComponent(2.0f, false);
        entity.addComponent(collider);
    }

    public void setPosition(float x, float y, float z) {
        entity.getTransform().setPosition(new Vec3(x,y,z));
    }
    
    public Entity getEntity() {
        return entity;
    }

    public float getMaxProgress() {
        return maxProgress;
    }
}
