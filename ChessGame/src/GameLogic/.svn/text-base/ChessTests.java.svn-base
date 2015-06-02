package GameLogic;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import ChessPieces.Piece;
import ChessPieces.Pieces;

public class ChessTests {

	@BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
		BoardLogic.init();
    }
	@Test
	public void testFindMoves() 
	{
		ArrayList<Location> theoretical = new ArrayList<Location>();
		theoretical.add(new Location(0,2));
		theoretical.add(new Location(0,3));
		assert(theoretical.equals(BoardLogic.getPiece(Pieces.WhitePawn1).findMoves(true)));
		
		theoretical.clear();
		assert(theoretical.equals(BoardLogic.getPiece(Pieces.BlackKing)));
		
	}

	@Test
	public void testMove() 
	{
		Piece p = BoardLogic.getPiece(Pieces.WhitePawn1);
		BoardLogic.move(p, new Location(0,3));
		assert(p.getLocation().equals(new Location(0,3)));
		BoardLogic.move(p,  new Location(0,2));
		assert(!p.getLocation().equals(new Location(0,2)));
	}

	@Test
	public void testUpdateGameState() 
	{
		assert(BoardLogic.updateGameState() == GameState.Normal);
		Piece p = BoardLogic.getPiece(Pieces.WhiteKnight1);
		BoardLogic.move(p, new Location(2,2));
		assert(p.getLocation().equals(new Location(2,2)));
		assert(BoardLogic.updateGameState() == GameState.Normal);

	}
	
}
