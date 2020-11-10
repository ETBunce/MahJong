import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MahJong extends JFrame {
	
	private MahJongBoard board;

	public MahJong()
	{
		//Menu
		JMenuBar bar = new JMenuBar();
//		JMenu file = new JMenu("File");
//		JMenuItem save = new JMenuItem("Save",'S');
//		file.setMnemonic('F');
//		file.add(save);
//		bar.add(file);
		setJMenuBar(bar);
		MenuFactory.setMenuBar(bar);
		MenuFactory.newJMenu("File",KeyEvent.VK_F);
		
		//Board
		board = new MahJongBoard(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(board);
		
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MahJong();
	}

}

//jar cvmf manifest.txt MahJong.java *.class images