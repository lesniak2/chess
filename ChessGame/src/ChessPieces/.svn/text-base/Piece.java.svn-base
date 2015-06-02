package ChessPieces;
import java.util.ArrayList;

import GameLogic.Location;
import GameLogic.Team;

/**
 * @author tlesnia2
 * An abstract interface for a chess piece. Defines the basic 
 * functionality required for each piece.
 */
public abstract interface Piece 
{
	/**
	 * 
	 * @param l The location to which the piece should move.
	 */
	public void move(Location l);
	/**
	 * Incorporates the logic for a piece, and removes any invalid moves
	 * whether from being a check-inducing move or being off the board.
	 * @param removeCheckMoves false to include moves that would result in a check against you, true otherwise
	 * @return A comprehensive list of valid moves for this piece.
	 */
	public ArrayList<Location> findMoves(boolean removeCheckMoves);
	/**
	 * @return The current type of the Chess Piece.
	 * @see ChessPiece.java
	 */
	public ChessPiece getType();
	/**
	 * @return The corresponding team of the chess piece.
	 * @see Team.java
	 */
	public Team getTeam();
	/**
	 * @return The current location on the board of the chess piece.
	 */
	public Location getLocation();
}
