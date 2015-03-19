package com.github.borione.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

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
	
	public static void fitNameFont(JTextArea txtArea) {
		Font txtFont = txtArea.getFont();
		String txtText = txtArea.getText();

		int stringWidth = txtArea.getFontMetrics(txtFont).stringWidth(txtText);
		int componentWidth = txtArea.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(txtFont.getSize() * widthRatio);
		int componentHeight = txtArea.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		txtArea.setFont(new Font(txtFont.getName(), Font.PLAIN, fontSizeToUse));
	}
	
	public static BufferedImage stretch(String txt, Font font, int width, int heigth, Color back, Color fore) {
		// used to stretch the graphics instance sideways
        AffineTransform stretch = new AffineTransform();
        int w = width; // image width
        int h = heigth; // image height
        int f = font.getSize(); // Font size in px
        String s = txt;

        final BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setFont(new Font(font.getName(), font.getStyle(), f));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // paint BG
        g.setColor(back);
        g.fillRect(0, 0, w, h);
        g.setColor(fore);

        for (int i = 0; (i * f) + f <= h; i++) {
            g.drawString(s, 0, (i * f) + f);
            // stretch
            stretch.concatenate(AffineTransform.getScaleInstance(1.18, 1d));
            g.setTransform(stretch);

            // fade
//            Color c = g.getColor();
//            g.setColor(new Color (
//                    c.getRed(),
//                    c.getGreen(),
//                    c.getBlue(),
//                    (int)(c.getAlpha()*.75)));
        }

        g.dispose();

        return bi;
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
