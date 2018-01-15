package engine;

/*
 * Game State Manager - rather self explanatory name
 */
public class StateManager {
	GameStates state;
	
	public StateManager(GameStates state){
		this.state = state;
	}
	
	private enum GameStates {
		//list of all of the different states in the game 
		MAIN_MENU, LOBBY
	}
	
	private void GameStateUpdate(){
		/*Load the correct game state according to the user request - each 
		  case statement would call the update method of the relevant state	*/	
		switch(state){
		case LOBBY:
			break;
		case MAIN_MENU:
			break;
		}
	}
}
