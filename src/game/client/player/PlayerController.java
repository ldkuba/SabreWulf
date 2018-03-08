package game.client.player;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec4;
import engine.net.common_net.networking_messages.AttackPlayerMessage;
import game.common.actors.Player;
import game.common.player.PlayerManager;
import org.lwjgl.glfw.GLFW;

import engine.input.InputManager;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.net.common_net.networking_messages.DesiredLocationMessage;
import engine.scene.Scene;
import game.client.Main;
import game.client.states.GameState;

import static engine.maths.Vec3.inRadius;

//For handling actual instruction sets to be passed to transformation and interaction components of the players
public class PlayerController {

	private boolean debug = true;
	
	private InputManager inputManager;
	private Main main;
	private GameState gamestate;
	private Scene scene;

	private PlayerManager playerManager;
	private Application app;

	public PlayerController(Main main, GameState gs, Scene scene, PlayerManager playMan) {
		gamestate = gs;
		this.main = main;
		inputManager = main.getInputManager();
		this.scene = scene;
		playerManager = playMan;

	}
	
	public void update()
	{
		//input
				
	}
	
	public void onKeyPress(int key, int action)
	{
		
	}
	
	public void mouseAction(int button, int action)
	{
		if(button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS)
		{
			DesiredLocationMessage msg = new DesiredLocationMessage();



			Vec3 worldPos = scene.getCamera().getWorldCoordinates((float)main.getInputManager().getMouseX(), (float)main.getInputManager().getMouseY());

			NetTransformComponent playerTrans = (NetTransformComponent) playerManager.getPlayer(0).getEntity().getComponent(NetTransformComponent.class);

			if (debug) {

				System.out.println("Player x: " + playerTrans.getPosition().getX() + " :: " + "Player y: " + playerTrans.getPosition().getY());
				//System.out.println("Dummy x: " + dummyTrans.getPosition().getX() + " :: " + "Dummy y: " + dummyTrans.getPosition().getY());

				System.out.println("Desired Location: " + worldPos.getX() + " : " + worldPos.getY());

			}

			Player myPlayerTest = playerManager.getPlayer(0);

			NetTransformComponent dummyTrans = null;
			int attackedPlayerID = -1;

			//Player located in worldPos
			for(int i = 0; i < playerManager.getPlayers().size(); i++) {
				dummyTrans = (NetTransformComponent) playerManager.getPlayer(i).getEntity().getComponent(NetTransformComponent.class);

				if(inRadius(worldPos, dummyTrans.getPosition())) {

					if (debug) {
						System.out.println("\n------Click Action-----");
						System.out.println("Clicked Player");
						System.out.println(i);
						System.out.println("------End of Click Action--------");
					}
					attackedPlayerID = i;
					break;
				}

				dummyTrans = null;

			}

			if(dummyTrans != null) {
				//In range to attack.
				if(playerManager.getPlayer(0).getLogic().inRange(playerTrans.getPosition(), dummyTrans.getPosition(), myPlayerTest.getAttackRange())) {
					if (debug) {
						System.out.println("------Attack Action-----");
						System.out.println("Player attack ID: " + attackedPlayerID);
						//System.out.println("Dealt: " + myPlayerTest.getDamage());
						System.out.println("------End of Attack Action----");
					}

					//Prepare attack message
					AttackPlayerMessage attmsg = new AttackPlayerMessage();

					/*
					Server handles the damage.
					 */
					//attmsg.setDamage(5);

					if(attackedPlayerID != -1) {
						attmsg.setPlayerID(attackedPlayerID);
					}



					main.getClient().sendTCP(attmsg);

				} else {
					msg.setPos(worldPos);

					main.getClient().sendTCP(msg);
				}

			}
			//No entity in the position.
			else {

				msg.setPos(worldPos);

				main.getClient().sendTCP(msg);
			}

		}
	}
}
