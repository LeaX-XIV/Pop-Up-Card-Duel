package com.github.borione.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public class FontUtils {

	public static void fitNameFont(JLabel lbl) {
		Font labelFont = lbl.getFont();
		String labelText = lbl.getText();

		int stringWidth = lbl.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = lbl.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = lbl.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		lbl.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}
	
	public static Font registerFont(String fileName) {
		try {
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fileName)));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		Font f = new Font("card_font", Font.PLAIN, 12);
		
		return f;
	}
}
