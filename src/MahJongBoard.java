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
	
	// VARIABLES
	private Tile selectedTile = null;
//	private Border selectedBorder = BorderFactory.createLineBorder(Color.GREEN,2);
	private ImageIcon backgroundImage;
	private MahJong game;
	private MahJongModel model;
	private Outline outline = new Outline();

	public MahJongBoard(MahJong game)
	{
		this.game = game;
		model = new MahJongModel(this);
		
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
		
		//Add the tiles
		
		//Special
		Tile st;
		st = model.getSpecialTile(MahJongModel.SPECIAL_INDEX_CAP);
		st.setLocation(
				6 * Tile.FACE_LENGTH + 4 * Tile.THICK + OFFSET_X + Tile.FACE_LENGTH / 2,
				3 * Tile.FACE_LENGTH - 4 * Tile.THICK + OFFSET_Y + Tile.FACE_LENGTH / 2);
		add(st);
		st.addMouseListener(this);
		st = model.getSpecialTile(MahJongModel.SPECIAL_INDEX_LEFT);
		st.setLocation(
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
						t.setLocation(
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
		st.setLocation(
				13 * Tile.FACE_LENGTH + OFFSET_X,
				3 * Tile.FACE_LENGTH + OFFSET_Y + Tile.FACE_LENGTH / 2);
		add(st);
		st.addMouseListener(this);
		st = model.getSpecialTile(MahJongModel.SPECIAL_INDEX_RIGHT_2);
		st.setLocation(
				14 * Tile.FACE_LENGTH + OFFSET_X,
				3 * Tile.FACE_LENGTH + OFFSET_Y + Tile.FACE_LENGTH / 2);
		add(st);
		st.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		backgroundImage.paintIcon(this, g, (SIZE.width - backgroundImage.getIconWidth()) / 2, (SIZE.height - backgroundImage.getIconHeight()) / 2);
	}

	//Mouse Listener implementation
	public void mousePressed(MouseEvent e) {
		Tile t = (Tile)e.getSource();
		if (!t.isOpen() || t.equals(selectedTile)) {
			deselect();
//			revalidate();
//			repaint();
			return;
		}
		if (t.matches(selectedTile)) {
			t.setZOrder();
			model.liftTile(t);
			remove(t);
			selectedTile.setZOrder();
			model.liftTile(selectedTile);
			remove(selectedTile);
			deselect();
		} else {
			select(t);
		}
		revalidate();
		repaint();
	}
	
	private void select(Tile t) {
		selectedTile = t;
		outline.show(t.getX(),t.getY());
	}
	
	private void deselect() {
		selectedTile = null;
		outline.hide();
	}
	
	private class Outline extends JPanel {
		
		private boolean visible = false;
		
		public Outline(int x, int y) {
			setLocation(x,y);
			setSize(Tile.SIZE);
			setPreferredSize(Tile.SIZE);
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
			g.setColor(new Color(50,155,50));
			g.drawRect(1,1,Tile.SIZE.width - 2,Tile.SIZE.height - 2);
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
