package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JTextArea;
import javax.swing.border.Border;

import GameLogic.BoardLogic;
import GameLogic.Team;
import GameLogic.Location;
import GameLogic.TurnController;
import ChessPieces.Piece;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class ChessBoard extends JFrame implements ActionListener {


	/**
	 * Create the frame.
	 */
	public ChessBoard() {
		
		whiteScore = 0;
		blackScore = 0;
		
		board = new JPanel[8][8];
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 675, 725);
		setResizable(false);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu FileMenu = new JMenu("File");
		menuBar.add(FileMenu);
        
		JMenuItem mntmRestart = new JMenuItem("Restart");
		FileMenu.add(mntmRestart);
		mntmRestart.addActionListener(this);

		JMenuItem mntmForfeit = new JMenuItem("Forfeit");
		FileMenu.add(mntmForfeit);
		mntmForfeit.addActionListener(this);
        
		JMenuItem mntmClose = new JMenuItem("Close");
		FileMenu.add(mntmClose);
		mntmClose.addActionListener(this);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 75, 75, 75, 75, 75, 75, 75,
				75, 75 };
		gridBagLayout.rowHeights = new int[] { 75, 75, 75, 75, 75, 75, 75, 75,
				75 };
		gridBagLayout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0 };
		panel.setLayout(gridBagLayout);

		/**
		 * Add the chess board's grid locations to the gui, 1-8 and A-H
		 */
		for (int i = 1; i < 9; i++) {
			JTextArea textNum = new JTextArea();
			textNum.setEditable(false);
			textNum.setFont(new Font("Georgia", Font.PLAIN, 18));
			textNum.setBackground(new Color(0, 0, 0, 0));
			textNum.setForeground(Color.WHITE);
			textNum.setText(""+i);
			GridBagConstraints gbc_textNum = new GridBagConstraints();
			gbc_textNum.gridx = 0;
			gbc_textNum.gridy = 9-i;
			panel.add(textNum, gbc_textNum);
			
			JTextArea textLetter = new JTextArea();
			textLetter.setEditable(false);
			textLetter.setFont(new Font("Georgia", Font.PLAIN, 18));
			textLetter.setBackground(new Color(0, 0, 0, 0));
			textLetter.setForeground(Color.WHITE);
			textLetter.setText( (char)( (int)'A' + i - 1) + "");
			GridBagConstraints gbc_textLetter = new GridBagConstraints();
			gbc_textLetter.gridx = i;
			gbc_textLetter.gridy = 0;
			panel.add(textLetter, gbc_textLetter);
		}
		
		/**
		 * Add the black and white tiles to the board
		 * with text components included
		 */
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				JPanel p = new JPanel();
				Color bg = (i + j) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY;
				p.setSize(75,75);
				p.setBackground(bg);
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.fill = GridBagConstraints.BOTH;
				gbc_panel.gridx = i+1;
				gbc_panel.gridy = j+1;
				
				panel.add(p, gbc_panel);
				
				JTextArea a = new JTextArea();
				initText(a);
				p.add(a);
				board[i][j] = p;
				
				p.addMouseListener(new MouseAdapter() {
				     @Override
				     public void mouseClicked(MouseEvent e) 
				     {
				    	 	JPanel panel = (JPanel) e.getSource();
				    	 	//calculate the location from BoardLogic
				    	 	Point guiLocation = panel.getLocation();
				    	 	int boardx = (guiLocation.x) / 75;
				    	 	int boardy = (guiLocation.y-10) / 75;
				    	 	System.out.println(boardx + "," +boardy);
				    	 	Location boardLocation = cellToLocation(boardx, boardy);
				    	 
				    	 	if( panel.getBorder() != null)
				    	 	{
				    	 		moveGuiPiece(boardx, boardy, boardLocation);
				    	 		
				    	 	}
				    	 	else if(board[boardx][boardy] == selectedPanel)
				    	 	{
				    	 		clearBorders();				    
				    	 		selectedPiece = null;
				    	 		selectedPanel = null;
				    	 	}
				    	 	
				    	 	else
				    	 	{
			    	 			clearBorders();
					    	 	selectedPiece = BoardLogic.getPiece(boardLocation);
					    	 	if(selectedPiece!=null)
					    	 	{
					    	 		colorAvailablePanels(boardx, boardy);
					    	 	}
				    	 	}
				    	 	refreshBoard();
				    	
				     }
				});
			}
		}
		
		initScoreCard(panel);
		
		initPieces();
	}

	/**
	 * Add the score card
	 */
	private void initScoreCard(JPanel panel) {
		scoreArea = new JTextArea();
		scoreArea.setEditable(false);
		scoreArea.setHighlighter(null);
		scoreArea.setSelectionColor(null);
		scoreArea.setFont(new Font("Georgia", Font.PLAIN, 12));
		scoreArea.setBackground(new Color(0, 0, 0, 0));
		scoreArea.setForeground(Color.WHITE);
		scoreArea.setText("White: "+whiteScore +"\nBlack: " + blackScore);
		GridBagConstraints gbc_scoreArea = new GridBagConstraints();
		gbc_scoreArea.gridx = 0;
		gbc_scoreArea.gridy = 0;
		panel.add(scoreArea, gbc_scoreArea);
	}

	/**
	 * Gets the Text area belonging to a specified panel at x,y on the GUI board.
	 * @param x The row to get the TextArea
	 * @param y The column to get the TextArea
	 * @return JTextArea at a specified location
	 */
	private JTextArea getTextArea(int x, int y)
	{
		return (JTextArea) (board[x][y].getComponent(0));
	}
	/**
	 * Add the pieces
	 */
	private void initPieces()
	{
		// place the pawns
		for(int i = 0; i < BoardLogic.BOARD_WIDTH; i++)
		{
			getTextArea(i,BoardLogic.BOARD_HEIGHT-2).setText(UnicodeConstants.pieces.get("WhitePawn"));
			getTextArea(i,1).setText(UnicodeConstants.pieces.get("BlackPawn"));
		}
		
		//rooks
		getTextArea(0,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteRook"));
		getTextArea(BoardLogic.BOARD_WIDTH-1,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteRook"));
		getTextArea(0,0).setText(UnicodeConstants.pieces.get("BlackRook"));
		getTextArea(BoardLogic.BOARD_WIDTH-1,0).setText(UnicodeConstants.pieces.get("BlackRook"));
		
		//knights
		getTextArea(1,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteKnight"));
		getTextArea(BoardLogic.BOARD_WIDTH-2,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteKnight"));
		getTextArea(1,0).setText(UnicodeConstants.pieces.get("BlackKnight"));
		getTextArea(BoardLogic.BOARD_WIDTH-2,0).setText(UnicodeConstants.pieces.get("BlackKnight"));
		
		//bishops
		getTextArea(2,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteBishop"));
		getTextArea(BoardLogic.BOARD_WIDTH-3,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteBishop"));
		getTextArea(2,0).setText(UnicodeConstants.pieces.get("BlackBishop"));
		getTextArea(BoardLogic.BOARD_WIDTH-3,0).setText(UnicodeConstants.pieces.get("BlackBishop"));
		
		//queens
		getTextArea(BoardLogic.BOARD_WIDTH-5,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteQueen"));
		getTextArea(3,0).setText(UnicodeConstants.pieces.get("BlackQueen"));
		
		//kings
		getTextArea(BoardLogic.BOARD_WIDTH-4,BoardLogic.BOARD_HEIGHT-1).setText(UnicodeConstants.pieces.get("WhiteKing"));
		getTextArea(4,0).setText(UnicodeConstants.pieces.get("BlackKing"));
		
		
	}

	private void initText(JTextArea chessPiece) {
		chessPiece.setEditable(false);
		chessPiece.setFont(new Font("Sans-Serif", Font.PLAIN, 50));
		chessPiece.setHighlighter(null);
		chessPiece.setBackground(new Color(0, 0, 0, 0));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		if(e.getActionCommand().equals("Close"))
		{
			this.dispose();
		}
		else if(e.getActionCommand().equals("Restart"))
		{
			int result = JOptionPane.showConfirmDialog(this,
				    "Your opponent wishes to restart. Proceed?",
				    "Restart Confrimation",
				    JOptionPane.YES_NO_OPTION
				    );
			
			if(result == JOptionPane.YES_OPTION)
			{
				restart();
				
			}
		}
		else if(e.getActionCommand().equals("Forfeit"))
		{
			BoardLogic.forfeit();
			refreshBoard();
		}
	}

	private void refreshBoard() {
		this.setSize(this.getWidth(), this.getHeight() + 1);
		this.setSize(this.getWidth(), this.getHeight() - 1);
	}

	private void restart() {
		BoardLogic.init();
		initPieces();
		TurnController.CurrentTeam = Team.WHITE;
		Game.start();
		refreshBoard();
	}
	
	private void clearBorders() {
		for(int i = 0; i < BoardLogic.BOARD_WIDTH; i++)
			for(int j = 0; j < BoardLogic.BOARD_HEIGHT; j++)
				board[i][j].setBorder(null);
	}

	private void moveGuiPiece(int boardx, int boardy, Location boardLocation) {
		if(BoardLogic.move(selectedPiece, boardLocation))
		{
			System.out.println("Piece moved");
			JTextArea newArea = ((JTextArea)(board[boardx][boardy].getComponent(0)));
			JTextArea oldArea = (JTextArea) selectedPanel.getComponent(0);
			newArea.setText(oldArea.getText());
			oldArea.setText("");
			clearBorders();
			selectedPiece = null;
			selectedPanel = null;
		}
	}

	private void colorAvailablePanels(int boardx, int boardy) {
		for(Location loc : selectedPiece.findMoves(true))
		{
			Location cell = locationToCell(loc);
			Border redBorder = BorderFactory.createLineBorder(Color.RED,2);
			board[cell.x][cell.y].setBorder(redBorder);
		 	selectedPanel = board[boardx][boardy];
		}
	}

	/**
	 * Adds one to the specified team's win count.
	 * @param team The team which won.
	 */
	public static void incrementScore(Team team)
	{
		if(team == Team.WHITE)
		{
			whiteScore++;
		}
		else 
		{
			blackScore++;
		}
		if(scoreArea!= null)
		{
			scoreArea.setText("White: "+whiteScore +"\nBlack: " + blackScore);
		}
	}
	/**
	 * Convert a location from BoardLogic, which works in Quadrant I,
	 * to one in quadrant IV, where the GUI board works.
	 * @param loc The Location to convert to GUI Coordinates
	 * @return GUI coordinates
	 */
	public static Location locationToCell(Location loc)
	{
		return new Location(loc.x, BoardLogic.BOARD_HEIGHT - loc.y - 1);
	}
	/**
	 * Convert GUI coordinates from Quadrant IV to board coordinates in Quadrant I.
	 * @param x The x coordinate of the panel on the GUI
	 * @param y The y coordinate of the panel on the GUI
	 * @return
	 */
	public static Location cellToLocation(int x, int y)
	{
		return new Location(x, BoardLogic.BOARD_HEIGHT - y - 1);
	}
	
	/**
	 * Returns the current score value for the given team.
	 * @param team The team for which we get the score.
	 * @return The value of the score for the specified team.
	 */
	public static int getScore(Team team)
	{
		return team == Team.WHITE ? whiteScore : blackScore;
	}
	
	private static JTextArea scoreArea;
	private static int whiteScore = 0;
	private Piece selectedPiece;
	private JPanel selectedPanel;	
	private static int blackScore = 0;
	private JPanel[][] board;
}
