package GUI;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import GameLogic.BoardLogic;
import GameLogic.GameState;
import GameLogic.Team;
import GameLogic.TurnController;

public class Game {
	
	/**
	 * Launch the Chess Game.
	 */
	public static void main(String[] args) {
					final ChessBoard cb = new ChessBoard();
					BoardLogic.init();
					cb.setVisible(true);
					Game.start();
	}

	/**
	 * Starts the chess game's main loop.
	 */
	public static void start()
	{
		new Thread(new Runnable()
		{
		    public void run() 
		    {

				GameState state;
				do
				{
					// wait for player to move
					Team t = TurnController.CurrentTeam;
					while(t == TurnController.CurrentTeam)
					{
						try 
						{
							Thread.sleep(100);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
					// update the game state
					state = BoardLogic.updateGameState();
				}
				while (state != GameState.BlackInCheckmate && state != GameState.WhiteInCheckmate);
						
				if(state == GameState.BlackInCheckmate)	
				{
					JOptionPane.showMessageDialog(null, "White is the victor.");
					ChessBoard.incrementScore(Team.WHITE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Black is the victor.");
					ChessBoard.incrementScore(Team.BLACK);
				}
		    }
		}).start();
	}

}
