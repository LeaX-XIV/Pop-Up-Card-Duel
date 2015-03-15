package com.github.borione.gui.components;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.borione.crud.Card;
import com.github.borione.util.NumberUtils;
import java.awt.Dimension;

public class CardDrawn extends ImagePanel {
	
	private Card card;
	private String imagePath;
	Image background;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setContentPane(new CardDrawn(Card.factory(7)));
		f.setBounds(100, 100, 128, 192);
		f.setBackground(new Color(0, 255, 0, 0));
		f.setVisible(true);
	}
	
	
	private CardDrawn(Image image) {
		super(image);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public CardDrawn(Card c) {
		this(getImage(c));
		this.card = c;
		
		this.imagePath = NumberUtils.toNumDigits(c.getId(), 3) + ".png";
		
		background = getToolkit().createImage(getClass().getResource("/images/cards/" + NumberUtils.toNumDigits(c.getId(), 3) + ".png"));
		setOpaque(false);
		setSize(128, 192);
		setPreferredSize(new Dimension(128, 192));
		
		// FIXME: setSize(background.getWidth(null), background.getHeight(null));
	}
	
	private static Image getImage(Card c) {
		return new JPanel().getToolkit().createImage(CardDrawn.class.getResource("/images/cards/" + NumberUtils.toNumDigits(c.getId(), 3) + ".png"));
	}

}
