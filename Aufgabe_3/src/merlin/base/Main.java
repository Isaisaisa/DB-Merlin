package merlin.base;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		
		Application application = Application.getInstance();
		application.run();
	}
}
