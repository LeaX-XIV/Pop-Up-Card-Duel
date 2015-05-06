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
	public static Font defaultFont;
	
	public static void main(String[] args) {
		// FIXME: FUCKING FONT EXTENSION
//		cardFont = FontUtils.registerFont(Main.class.getResource("/font/card_font.nftr").toString());
//		defaultFont = FontUtils.registerFont(Main.class.getResource("/font/bleeding_cowboys.ttt").toString());

		new Login();
	}
}
