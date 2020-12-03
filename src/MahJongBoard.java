import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MahJongBoard extends JPanel implements MouseListener {
	
	// CONFIG
	public static final Dimension SIZE = new Dimension(1200,700);
	private static final int OFFSET_X = (SIZE.width - Tile.FACE_LENGTH * 15) / 2;
	private static final int OFFSET_Y = (SIZE.height - Tile.FACE_LENGTH * 8) / 2 - Tile.THICK;
	private static final int WIN_SCORE = 144;
	
	// VARIABLES
	private Tile selectedTile = null;
//	private Border selectedBorder = BorderFactory.createLineBorder(Color.GREEN,2);
	private ImageIcon backgroundImage;
	private MahJong game;
	private MahJongModel model;
	private Outline outline = new Outline();
	private JPanel[] discardRows = new JPanel[2];
	private JPanel discard = new JPanel();
	private int discardPadding = 6;
	private int discardSlotWidth = Tile.SIZE.width + discardPadding;
	private PlayClip scrape = new PlayClip("audio/scrape.wav");
	private boolean won = false;

	// CONSTRUCTOR
	public MahJongBoard(MahJong game)
	{
		model = new MahJongModel(this);
		buildBoard(game);
	}
	
	public MahJongBoard(MahJong game, long seed) {
		model = new MahJongModel(this, seed);
		buildBoard(game);
	}
	
	// METHODS
	
	public void undo(boolean batch) {
		if (model.getUndoSize() == 0 || won) return;
		Tile t = model.replaceTile();
		discard.remove(t);
		t.setToBoardLocation();
		t = model.replaceTile();
		discard.remove(t);
		t.setToBoardLocation();
		if (!batch) {
			resizeDiscard();
			revalidate();
			repaint();
		}
	}
	
	public void undo() {
		undo(false);
	}
	
	public void restart() {
		won = false;
		while (model.getUndoSize() > 0) {
			undo(true);
		}
		deselect();
		resizeDiscard();
		revalidate();
		repaint();
	}
	
	// GETTERS
	
	public MahJongModel getModel() {
		return model;
	}
	
	public JPanel getDiscardPanel() {
		return discard;
	}
	
	public int getUndoSize() {
		return model.getUndoSize();
	}
	
	// OVERRIDING
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		backgroundImage.paintIcon(this, g, (SIZE.width - backgroundImage.getIconWidth()) / 2, (SIZE.height - backgroundImage.getIconHeight()) / 2);
	}

	// INTERFACES
	//Mouse Listener implementation
	public void mousePressed(MouseEvent e) {
		Tile t = (Tile)e.getSource();
		if (!t.getParent().equals(this) || won) return;
		if (!t.isOpen() || t.equals(selectedTile)) {
			deselect();
			return;
		}
		if (t.matches(selectedTile)) {
//		if (selectedTile != null) { // Easy mode
			scrape.play();
			t.setZOrder();
			model.liftTile(t);
			discardRows[0].add(t);
			remove(t);
			selectedTile.setZOrder();
			model.liftTile(selectedTile);
			discardRows[1].add(selectedTile);
			remove(selectedTile);
			deselect();
			resizeDiscard();
			win();
		} else {
			select(t);
		}
		revalidate();
		repaint();
	}
	
	//HELPERS
	
	private void select(Tile t) {
		selectedTile = t;
		outline.show(t.getX()+ Tile.THICK + Tile.PADDING - Outline.THICK,t.getY() + Tile.PADDING - Outline.THICK);
	}
	
	private void deselect() {
		selectedTile = null;
		outline.hide();
	}
	
	private boolean win() {
		if (model.getUndoSize() < WIN_SCORE) return false;
		won = true;
		game.win();
		return true;
	}
	
	private void resizeDiscard() {
		Dimension size = new Dimension(model.getUndoSize() / 2 * discardSlotWidth + discardPadding, discardSlotWidth);
		discardRows[0].setPreferredSize(size);
		discardRows[1].setPreferredSize(size);
		discard.setPreferredSize(size);
		discard.setSize(size);
	}
	
	private void buildBoard(MahJong game) {
		this.game = game;
		
		//Configure the board
		setPreferredSize(SIZE);
		setLayout(null);
		URL url = MahJongBoard.class.getResource("images/dragon_bg.png");
		if (url != null) {
			backgroundImage = new ImageIcon(url);
		} else {
			System.err.println("Could not load image: images/dragon_bg.png");
			backgroundImage = new ImageIcon();
		}
		add(outline);
		setComponentZOrder(outline, 0);
		
		//Configure Discard
		discardRows[0] = new JPanel(new FlowLayout(FlowLayout.LEFT, discardPadding, 0));
		discardRows[1] = new JPanel(new FlowLayout(FlowLayout.LEFT, discardPadding, 0));
		discard.add(discardRows[0], BorderLayout.NORTH);
		discard.add(discardRows[1], BorderLayout.SOUTH);
		discard.setBackground(new Color(254,205,33));
		discard.setPreferredSize(new Dimension(0, 2 * discardSlotWidth + 40));
		discardRows[0].setBackground(new Color(254,205,33));
		discardRows[1].setBackground(new Color(254,205,33));
		discardRows[0].setPreferredSize(new Dimension(0,discardSlotWidth));
		discardRows[1].setPreferredSize(new Dimension(0,discardSlotWidth));
		
		//Add the tiles
		
		//Special
		Tile st;
		st = model.getSpecialTile(MahJongModel.SPECIAL_INDEX_CAP);
		st.setBoardLocation(
				6 * Tile.FACE_LENGTH + 4 * Tile.THICK + OFFSET_X + Tile.FACE_LENGTH / 2,
				3 * Tile.FACE_LENGTH - 4 * Tile.THICK + OFFSET_Y + Tile.FACE_LENGTH / 2);
		add(st);
		st.addMouseListener(this);
		st = model.getSpecialTile(MahJongModel.SPECIAL_INDEX_LEFT);
		st.setBoardLocation(
				OFFSET_X,
				3 * Tile.FACE_LENGTH + OFFSET_Y + Tile.FACE_LENGTH / 2);
		add(st);
		st.addMouseListener(this);
		//Normal
		for (int lay = 3; lay >= 0; lay--) {
			for (int row = 7; row >= 0; row--) {
				for (int col = 0; col < 12; col++) {
					Tile t = model.getTile(row, col, lay); 
					if (t != null) {
						t.setBoardLocation(
								col * Tile.FACE_LENGTH + lay * Tile.THICK + OFFSET_X + Tile.FACE_LENGTH,
								row * Tile.FACE_LENGTH - lay * Tile.THICK + OFFSET_Y);
						add(t);
						t.addMouseListener(this);
					}
				}
			}
		}
		//More special
		st = model.getSpecialTile(MahJongModel.SPECIAL_INDEX_RIGHT_1);
		st.setBoardLocation(
				13 * Tile.FACE_LENGTH + OFFSET_X,
				3 * Tile.FACE_LENGTH + OFFSET_Y + Tile.FACE_LENGTH / 2);
		add(st);
		st.addMouseListener(this);
		st = model.getSpecialTile(MahJongModel.SPECIAL_INDEX_RIGHT_2);
		st.setBoardLocation(
				14 * Tile.FACE_LENGTH + OFFSET_X,
				3 * Tile.FACE_LENGTH + OFFSET_Y + Tile.FACE_LENGTH / 2);
		add(st);
		st.addMouseListener(this);
	}
	
	private class Outline extends JPanel {
		
		private boolean visible = false;
		public static final int THICK = 3;
		public static final int WIDTH = Tile.FACE_LENGTH + THICK * 2;
		
		public Outline(int x, int y) {
			setLocation(x,y);
			setSize(new Dimension(WIDTH, WIDTH));
			setPreferredSize(new Dimension(WIDTH, WIDTH));
			setOpaque(false);
		}
		public Outline() { this(0,0); }
		
		public void setVisible(boolean v) {
			visible = v;
		}
		public void show() {
			visible = true;
			repaint();
		}
		public void hide() {
			visible = false;
			repaint();
		}
		
		public void show(int x, int y) {
			visible = true;
			setLocation(x,y);
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (!visible) return;
			g.setColor(new Color(80,255,80));
			g.fillRect(0, 0, WIDTH, THICK);
			g.fillRect(0, WIDTH - THICK, WIDTH, THICK);
			g.fillRect(0, 0, THICK, WIDTH);
			g.fillRect(WIDTH - THICK, 0, THICK, WIDTH);
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
