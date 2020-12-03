import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MahJong extends JFrame {
	
	private MahJongBoard board;
	private int discardPadding = 6;
	private int discardSlotWidth = Tile.SIZE.width + discardPadding;
	private Fireworks fireworks;
	private Help instructionsHelp = new Help("help/instructions.html", "Instructions");
	private Help rulesHelp = new Help("help/rules.html", "Rules");

	public MahJong()
	{
		
		// Configure JFrame
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		// Make menu
		makeMenu();
		
		// Make Board
		newBoard();
		
		//Finish up
		pack();
		setVisible(true);
	}
	
	private void makeMenu() {
		
		// Build Menu
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		
		// Configure MenuFactory
		MenuFactory.configure(bar,this);
		
		// Game
		JMenu gameMenu = MenuFactory.newMenu("Game",KeyEvent.VK_G);
		MenuFactory.newItem("Play",KeyEvent.VK_P,KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK),gameMenu,"newGame");
		MenuFactory.newItem("Restart",KeyEvent.VK_R,KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_DOWN_MASK),gameMenu,"restartGame");
		MenuFactory.newItem("Numbered",KeyEvent.VK_N,KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_DOWN_MASK),gameMenu,"numberedGame");
		MenuFactory.newItem("Exit",KeyEvent.VK_E,KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_DOWN_MASK),gameMenu,"exit");
		
		// Move
		JMenu movesMenu = MenuFactory.newMenu("Moves",KeyEvent.VK_M);
		MenuFactory.newItem("Undo",KeyEvent.VK_U,KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK),movesMenu,"undo");
		MenuFactory.newItem("Removed Tiles",KeyEvent.VK_R,KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.ALT_DOWN_MASK),movesMenu,"showRemoved");
		
		// Sound
		JMenu helpMenu = MenuFactory.newMenu("Help",KeyEvent.VK_H);
		MenuFactory.newItem("Instructions",helpMenu,"showInstructions");
		MenuFactory.newItem("Game Rules",helpMenu,"showGameRules");
		
	}
	
	public void showRemoved() {
		Object[] message = new Object[1];
		JScrollPane sp = new JScrollPane();
		JPanel discard = board.getDiscardPanel();
		sp.setViewportView(discard);
		sp.setPreferredSize(new Dimension(600, 2 * discardSlotWidth + 35));
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		message[0] = sp;
		Rectangle r = new Rectangle(board.getUndoSize() * discardSlotWidth, 0, discardSlotWidth, discardSlotWidth + discardPadding);
		sp.getViewport().scrollRectToVisible(r);
		JOptionPane.showMessageDialog(this, message, "Removed Tiles", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void newBoard() {
		board = new MahJongBoard(this);
		fireworks = new Fireworks(board);
		add(board);
	}
	public void newBoard(long seed) {
		board = new MahJongBoard(this, seed);
		fireworks = new Fireworks(board);
		add(board);
	}
	
	public void resetBoard() {
		remove(board);
		fireworks.stop();
		newBoard();
	}

	public void resetBoard(long seed) {
		remove(board);
		fireworks.stop();
		newBoard(seed);
	}
	
	public void newGame() {
		int choice = JOptionPane.showConfirmDialog(this,"Start a new game?","New Game",JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION) {
			resetBoard();
			revalidate();
		}
	}

	public void numberedGame() {
		boolean success =  false;
		String seedString;
		long seed = 0;
		while (!success) {
			seedString = JOptionPane.showInputDialog(this,"Enter a game number", "Numbered Game", JOptionPane.PLAIN_MESSAGE);
			if (seedString != null) {
				try {
					seed = Long.parseLong(seedString);
					success = true;
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "Please enter a decimal number", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				break;
			}
		}
		if (success) {
			resetBoard(seed);
			revalidate();
		}
	}
	
	public void restartGame() {
		int choice = JOptionPane.showConfirmDialog(this,"Restart this game?","Restart Game",JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION) {
			fireworks.stop();
			board.restart();
			fireworks = new Fireworks(board);
			revalidate();
		}
	}
	
	public void win() {
		fireworks.fire();
	}
	
	public void exit() {
		int choice = JOptionPane.showConfirmDialog(this,"Exit program?","Exit",JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION) System.exit(0);
	}
	
	public void undo() {
		board.undo();
	}
	
	public void showInstructions() {
		instructionsHelp.display();
	}

	public void showGameRules() {
		rulesHelp.display();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MahJong();
	}

}

//jar cvmf manifest.txt MahJong.java *.class images