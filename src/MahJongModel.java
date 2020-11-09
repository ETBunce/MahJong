import java.util.*;

public class MahJongModel {
	
	// CONFIG AND CONSTANTS
	public static final int NUM_ROWS = 8;
	public static final int NUM_COLS = 12;
	public static final int NUM_LAYERS = 4;
	
	public static final int SPECIAL_INDEX_LEFT = 0;
	public static final int SPECIAL_INDEX_CAP = 1;
	public static final int SPECIAL_INDEX_RIGHT_1 = 2;
	public static final int SPECIAL_INDEX_RIGHT_2 = 3;
	
	// VARIABLES
	private MahJongBoard board;
	private TileDeck deck = new TileDeck();
	private Tile[][][] slots = new Tile[8][12][4];
	private Tile[] specialSlots = new Tile[4];
	private Stack<Tile> undoStack = new Stack<Tile>();

	// CONSTRUCTOR
	public MahJongModel(MahJongBoard board)
	{
		this.board = board;
		dealBoard();
	}
	
	//METHODS
	
	private void dealBoard() {

		deck.shuffle();
		
		//Layer 0
		dealRow(0,0,0,12);
		dealRow(1,2,0,8);
		dealRow(2,1,0,10);
		dealRow(3,0,0,12);
		dealRow(4,0,0,12);
		dealRow(5,1,0,10);
		dealRow(6,2,0,8);
		dealRow(7,0,0,12);
		
		//Layer 1
		dealRow(1,3,1,6);
		dealRow(2,3,1,6);
		dealRow(3,3,1,6);
		dealRow(4,3,1,6);
		dealRow(5,3,1,6);
		dealRow(6,3,1,6);
		
		//lAYER 2
		dealRow(2,4,2,4);
		dealRow(3,4,2,4);
		dealRow(4,4,2,4);
		dealRow(5,4,2,4);
		
		//Layer 3
		dealRow(3,5,3,2);
		dealRow(4,5,3,2);
		
		//Special

		for (int i = 0; i < 4; i++) {
			Tile t = deck.deal();
			specialSlots[i] = t;
			t.setSpecial(i);
			t.setModel(this);
		}
	}
	
	private void dealRow(int row, int col, int layer, int length) {
		for (int i = 0; i < length; i++) {
			Tile t = deck.deal();
			slots[row][col + i][layer] = t;
			t.setCoordinates(row,col + i,layer);
			t.setModel(this);
		}
	}
	
	// GETTERS

	public Tile getSpecialTile(int i) {
		if (i < 0 || i > 3) return null;
		return specialSlots[i];
	}
	
	public Tile getTile(int row, int col, int layer) {
		if (row < 0 || col < 0 || layer < 0
			|| row >= NUM_ROWS || col >= NUM_COLS || layer >= NUM_LAYERS) {
			return null; }
		Tile t = slots[row][col][layer];
		return t;
	}
	
	public Tile printTile(int row, int col, int layer, String mod) { //TODO: used for debugging, remove before shipping
		Tile t = getTile(row,col,layer);
		System.out.println("printTile - Tile: " + t + " " + mod);
		return t;
	}
	
	public Tile printTile(int row, int col, int layer) { //TODO: used for debugging, remove before shipping
		return printTile(row,col,layer,"");
	}
	
	public boolean isTileOpen(int row, int col, int layer) {
		if (layer == 3 && getSpecialTile(SPECIAL_INDEX_CAP) != null) return false;
		if (col == 11 && (row == 3 || row == 4) && getSpecialTile(SPECIAL_INDEX_RIGHT_1) != null) return false;
		if (col == 0 && (row == 3 || row == 4) && getSpecialTile(SPECIAL_INDEX_LEFT) != null) return false;
		if (
			getTile(row,col,layer + 1) != null ||
			(getTile(row, col - 1, layer) != null
			&& getTile(row, col + 1, layer) != null)
		) return false;
		return true;
	}
	
	public boolean isTileOpen(int specialIndex) {
		switch(specialIndex) {
		case SPECIAL_INDEX_LEFT :
		case SPECIAL_INDEX_CAP :
		case SPECIAL_INDEX_RIGHT_2 :
			return true;
		case SPECIAL_INDEX_RIGHT_1 :
			if (getSpecialTile(SPECIAL_INDEX_RIGHT_2) == null) return true;
		}
		return false;
	}
	
	public boolean isTileOpen(Tile t) {
		return t.isOpen();
	}
	
	// SETTERS
	public void liftTile(Tile t) {
		undoStack.push(t);
		if (t.isSpecial()) specialSlots[t.getSpecialIndex()] = null;
		else slots[t.getRow()][t.getCol()][t.getLayer()] = null;
	}
}




