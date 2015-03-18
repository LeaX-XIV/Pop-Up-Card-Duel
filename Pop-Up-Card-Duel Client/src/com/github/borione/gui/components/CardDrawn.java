package com.github.borione.gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.github.borione.crud.Card;
import com.github.borione.main.Main;
import com.github.borione.util.FontUtils;
import com.github.borione.util.ImageUtils;
import com.github.borione.util.ListUtils;
import com.github.borione.util.NumberUtils;
import com.github.borione.util.StringUtils;

import java.awt.Dimension;

import javax.swing.SwingConstants;

import sun.nio.ch.IOUtil;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardDrawn extends ImagePanel {
	
	private Card card;
	private String imagePath;
	private Image background;
	private JLabel esper;
	private JLabel attack;
	private JTextArea description;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setContentPane(new CardDrawn(Card.factory(5)));
		f.setBounds(100, 100, 256, 384);
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
//		this.card = c;
		setLayout(null);
		
//		this.imagePath = NumberUtils.toNumDigits(c.getId(), 3) + ".png";
//		Image bg = ImageUtils.readImage("images/cards/" + NumberUtils.toNumDigits(c.getId(), 3) + ".png");
//		background = ImageUtils.resizeBetter(bg, 256, 384);
		setOpaque(false);
		setSize(256, 384);
		setPreferredSize(new Dimension(256, 384));
		
		// FIXME: setSize(background.getWidth(null), background.getHeight(null));
		
		esper = new JLabel(c.getEsperName());
		esper.setBounds(70, 150, 120, 14);
		esper.setForeground(Color.WHITE);
//		esper.setFont(Main.cardFont);
		FontUtils.fitNameFont(esper);
		esper.setFont(new Font(esper.getFont().getName(), Font.BOLD, esper.getFont().getSize()));
		add(esper);
		
		attack = new JLabel(c.getAttack());
		attack.setHorizontalAlignment(SwingConstants.RIGHT);
		attack.setBounds(70, 165, 120, 35);
		attack.setForeground(Color.WHITE);
//		attack.setFont(Main.cardFont);
		FontUtils.fitNameFont(attack);
		attack.setFont(new Font(attack.getFont().getName(), Font.BOLD, attack.getFont().getSize()));
		add(attack);
		
		description = new JTextArea(ListUtils.toString(c.retriveCpCost(), "\n\n") + "\n\n" +  c.retrievePrimaryEffect());
		description.setOpaque(false);
		// FOXME: FONT SIZE
		description.setFont(new Font("Monospaced", Font.BOLD, 8));
//		FontUtils.fitNameFont(description);
		description.setSelectionColor(Color.WHITE);
		description.setSelectedTextColor(Color.BLACK);
		description.setEditable(false);
		description.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		description.setAutoscrolls(false);
		description.setBounds(40, 215, 180, 105);
		description.setBackground(new Color(0, 255, 0, 0));
		add(description);
	}
	
	private static Image getImage(Card c) {
		BufferedImage bg;
		try {
			bg = ImageIO.read(CardDrawn.class.getResource("/images/cards/" + NumberUtils.toNumDigits(c.getId(), 3) + ".png"));
			bg = ImageUtils.resiz(ImageUtils.toBufferedImage(bg), 256, 384);
			return bg;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
}
