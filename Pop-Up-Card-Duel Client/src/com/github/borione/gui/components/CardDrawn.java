package com.github.borione.gui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.github.borione.crud.Card;
import com.github.borione.crud.CardColor;
import com.github.borione.crud.CpCost;
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
import java.util.List;

public class CardDrawn extends ImagePanel {

	private Card card;
	private String imagePath;
	private Image background;
	private JLabel esper;
	private JLabel attack;
	private JTextPane description;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setContentPane(new CardDrawn(Card.factory(6)));
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

		attack = new JLabel();
		attack.setHorizontalAlignment(SwingConstants.RIGHT);
		attack.setBounds(65, 170, 125, 35);
		//		attack.setFont(Main.cardFont);
		//		FontUtils.fitNameFont(attack);
		attack.setIcon(new ImageIcon(FontUtils.stretch(c.getAttack(), new Font(attack.getFont().getName(), Font.BOLD, 24), attack.getWidth(), attack.getHeight(), new Color(0, 255, 0, 0), Color.WHITE)));
		add(attack);		

		description = new JTextPane();
		description.setContentType("text/html");

		List<CpCost> cps = c.retriveCpCost();

		if(!cps.isEmpty()) {
			description.setText("<html><b>&#60;Crystal Ability&#62;<br>CP: ");
			for (CpCost cpCost : cps) {
				for(int i = 0; i < cpCost.getCost(); i++) {
					if(cpCost.getCp().equals(Color.RED)) {
						description.setText(description.getText() + "<img src=\"37.59.123.99/popup/crystals/red_crystal.png\">");
					} else if(cpCost.getCp().equals(Color.GREEN)) {
						description.setText(description.getText() + "<img src=\"37.59.123.99/popup/crystals/green_crystal.png\">");
					} else if(cpCost.getCp().equals(Color.BLUE)) {
						description.setText(description.getText() + "<img src=\"37.59.123.99/popup/crystals/blue_crystal.png\">");
					} else if(cpCost.getCp().equals(Color.YELLOW)) {
						description.setText(description.getText() + "<img src=\"37.59.123.99/popup/crystals/yellow_crystal.png\">");
					}
				}
			}

			description.setText(description.getText() + "</b></html>");
		}
		description.setOpaque(false);
		// FIXME: FONT SIZE
		description.setFont(new Font("Monospaced", Font.BOLD, 12));
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
