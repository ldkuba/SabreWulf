package game.client.states;

import engine.application.Application;
import engine.graphics.texture.Texture;
import engine.gui.components.Button;
import engine.gui.components.Sprite;
import engine.gui.components.TextField;
import engine.gui.components.ToggleButton;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.net.common_net.networking_messages.LobbyConnectionRequestMessage;
import engine.state.AbstractState;
import game.client.Main;

public class MenuState extends AbstractState {
	private Main app;

	private Sprite menuBackground;
	private Button playButton;
	private ToggleButton muteButton;
	private Button settingsButton;
	private Button exitButton;
	private TextField playerNameField;

	public MenuState(Main app) {
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
	public void init() {
		super.init();
		
		if(!app.getSoundManager().getIsMuted()){
			app.getSoundManager().invokeSound("background/menu", true, true);
			app.getSoundManager().getSoundSource("background/menu").setGain(0.8f);
		}
		Texture menuBackgroundTexture = app.getAssetManager().getTexture("res/textures/gui/backgrounds/mainmenu_background.png");
		menuBackground = new Sprite(0, 0, 100.0f, 100.0f, menuBackgroundTexture);
		app.getGui().add(menuBackground);

		Texture playButtonReleasedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/play_button_released.png");
		Texture playButtonPressedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/play_button_pressed.png");
		playButton = new Button(45.0f, 90.0f, 10.0f, 6.0f, playButtonPressedTexture, playButtonReleasedTexture) {

			@Override
			public void onClick() {
				if(!app.getSoundManager().getIsMuted()){
					app.getSoundManager().invokeSound("click", false, true);
				}
				LobbyConnectionRequestMessage cnm = new LobbyConnectionRequestMessage();
				cnm.setName(playerNameField.getText());
				Main.clientName = playerNameField.getText();
				app.getClient().sendTCP(cnm);
				if(!app.getSoundManager().getIsMuted()){
					app.getSoundManager().stopSoundSource("background/menu");
				}
			}
		};
		app.getGui().add(playButton);

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
		muteButton = new ToggleButton(85.0f, 93.0f, 4.0f, 6.0f, muteButtonPressedTexture, muteButtonReleasedTexture) {
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
	
		Texture settingButtonReleasedTexture = app.getAssetManager()
				.getTexture("res/textures/gui/buttons/settings_button_released.png");
		Texture settingButtonPressedTexture = app.getAssetManager()
				.getTexture("res/textures/gui/buttons/settings_button_pressed.png");
		settingsButton = new Button(90.0f, 93.0f, 4.0f, 6.0f, settingButtonPressedTexture,
				settingButtonReleasedTexture) {
			@Override
			public void onClick() {
				if(!app.getSoundManager().getIsMuted()){
					app.getSoundManager().invokeSound("click", false, true);
				}
			}
		};
		app.getGui().add(settingsButton);

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
		
		playerNameField = new TextField(38.0f, 10.0f, app.getAssetManager().getFont("fontSprite.png"), 4.0f, 0.6f, 20, new Vec4(1.0f, 0.0f, 0.0f, 1.0f));
		app.getGui().add(playerNameField);

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

	@Override
	public void deactivate() {
	}
}
