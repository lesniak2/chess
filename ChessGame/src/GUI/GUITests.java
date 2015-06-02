package GUI;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GameLogic.Location;
import GameLogic.Team;

public class GUITests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIncrementScore() {
		ChessBoard.incrementScore(Team.WHITE);
		assert(ChessBoard.getScore(Team.WHITE) == 1);
		assert(ChessBoard.getScore(Team.BLACK) == 0);
		ChessBoard.incrementScore(Team.BLACK);
		assert(ChessBoard.getScore(Team.WHITE) == 1);
		assert(ChessBoard.getScore(Team.BLACK) == 1);
		
	}

	@Test
	public void testLocationToCell() {
		Location loc = new Location(1,3);
		Location guiLoc = ChessBoard.locationToCell(loc);
		assert(guiLoc.equals(new Location(1, 5)));
	}

	@Test
	public void testCellToLocation() {
		Location loc = new Location(1,5);
		Location guiLoc = ChessBoard.locationToCell(loc);
		assert(guiLoc.equals(new Location(1, 3)));
	}

}
