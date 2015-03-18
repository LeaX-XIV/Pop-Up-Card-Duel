package com.github.borione.main;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import com.github.borione.gui.Login;
import com.github.borione.util.FontUtils;
import com.github.borione.util.ImageUtils;
import com.github.borione.util.NumberUtils;

public class Main {
	
	public static Font cardFont;
	
	public static void main(String[] args) {
		// FIXME: FUCKING FONT EXTENSION
		cardFont = FontUtils.registerFont(Main.class.getResource("/font/card_font.nftr").toString());

//		new Login();
		
		resizeCards();
	}

	private static void resizeCards() {
		for(int i = 0; i < new File("C:/Users/Quarta/Desktop/cards").list().length; i++) {
			BufferedImage img = ImageUtils.readImage("C:/Users/Quarta/Desktop/cards/" + NumberUtils.toNumDigits(i, 3) + ".png");
			img = ImageUtils.resizeBetter(img, 256, 384);
			ImageUtils.writeImage(img, "C:/Users/Quarta/Documents/GitHub/Pop-Up-Card-Duel/Pop-Up-Card-Duel Client/res/images/cards/" + NumberUtils.toNumDigits(i, 3) + ".png", ".png");
		}
	}

}
