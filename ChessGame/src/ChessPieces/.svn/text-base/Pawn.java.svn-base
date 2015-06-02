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
 * A class modeled after the Pawn piece in chess.
 */
public class Pawn implements Piece {

	/**
	 * Constructs a Pawn piece belonging to a certain team at a location on the board.
	 * @param l The starting location of the Rook.
	 * @param t The team to which the Rook belongs. Valid values: Team.WHITE, Team.BLACK
	 * @throws IllegalArgumentException when a team is not assigned.
	 * @see Team.java
	 */
	public Pawn(Location l, Team t)
	{
		if(t != Team.WHITE && t != Team.BLACK)
		{
			throw new IllegalArgumentException("for Pawn with team " + t); 
		}
		_team = t;
		this._location = l;
		this._hasMoved = false;
	}
	
	/**
	 * Internally translates the location of the piece.
	 * Note that moving a piece on the board is handled by the Board class.
	 * @param l The new location the piece is going to be moved.
	 */
	public void move(Location l)
	{
		this._hasMoved = true;
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
		
		// add diagonal attacks
		for(Piece q : BoardLogic.getPieces())
		{
			if(q.getTeam() == Team.WHITE && TurnController.CurrentTeam == Team.BLACK)
			{
				Location loc = q.getLocation();
				// south east || south west
				if(loc.equals(new Location(_location.x + 1, _location.y - 1)) || 
						loc.equals(new Location(_location.x - 1, _location.y - 1)) )
				{
					validLocations.add(loc);
				}
			}
			else if(q.getTeam() == Team.BLACK && TurnController.CurrentTeam == Team.WHITE)
			{
				Location loc = q.getLocation();
				// north east || north west
				if(loc.equals(new Location(_location.x + 1, _location.y + 1)) 
						|| loc.equals(new Location(_location.x - 1, _location.y + 1)) )
				{
					validLocations.add(loc);
				}
			}
		}
		if(removeCheckMoves)
			validLocations = BoardLogic.removeCheckInducingMoves(this, validLocations, _location);
		return validLocations;

	}
	
	/**
	 * @return The current chess class of the piece.
	 */
	public ChessPiece getType()
	{
		return ChessPiece.Pawn;
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
	private boolean _hasMoved;
	private Team _team;
}
