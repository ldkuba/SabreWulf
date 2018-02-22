package game.client.states;

import engine.application.Application;
import engine.graphics.texture.Texture;
import engine.gui.components.Button;
import engine.gui.components.Sprite;
import engine.gui.components.TextField;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.net.common_net.networking_messages.LobbyConnectionRequestMessage;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.client.Main;

public class MenuState extends AbstractState {
	private Main app;
	private Scene scene;

	private Sprite menuBackground;
	private Button playButton;
	private Button settingsButton;
	private Button exitButton;
	private TextField playerNameField;

	public MenuState(Main app) {
		this.app = app;
		scene = new Scene(0);
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
		app.getSoundManager().invokeSound("background/menu", true);
		Texture menuBackgroundTexture = app.getAssetManager().getTexture("res/textures/mainmenu_background.png");
		menuBackground = new Sprite(0, 0, 100.0f, 100.0f, menuBackgroundTexture);
		app.getGui().add(menuBackground);

		Texture playButtonReleasedTexture = app.getAssetManager().getTexture("res/textures/play_button_released.png");
		Texture playButtonPressedTexture = app.getAssetManager().getTexture("res/textures/play_button_pressed.png");
		playButton = new Button(45.0f, 90.0f, 10.0f, 6.0f, playButtonPressedTexture, playButtonReleasedTexture) {

			@Override
			public void onClick() {
				LobbyConnectionRequestMessage cnm = new LobbyConnectionRequestMessage();
				cnm.setName(playerNameField.getText());
				app.getClient().sendTCP(cnm);
				app.getSoundManager().stopSoundSource("background/menu");
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
				app.getSoundManager().invokeSound("quit", false);
				app.exit();
				app.cleanup();
				app.getClient().stop();
			}
		};
		app.getGui().add(exitButton);
		
		playerNameField = new TextField(38.0f, 10.0f, app.getAssetManager().getFont("fontSprite.png"), 4.0f, 0.6f, 20, new Vec4(1.0f, 0.0f, 0.0f, 1.0f));
		app.getGui().add(playerNameField);

		float aspectRatio = Application.s_WindowSize.getX() / Application.s_WindowSize.getY();
		scene.getCamera().setProjectionMatrix(
				MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));

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
	}
	// TODO: Fill in state methods to make them functional
}
