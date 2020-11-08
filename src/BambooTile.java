import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class BambooTile extends RankTile{

	private static final Color G = new Color(30, 200, 30);
	private static final Color B = Color.BLUE;
	private static final Color R = Color.RED;
	
	private ArrayList<Bamboo> bamboos = new ArrayList<>();
	
	public BambooTile(int rank) {
		super(rank);
		switch(rank) {
		case 2:
			addBamboo(1/2f, 13/40f, B);
			addBamboo(1/2f, 27/40f, G);
			break;
		case 3:
			addBamboo(1/2f, 13/40f, B);
			addBamboo(1/3f, 27/40f, G);
			addBamboo(2/3f, 27/40f, G);
			break;
		case 4:
			addBamboo(1/3f, 13/40f, B);
			addBamboo(2/3f, 13/40f, G);
			addBamboo(1/3f, 27/40f, G);
			addBamboo(2/3f, 27/40f, B);
			break;
		case 5:
			addBamboo(1/4f, 13/40f, G);
			addBamboo(3/4f, 13/40f, B);
			addBamboo(1/2f, 1/2f, R);
			addBamboo(1/4f, 27/40f, B);
			addBamboo(3/4f, 27/40f, G);
			break;
		case 6:
			addBamboo(1/4f, 13/40f, G);
			addBamboo(1/2f, 13/40f, G);
			addBamboo(3/4f, 13/40f, G);
			addBamboo(1/4f, 27/40f, B);
			addBamboo(1/2f, 27/40f, B);
			addBamboo(3/4f, 27/40f, B);
			break;
		case 7:
			addBamboo(1/2f, 7/40f, R);
			addBamboo(1/4f, 1/2f, G);
			addBamboo(1/2f, 1/2f, B);
			addBamboo(3/4f, 1/2f, G);
			addBamboo(1/4f, 33/40f, G);
			addBamboo(1/2f, 33/40f, B);
			addBamboo(3/4f, 33/40f, G);
			break;
		case 8:
			addRotatedBamboo(2/5f, 13/40f, Math.toRadians(45), G);
			addRotatedBamboo(3/5f, 13/40f, Math.toRadians(-45), G);
			addRotatedBamboo(2/5f, 27/40f, Math.toRadians(-45), B);
			addRotatedBamboo(3/5f, 27/40f, Math.toRadians(45), B);
			addBamboo(1/4f, 13/40f, G);
			addBamboo(3/4f, 13/40f, G);
			addBamboo(1/4f, 27/40f, B);
			addBamboo(3/4f, 27/40f, B);
			break;
		case 9:
			addBamboo(1/4f, 7/40f, R);
			addBamboo(1/4f, 1/2f, R);
			addBamboo(1/4f, 33/40f, R);
			addBamboo(1/2f, 7/40f, B);
			addBamboo(1/2f, 1/2f, B);
			addBamboo(1/2f, 33/40f, B);
			addBamboo(3/4f, 7/40f, G);
			addBamboo(3/4f, 1/2f, G);
			addBamboo(3/4f, 33/40f, G);
		}
		setToolTipText(toString());
	}
	
	public String toString() { return "Bamboo " + rank;	}

	private void addBamboo(float xf, float yf, Color c) {
		bamboos.add(new Bamboo((int)(PADDING + THICK + FACE_LENGTH * xf), (int)(PADDING + FACE_LENGTH * yf), c));
	}
	
	private void addRotatedBamboo(float xf, float yf, double rotation, Color c) {
		bamboos.add(new RotatedBamboo((int)(PADDING + THICK + FACE_LENGTH * xf), (int)(PADDING + FACE_LENGTH * yf), rotation, c));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Bamboo b : bamboos) {
			b.draw(g);
		}
	}
	
	private class Bamboo {
		protected Point position;
		private Color color;
		
		private static final int SHAFT_WIDTH = 5;
		private static final int SHAFT_HEIGHT = 16;
		private static final int JOINT_WIDTH = 9;
		private static final int JOINT_HEIGHT = 4;
		
		public Bamboo(int x, int y, Color color) {
			position = new Point(x, y);
			this.color = color;
		}
		
		public void draw(Graphics g) {
			g.setColor(color);
			g.fillOval(position.x - JOINT_WIDTH / 2, position.y - SHAFT_HEIGHT / 2 - JOINT_HEIGHT / 2, JOINT_WIDTH, JOINT_HEIGHT);
			g.fillOval(position.x - JOINT_WIDTH / 2, position.y + SHAFT_HEIGHT / 2 - JOINT_HEIGHT / 2, JOINT_WIDTH, JOINT_HEIGHT);
			g.fillRect(position.x - SHAFT_WIDTH / 2, position.y - SHAFT_HEIGHT / 2, SHAFT_WIDTH, SHAFT_HEIGHT);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(position.x - SHAFT_WIDTH / 2 + 2, position.y - SHAFT_HEIGHT / 2 + 2, 1,  SHAFT_HEIGHT - 4);
			g.setColor(color);
			g.fillOval(position.x - JOINT_WIDTH / 2, position.y - JOINT_HEIGHT / 2, JOINT_WIDTH, JOINT_HEIGHT);
		}
	}
	
	private class RotatedBamboo extends Bamboo {
		
		private double rotation;
		
		public RotatedBamboo(int x, int y, double rotation, Color color) {
			super(x, y, color);
			this.rotation = rotation;
		}
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.rotate(rotation, position.x, position.y);
			super.draw(g);
			g2.rotate(-rotation, position.x, position.y);
		}
	}
	
	public static void main(String[] args)
	{
		JFrame	frame = new JFrame();

		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Bamboo Tiles");

		frame.add(new BambooTile(2));
		frame.add(new BambooTile(3));
		frame.add(new BambooTile(4));
		frame.add(new BambooTile(5));
		frame.add(new BambooTile(6));
		frame.add(new BambooTile(7));
		frame.add(new BambooTile(8));
		frame.add(new BambooTile(9));

		frame.pack();
		frame.setVisible(true);
	}
}
