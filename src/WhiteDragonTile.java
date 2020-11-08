import java.awt.*;

import javax.swing.JFrame;

public class WhiteDragonTile extends Tile {
	
	private static final int INNER_WIDTH = 46;
	private static final int BORDER_THICK = 4;
	private static final int CORNER_SIZE = 8;
	
	private static final int CX = PADDING + THICK + FACE_LENGTH / 2;
	private static final int CY = PADDING + FACE_LENGTH / 2;
	
	public WhiteDragonTile() {
		setToolTipText(toString());
	}
	
	public String toString()
	{
		return "White Dragon";
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.drawOval(CX - INNER_WIDTH / 2, CY - INNER_WIDTH / 2, INNER_WIDTH, INNER_WIDTH);
		g.drawRect(CX - INNER_WIDTH / 2 - BORDER_THICK, CY - INNER_WIDTH / 2,BORDER_THICK,INNER_WIDTH); // Left
		g.drawRect(CX + INNER_WIDTH / 2, CY - INNER_WIDTH / 2,BORDER_THICK,INNER_WIDTH); // Right
		g.fillRect(CX - INNER_WIDTH / 2, CY - INNER_WIDTH / 2 - BORDER_THICK,INNER_WIDTH,BORDER_THICK); // Top
		g.fillRect(CX - INNER_WIDTH / 2, CY + INNER_WIDTH / 2,INNER_WIDTH,BORDER_THICK); // Top
		g.setColor(new Color(30, 200, 30));
		g.fillRect(CX - INNER_WIDTH / 2 - BORDER_THICK / 2 - CORNER_SIZE / 2, CY - INNER_WIDTH / 2 - BORDER_THICK / 2 - CORNER_SIZE / 2, CORNER_SIZE, CORNER_SIZE);
		g.fillRect(CX + INNER_WIDTH / 2 + BORDER_THICK / 2 - CORNER_SIZE / 2, CY + INNER_WIDTH / 2 + BORDER_THICK / 2 - CORNER_SIZE / 2, CORNER_SIZE, CORNER_SIZE);
		g.setColor(Color.RED);
		g.fillRect(CX - INNER_WIDTH / 2 - BORDER_THICK / 2 - CORNER_SIZE / 2, CY + INNER_WIDTH / 2 + BORDER_THICK / 2 - CORNER_SIZE / 2, CORNER_SIZE, CORNER_SIZE);
		g.fillRect(CX + INNER_WIDTH / 2 + BORDER_THICK / 2 - CORNER_SIZE / 2, CY - INNER_WIDTH / 2 - BORDER_THICK / 2 - CORNER_SIZE / 2, CORNER_SIZE, CORNER_SIZE);
	}
	
	public static void main(String[] args)
	{
		JFrame	frame = new JFrame();

		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("White Dragon Tile");

		frame.add(new WhiteDragonTile());

		frame.pack();
		frame.setVisible(true);
	}
}
