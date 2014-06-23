package merlin.utils;

import java.awt.*;
import java.awt.event.*;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DialogExample {
	private static Dialog d;

	public static void show(String msg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		Frame window = new Frame();

		// Create a modal dialog
		d = new Dialog(window, "Alert", true);

		// Use a flow layout
		d.setLayout( new FlowLayout() );

		// Create an OK button
		Button ok = new Button ("OK");
		ok.addActionListener ( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				// Hide dialog
				DialogExample.d.setVisible(false);
			}
		});

		d.add( new Label (msg));
		d.add( ok );

		// Show dialog
		d.pack();
		d.setVisible(true);
	}
}