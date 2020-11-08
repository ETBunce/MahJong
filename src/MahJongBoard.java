import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MahJongBoard extends JPanel implements MouseListener {
	
	public static final Dimension SIZE = new Dimension(1200,700);
	
	private static final int OFFSET_X = (SIZE.width - Tile.FACE_LENGTH * 15) / 2;
	private static final int OFFSET_Y = (SIZE.height - Tile.FACE_LENGTH * 8) / 2 - Tile.THICK;
	
	private ImageIcon backgroundImage;
	
	private MahJong game;
	private MahJongModel model;

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
		remove(t);
		repaint();
	}
	
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
