import java.awt.*;
import java.net.URL;

import javax.swing.*;

public class PictureTile extends Tile {
	private String name;
	protected ImageIcon image;
	
	public PictureTile(String name) {
		this.name = name;
		URL url = PictureTile.class.getResource("images/" + name + ".png");
		if (url != null) {
			image = new ImageIcon(url);
		} else {
			System.err.println("Could not load image: images/" + name + ".png");
			image = new ImageIcon();
		}
		setToolTipText(toString());
	}
	
	public String toString() { return name; }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		image.paintIcon(this, g, PADDING + THICK + (FACE_LENGTH - image.getIconWidth()) / 2, PADDING + (FACE_LENGTH - image.getIconHeight()) / 2);
	}
	
	public static void main(String[] args)
	{
		JFrame	frame = new JFrame();

		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Picture Tiles");

		frame.add(new Bamboo1Tile());

		frame.add(new FlowerTile("Chrysanthemum"));
		frame.add(new FlowerTile("Orchid"));
		frame.add(new FlowerTile("Plum"));
		frame.add(new FlowerTile("Bamboo"));

		frame.add(new SeasonTile("Spring"));
		frame.add(new SeasonTile("Summer"));
		frame.add(new SeasonTile("Fall"));
		frame.add(new SeasonTile("Winter"));

		frame.pack();
		frame.setVisible(true);
	}
}
