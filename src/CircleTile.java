import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;

public class CircleTile extends RankTile{
	
	private static final int SMALL = 12;
	private static final int MEDIUM = 18;
	private static final int LARGE = 24;
	private static final Color G = new Color(30, 200, 30);
	private static final Color B = Color.BLUE;
	private static final Color R = Color.RED;
	private static final Color GRAY = Color.GRAY;
	
	private ArrayList<Pip> circles = new ArrayList<>();
	
	public CircleTile(int rank) {
		super(rank);
		switch(rank) {
		case 1:
			circles.add(new Pancake(56,PADDING + THICK + FACE_LENGTH / 2,PADDING + FACE_LENGTH / 2));
			break;
		case 2:
			addCircle(LARGE,1/2F,2/7F,G,GRAY);
			addCircle(LARGE,1/2F,5/7F,R,GRAY);
			break;
		case 3:
			addCircle(MEDIUM,1/4F,1/4F,B,GRAY);
			addCircle(MEDIUM,1/2F,1/2F,R,GRAY);
			addCircle(MEDIUM,3/4F,3/4F,G,GRAY);
			break;
		case 4:
			addCircle(LARGE,2/7F,2/7F,B,GRAY);
			addCircle(LARGE,5/7F,2/7F,G,GRAY);
			addCircle(LARGE,2/7F,5/7F,G,GRAY);
			addCircle(LARGE,5/7F,5/7F,B,GRAY);
			break;
		case 5:
			addCircle(MEDIUM,1/5F,1/5F,B,GRAY);
			addCircle(MEDIUM,4/5F,1/5F,G,GRAY);
			addCircle(MEDIUM,1/5F,4/5F,G,GRAY);
			addCircle(MEDIUM,4/5F,4/5F,B,GRAY);
			addCircle(MEDIUM,1/2F,1/2F,R,GRAY);
			break;
		case 6:
			addCircle(MEDIUM,1/3F,3/16F,G,GRAY);
			addCircle(MEDIUM,2/3F,3/16F,G,GRAY);
			addCircle(MEDIUM,1/3F,1/2F,R,GRAY);
			addCircle(MEDIUM,2/3F,1/2F,R,GRAY);
			addCircle(MEDIUM,1/3F,13/16F,R,GRAY);
			addCircle(MEDIUM,2/3F,13/16F,R,GRAY);
			break;
		case 7:
			addCircle(SMALL,1/4F,1/4F,G,GRAY);
			addCircle(SMALL,1/2F,1/3F,G,GRAY);
			addCircle(SMALL,3/4F,5/12F,G,GRAY);
			addCircle(SMALL,1/3F,9/14F,R,GRAY);
			addCircle(SMALL,2/3F,9/14F,R,GRAY);
			addCircle(SMALL,1/3F,12/14F,R,GRAY);
			addCircle(SMALL,2/3F,12/14F,R,GRAY);
			break;
		case 8:
			addCircle(SMALL,1/3F,1/8F,B,GRAY);
			addCircle(SMALL,1/3F,3/8F,B,GRAY);
			addCircle(SMALL,1/3F,5/8F,B,GRAY);
			addCircle(SMALL,1/3F,7/8F,B,GRAY);
			addCircle(SMALL,2/3F,1/8F,B,GRAY);
			addCircle(SMALL,2/3F,3/8F,B,GRAY);
			addCircle(SMALL,2/3F,5/8F,B,GRAY);
			addCircle(SMALL,2/3F,7/8F,B,GRAY);
			break;
		case 9:
			addCircle(MEDIUM,1/5F,1/5F,G,GRAY);
			addCircle(MEDIUM,1/2F,1/5F,G,GRAY);
			addCircle(MEDIUM,4/5F,1/5F,G,GRAY);
			addCircle(MEDIUM,1/5F,1/2F,R,GRAY);
			addCircle(MEDIUM,1/2F,1/2F,R,GRAY);
			addCircle(MEDIUM,4/5F,1/2F,R,GRAY);
			addCircle(MEDIUM,1/5F,4/5F,B,GRAY);
			addCircle(MEDIUM,1/2F,4/5F,B,GRAY);
			addCircle(MEDIUM,4/5F,4/5F,B,GRAY);
			break;
		}
		setToolTipText(toString());
	}
	
	public String toString() { return "Circle " + rank;	}
		
	private void addCircle(int size, float xf, float yf, Color color1, Color color2) {
		circles.add(new Circle(size,(int)(PADDING + THICK + FACE_LENGTH * xf), (int)(PADDING + FACE_LENGTH * yf), color1,color2));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Pip c : circles) {
			c.draw(g);
		}
	}

	private abstract class Pip {
		protected Point position;
		protected Dimension size;
		
		public Pip(int size, int x, int y) {
			this.size = new Dimension(size,size);
			this.position = new Point(x,y);
		}
		
		public abstract void draw(Graphics g);
	}
	
	private class Circle extends Pip{
		
		private RadialGradientPaint paint;
		
		public Circle(int size, int x, int y, Color color1, Color color2) {
			super(size,x,y);
			Point2D center = new Point2D.Float(x,y);
			float radius = size/2;
			float[] dist = {0.2F,1F};
			Color[] colors = {color1,color2};
			paint = new RadialGradientPaint(center,radius,dist,colors);
		}
		
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setPaint(paint);
			g2.fillOval(position.x - size.width / 2, position.y - size.height / 2, size.width, size.height);
		}
	}
	
	private class Pancake extends Pip {
		
		private RadialGradientPaint paint;

		public Pancake(int size, int x, int y) {
			super(size, x, y);
			Point2D center = new Point2D.Float(x,y);
			float radius = size/4;
			float[] dist = {0f,0.25f,0.5f, 0.75f, 1f};
			Color[] colors = {R,B,G,B,R};
			paint = new RadialGradientPaint(center,radius,dist,colors,CycleMethod.REFLECT);
		}
		
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setPaint(paint);
			g2.fillOval(position.x - size.width / 2, position.y - size.height / 2, size.width, size.height);
		}
		
	}
	
	public static void main(String[] args)
	{
		JFrame	frame = new JFrame();

		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Circle Tiles");

		frame.add(new CircleTile(1));
		frame.add(new CircleTile(2));
		frame.add(new CircleTile(3));
		frame.add(new CircleTile(4));
		frame.add(new CircleTile(5));
		frame.add(new CircleTile(6));
		frame.add(new CircleTile(7));
		frame.add(new CircleTile(8));
		frame.add(new CircleTile(9));

		frame.pack();
		frame.setVisible(true);
	}
}
