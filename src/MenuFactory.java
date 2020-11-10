import java.awt.*;
import javax.swing.*;

public class MenuFactory {
	
	private static JMenuBar bar;
	
	public static void setMenuBar(JMenuBar newBar) {
		bar = newBar;
	}

	public static JMenu newJMenu(String name, int mnemonic) {
		if (bar == null) return null;
		JMenu menu = new JMenu(name);
		menu.setMnemonic(mnemonic);
		bar.add(menu);
		return menu;
	}
}
