package game.server.global;

import engine.net.common_net.networking_messages.*;
import engine.net.server.core.GameInstance;
import engine.net.server.core.NetPlayer;
import game.server.GameServer;

public class GlobalServerMessageListener {

    private GameServer gameServer;
    private GameInstance gameInstance;
    public GlobalServerMessageListener(GameServer gameServer){
        this.gameServer = gameServer;
    }

    public void receiveMessage(AbstractMessage msg, NetPlayer source) {

        // Handling Play button action here
        if(msg instanceof LobbyConnectionRequestMessage) {
            LobbyConnectionRequestMessage m = (LobbyConnectionRequestMessage) msg;
            if (gameServer.isFreeGameInstance()) {

                gameInstance = gameServer.getFreeGameInstance();

                if(gameInstance==null){
                    gameInstance = gameServer.createGameInstance();
                }
                source.setName(m.getName());
                gameInstance.addPlayer(source);

                LobbyConnectionResponseMessage lobbyConn = new LobbyConnectionResponseMessage();
                lobbyConn.setAccepted(true);
                lobbyConn.setMessage("Welcome to the gameServer");
                gameServer.sendTCP(lobbyConn, source);

                LobbyUpdateMessage lobbyUpd = new LobbyUpdateMessage();
                lobbyUpd.setPlayersInLobby(gameInstance.getPlayerPayload());
                gameServer.broadcastTCP(lobbyUpd, gameInstance.getPlayersInLobby());
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
            LobbyUpdateMessage lobbyUpd = new LobbyUpdateMessage();
            lobbyUpd.setPlayersInLobby(gameInstance.getPlayerPayload());
            gameServer.broadcastTCP(lobbyUpd, gameInstance.getPlayersInLobby());

        } else if(msg instanceof LobbyQuitMessage){
            source.setChar(-1);
            source.setReady(false);
            gameInstance.removePlayer(source);

            LobbyUpdateMessage lobbyUpd = new LobbyUpdateMessage();
            lobbyUpd.setPlayersInLobby(gameInstance.getPlayerPayload());
            gameServer.broadcastTCP(lobbyUpd, gameInstance.getPlayersInLobby());
        }

    }
}