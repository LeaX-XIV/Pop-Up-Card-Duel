package com.github.borione.gui.components;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * 
 * Uploaded by Michael Myers on stackoverflow.com
 * http://stackoverflow.com/users/13531/michael-myers
 *
 */
public class ImagePanel extends JPanel {
	
	private Image image;
	
	public ImagePanel(Image image) {
		this.image = image;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}