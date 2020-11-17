import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MahJong extends JFrame {
	
	private MahJongBoard board;

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
		MenuFactory.setMenuBar(bar);
		MenuFactory.setTarget(this);
		
		// Game
		JMenu gameMenu = MenuFactory.newMenu("Game",KeyEvent.VK_G);
		MenuFactory.newItem("New Game",KeyEvent.VK_N,KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK),gameMenu,"newGame");
		MenuFactory.newItem("Exit",KeyEvent.VK_E,KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_DOWN_MASK),gameMenu,"exit");
		
		// Actions
		JMenu actionsMenu = MenuFactory.newMenu("Actions",KeyEvent.VK_A);
		MenuFactory.newItem("Undo",KeyEvent.VK_U,KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK),actionsMenu,"undo");
		
		// Help
		JMenu helpMenu = MenuFactory.newMenu("Help",KeyEvent.VK_H);
		
	}
	
	public void newBoard() {
		board = new MahJongBoard(this);
		add(board);
	}
	
	public void resetBoard() {
		remove(board);
		newBoard();
	}
	
	public void newGame() {
		int choice = JOptionPane.showConfirmDialog(this,"Start a new game?","New Game",JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION) {
			resetBoard();
			revalidate();
		}
	}
	
	public void exit() {
		int choice = JOptionPane.showConfirmDialog(this,"Exit program?","Exit",JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION) System.exit(0);
	}
	
	public void undo() {
		board.undo();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MahJong();
	}

}

//jar cvmf manifest.txt MahJong.java *.class images