import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

public class CharacterTile extends Tile {
	protected char symbol;
	
	private boolean isNum;

	private static HashMap<Character, String> specialNames = new HashMap<>();
	private static HashMap<Character, Character> specialSymbols = new HashMap<>();
	static {
		specialNames.put('N', "North Wind");
		specialNames.put('E', "East Wind");
		specialNames.put('S', "South Wind");
		specialNames.put('W', "West Wind");
		specialNames.put('C', "Red Dragon");
		specialNames.put('F', "Green Dragon");
		
		specialSymbols.put('1', '\u4E00');
		specialSymbols.put('2', '\u4E8C');
		specialSymbols.put('3', '\u4E09');
		specialSymbols.put('4', '\u56DB');
		specialSymbols.put('5', '\u4E94');
		specialSymbols.put('6', '\u516D');
		specialSymbols.put('7', '\u4E03');
		specialSymbols.put('8', '\u516B');
		specialSymbols.put('9', '\u4E5D');
		specialSymbols.put('N', '\u5317');
		specialSymbols.put('E', '\u6771');
		specialSymbols.put('S', '\u5357');
		specialSymbols.put('W', '\u897F');
		specialSymbols.put('C', '\u4E2D');
		specialSymbols.put('F', '\u767C');
	}

	public CharacterTile(char symbol) {
		this.symbol = symbol;
		setToolTipText(toString());

		//Check if dragon or number tile
		int num = symbol - '0';
		isNum = false;
		if (num >= 1 && num <= 9) isNum = true;
		
	}

	public boolean matches(Tile other) {
		return super.matches(other) && ((CharacterTile) other).symbol == symbol;
	}

	public String toString() {
		String specialName = specialNames.get(symbol);
		if (specialName != null)
			return specialName;
		return "Character " + symbol;
	}
	
	public String toChinese() {
		return specialSymbols.get(symbol) + "";
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Declare some reusable variables
		Font f;
		FontMetrics fm;
		int w; // string width
		int y; // y coordinate for drawing strings
		float fs; // font size
		
		//Draw the Chinese character
		if (isNum) {
			fs = 28;
			y = 32;
		} else {
			fs = 40;
			y = 52;
		}
		f = g.getFont().deriveFont(fs);
		g.setFont(f);
		fm = g.getFontMetrics();
		w = fm.stringWidth(toChinese());
		g.setColor(new Color(0,100,100));
		g.drawString(toChinese(), (FACE_LENGTH - w) / 2 + THICK + PADDING, y + PADDING);
		
		//Draw the alphanumeric symbol
		f = g.getFont().deriveFont(14F);
		g.setFont(f);
		fm = g.getFontMetrics();
		w = fm.stringWidth(Character.toString(symbol));
		g.setColor(new Color(0,100,100));
		g.drawString(Character.toString(symbol), THICK + FACE_LENGTH - w - 2 - PADDING, 16 + PADDING);

		//Draw the wan character
		if (isNum) {
			f = g.getFont().deriveFont(24F);
			g.setFont(f);
			fm = g.getFontMetrics();
			w = fm.stringWidth(Character.toString('\u842C'));
			g.setColor(new Color(220,50,50));
			g.drawString(Character.toString('\u842C'), (FACE_LENGTH - w) / 2 + THICK + PADDING, PADDING + FACE_LENGTH - 10);
		}
		
	}
	
	public static void main(String[] args)
	{
		JFrame		frame = new JFrame();
		JPanel		tiles = new JPanel();
		JScrollPane	scroller = new JScrollPane(tiles);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Character Tiles");
		frame.add(scroller);

		// Try something like this if your tiles don't fit on the screen.
		// Replace "tile width" and "tile height" with your values.
		//scroller.setPreferredSize(new Dimension(8 * tile width, 40 + tile height));

		tiles.add(new CharacterTile('1'));
		tiles.add(new CharacterTile('2'));
		tiles.add(new CharacterTile('3'));
		tiles.add(new CharacterTile('4'));
		tiles.add(new CharacterTile('5'));
		tiles.add(new CharacterTile('6'));
		tiles.add(new CharacterTile('7'));
		tiles.add(new CharacterTile('8'));
		tiles.add(new CharacterTile('9'));
		tiles.add(new CharacterTile('N'));
		tiles.add(new CharacterTile('E'));
		tiles.add(new CharacterTile('W'));
		tiles.add(new CharacterTile('S'));
		tiles.add(new CharacterTile('C'));
		tiles.add(new CharacterTile('F'));

		frame.pack();
		frame.setVisible(true);
	}
}
