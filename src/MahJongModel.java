
public class MahJongModel {
	private MahJongBoard board;
	private TileDeck deck = new TileDeck();
	private Tile[][][] slots = new Tile[8][12][4];
	private Tile[] specialSlots = new Tile[4];
	
	public static final int SPECIAL_INDEX_LEFT = 0;
	public static final int SPECIAL_INDEX_CAP = 1;
	public static final int SPECIAL_INDEX_RIGHT_1 = 2;
	public static final int SPECIAL_INDEX_RIGHT_2 = 3;

	public MahJongModel(MahJongBoard board)
	{
		
		deck.shuffle();
		
		//Layer 0
		this.board = board;
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
			specialSlots[i] = deck.deal();
			specialSlots[i].setSpecial();
		}
		
	}
	
	private void dealRow(int row, int col, int layer, int length) {
		for (int i = 0; i < length; i++) {
			Tile t = deck.deal();
			slots[row][col + i][layer] = t;
			t.setCoordinates(row,col,layer);
		}
	}
	
	public Tile getTile(int row, int col, int lay) {
		return slots[row][col][lay];
	}
	

	public Tile getSpecialTile(int i) {
		return specialSlots[i];
	}
}
