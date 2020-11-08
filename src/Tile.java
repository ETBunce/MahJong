import java.awt.*;
import javax.swing.*;

public class Tile extends JPanel{
	
	public	static	final	Dimension	SIZE;
	private	static	final	Polygon		SIDE1;
	private	static	final	Polygon		BOTTOM1;
	private	static	final	Polygon		SIDE2;
	private	static	final	Polygon		BOTTOM2;

	private static	final	Rectangle	FACE;

	private	static	final	GradientPaint	FACE_PAINT;
	private	static	final	GradientPaint	SIDE1_PAINT;
	private	static	final	GradientPaint	BOTTOM1_PAINT;
	private	static	final	GradientPaint	SIDE2_PAINT;
	private	static	final	GradientPaint	BOTTOM2_PAINT;
	
	//CONFIG
	public static final int TILE_SIZE = 86;
	public static final int THICK_1 = 10;
	public static final int THICK_2 = 6;
	public static final int PADDING = 1;
	
	public static final int THICK = THICK_1 + THICK_2;
	public static final int FACE_LENGTH = TILE_SIZE - THICK;
	
	private boolean isSpecial = false;
	private int row = -1;
	private int col = -1;
	private int lay = -1;
	
	static
	{
		
		SIZE = new Dimension(TILE_SIZE + PADDING * 2,TILE_SIZE + PADDING * 2);

		int[] xs1 = { THICK_2, THICK, THICK, THICK_2 };
		int[] ys1 = { THICK_1, 0, FACE_LENGTH, FACE_LENGTH + THICK_1 };

		int[] xb1 = { THICK, TILE_SIZE, TILE_SIZE - THICK_1, THICK_2 };
		int[] yb1 = { FACE_LENGTH, FACE_LENGTH, FACE_LENGTH + THICK_1, FACE_LENGTH + THICK_1 };

		int[] xs2 = { 0, THICK_2, THICK_2, 0 };
		int[] ys2 = { THICK, THICK_1, TILE_SIZE - THICK_2, TILE_SIZE };

		int[] xb2 = { THICK_2, TILE_SIZE - THICK_1, TILE_SIZE - THICK, 0 };
		int[] yb2 = { FACE_LENGTH + THICK_1, FACE_LENGTH + THICK_1, TILE_SIZE, TILE_SIZE };

		// Add the PADDING TILE_SIZE to each coordinate
		for (int i = 0; i < 4; i++) {
			xs1[i] = xs1[i] + PADDING;
			ys1[i] = ys1[i] + PADDING;
			xb1[i] = xb1[i] + PADDING;
			yb1[i] = yb1[i] + PADDING;
			xs2[i] = xs2[i] + PADDING;
			ys2[i] = ys2[i] + PADDING;
			xb2[i] = xb2[i] + PADDING;
			yb2[i] = yb2[i] + PADDING;
		}
		
		SIDE1 = new Polygon(xs1, ys1, 4);
		BOTTOM1 = new Polygon(xb1, yb1, 4);
		SIDE2 = new Polygon(xs2, ys2, 4);
		BOTTOM2 = new Polygon(xb2, yb2, 4);

		FACE = new Rectangle(THICK + PADDING, PADDING, FACE_LENGTH, FACE_LENGTH);

		Color stoneColor = new Color(0,220,220);
		Color stoneFade = new Color(20,160,180);
		FACE_PAINT = new GradientPaint(THICK + PADDING, FACE_LENGTH + PADDING, Color.WHITE, TILE_SIZE + PADDING, PADDING, new Color(150,165,165));
		SIDE1_PAINT = new GradientPaint(THICK + PADDING, FACE_LENGTH + PADDING, new Color(220,220,220), THICK_2 + PADDING, PADDING, new Color(100,140,140));
		BOTTOM1_PAINT = new GradientPaint(THICK + PADDING,FACE_LENGTH + PADDING, new Color(220,220,220), TILE_SIZE + PADDING,THICK_1 + PADDING, new Color(100,140,140));
		SIDE2_PAINT = new GradientPaint(THICK_2 + PADDING, FACE_LENGTH + THICK_1 + PADDING, stoneColor, PADDING, THICK + PADDING, stoneFade);
		BOTTOM2_PAINT = new GradientPaint(THICK_2 + PADDING, FACE_LENGTH + THICK_1 + PADDING,stoneColor, TILE_SIZE - THICK + PADDING,TILE_SIZE + PADDING, stoneFade);
	}

	public Tile () {
		setSize(SIZE);
		setPreferredSize(SIZE);
		setOpaque(false);
	}
	
	public boolean matches(Tile other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() == other.getClass()) return true;
		return false;
	}
	
	public void setCoordinates(int row, int col, int layer) {
		this.row = row;
		this.col = col;
		this.lay = layer;
	}
	
	public void setSpecial() {
		isSpecial = true;
	}
	
	public int getRow() { return row; }
	public int getCol() { return col; }
	public int getLay() { return lay; }
	
	public boolean isSpecial() {
		return isSpecial;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D	g2 = (Graphics2D)g;
		g2.setPaint(FACE_PAINT);
		g2.fill(FACE);
		g2.setPaint(SIDE1_PAINT);
		g2.fill(SIDE1);
		g2.setPaint(BOTTOM1_PAINT);
		g2.fill(BOTTOM1);
		g2.setPaint(SIDE2_PAINT);
		g2.fill(SIDE2);
		g2.setPaint(BOTTOM2_PAINT);
		g2.fill(BOTTOM2);
		g2.setColor(Color.GRAY);
		g2.draw(FACE);
		g2.draw(SIDE1);
		g2.draw(SIDE2);
		g2.draw(BOTTOM1);
		g2.draw(BOTTOM2);
	}
	
	public static void main(String[] args)
	{
		JFrame	frame = new JFrame();

		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Tile");

		frame.add(new Tile());

		frame.pack();
		frame.setVisible(true);
	}
}
