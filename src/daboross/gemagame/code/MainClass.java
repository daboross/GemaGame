package daboross.gemagame.code;

import java.awt.event.*;

public interface MainClass {
	public void paint(Paintable objToPaint);

	public void keyListenerAdd(KeyListener keyListener);

	public void keyListenerRemove(KeyListener keyListener);

	public void mouseListenerAdd(MouseListener mouseListener);

	public void mouseListenerRemove(MouseListener mouseListener);

	public void focusListenerAdd(FocusListener focusListener);

	public void focusListenerRemove(FocusListener focusListener);
}
