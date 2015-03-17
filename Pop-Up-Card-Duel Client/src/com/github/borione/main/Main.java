package com.github.borione.main;

import java.awt.Font;

import com.github.borione.gui.Login;
import com.github.borione.util.FontUtils;

public class Main {
	
	public static Font cardFont;
	
	public static void main(String[] args) {
		// FIXME: FUCKING FONT EXTENSION
		cardFont = FontUtils.registerFont(Main.class.getResource("/font/card_font.nftr").toString());

		new Login();
	}

}
