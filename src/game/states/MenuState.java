package game.states;

import org.lwjgl.openal.AL11;

import engine.application.Application;
import engine.graphics.texture.Texture;
import engine.gui.components.Button;
import engine.gui.components.Sprite;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.state.AbstractState;
import engine.sound.*;
import game.Main;
import game.networking.LobbyConnectionMessage;

public class MenuState extends AbstractState {
	private Main app;
	private Scene scene;

	private Sprite menuBackground;
	private Button playButton;
	private Button settingsButton;
	private Button exitButton;

	//
	private final SoundManager soundMgr;

	public MenuState(Main app) {
		this.app = app;
		scene = new Scene(0);
		soundMgr = new SoundManager();
	}

	@Override
	public void keyAction(int key, int action) {

	}

	@Override
	public void mouseAction(int button, int action) {

	}

	@Override
	public void init() {
		scene.init();
		app.getGui().init(scene);

		//
		soundMgr.init();

		Texture menuBackgroundTexture = app.getAssetManager().getTexture("res/textures/mainmenu_background.png");
		menuBackground = new Sprite(0, 0, 100.0f, 100.0f, menuBackgroundTexture);
		app.getGui().add(menuBackground);

		Texture playButtonReleasedTexture = app.getAssetManager().getTexture("res/textures/play_button_released.png");
		Texture playButtonPressedTexture = app.getAssetManager().getTexture("res/textures/play_button_pressed.png");
		playButton = new Button(45.0f, 90.0f, 10.0f, 6.0f, playButtonPressedTexture, playButtonReleasedTexture) {
			
			@Override
			public void onClick()
			{
				LobbyConnectionMessage cnm = new LobbyConnectionMessage();
				cnm.setName("bob");
				app.getClient().sendTCP(cnm);
			}
		};
		app.getGui().add(playButton);

		Texture settingButtonReleasedTexture = app.getAssetManager()
				.getTexture("res/textures/settings_button_released.png");
		Texture settingButtonPressedTexture = app.getAssetManager()
				.getTexture("res/textures/settings_button_pressed.png");
		settingsButton = new Button(90.0f, 93.0f, 4.0f, 6.0f, settingButtonPressedTexture,
				settingButtonReleasedTexture) {
			@Override
			public void onClick() {

			}
		};
		app.getGui().add(settingsButton);

		Texture exitButtonReleasedTexture = app.getAssetManager().getTexture("res/textures/exit_button_released.png");
		Texture exitButtonPressedTexture = app.getAssetManager().getTexture("res/textures/exit_button_pressed.png");
		exitButton = new Button(95.0f, 93.0f, 4.0f, 6.0f, exitButtonPressedTexture, exitButtonReleasedTexture) {
			@Override
			public void onClick() {
				app.exit();
				app.getClient().stop();
			}
		};
		app.getGui().add(exitButton);

		float aspectRatio = Application.s_WindowSize.getX() / Application.s_WindowSize.getY();
		scene.getCamera().setProjectionMatrix(
				MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));

		// sounds

		this.soundMgr.init();
		this.soundMgr.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
		setupSounds();
		// TODO Auto-generated catch block
	}

	//
	private void setupSounds() {
		SoundBuffer buffFire = new SoundBuffer("res/sounds/wolf.ogg");
		soundMgr.addSoundBuffer(buffFire);
		SoundSource sourceFire = new SoundSource(true, false);
		sourceFire.setBuffer(buffFire.getBufferId());
		soundMgr.addSoundSource("wolf", sourceFire);
		sourceFire.play();
		soundMgr.setListener(new SoundListener());
	}

	@Override
	public void render() {
		scene.render();
	}

	@Override
	public void update() {
		scene.update();
	}

	@Override
	public void deactivate() {
		soundMgr.clean();

	}
	// TODO: Fill in state methods to make them functional
}
