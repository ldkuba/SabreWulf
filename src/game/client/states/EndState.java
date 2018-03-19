package game.client.states;

import org.lwjgl.glfw.GLFW;

import game.client.Main;

import engine.maths.Vec3;
import engine.maths.MathUtil;
import engine.state.AbstractState;
import engine.gui.components.Button;
import engine.gui.components.Sprite;
import engine.gui.components.ToggleButton;
import engine.application.Application;
import engine.graphics.texture.Texture;

public class EndState extends AbstractState {
	private Main app;
	
	private Sprite endBackground;
	private Button exitButton;
	private Button replayButton;
	private ToggleButton muteButton;

	public EndState(Main app) {
		super(app);
		this.app = app;
	}

	@Override
	public void keyAction(int key, int action) {

	}

	@Override
	public void mouseAction(int button, int action) {

	}

	@Override
	public void deactivate() {

	}

	@Override
	public void init() {
		if(!app.getSoundManager().getIsMuted()){
			app.getSoundManager().invokeSound("background/end", true, true);
		}
		//to change
		super.init();
		Texture endBackgroundTexture = app.getAssetManager().getTexture("res/textures/gui/backgrounds/end_background.png");
		endBackground = new Sprite(0, 0, 100.0f, 100.0f, endBackgroundTexture);
		app.getGui().add(endBackground);
		
		Texture replayButtonReleasedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/replay_button_released.png");
		//change
		Texture replayButtonPressedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/replay_button_pressed.png");
		replayButton = new Button(45.0f, 90.0f, 10.0f, 6.0f, replayButtonPressedTexture, replayButtonReleasedTexture) {

			@Override
			public void onClick() {
				if(!app.getSoundManager().getIsMuted()){
					app.getSoundManager().invokeSound("click", false, true);
					app.getSoundManager().stopSoundSource("background/end");
				}
				app.getStateManager().setCurrentState(Main.menuState);
			}
		};
		app.getGui().add(replayButton);
		
		//mute button
		String muteReleasedPath;
		String mutePressedPath;
		if(app.getSoundManager().getIsMuted()){
			muteReleasedPath = "res/textures/gui/buttons/mute_button_pressed.png";
			mutePressedPath = "res/textures/gui/buttons/mute_button_released.png";
		} else {
			muteReleasedPath = "res/textures/gui/buttons/mute_button_released.png";
			mutePressedPath = "res/textures/gui/buttons/mute_button_pressed.png";
		}
		
		Texture muteButtonReleasedTexture = app.getAssetManager()
				.getTexture(muteReleasedPath);
		Texture muteButtonPressedTexture = app.getAssetManager()
				.getTexture(mutePressedPath);
		muteButton = new ToggleButton(90.0f, 93.0f, 4.0f, 6.0f, muteButtonPressedTexture, muteButtonReleasedTexture) {
			@Override
			public void onClick(boolean toggled) {
				if(toggled && !app.getSoundManager().getIsMuted()) {
					app.getSoundManager().invokeSound("click", false, true);
					app.getSoundManager().stopSoundSource("background/menu");
					app.getSoundManager().muteSounds();
				} else {
					app.getSoundManager().invokeSound("click", false, true);
					app.getSoundManager().unmuteSounds();
					app.getSoundManager().playSoundSource("background/menu");
				}
			}
		};
		app.getGui().add(muteButton);
		
		//exit button
		Texture exitButtonReleasedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/exit_button_released.png");
		Texture exitButtonPressedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/exit_button_pressed.png");
		exitButton = new Button(95.0f, 93.0f, 4.0f, 6.0f, exitButtonPressedTexture, exitButtonReleasedTexture) {
			@Override
			public void onClick() {
				if(!app.getSoundManager().getIsMuted()){
					app.getSoundManager().invokeSound("quit", false, true);
				}
				app.exit();
				app.cleanup();
				app.getClient().stop();
			}
		};
		app.getGui().add(exitButton);
		
		float aspectRatio = Application.s_WindowSize.getX() / Application.s_WindowSize.getY();
		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void update() {
		super.update();
		
	}
}
