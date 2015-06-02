package GameLogic;
/**
 * @author tlesnia2
 * 
 * A simple way to keep track of which team is allowed
 * to move at the given time. The game will start with
 * the White team moving first.
 */
public class TurnController 
{
	public static Team CurrentTeam = Team.WHITE;
	
	/**
	 * Switch which team is allowed to move. Called after successfully moving a piece.
	 */
	public static void endTurn() 
	{
		CurrentTeam = CurrentTeam == Team.WHITE ? Team.BLACK : Team.WHITE;
		Turn++;
	}
	
	/**
	 * A simple counter to keep track of which turn we are on.
	 * Currently unused.
	 */
	public static int Turn = 1;
}