package GameLogic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ChessPieces.Bishop;
import ChessPieces.ChessPiece;
import ChessPieces.King;
import ChessPieces.Knight;
import ChessPieces.Pawn;
import ChessPieces.Piece;
import ChessPieces.Pieces;
import ChessPieces.Queen;
import ChessPieces.Rook;
import GUI.ChessBoard;

/**
 * @author tlesnia2
 * 
 * This class contains a chess board representation, 
 * along with any logic that is handled by the board.
 * A chess game only contains one board which is universal.
 */
public class BoardLogic {

	// 8x8 chess board
	public static int BOARD_SIZE = 64;
	public static int BOARD_WIDTH = 8;
	public static int BOARD_HEIGHT = 8;

	/**
	 * Attempts to moves a chess piece to a target location. If a chess piece 
	 * of the enemy team is occupying the location, it will be removed from the board
	 * and replaced by the moving piece.
	 * @param p The piece to move.
	 * @param targetLocation The location to which the piece will attempt to move.
	 * @return True on a successful move, false otherwise.
	 */
	public static boolean move(Piece p, Location targetLocation)
	{
		if(p.getTeam() != TurnController.CurrentTeam)
		{
			return false;
		}
		
		
		for(Piece q : _chessPieces.values())
		{
			if(q.getLocation() == targetLocation)
			{
				_chessPieces.remove(q);
				break;
			}
		}
		p.move(targetLocation);
		TurnController.endTurn();
		return true;
	}
	
	/**
	 * A way to check the current state of the teams on the board.
	 * @return A member of GameState which is calculated in this method.
	 * @see GameState.java
	 */
	public static GameState updateGameState() 
	{
		if(whiteInCheck().size() > 0)
		{
			return GameState.WhiteInCheck;
		}
		else if(blackInCheck().size() > 0)
		{
			return GameState.BlackInCheck;
		}
		
		if(whiteInCheckmate())
		{
			return GameState.WhiteInCheckmate;
		}
		else if(blackInCheckmate())
		{
			return GameState.BlackInCheckmate;
		}
		
		return GameState.Normal;
		
	}

	/**
	 * Checks to see whether or not a location is on the board.
	 * @param tx The x-coordinate of a location in space.
	 * @param ty The y-coordinate of a location in space.
	 * @return True if the location is on the board, False otherwise.
	 */
	public static boolean isValidLocation(int tx, int ty)
	{	
		// the board is 8x8
		return (tx >= 0 && tx  < 8) && ( ty >= 0 && ty < 8);
	}

	/**
	 * Checks to see if the black King is able to be reached
	 * by a piece.
	 * @return A list of pieces that are able to reach the King in the next move.
	 * @note the returned list will have size 0 is the black king is not in check.
	 */
	private static ArrayList<Piece> blackInCheck() 
	{
		
		ArrayList<Piece> checkPieces = new ArrayList<Piece>();
		
		for(Pieces i : _chessPieces.keySet() )
		{
			Piece p = _chessPieces.get(i);
			if(p.getTeam() == Team.WHITE){
				
				for(Location loc : p.findMoves(false)) 
				{
					Piece blackKing = _chessPieces.get(Pieces.BlackKing);
					if(loc.equals(blackKing.getLocation()))
					{
						checkPieces.add(p);
						break;
					}
				}
			}
			
		}
		
		return checkPieces;
	}

	/**
	 * Checks to see if the white King is able to be reached
	 * by a piece.
	 * @return A list of pieces that are able to reach the King in the next move.
	 * @note the returned list will have size 0 is the white king is not in check.
	 */
	private static ArrayList<Piece> whiteInCheck()
	{
		ArrayList<Piece> checkPieces = new ArrayList<Piece>();
		Collection<Piece> test = _chessPieces.values();
		for(Piece p : test )
		{
			if(p.getTeam() == Team.BLACK)
			{
				for(Location loc : p.findMoves(false)) 
				{
					Piece whiteKing = _chessPieces.get(Pieces.WhiteKing);
					if( loc.equals(whiteKing.getLocation()) )
					{
						checkPieces.add(p);
						break;
					}
				}
			}
		}
		return checkPieces;
	}

	/**
	 * Checks to see if the White team has won by placing
	 * the black king in checkmate.
	 * @return True if black forfeits or there are no available moves for the black team, False otherwise.
	 */
	private static boolean blackInCheckmate() 
	{
		if(blackquits)
		{
			return true;
		}
		// we are not in checkmate if we are not in check
		ArrayList<Piece> checkPieces;
		checkPieces = blackInCheck();
		if(checkPieces.size() == 0)
		{
			return false;
		}
		
		// see if a move exists to get out of check
		for(Piece p : _chessPieces.values())
		{
			if(p.getTeam() == Team.BLACK)
			{	
				if(canAvoidCheckmate(checkPieces, p))
					return false;
			}
		}
			
		return true;
	}
	
	/**
	 * Checks to see if the Black team has won by placing
	 * the White king in checkmate.
	 * @return True if white forfeits or there are no available moves for the White team, False otherwise.
	 */
	private static boolean whiteInCheckmate()
	{
		if(whitequits)
		{
			return true;
		}
		// we are not in checkmate if we are not in check
		ArrayList<Piece> checkPieces;
		checkPieces = whiteInCheck();
		if(checkPieces.size() == 0)
		{
			return false;
		}
		
		// see if a move exists to get out of check
		for(Piece p : _chessPieces.values())
		{
			if(p.getTeam() == Team.WHITE)
			{
				if(canAvoidCheckmate(checkPieces, p))
				{
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * A way to check if we can avoid checkmate. This can happen only if:
	 * 1) We can take the piece putting us in check
	 * 2) We can block the piece putting us in check
	 * 3) We can move the King to a location where we are not in check
	 * @param checkPieces The list of pieces currently putting the King in check
	 * @param defender A piece on the defending team where we see if it can move to avoid checkmate.
	 * @return True if there is a move to avoid checkmate, False otherwise.
	 */
	private static boolean canAvoidCheckmate(ArrayList<Piece> checkPieces, Piece defender) 
	{
		for(Location loc : defender.findMoves(true))
		{
			for(Piece attacker : checkPieces)
			{
				// if we can take the piece putting us in check or block its line of sight
				if(defender.findMoves(true).contains(attacker.getLocation()) || (locationBlocksSight(defender,loc) && defender.getType()!= ChessPiece.King))
				{
					return true;
				}
			}
		}
		
		// see if we can move the king
		Piece king = defender.getTeam() == Team.WHITE ? _chessPieces.get(Pieces.WhiteKing) : _chessPieces.get(Pieces.BlackKing);
		if(king.findMoves(true).size() > 0)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * A way to see if a piece can reach a particular location in one move.
	 * @param attacker The piece to see if it can move to a location.
	 * @param l	The location to which the piece is attempting to move.
	 * @return True if the the piece can move in a straight line to reach the location.
	 * @note: The knight is excluded since it can jump pieces.
	 */
	private static boolean locationBlocksSight(Piece attacker, Location l) {
		if(attacker.findMoves(false).contains(l) && attacker.getType() != ChessPiece.Knight)
			return true;
		return false;
	}


	/**
	 * Removes any moves which would cause the king to be in check from a list of moves.
	 * @param p The piece we are checking moves against.
	 * @param validLocations A list of raw locations for the piece to move.
	 * @param original The original location of the piece.
	 * @return
	 */
	public static ArrayList<Location> removeCheckInducingMoves(Piece p, ArrayList<Location> validLocations, Location original) 
	{
		// we move the piece to each location and check to see if the King is in check after we do so
		for(Location tempLoc : validLocations)
		{
			p.move(tempLoc);
			if(p.getTeam() == Team.BLACK)
			{
				if(blackInCheck().size() > 0)
				{
					// king in check, we remove the location
					validLocations.remove(tempLoc);
				}
			}
			else if(p.getTeam() == Team.WHITE)
			{
				if(whiteInCheck().size() > 0)
				{
					// king in check, we remove the location
					validLocations.remove(tempLoc);
				}
			}
		}
		//return the piece to its original location
		p.move(original);
		return validLocations;
	}

	/**
	 * A search on the board is performed to see if a location is in reach via a straight line given a starting location, direction, and a length.
	 * The search will stop prematurely if the location it finds is occupied by a piece.
	 * @param l The starting location of the search.
	 * @param max The length of the allowed search.
	 * @param dir The direction in which the search is performed.
	 * @return A list of locations wo which we can move.
	 */
	public static ArrayList<Location> search(Location l, int max, Direction dir) {
		Location tempLoc = l;
		ArrayList<Location> validLocations = new ArrayList<Location>();
		boolean blocked = false;

		// continue looking at spaces in the direction until we hit our length or are blocked
		for (int i = 0; i < max && !blocked; i++) {

			if(dir == Direction.NORTH)
			{
				tempLoc = new Location(tempLoc.x, tempLoc.y + 1);
			} 
			else if(dir == Direction.SOUTH)
			{
				tempLoc = new Location(tempLoc.x, tempLoc.y - 1);
			}
			else if(dir == Direction.WEST)
			{
				tempLoc = new Location(tempLoc.x - 1, tempLoc.y);
			}
			else if(dir == Direction.EAST)
			{
				tempLoc = new Location(tempLoc.x + 1, tempLoc.y);
			}
			else if(dir == Direction.NORTHWEST)
			{
				tempLoc = new Location(tempLoc.x - 1, tempLoc.y + 1);
			}
			else if(dir == Direction.NORTHEAST)
			{
				tempLoc = new Location(tempLoc.x + 1, tempLoc.y + 1);
			}
			else if(dir == Direction.SOUTHWEST)
			{
				tempLoc = new Location(tempLoc.x - 1, tempLoc.y - 1);
			}
			else if(dir == Direction.SOUTHEAST)
			{
				tempLoc = new Location(tempLoc.x + 1, tempLoc.y - 1);
			}
			else
			{
				tempLoc = null;
				break;
			}

			// we are blocked if we are off the board or hit a square with our own piece
			if (tempLoc.x == -1 || getOccupant(tempLoc) == TurnController.CurrentTeam)
			{
				//dont add this location the the list since we cant take our own piece
				blocked = true;
			}
			else if (getOccupant(tempLoc) == Team.NONE) 
			{
				// a square is empty, we are not blocked and we can add it to the list
				validLocations.add(tempLoc);
			} 
			else 
			{
				// the square is occupied by the enemy, which we can take.
				blocked = true;
				validLocations.add(tempLoc);
			}
		}

		return validLocations;
	}

	/**
	 * Returns the team (if any) occupying a location in space.
	 * @param loc The location we want to know if someone is occupying.
	 * @return The team occupying the location.
	 */
	public static Team getOccupant(Location loc)
	{
		for(Piece p : _chessPieces.values())
		{
			Location tempLoc = p.getLocation();
			if(tempLoc.equals(loc))
			{
				return p.getTeam();
			}
		}
		
		return Team.NONE;
	}
	
	/**
	 * Access a piece given its unique identifier.
	 * @param p The unique identifier for a piece on the board.
	 * @return The piece to which the identifier is attached.
	 */
	public static Piece getPiece(Pieces p) 
	{
		return _chessPieces.get(p);
	}
	/**
	 * 
	 * @param loc The location to search for.
	 * @return The piece on the specified location
	 */
	public static Piece getPiece(Location loc)
	{
		for(Piece p : _chessPieces.values())
		{
			Location tempLoc = p.getLocation();
			if(tempLoc.equals(loc))
			{
				return p;
			}
		}
		
		return null;
	}
	/**
	 * Resets the board to an initial state, with all pieces 
	 * placed on the gui board with their defined starting locations.
	 */
	public static void init() 
	{
		_chessPieces = new HashMap<Pieces, Piece>();
		
		_chessPieces.put(Pieces.WhitePawn1, new Pawn(Location.WHITEPAWN1_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhitePawn2, new Pawn(Location.WHITEPAWN2_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhitePawn3, new Pawn(Location.WHITEPAWN3_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhitePawn4, new Pawn(Location.WHITEPAWN4_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhitePawn5, new Pawn(Location.WHITEPAWN5_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhitePawn6, new Pawn(Location.WHITEPAWN6_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhitePawn7, new Pawn(Location.WHITEPAWN7_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhitePawn8, new Pawn(Location.WHITEPAWN8_STARTING_LOCATION, Team.WHITE));

		_chessPieces.put(Pieces.BlackPawn1, new Pawn(Location.BLACKPAWN1_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackPawn2, new Pawn(Location.BLACKPAWN2_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackPawn3, new Pawn(Location.BLACKPAWN3_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackPawn4, new Pawn(Location.BLACKPAWN4_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackPawn5, new Pawn(Location.BLACKPAWN5_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackPawn6, new Pawn(Location.BLACKPAWN6_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackPawn7, new Pawn(Location.BLACKPAWN7_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackPawn8, new Pawn(Location.BLACKPAWN8_STARTING_LOCATION, Team.BLACK));
		
		
		_chessPieces.put(Pieces.WhiteRook1,   new Rook(	 Location.WHITEROOK1_STARTING_LOCATION,	  Team.WHITE));
		_chessPieces.put(Pieces.WhiteRook2,   new Rook(	 Location.WHITEROOK2_STARTING_LOCATION,	  Team.WHITE));
		_chessPieces.put(Pieces.WhiteBishop1, new Bishop(Location.WHITEBISHOP1_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhiteBishop2, new Bishop(Location.WHITEBISHOP2_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhiteKnight1, new Knight(Location.WHITEKNIGHT1_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhiteKnight2, new Knight(Location.WHITEKNIGHT2_STARTING_LOCATION, Team.WHITE));
		_chessPieces.put(Pieces.WhiteKing,	  new King(	 Location.WHITEKING_STARTING_LOCATION,	  Team.WHITE));
		_chessPieces.put(Pieces.WhiteQueen,   new Queen( Location.WHITEQUEEN_STARTING_LOCATION,	  Team.WHITE));
		

		_chessPieces.put(Pieces.BlackRook1,	  new Rook(	 Location.BLACKROOK1_STARTING_LOCATION,	  Team.BLACK));
		_chessPieces.put(Pieces.BlackRook2,   new Rook(	 Location.BLACKROOK2_STARTING_LOCATION,   Team.BLACK));
		_chessPieces.put(Pieces.BlackBishop1, new Bishop(Location.BLACKBISHOP1_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackBishop2, new Bishop(Location.BLACKBISHOP2_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackKnight1, new Knight(Location.BLACKKNIGHT1_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackKnight2, new Knight(Location.BLACKKNIGHT2_STARTING_LOCATION, Team.BLACK));
		_chessPieces.put(Pieces.BlackKing,    new King(	 Location.BLACKKING_STARTING_LOCATION, 	  Team.BLACK));
		_chessPieces.put(Pieces.BlackQueen,   new Queen( Location.BLACKQUEEN_STARTING_LOCATION,   Team.BLACK));
		
	}
	/**
	 * @return A Piece array of pieces on the board.
	 */
	public static Piece[] getPieces()
	{
		return (Piece[]) (_chessPieces.values().toArray(new Piece[_chessPieces.values().size()]));
	}
	/**
	 * 
	 */
	public static void forfeit()
	{
		if(TurnController.CurrentTeam == Team.WHITE)
		{
			whitequits = true;
		}
		else
		{
			blackquits = true;
		}
		TurnController.endTurn();
		
	}

	private static boolean whitequits = false;
	private static boolean blackquits = false;
	private static Map<Pieces, Piece> _chessPieces;
}
