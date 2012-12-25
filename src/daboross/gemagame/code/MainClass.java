package daboross.gemagame.code;

import java.awt.event.KeyListener;

public interface MainClass {
	public void paint(Paintable objToPaint);

	public void keyListenerAdd(KeyListener keyListener);

	public void keyListenerRemove(KeyListener keyListener);
}
