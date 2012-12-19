package daboross.gemagame.code;

import java.awt.Image;
import java.awt.event.KeyListener;
import java.net.URL;

public interface MainClass {
	public void paint(boolean gameOn);

	public Image getImage(URL url, String string);

	public URL getDocumentBase();

	public void addKeyListener(KeyListener keyListener);

	public void removeKeyListener(KeyListener keyListener);

	public void setVisible(boolean bool);
}
