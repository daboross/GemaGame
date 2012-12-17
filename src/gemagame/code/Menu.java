package gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class Menu {

	MainClass mainClass;
	Image upperImage;

	public Menu(MainClass mainClassSet) {
		mainClass = mainClassSet;
		try {
			URL base = mainClass.getDocumentBase();
			URL imageBase = new URL(base, "gemagame/data/images/menu/");
			upperImage = mainClass.getImage(imageBase, "upperImage.png");
			System.out.println("Loaded Images -Menu");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Images Failed -Menu");
		}
	}

	public void paint(Graphics g) {
		g.drawImage(upperImage, 0, 0, null);
	}
}
