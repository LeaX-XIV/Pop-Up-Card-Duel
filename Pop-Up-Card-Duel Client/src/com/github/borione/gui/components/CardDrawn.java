package com.github.borione.gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import com.github.borione.crud.Card;
import com.github.borione.util.FontUtils;
import com.github.borione.util.ImageUtils;
import com.github.borione.util.NumberUtils;

import java.awt.Dimension;

import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardDrawn extends ImagePanel {

	private Card card;
	private String imagePath;
	private Image background;
	private JLabel esper;
	private JLabel attack;
	private JTextPane description;
	
	public static final int DEFAULT_WIDTH = 256;
	public static final int DEFAULT_HEIGHT = 384;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setContentPane(new CardDrawn(Card.factory(1)));
		f.setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		f.setBackground(new Color(0, 255, 0, 0));
		f.setVisible(true);
	}

	public CardDrawn(Image image) {
		super(image);
		setOpaque(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
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
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		// FIXME: setSize(background.getWidth(null), background.getHeight(null));

		esper = new JLabel(c.getEsperName());
		esper.setBounds(70, 150, 120, 14);
		esper.setForeground(Color.WHITE);
		//		esper.setFont(Main.cardFont);
		FontUtils.fitNameFont(esper);
		esper.setFont(new Font(esper.getFont().getName(), Font.BOLD, esper.getFont().getSize()));
		add(esper);

		attack = new JLabel();
		attack.setHorizontalAlignment(SwingConstants.RIGHT);
		attack.setBounds(65, 170, 125, 35);
		//		attack.setFont(Main.cardFont);
		//		FontUtils.fitNameFont(attack);
		attack.setIcon(new ImageIcon(FontUtils.stretch(c.getAttack(), new Font(attack.getFont().getName(), Font.BOLD, 24), attack.getWidth(), attack.getHeight(), new Color(0, 255, 0, 0), Color.WHITE)));
		add(attack);		

		description = new JTextPane();
		description.setContentType("text/html");

		try {
			description.setPage("http://37.59.123.99/popup/cardDescription.php?card=" + c.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		description.setOpaque(false);
		// FIXME: FONT SIZE
		description.setFont(new Font("Arial", Font.BOLD, 12));
		//		FontUtils.fitNameFont(description);
		description.setSelectionColor(new Color(0, 255, 0, 0));
		description.setSelectedTextColor(Color.BLACK);
		description.setEditable(false);
		description.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		description.setAutoscrolls(false);
		description.setBounds(30, 205, 190, 115);
		description.setBackground(new Color(0, 255, 0, 0));
		description.setForeground(new Color(0, 255, 0, 0));
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
