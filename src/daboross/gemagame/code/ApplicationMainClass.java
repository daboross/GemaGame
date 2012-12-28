package daboross.gemagame.code;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ApplicationMainClass extends JFrame implements MainClass {
	public ApplicationMainClass(ObjectHandler objectHandler) {
		setSize(640, 480);
		setTitle("Gema Game");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		objectHandler.setFocused(false);
		objectHandler.setApplet(false);
		objectHandler.setMainClass(this);
		objectHandler.setjFrame(this);
		new LoadingScreen(objectHandler);
	}

	public static void main(String[] args) {
		ObjectHandler objectHandler = new ObjectHandler();
		new ApplicationMainClass(objectHandler);
	}
}