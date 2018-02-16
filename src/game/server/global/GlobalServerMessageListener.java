package game.server.global;

import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.*;
import engine.net.server.core.GameInstance;
import engine.net.server.core.Player;
import game.server.GameServer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobalServerMessageListener implements MessageListener {

    private CopyOnWriteArrayList<MessageData> messageQueue;
    private GameServer gameServer;
    private GameInstance gameInstance;
    private class MessageData{
        public AbstractMessage message;
        public Player player;

        public MessageData(AbstractMessage message, Player player){
            this.message = message;
            this.player = player;
        }
    }

    public GlobalServerMessageListener(GameServer server){
        messageQueue = new CopyOnWriteArrayList<>();
        this.gameServer = server;
    }

    @Override
    public void receiveMessage(AbstractMessage msg, Player source) {
        // Handling Play button action here
        if(msg instanceof LobbyConnectionRequestMessage) {
            LobbyConnectionRequestMessage m = (LobbyConnectionRequestMessage) msg;
            if (gameServer.isFreeGameInstance()) {
                gameInstance = gameServer.getFreeGameInstance();
                source.setName(m.getName());
                gameInstance.addPlayer(source);

                LobbyConnectionResponseMessage lobbyConn = new LobbyConnectionResponseMessage();
                lobbyConn.setAccepted(true);
                lobbyConn.setMessage("Welcome to the gameServer");
                gameServer.sendTCP(lobbyConn, source);

                LobbyUpdateMessage lobbyUpd = new LobbyUpdateMessage();
                lobbyUpd.setPlayersInLobby(gameInstance.getPlayerPayload());
                gameServer.broadcastTCP(lobbyUpd);
            }
            else{
                LobbyConnectionResponseMessage lobbyConn = new LobbyConnectionResponseMessage();
                lobbyConn.setAccepted(false);
                lobbyConn.setMessage("GameServer is full");
                gameServer.sendTCP(lobbyConn, source);
            }
        } else if(msg instanceof LockInMessage){
            LockInMessage lim = (LockInMessage) msg;
            source.setReady(true);
            source.setChar(lim.getCharacterSelected());

        } else if(msg instanceof LobbyQuitMessage){

        }

    }

    @Override
    public void receiveMessage(AbstractMessage msg) {

    }

    @Override
    public void addMessage(AbstractMessage message, Player player) {
        messageQueue.add(new MessageData(message, player));
    }

    @Override
    public void addMessage(AbstractMessage message){

    }

    @Override
    public void handleMessageQueue() {
        for(MessageData msg : messageQueue){
            receiveMessage(msg.message, msg.player);
        }
        messageQueue.clear();
    }
}