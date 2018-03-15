package engine.entity.component;

import engine.font.Font;
import engine.graphics.renderer.Renderable2D;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.texture.Texture;
import engine.maths.Mat4;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;

import java.util.ArrayList;

public class ProgressBarComponent extends AbstractComponent{

    private float width;
    private float height;
    private float m_Size;
    private float m_Spread;

    private boolean toChange;

    private float progress;

    private Texture texture;
    private Vec4 m_Color;

    private Renderable2D m_Sprite;
    private TextComponent text;
    private SpriteComponent m_Component;

    public ProgressBarComponent (Vec4 color, Texture texture, float width, float height, float size, float spread) {

        this.width = width;
        this.height = height;
        m_Size = size;
        m_Spread = spread;
        this.texture = texture;
        m_Color = color;

        this.progress = progress;

        //m_Sprite = new Renderable2D(width, height, color, texture);
        setBar();
    }

    public void setBar() {

        SpriteComponent component = new SpriteComponent(m_Color, texture, width,height);
        m_Component = component;
    }

    public void setTexture(Texture texture) {
        m_Sprite.setTexture(texture);
    }

    public void setColor(Vec4 newColor) {
        m_Sprite.setColor(newColor);
    }

    public void submit(Renderer2D renderer2d, TransformComponent transform)
    {
        Vec3 tmpPos = transform.getPosition();
        Vec3 tmpRot = transform.getRotationAngles();
        Vec3 tmpScale = transform.getScale();

        //transform.setPosition(new Vec3(40.0f,30.0f,0.0f));

        //transform.move(new Vec3(-m_Size*m_Spread  + (m_Size*m_Spread / 2.0f), 0.0f, 0.0f));

        if (toChange) {
            //transform.move(new Vec3(-m_Size*m_Spread  + (m_Size*m_Spread / 2.0f), 0.0f, 0.0f));
        }

        m_Component.submit(renderer2d, transform.getTransformationMatrix());
        //later change to TransformComponent.moveLocal()
        //transform.move(new Vec3(m_Size*m_Spread, 30.0f, 0));
        //reset the trasnform
        transform.setPosition(tmpPos);
        transform.setRotationAngles(tmpRot);
        transform.setScale(tmpScale);

    }

    public float getHeight(){
        return m_Component.getHeight();
    }

    public float getWidth(){
        return m_Component.getWidth();

    }

    public void setUVs(Vec2[] UVs)
    {
        m_Sprite.setUVs(UVs);
    }

    public void moveBar() {
        //transform.move(new Vec3(-m_Size*m_Spread  + (m_Size*m_Spread / 2.0f), 0.0f, 0.0f));
    }

    public void setWidth(float width)
    {
        m_Component.setWidth(width);
    }

    public void setSpread(float spread)
    {
        m_Spread = spread;
    }

    public void setHeight(float height)
    {
        m_Component.setHeight(height);
    }

    public void setChange() {
        toChange = true;
    }
}
