package com.github.borione.gui.components;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.borione.crud.Card;
import com.github.borione.main.Main;
import com.github.borione.util.FontUtils;
import com.github.borione.util.ListUtils;
import com.github.borione.util.NumberUtils;
import com.github.borione.util.StringUtils;

import java.awt.Dimension;

import javax.swing.SwingConstants;

public class CardDrawn extends ImagePanel {
	
	private Card card;
	private String imagePath;
	private Image background;
	private JLabel esper;
	private JLabel attack;
	private JLabel description;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setContentPane(new CardDrawn(Card.factory(6)));
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
		setLayout(null);
		
		this.imagePath = NumberUtils.toNumDigits(c.getId(), 3) + ".png";
		
		background = getToolkit().createImage(getClass().getResource("/images/cards/" + NumberUtils.toNumDigits(c.getId(), 3) + ".png"));
		setOpaque(false);
		setSize(128, 192);
		setPreferredSize(new Dimension(128, 192));
		
		// FIXME: setSize(background.getWidth(null), background.getHeight(null));
		
		esper = new JLabel(c.getEsperName());
		esper.setBounds(33, 75, 60, 10);
		esper.setForeground(Color.WHITE);
//		esper.setFont(Main.cardFont);
		FontUtils.fitNameFont(esper);
		add(esper);
		
		attack = new JLabel(c.getAttack());
		attack.setHorizontalAlignment(SwingConstants.LEFT);
		attack.setBounds(33, 90, 60, 10);
		attack.setForeground(Color.WHITE);
//		attack.setFont(Main.cardFont);
		FontUtils.fitNameFont(attack);
		add(attack);
		
		description = new JLabel(ListUtils.toString(c.retriveCpCost(), "\n\n"));
		description.setBounds(20, 107, 85, 53);
		FontUtils.fitNameFont(description);
		add(description);
	}
	
	private static Image getImage(Card c) {
		return new JPanel().getToolkit().createImage(CardDrawn.class.getResource("/images/cards/" + NumberUtils.toNumDigits(c.getId(), 3) + ".png"));
	}
}
