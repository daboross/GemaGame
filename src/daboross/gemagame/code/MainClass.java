package daboross.gemagame.code;

import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface MainClass {
	public void paint(Paintable objToPaint);

	public void addKeyListener(KeyListener l);

	public void removeKeyListener(KeyListener l);

	public void addMouseListener(MouseListener l);

	public void removeMouseListener(MouseListener l);

	public void addFocusListener(FocusListener l);

	public void removeFocusListener(FocusListener l);

	public void addMouseMotionListener(MouseMotionListener l);

	public void removeMouseMotionListener(MouseMotionListener l);

	public int realX(int x);

	public int realY(int y);
}
