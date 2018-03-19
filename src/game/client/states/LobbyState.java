package game.client.states;

import java.util.ArrayList;

import engine.application.Application;
import engine.graphics.texture.Texture;
import engine.gui.components.Button;
import engine.gui.components.Label;
import engine.gui.components.SelectList;
import engine.gui.components.Sprite;
import engine.gui.components.ToggleButton;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.net.common_net.networking_messages.LobbyQuitMessage;
import engine.net.common_net.networking_messages.LockInMessage;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.client.Main;

public class LobbyState extends AbstractState
{
	private Main app;
	
	private Sprite lobbyBackground;
	private SelectList characterChoice;
	private Button lockInButton;
	private Button quitButton;
	private ToggleButton muteButton;
	private ArrayList<Texture> characterAvatars;
	private ArrayList<Sprite> playerAvatars;
	
	private ArrayList<Label> playerNames;
	
	private int localPlayerIndex;

	public LobbyState(Main app)
	{
		super(app);
		this.app = app;
		
		localPlayerIndex = 0;
	}

	@Override
	public void keyAction(int key, int action)
	{

	}

	@Override
	public void mouseAction(int button, int action)
	{

	}

	@Override
	public void init()
	{
		super.init();
		// set up background sound
		app.getSoundManager().invokeSound("background/lobby", true, true);
		playerAvatars = new ArrayList<>();
		playerNames = new ArrayList<>();
		characterAvatars = new ArrayList<>();
		Texture lobbyBackgroundTexture = app.getAssetManager().getTexture("res/textures/gui/backgrounds/lobby_background.png");
		lobbyBackground = new Sprite(0, 0, 100.0f, 100.0f, lobbyBackgroundTexture);
		app.getGui().add(lobbyBackground);
		
		//currently 3 characters
		Texture char1Avatar = app.getAssetManager().getTexture("res/actors/link/textures/link_avatar.png");
		Texture char2Avatar = app.getAssetManager().getTexture("res/actors/wolf/textures/wolf_avatar.png");
		Texture char3Avatar = app.getAssetManager().getTexture("res/actors/ghost/textures/ghost_avatar.png");
		
		characterAvatars.add(char1Avatar);
		characterAvatars.add(char2Avatar);
		characterAvatars.add(char3Avatar);
		
		Sprite character1Avatar = new Sprite(32.0f, 16.8f, 10.5f, 18.2f, char1Avatar);
		app.getGui().add(character1Avatar);
		Sprite character2Avatar = new Sprite(44.8f, 16.8f, 10.5f, 18.2f, char2Avatar);
		app.getGui().add(character2Avatar);
		Sprite character3Avatar = new Sprite(57.5f, 16.8f, 10.5f, 18.2f, char3Avatar);
		app.getGui().add(character3Avatar);
		
		characterChoice = new SelectList(app.getGui());
		
		Texture charDeselected = app.getAssetManager().getTexture("res/textures/avatars/character_deselected.png");
		Texture charSelected = app.getAssetManager().getTexture("res/textures/avatars/character_selected.png");
		characterChoice.addButton(32.0f, 16.8f, 10.5f, 18.2f, charSelected, charDeselected);
		characterChoice.addButton(44.8f, 16.8f, 10.5f, 18.2f, charSelected, charDeselected);
		characterChoice.addButton(57.5f, 16.8f, 10.5f, 18.2f, charSelected, charDeselected);
		
		Texture lockInPressedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/lock_button_pressed.png");
		Texture lockInReleasedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/lock_button_released.png");
		lockInButton = new Button(50.0f, 60.0f, 10.0f, 7.0f, lockInPressedTexture, lockInReleasedTexture)
		{
			@Override
			public void onClick()
			{
				if(characterChoice.getSelectedId() != -1)
				{
					//Lock in champ and notify server that player is ready
					if(!app.getSoundManager().getIsMuted()){
						app.getSoundManager().invokeSound("lockIn", false, true); 
					}
					LockInMessage lim = new LockInMessage();
					lim.setCharacterSelected(characterChoice.getSelectedId());
					lockInButton.setEnabled(false);
					characterChoice.setEnabled(false);
					app.getClient().sendTCP(lim);
				}
			}
		};
		app.getGui().add(lockInButton);
		
		Texture quitPressedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/quit_button_pressed.png");
		Texture quitReleasedTexture = app.getAssetManager().getTexture("res/textures/gui/buttons/quit_button_released.png");
		quitButton = new Button(32.0f, 60.0f, 10.0f, 7.0f, quitPressedTexture, quitReleasedTexture)
		{
			@Override
			public void onClick()
			{
				if(!app.getSoundManager().getIsMuted()){
					app.getSoundManager().invokeSound("click", false, true);
					app.getSoundManager().stopSoundSource("background/lobby");
				}
				app.getStateManager().setCurrentState(Main.menuState);
				LobbyQuitMessage quit = new LobbyQuitMessage();
				app.getClient().sendTCP(quit);
			}
		};
		app.getGui().add(quitButton);
		
		for(int i = 0; i < 6; i++)
		{
			Texture transparent = app.getAssetManager().getTexture("res/textures/transparent.png");
			
			if(i < 3)
			{
				Sprite sprite = new Sprite(3.0f, 17.0f + (i*24.5f), 9.5f, 17.0f, transparent);
				playerAvatars.add(sprite);
				app.getGui().add(sprite);
				
				Label label = new Label(3.0f, 35.0f + (i*24.5f), app.getAssetManager().getFont("fontSprite.png"), 3.0f, 0.7f, new Vec4(1.0f, 1.0f, 1.0f, 1.0f));
				playerNames.add(label);
				app.getGui().add(label);
			}else
			{
				Sprite sprite = new Sprite(87.5f, 17.0f + ((i%3)*24.5f), 9.5f, 17.0f, transparent);
				playerAvatars.add(sprite);
				app.getGui().add(sprite);
				
				Label label = new Label(87.5f, 35.0f + ((i%3)*24.5f), app.getAssetManager().getFont("fontSprite.png"), 3.0f, 0.7f, new Vec4(1.0f, 1.0f, 1.0f, 1.0f));
				playerNames.add(label);
				app.getGui().add(label);
			}	
		}
		
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
		muteButton = new ToggleButton(95.0f, 93.0f, 4.0f, 6.0f, muteButtonPressedTexture, muteButtonReleasedTexture) {
			@Override
			public void onClick(boolean toggled) {
				if(toggled && !app.getSoundManager().getIsMuted()) {
					app.getSoundManager().invokeSound("click", false, true);
					app.getSoundManager().stopSoundSource("background/lobby");
					app.getSoundManager().muteSounds();
				} else {
					app.getSoundManager().invokeSound("click", false, true);
					app.getSoundManager().unmuteSounds();
					app.getSoundManager().playSoundSource("background/lobby");
				}
			}
		};
		app.getGui().add(muteButton);
		
		float aspectRatio = Application.s_WindowSize.getX()/Application.s_WindowSize.getY();
		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void update()
	{
		super.update();
	}

	//Set from message listener when a accept message from a lobby is received
	public void setLocalPlayerIndex(int id)
	{
		this.localPlayerIndex = id;
	}
	
	//Called from message listener when a someone locks in and message is received
	public void updatePlayer(int slot, int selection, String name)
	{
		playerNames.get(slot).setText(name);
		
		if(selection!=-1) {
			playerAvatars.get(slot).getEntity().getSprite().setTexture(characterAvatars.get(selection));
		}
		else{
			Texture transparent = app.getAssetManager().getTexture("res/textures/transparent.png");
			playerAvatars.get(slot).getEntity().getSprite().setTexture(transparent);
		}
	}
	
	@Override
	public void deactivate()
	{

	}
}
