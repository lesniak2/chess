package GameLogic;
/**
 * @author tlesnia2
 * A representation of a tile on the chess board as a 2D Point.
 * Also includes the starting locations for the types of pieces on a board.
 * 
 */
public class Location
{

	/**
	 * Constructs a new location in space and ensures it exists within the board.
	 * @param tx The x-coordinate of the point
	 * @param ty The y-coordinate of the point
	 * @throws IndexOutOfBoundException
	 */
	public Location(int tx, int ty)
	{
		if(BoardLogic.isValidLocation(tx, ty))
		{
			this.x = tx;
			this.y = ty;
		}
		else
		{
			this.x = -1;
			this.y = -1;
		}
	}
	/**
	 * Checks to see if two points share the same location in space.
	 * @param other The point against which we check.
	 * @return True if they occupy the same location, False otherwise.
	 */
	public boolean equals(Location other)
	{
		if(other == null)
		{
			return false;
		}
		return (x == other.x && this.y == other.y);
		
	}
	
	/**
	 * Variables left public for easy access, and since the location isn't a member of the board,
	 * no harm would be done in changing the x and y values.
	 */
	public int x;
	public int y;

	public static final Location WHITEPAWN1_STARTING_LOCATION = new Location(0,1);
	public static final Location WHITEPAWN2_STARTING_LOCATION = new Location(1,1);
	public static final Location WHITEPAWN3_STARTING_LOCATION = new Location(2,1);
	public static final Location WHITEPAWN4_STARTING_LOCATION = new Location(3,1);
	public static final Location WHITEPAWN5_STARTING_LOCATION = new Location(4,1);
	public static final Location WHITEPAWN6_STARTING_LOCATION = new Location(5,1);
	public static final Location WHITEPAWN7_STARTING_LOCATION = new Location(6,1);
	public static final Location WHITEPAWN8_STARTING_LOCATION = new Location(7,1);
	

	public static final Location WHITEROOK1_STARTING_LOCATION 	= new Location(0,0);
	public static final Location WHITEROOK2_STARTING_LOCATION 	= new Location(7,0);
	public static final Location WHITEKNIGHT1_STARTING_LOCATION = new Location(1,0);
	public static final Location WHITEKNIGHT2_STARTING_LOCATION = new Location(6,0);
	public static final Location WHITEBISHOP1_STARTING_LOCATION = new Location(2,0);
	public static final Location WHITEBISHOP2_STARTING_LOCATION = new Location(5,0);
	public static final Location WHITEQUEEN_STARTING_LOCATION   = new Location(3,0);
	public static final Location WHITEKING_STARTING_LOCATION 	= new Location(4,0);
	
	public static final Location BLACKPAWN1_STARTING_LOCATION = new Location(0,6);
	public static final Location BLACKPAWN2_STARTING_LOCATION = new Location(1,6);
	public static final Location BLACKPAWN3_STARTING_LOCATION = new Location(2,6);
	public static final Location BLACKPAWN4_STARTING_LOCATION = new Location(3,6);
	public static final Location BLACKPAWN5_STARTING_LOCATION = new Location(4,6);
	public static final Location BLACKPAWN6_STARTING_LOCATION = new Location(5,6);
	public static final Location BLACKPAWN7_STARTING_LOCATION = new Location(6,6);
	public static final Location BLACKPAWN8_STARTING_LOCATION = new Location(7,6);
	

	public static final Location BLACKROOK1_STARTING_LOCATION 	= new Location(0,7);
	public static final Location BLACKROOK2_STARTING_LOCATION 	= new Location(7,7);
	public static final Location BLACKKNIGHT1_STARTING_LOCATION = new Location(1,7);
	public static final Location BLACKKNIGHT2_STARTING_LOCATION = new Location(6,7);
	public static final Location BLACKBISHOP1_STARTING_LOCATION = new Location(2,7);
	public static final Location BLACKBISHOP2_STARTING_LOCATION = new Location(5,7);
	public static final Location BLACKQUEEN_STARTING_LOCATION 	= new Location(3,7);
	public static final Location BLACKKING_STARTING_LOCATION 	= new Location(4,7);
	
}
