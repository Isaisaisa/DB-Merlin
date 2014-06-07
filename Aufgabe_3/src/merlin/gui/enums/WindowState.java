package merlin.gui.enums;

import javax.swing.UnsupportedLookAndFeelException;

import merlin.gui.MerlinLogin;
import merlin.gui.MerlinMainWindow;
import merlin.gui.State;

public enum WindowState implements State{
	
//	private ExitCode exitCode = CANCEL_BUTTON_PUSHED;
	
	DATABASELOGIN() {
		public void nextState() {
			try {
				MerlinLogin.main();
//				DatabaseSetup.dispose(); 
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}},
	BIRDWATCHERLOGIN(){

			public void nextState() {
				try {
					MerlinMainWindow.main();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}},
	MAINWINDOW(){

		public void nextState() {
			// TODO Auto-generated method stub
			
		}};

		public abstract void nextState();
	

	
}

 
