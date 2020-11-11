import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import javax.swing.*;

public class MenuFactory {
	
	private static JMenuBar bar;
	private static Object target;
	
	public static void setMenuBar(JMenuBar newBar) {
		bar = newBar;
	}
	public static void setTarget(Object newTarget) {
		target = newTarget;
	}

	// newMenu
	public static JMenu newMenu(String name) {
		if (bar == null) return null;
		JMenu menu = new JMenu(name);
		bar.add(menu);
		return menu;
	}
	public static JMenu newMenu(String name, int mnemonic) {
		if (bar == null) return null;
		JMenu menu = newMenu(name);
		menu.setMnemonic(mnemonic);
		return menu;
	}
	
	// newItem
	public static JMenuItem newItem(String name) {
		if (bar == null) return null;
		JMenuItem item = new JMenuItem(name);
		return item;
	}
	public static JMenuItem newItem(String name, JMenu menu) {
		if (bar == null) return null;
		JMenuItem item = newItem(name);
		menu.add(item);
		return item;
	}
	public static JMenuItem newItem(String name, int mnemonic, JMenu menu) {
		if (bar == null) return null;
		JMenuItem item = newItem(name, menu);
		item.setMnemonic(mnemonic);
		return item;
	}
	public static JMenuItem newItem(String name, int mnemonic, KeyStroke accel, JMenu menu) {
		if (bar == null) return null;
		JMenuItem item = newItem(name, mnemonic, menu);
		item.setAccelerator(accel);
		return item;
	}
	public static JMenuItem newItem(String name, int mnemonic, KeyStroke accel, JMenu menu, String method) {
		if (bar == null || target == null) return null;
		JMenuItem item = newItem(name, mnemonic, accel, menu);
		item.addActionListener((ActionListener)EventHandler.create(ActionListener.class, target, method));
		return item;
	}
}
