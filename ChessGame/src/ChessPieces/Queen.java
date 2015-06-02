package ChessPieces;
import java.util.ArrayList;

import GameLogic.BoardLogic;
import GameLogic.Direction;
import GameLogic.Location;
import GameLogic.Team;
import GameLogic.TurnController;

/**
 * @author tlesnia2
 *
 * A class modeled after the Queen piece in chess.
 */
public class Queen implements Piece {
	
	/**
	 * Constructs a Queen piece belonging to a certain team at a location on the board.
	 * @param l The starting location of the Queen.
	 * @param t The team to which the Queen belongs. Valid values: Team.WHITE, Team.BLACK
	 * @throws IllegalArgumentException when a team is not assigned.
	 * @see Team.java
	 */
	public Queen(Location l, Team t)
	{
		if(t != Team.WHITE && t != Team.BLACK)
		{
			throw new IllegalArgumentException("for Queen with team " + t); 
		}
		_team = t;
		this._location = l;
	}
	
	/**
	 * Internally translates the location of the piece.
	 * Note that moving a piece on the board is handled by the Board class.
	 * @param l The new location the piece is going to be moved.
	 */
	public void move(Location l)
	{
		this._location = l;
	}
	
	/**
	 * Finds a list of valid moves using the official movement logic
	 * for this particular piece.
	 * 
	 * @return A list of valid locations to which this piece can move. Moving to a location where the king is left vulnerable is not considered valid.
	 */
	public ArrayList<Location> findMoves(boolean removeCheckMoves) {
		
		ArrayList<Location> validLocations = new ArrayList<Location>();

		if(_team != TurnController.CurrentTeam) return validLocations;
		
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.NORTH));
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.SOUTH));
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.WEST));
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.EAST));
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.NORTHWEST));
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.NORTHEAST));
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.SOUTHWEST));
		validLocations.addAll(BoardLogic.search(_location, 7, Direction.SOUTHEAST));

		if(removeCheckMoves)
			validLocations = BoardLogic.removeCheckInducingMoves(this, validLocations, _location);
		
		return validLocations;
	}
	
	/**
	 * @return The current chess class of the piece.
	 */
	public ChessPiece getType()
	{
		return ChessPiece.Queen;
	}
	
	/**
	 * @return The board location of the piece.
	 */
	public Location getLocation()
	{
		return _location;
	}

	/**
	 * @return The team to which the piece belongs: White or Black.
	 */
	public Team getTeam() 
	{
		return _team;
	}
	
	private Location _location;
	private Team _team;
}
