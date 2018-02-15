package engine.sound;

import engine.entity.component.AbstractComponent;
import engine.maths.Vec3;
import static org.lwjgl.openal.AL10.*;

public class SoundListener extends AbstractComponent {

    public SoundListener() {
       
    }

    public SoundListener(Vec3 position) {
        alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }
    
    public void setPosition(Vec3 position) {
        alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
    }
    
    public void setSpeed(Vec3 speed) {
        alListener3f(AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
    }
   
    public void setOrientation(Vec3 pos, Vec3 up) {
        float[] data = new float[6];
        data[0] = pos.getX();
        data[1] = pos.getY();
        data[2] = pos.getZ();
        data[3] = up.getX();
        data[4] = up.getY();
        data[5] = up.getZ();
        alListenerfv(AL_ORIENTATION, data);
    }
}