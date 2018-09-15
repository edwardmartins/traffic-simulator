package utils;

import java.awt.Image;
import java.awt.Toolkit;

public class Images {
	
	public static Image loadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);   
	}

}
