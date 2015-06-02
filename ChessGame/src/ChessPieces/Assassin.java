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
 * A class modeled after a custom Cannon piece in chess.
 */
public class Assassin implements Piece {
	/**
	 * Constructs a Knight piece belonging to a certain team at a location on the board.
	 * @param l The starting location of the Rook.
	 * @param t The team to which the Rook belongs. Valid values: Team.WHITE, Team.BLACK
	 * @throws IllegalArgumentException when a team is not assigned.
	 * @see Team.java
	 */
	public Assassin(Location l, Team t)
	{
		if(t != Team.WHITE && t != Team.BLACK)
		{
			throw new IllegalArgumentException("for Cannon with team " + t); 
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
	 * Finds a list of valid moves using the custom movement logic
	 * for this particular piece. Behaves the same as a pawn but moves back to original
	 * location after taking a piece.
	 * 
	 * @return A list of valid locations to which this piece can move. Moving to a location where the king is left vulnerable is not considered valid.
	 */
	public ArrayList<Location> findMoves(boolean removeCheckMoves) {
		
		ArrayList<Location> validLocations = new ArrayList<Location>();
		if(_team != TurnController.CurrentTeam) return validLocations;
		
		if (_hasMoved && _team == Team.WHITE)
			validLocations.addAll(BoardLogic.search(_location, 1, Direction.NORTH));
		else if (_hasMoved && _team == Team.BLACK)
			validLocations.addAll(BoardLogic.search(_location, 1, Direction.SOUTH));
		else if (!_hasMoved && _team == Team.WHITE)
			validLocations.addAll(BoardLogic.search(_location, 2, Direction.NORTH));
		else if (!_hasMoved && _team == Team.BLACK)
			validLocations.addAll(BoardLogic.search(_location, 2, Direction.SOUTH));
		else
			throw new IllegalStateException("for pawn on team " + _team
					+ " with hasMoved() = " + _hasMoved);
		
		/**
		// add diagonal attacks
		for(Piece q : _chessPieces.values())
		{
			if(q.getTeam() == Team.WHITE && TurnController.CurrentTeam == Team.BLACK)
			{
				Location loc = q.getLocation();
				if(loc.equals(_location.moveDownRight()) || loc.equals(_location.moveDownLeft()))
					validLocations.add(loc);
			}
			else if(q.getTeam() == Team.BLACK && TurnController.CurrentTeam == Team.WHITE)
			{
				Location loc = q.getLocation();
				if(loc.equals(_location.moveUpRight()) || loc.equals(l.moveUpLeft()))
					validLocations.add(loc);
			}
		}*/
		if(removeCheckMoves)
			validLocations = BoardLogic.removeCheckInducingMoves(this, validLocations, _location);
		return validLocations;
	}
	
	/**
	 * @return The current chess class of the piece.
	 */
	public ChessPiece getType()
	{
		return ChessPiece.Custom;
	}

	/**
	 * @return The board location of the piece.
	 */
	public Location getLocation() 
	{
		return _location;
	}	
	
	/**
	 * @return True if this is not the first turn, false if it is.
	 */
	public boolean hasMoved() 
	{
		return _hasMoved;
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
	private boolean _hasMoved;
}
