package game.server.ingame;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.entity.Entity;
import engine.entity.component.NetTransformComponent;
import engine.maths.Vec3;
import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.*;
import engine.net.server.core.GameInstance;
import engine.net.server.core.NetPlayer;
import game.common.abilities.basic.GhostBaseAttack;
import game.common.abilities.basic.LinkBaseAttack;
import game.common.abilities.basic.WolfBaseAttack;
import game.common.actors.Actor;
import game.common.actors.Player;
import game.common.classes.classes.GhostClass;
import game.common.classes.classes.LinkClass;
import game.common.classes.classes.WolfClass;
import game.common.logic.actions.Action;
import game.common.logic.actions.AttackAction;

public class ServerMessageListener implements MessageListener
{
	private boolean debug = true;

	private class MessageEvent
	{
		public AbstractMessage msg;
		public NetPlayer player;
		
		public MessageEvent(AbstractMessage msg, NetPlayer player)
		{
			this.msg = msg;
			this.player = player;
		}
	};
		
	private ServerMain app;
	private BlockingQueue<MessageEvent> abstractMessageInbound;

	private final int maxTraffic = 100;

	public ServerMessageListener(ServerMain app)
	{
		this.app = app;
		abstractMessageInbound = new LinkedBlockingQueue<>();
	}

	@Override
	public void addMessage(AbstractMessage message)
	{
	}

	public void handleMessageQueue()
	{
		int count = 0;
		while (count < maxTraffic && !abstractMessageInbound.isEmpty())
		{
			try
			{
				MessageEvent msgEvent = abstractMessageInbound.take();
				receiveMessage(msgEvent.msg, msgEvent.player);
			}catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			count++;
		}
	}

	@Override
	public void receiveMessage(AbstractMessage msg)
	{

	}

	@Override
	public void receiveMessage(AbstractMessage msg, NetPlayer player)
	{
		if(msg instanceof PeerCountMessage)
		{
			String soundName = "message_beep";
			PeerCountMessage pcm = (PeerCountMessage) msg;
			System.out.println("Number of players online: " + pcm.getNoPlayers());
			app.getSoundManager().invokeSound(soundName, false, true);
			PeerCountMessage plm = (PeerCountMessage) msg;
			//System.out.println(plm.getNoPlayers());
			app.getSoundManager().pauseSoundSource(soundName);
		}else if(msg instanceof DesiredLocationMessage)
		{
			DesiredLocationMessage dlm = (DesiredLocationMessage) msg;
			
			ArrayList<Entity> clickedEntities = ServerMain.gameState.getScene().pickEntities(dlm.getPos());
			
			Entity target = null;
			
			for(Entity e : clickedEntities)
			{
				if(e.hasTag("Targetable"))
				{
					Actor targetActor = ServerMain.gameState.getActorManager().getActor(e);
					Actor sourceActor = ServerMain.gameState.getActorManager().getActor(player.getPlayerId());
					
					if(targetActor != null)
					{
						if(targetActor.getTeam() != sourceActor.getTeam())
						{
							target = e;

							Action attackAction = null;

							if(sourceActor.getRole() instanceof LinkClass) {
								attackAction = new LinkBaseAttack(targetActor.getActorId(), sourceActor.getActorId());
							} else if(sourceActor.getRole() instanceof WolfClass) {
								attackAction = new WolfBaseAttack(targetActor.getActorId(), sourceActor.getActorId());
							} else if(sourceActor.getRole() instanceof GhostClass) {
								attackAction = new GhostBaseAttack(targetActor.getActorId(), sourceActor.getActorId());
							}
							
							if(attackAction == null) {
								System.out.println("Actor does not have basic attack");
							}

							if(sourceActor.getBaseAttack().getCooldown() == 0) {
								if (attackAction.verify(ServerMain.gameState.getActorManager())) {
									ExecuteActionMessage eam = new ExecuteActionMessage();
									eam.setAction(attackAction);
									app.getNetworkManager().broadcast(eam);

									attackAction.executeServer(ServerMain.gameState.getActorManager(), app);

								} else {
									target = null;
								}
							}
							
							break;
						}
					}	
				}
			}
			
			if(target == null)
			{
				ServerMain.gameState.getActorManager().getActor(player.getPlayerId()).calculatePath(dlm.getPos(), ServerMain.gameState.getMap().getNavmesh());
			}
			
			System.out.println("Received path message in game");
			
		}else if(msg instanceof ChatMessage){
			broadcastMessage(msg, app.getNetworkManager().getNetPlayers());

		}else if(msg instanceof RequestAbilityMessage) {

		    Action ability = ((RequestAbilityMessage) msg).getAbility();
		    Vec3 desiredTarget = ((RequestAbilityMessage) msg).getTargetLocation();

            ArrayList<Entity> clickedEntities = ServerMain.gameState.getScene().pickEntities(desiredTarget);

            Entity target = null;

            for(Entity e : clickedEntities)
            {
                if(e.hasTag("Targetable"))
                {
                    Actor targetActor = ServerMain.gameState.getActorManager().getActor(e);
                    Actor sourceActor = ServerMain.gameState.getActorManager().getActor(player.getPlayerId());

                    if(targetActor != null)
                    {
                        if(targetActor.getTeam() != sourceActor.getTeam())
                        {
                            target = e;

                            if(ability == null) {
                                System.out.println("Actor does not have basic attack");
                            }

                            if(ability.getCooldown() == 0) {
                                if (ability.verify(ServerMain.gameState.getActorManager())) {
                                    ExecuteAbilityMessage eam = new ExecuteAbilityMessage();
                                    eam.setAbility(ability);
                                    app.getNetworkManager().broadcast(eam);

                                    ability.executeServer(ServerMain.gameState.getActorManager(), app);

                                } else {
                                    target = null;
                                }
                            }

                            break;
                        }
                    }
                }
            }

		}
	}

	@Override
	public void addMessage(AbstractMessage message, NetPlayer player)
	{
		abstractMessageInbound.add(new MessageEvent(message, player));
	}

	public void broadcastMessage(AbstractMessage msg, ArrayList<NetPlayer> players){
		for(int i=0; i<players.size(); i++){
			abstractMessageInbound.add(new MessageEvent(msg, players.get(i)));
		}
	}
}
