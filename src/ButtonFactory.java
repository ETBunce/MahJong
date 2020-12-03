//package	common;

import	java.awt.*;
import	java.awt.event.*;
import	java.io.*;
import	java.net.*;
import	java.util.*;
import	javax.swing.*;
import	java.beans.*;


/**
 * Provides factory methods to create several kinds of buttons.
 * Target classes (i.e., the classes that define the event handler methods) must be public.
 * Event handler methods must be public and, unless otherwise noted, may not have arguments:<br>
 * <code>public void method() { . . . }</code>
 */


public class ButtonFactory
{
	/**
	 * Makes a button displaying an image and having a tool tip.
	 * The button is the same size as the image (unless stretched by the container).
	 * @param file the name of the file containing the image that will be placed on the button.
	 * @param tip the tool tip text associated with the button.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @return the initialized JButton instance.
	 */

	public static JButton makeImageButton(String file, String tip, String method, Object target)
	{
		return makeImageButton(file, tip, method, target, true);
	}





	/**
	 * Makes a button displaying an image and having a tool tip.  The size of the button is determined by
	 * the size parameter.
	 * @param file the name of the file containing the image that will be placed on the button.
	 * @param tip the tool tip text associated with the button.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @param size true - size button to the image; false - do not resize button
	 * @return the initialized JButton instance.
	 */

	public static JButton makeImageButton(String file, String tip, String method, Object target, boolean size)
	{
		JButton		button;

		URL		url = target.getClass().getResource(file);
		ImageIcon	icon = new ImageIcon(url);

		button = new JButton(icon);
		if (size)
			button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

		button.setToolTipText(tip);				// adds tool tip text

		// sets up event handling: "method" is called when "button" is pressed
		button.addActionListener((ActionListener)EventHandler.create(ActionListener.class, target, method));

		return button;
	}



	/**
	 * Makes an array of image buttons from a file containing a list of button features.  Each button
	 * displays a label and has a tool tip.
	 * @param file the name of the file that contains the button descriptions. The file must have the
	 * following entries, one per line, for each button (blank or empty lines are ignored and my be used
	 * as button information separators):
	 * <ul>
	 * <li>Button label
	 * <li>Tool tip
	 * <li>Method name
	 * </ul>
	 * @param target the object in which the event handler methods are defined.
	 * @param size true - size button to the image; false - do not resize button
	 * @return an array of initialized JButton instances.
	 */

	public static JButton[] makeImageButtonArray(String file, Object target, boolean size)
	{
		ArrayList<JButton>	buttons = new ArrayList<JButton>();
		InputStream		stream = target.getClass().getResourceAsStream(file);
		InputStreamReader	reader = new InputStreamReader(stream);
		BufferedReader		input = new BufferedReader(reader);

		try
		{
			for (String imageFile = input.readLine(); imageFile != null; imageFile = input.readLine())
			{
				if (imageFile.length() == 0)
					continue;

				String	tip = input.readLine();
				String	method = input.readLine();

				buttons.add(makeImageButton(imageFile, tip, method, target));
			}
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "Error open button description file: " + ioe,
					"Button Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return buttons.toArray( new JButton[ buttons.size() ] );
	}





	/**	Adds an icon that is displayed when the button is selected.
	 * 	@param button the button on which the icon will be added
	 * 	@param file the image file containing the icon
	 */

	public static void setSelectedIcon(JToggleButton button, String file)
	{
		URL		url = ButtonFactory.class.getResource(file);
		ImageIcon	icon = new ImageIcon(url);
		button.setSelectedIcon(icon);
	}





	/**
	 * Makes a button displaying a label and having a tool tip.  The size of the button is determined
	 * by the label.
	 * @param label the button label.
	 * @param tip the tool tip text associated with the button.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @return the initialized JButton instance.
	 */

	public static JButton makeLabelButton(String label, String tip, String method, Object target)
	{
		JButton		button;

		button = new JButton(label);
		button.setToolTipText(tip);				// adds tool tip text

		// sets up event handling: "method" is called when "button" is pressed
		button.addActionListener((ActionListener)EventHandler.create(ActionListener.class, target, method));

		return button;
	}




	/**
	 * Makes an array of labeled buttons from a file containing a list of button features.  Each button
	 * displays a label and has a tool tip.  The size of the button is determined by the label.
	 * @param file the name of the file that contains the button descriptions.
	 * The file must have the following enteries, one per line, for each button (blank or empty lines
	 * are ignored and my be used as button information separators):
	 * <ul>
	 * <li>Button label
	 * <li>Tool tip
	 * <li>Method name
	 * </ul>
	 * @param target the object in which the event handler methods are defined.
	 * @return an array of initialized JButton instances.
	 */

	public static JButton[] makeLabelButtonArray(String file, Object target)
	{
		ArrayList<JButton>	buttons = new ArrayList<JButton>();
		InputStream		stream = target.getClass().getResourceAsStream(file);
		InputStreamReader	reader = new InputStreamReader(stream);
		BufferedReader		input = new BufferedReader(reader);

		try
		{
			for (String label = input.readLine(); label != null; label = input.readLine())
			{
				if (label.length() == 0)
					continue;

				String	tip = input.readLine();
				String	method = input.readLine();

				buttons.add(makeLabelButton(label, tip, method, target));
			}
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "Error open button description file: " + ioe,
					"Button Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return buttons.toArray( new JButton[ buttons.size() ] );
	}





	/**
	 * Configures an existing button with a tool tip and adds it to a proxy event listener.
	 * @param button the button that will be configured.
	 * @param tip the tool tip text associated with the button.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 */

	public static void configure(AbstractButton button, String tip, String method, Object target)
	{
		button.setToolTipText(tip);				// adds tool tip text

		// sets up event handling: "method" is called when "button" is pressed
		button.addActionListener((ActionListener)EventHandler.create(ActionListener.class, target, method));
	}





	/**
	 * Adds a text label to an existing button.
	 * The label is centered below the button.
	 * @param button the button on which the text will be placed
	 * @param label the string label appearing on the button.
	 */

	public static void addText(AbstractButton button, String text)
	{
		button.setText(text);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
	}





	/**
	 * Makes a toggle button displaying an image and having a tool tip.
	 * The size of the button is determined by the size parameter (see "size" below).
	 * The event handler method should have the following signature:<br>
	 * <code>public void method(Object button) { . . . }</code><br>
	 * <code>button</code> is a reference to the button that generated the event (i.e., was pressed).
	 * @param file the name of the file containing the image that will be placed on the button.
	 * @param tip the tool tip text associated with the button.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @param size true - size button to the image; false - do not resize button
	 * @return the initialized JButton instance.
	 */

	public static JToggleButton makeImageToggleButton(String file, String tip, String method,
			Object target, boolean size)
	{
		JToggleButton	button;

		URL		url = target.getClass().getResource(file);
		ImageIcon	icon = new ImageIcon(url);

		button = new JToggleButton(icon);
		if (size)
			button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

		button.setToolTipText(tip);				// adds tool tip text

		// sets up event handling: "method" is called when "button" is pressed
		button.addActionListener((ActionListener)EventHandler.create(ActionListener.class,
					target, method, "source"));

		return button;
	}





	/**
	 * Makes an unlabeled button (having neither a label nor an icon) but having a tool tip.
	 * The event handler method must have the following signature:<br>
	 * <code>public void method(Object button) { . . . }</code><br>
	 * <code>button</code> is a reference to the button that generated the event (i.e., was pressed).
	 * @param tip the tool tip text associated with the button.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @return the initialized JButton instance.
	 */

	public static JButton makePlainButton(String tip, String method, Object target)
	{
		JButton		button;

		button = new JButton();
		button.setToolTipText(tip);				// adds tool tip text
		button.setPreferredSize(new Dimension(20,20));
		button.setMaximumSize(new Dimension(20,20));

		// sets up event handling: "method" is called when "button" is pressed
		button.addActionListener((ActionListener)EventHandler.create(ActionListener.class,
					target, method, "source"));

		return button;
	}





	/**
	 * Makes a radio button displaying a label and having a tool tip; the button can be set as selected.
	 * The radio button is placed in button group provided by the caller.
	 * The event handler method must have the following signature:<br>
	 * <code>public void method(Object button) { . . . }</code><br>
	 * where <code>method</code> is the name of the event handler method and
	 * <code>button</code> is a reference to the button that generated the event (i.e., was pressed).
	 * @param label the label placed on the button.
	 * @param tip the tool tip text associated with the button.
	 * @param group the button group to which this button will be added.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @param selected determines if this buttons is selected (i.e., checked) or not.
	 * @return the initialized JRadioButton instance.
	 */

	public static JRadioButton makeRadioButton(String label, String tip, ButtonGroup group,
			String method, Object target, boolean selected)
	{
		JRadioButton	button;

		button = new JRadioButton(label, selected);

		button.setToolTipText(tip);				// adds tool tip text
		group.add(button);

		// sets up event handling: "method" is called when "button" is pressed
		button.addActionListener((ActionListener)EventHandler.create(ActionListener.class,
					target, method, "source"));

		return button;
	}




	/**
	 * Makes a radio button displaying a label and having a tool tip; the button is not selected by default.
	 * The radio button is placed in button group provided by the caller.
	 * The event handler method must have the following signature:<br>
	 * <code>public void method(Object button) { . . . }</code><br>
	 * where <code>method</code> is the name of the event handler method and
	 * <code>button</code> is a reference to the button that generated the event (i.e., was pressed).
	 * <br>This method is equivalent to<br>
	 * <code>makeRadioButton(label, tip, group, method, <i>false</i>)</code>
	 * @param label the label placed on the button.
	 * @param tip the tool tip text associated with the button.
	 * @param group the button group to which this button will be added.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @return the initialized JRadioButton instance.
	 */

	public static JRadioButton makeRadioButton(String label, String tip, ButtonGroup group,
			String method, Object target)
	{
		return makeRadioButton(label, tip, group, method, target, false);
	}





	/**
	 * Makes a check box displaying a label and having a tool tip.
	 * <code>public void method(Object button) { . . . }</code><br>
	 * <code>button</code> is a reference to the button that generated the event (i.e., was pressed).
	 * @param label the label placed on the button.
	 * @param tip the tool tip text associated with the button.
	 * @param method the name of the method called when the button is pressed (must be public).
	 * @param target the object in which the event handler method (above) is defined.
	 * @return the initialized JCheckBox instance.
	 */

	public static JCheckBox makeCheckBox(String label, String tip, String method, Object target)
	{
		JCheckBox	box;

		box = new JCheckBox(label);

		box.setToolTipText(tip);				// adds tool tip text

		// sets up event handling: "method" is called when "button" is pressed
		box.addActionListener((ActionListener)EventHandler.create(ActionListener.class, target,
					method, "source"));

		return box;
	}
}

