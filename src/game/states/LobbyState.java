package game.states;

import java.util.ArrayList;

import engine.application.Application;
import engine.graphics.texture.Texture;
import engine.gui.components.Button;
import engine.gui.components.SelectList;
import engine.gui.components.Sprite;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.server.core.LobbyQuitMessage;
import engine.state.AbstractState;
import game.Main;
import game.networking.LockInMessage;

public class LobbyState extends AbstractState
{
	private Main app;
	private Scene scene;
	
	private Sprite lobbyBackground;
	private SelectList characterChoice;
	private Button lockInButton;
	private Button quitButton;
	private ArrayList<Texture> characterAvatars;
	private ArrayList<Sprite> playerAvatars;
	
	private int localPlayerIndex;

	public LobbyState(Main app)
	{
		this.app = app;
		scene = new Scene(0);
		playerAvatars = new ArrayList<>();
		characterAvatars = new ArrayList<>();
		
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
		scene.init();
		app.getGui().init(scene);
		
		Texture lobbyBackgroundTexture = app.getAssetManager().getTexture("res/textures/lobby_background.png");
		lobbyBackground = new Sprite(0, 0, 100.0f, 100.0f, lobbyBackgroundTexture);
		app.getGui().add(lobbyBackground);
		
		//currently 3 characters
		Texture char1Avatar = app.getAssetManager().getTexture("res/textures/avatars/dps_range_mage_clr.png");
		Texture char2Avatar = app.getAssetManager().getTexture("res/textures/avatars/dps_melee_clr.png");
		Texture char3Avatar = app.getAssetManager().getTexture("res/textures/avatars/dps_range_clr.png");
		
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
		
		Texture lockInPressedTexture = app.getAssetManager().getTexture("res/textures/lock_button_pressed.png");
		Texture lockInReleasedTexture = app.getAssetManager().getTexture("res/textures/lock_button_released.png");
		lockInButton = new Button(50.0f, 60.0f, 10.0f, 7.0f, lockInPressedTexture, lockInReleasedTexture)
		{
			@Override
			public void onClick()
			{
				if(characterChoice.getSelectedId() != -1)
				{
					//Lock in champ and notify server that player is ready
					LockInMessage lim = new LockInMessage();
					lim.setCharacterSelected(characterChoice.getSelectedId());
					lockInButton.setEnabled(false);
					characterChoice.setEnabled(false);
					app.getClient().sendTCP(lim);
				}
			}
		};
		app.getGui().add(lockInButton);
		
		Texture quitPressedTexture = app.getAssetManager().getTexture("res/textures/quit_button_pressed.png");
		Texture quitReleasedTexture = app.getAssetManager().getTexture("res/textures/quit_button_released.png");
		quitButton = new Button(32.0f, 60.0f, 10.0f, 7.0f, quitPressedTexture, quitReleasedTexture)
		{
			@Override
			public void onClick()
			{
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
			}else
			{
				Sprite sprite = new Sprite(87.5f, 17.0f + ((i%3)*24.5f), 9.5f, 17.0f, transparent);
				playerAvatars.add(sprite);
				app.getGui().add(sprite);
			}	
		}
		
		float aspectRatio = Application.s_WindowSize.getX()/Application.s_WindowSize.getY();
		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
	}

	@Override
	public void render()
	{
		scene.render();
	}

	@Override
	public void update()
	{
		scene.update();
	}

	//Set from message listener when a accept message from a lobby is received
	public void setLocalPlayerIndex(int id)
	{
		this.localPlayerIndex = id;
	}
	
	//Called from message listener when a someone locks in and message is received
	public void updatePlayer(int slot, int selection)
	{
		playerAvatars.get(slot).getEntity().getSprite().setTexture(characterAvatars.get(selection));
	}
	
	@Override
	public void deactivate()
	{

	}
	// TODO: Fill in state methods to make them functional
}
