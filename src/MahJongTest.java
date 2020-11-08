import java.awt.*;
import javax.swing.*;

public class MahJongTest extends JFrame
{
	public MahJongTest()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new TestPanel());

		setSize(500, 500);
		setVisible(true);
	}

	public class TestPanel extends JPanel
	{
		public TestPanel()
		{
			setLayout(null);		// requires a setSize in Tile!!!!!!!!!!!
		
			Tile t;

			t = new SeasonTile("Spring");
			t.setLocation(200, 100);
			add(t);
			System.out.println(getComponentZOrder(t));

			t = new SeasonTile("Summer");
			t.setLocation(200 - Tile.THICK, 100 + Tile.THICK);
			add(t);
			System.out.println(getComponentZOrder(t));

			t = new SeasonTile("Fall");
			t.setLocation(200 - Tile.THICK * 2, 100 + Tile.THICK * 2);
			add(t);
			System.out.println(getComponentZOrder(t));

			t = new SeasonTile("Winter");
			t.setLocation(200 - Tile.THICK * 3, 100 + Tile.THICK * 3);
			add(t);
			System.out.println(getComponentZOrder(t));
		}
	}

	public static void main(String[] args)
	{
		new MahJongTest();
	}
}

