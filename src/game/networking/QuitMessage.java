package game.networking;

import engine.common_net.AbstractMessage;
/** This triggers threads to stop both on server
  * and client. It also handles sudden client disconnect
 */
public class QuitMessage extends AbstractMessage {
    public QuitMessage(){

    }
}
