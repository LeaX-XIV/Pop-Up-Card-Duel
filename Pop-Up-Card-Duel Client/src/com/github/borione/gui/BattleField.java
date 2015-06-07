package com.github.borione.gui;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.github.borione.crud.Avatar;
import com.github.borione.crud.Card;
import com.github.borione.crud.CardColor;
import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.gui.components.CardDrawn;
import com.github.borione.gui.components.ColoredProgressBar;
import com.github.borione.gui.components.ImagePanel;
import com.github.borione.gui.components.Timer2D;
import com.github.borione.util.Consts;
import com.github.borione.util.ImageUtils;
import com.github.borione.util.NumberUtils;
import com.github.borione.util.StringUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

public class BattleField extends JPanel {

	private Socket sk;
	private DataOutputStream output;
	private BufferedReader input;

	private JPanel opponentPane;
	private JPanel opponentDeckPane;
	private JPanel opponentHandPane;
	private JPanel opponentAvatarPane;
	private JPanel panel;
	private JLabel opponentAvatar;
	private ColoredProgressBar opponentHP;
	private JPanel opponentColorCardPane;
	private JPanel selfPane;
	private JPanel selfAvatarPane;
	private JLabel selfAvatar;
	private ColoredProgressBar selfHP;
	private JPanel selfHandPane;
	private JPanel selfDeckPane;
	private JLabel selfDeckBack;
	private JLabel opponentDeckBack;
	private Timer2D timer;

	public static void main(String[] args) {
		MainGui mg = new MainGui();
		BattleField m1 = new BattleField(Player.factory("LeaX_XIV"), Deck.factory(1), Player.factory("admin"), new Socket());
		mg.getContentPane().add(m1, BorderLayout.CENTER);
		mg.setVisible(true);
		m1.populateView();
	}

	/**
	 * Create the panel.
	 */
	public BattleField(Player self, Deck selfDeck, Player opponent, Socket sk) {
		setLayout(new GridLayout(2, 0, 0, 0));

		this.sk = sk;
		try {
			input = new BufferedReader(new InputStreamReader(this.sk.getInputStream()));
			output = new DataOutputStream(sk.getOutputStream());
		} catch(IOException e) {
			// Do something
		}

		opponentPane = new JPanel();
		add(opponentPane);
		opponentPane.setLayout(new MigLayout("", "[275.00px][515.00,grow][275.00]", "[240.00px,grow]"));

		opponentDeckPane = new JPanel();
		opponentDeckPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		opponentPane.add(opponentDeckPane, "cell 0 0,grow");
		opponentDeckPane.setLayout(new BorderLayout(0, 0));

		opponentDeckBack = new JLabel("");
		opponentDeckBack.setIcon(new ImageIcon(BattleField.class.getResource("/images/cards/back.png")));
		opponentDeckPane.add(opponentDeckBack);

		opponentHandPane = new JPanel();
		opponentHandPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		opponentPane.add(opponentHandPane, "cell 1 0,grow");
		opponentHandPane.setLayout(new GridLayout(0, 3, 0, 0));

		opponentAvatarPane = new JPanel();
		opponentAvatarPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		opponentPane.add(opponentAvatarPane, "cell 2 0,grow");
		opponentAvatarPane.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		opponentAvatarPane.add(panel);

		opponentAvatar = new JLabel("");
		try {
			// $ Opponent Avatar
			String url = "http://" + Consts.SERVER + "/" + Consts.AVATAR_PATH + Avatar.factory(opponent.getAvatar()).getPath();
			opponentAvatar.setIcon(new ImageIcon(ImageUtils.resizeBetter(ImageUtils.getImageFromWeb(url), 100, 100)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		opponentAvatar.setSize(new Dimension(100, 100));
		opponentAvatar.setPreferredSize(new Dimension(100, 100));
		opponentAvatar.setMinimumSize(new Dimension(100, 100));

		opponentHP = new ColoredProgressBar();
		opponentHP.setMaximum(20);
		opponentHP.addColorRange(0.0, Color.RED);
		opponentHP.addColorRange(0.25, Color.YELLOW);
		opponentHP.addColorRange(0.5, Color.GREEN);
		opponentHP.setValue(20);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap(41, Short.MAX_VALUE)
						.addComponent(opponentAvatar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap((275 / 2) - (100 / 2)))
						.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap(18, Short.MAX_VALUE)
								.addComponent(opponentHP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap((275 / 2) - (146 / 2)))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(20)
						.addComponent(opponentAvatar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(5)
						.addComponent(opponentHP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				);
		panel.setLayout(gl_panel);

		opponentColorCardPane = new JPanel();
		opponentColorCardPane.setSize(new Dimension(0, 50));
		opponentColorCardPane.setPreferredSize(new Dimension(10, 50));
		opponentAvatarPane.add(opponentColorCardPane, BorderLayout.SOUTH);
		opponentColorCardPane.setLayout(new GridLayout(0, 3, 0, 0));

		selfPane = new JPanel();
		add(selfPane);
		selfPane.setLayout(new MigLayout("", "[270.00px][515.00,grow,fill][275.00]", "[240.00px,grow]"));

		selfAvatarPane = new JPanel();
		selfAvatarPane.setBorder(new LineBorder(new Color(0, 0, 0)));

		selfAvatar = new JLabel("");
		try {
			String url = "http://" + Consts.SERVER + "/" + Consts.AVATAR_PATH + "/" + Avatar.factory(self.getAvatar()).getPath();
			selfAvatar.setIcon(new ImageIcon(ImageUtils.resizeBetter(ImageUtils.getImageFromWeb(url), 100, 100)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selfAvatar.setPreferredSize(new Dimension(100, 100));
		selfAvatar.setSize(new Dimension(100, 100));
		selfAvatar.setMinimumSize(new Dimension(100, 100));

		selfHP = new ColoredProgressBar();
		selfHP.setMaximum(20);
		selfHP.addColorRange(0.0, Color.RED);
		selfHP.addColorRange(0.25, Color.YELLOW);
		selfHP.addColorRange(0.5, Color.GREEN);
		selfHP.setValue(20);
		selfPane.add(selfAvatarPane, "cell 0 0,grow");

		timer = new Timer2D();
		GroupLayout gl_selfAvatarPane = new GroupLayout(selfAvatarPane);
		gl_selfAvatarPane.setHorizontalGroup(
				gl_selfAvatarPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selfAvatarPane.createSequentialGroup()
						.addGroup(gl_selfAvatarPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_selfAvatarPane.createSequentialGroup()
										.addGap(87)
										.addComponent(selfAvatar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_selfAvatarPane.createSequentialGroup()
												.addGap(64)
												.addComponent(selfHP, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_selfAvatarPane.createSequentialGroup()
														.addGap(35)
														.addComponent(timer, 200, 200, 200)))
														.addContainerGap(15, Short.MAX_VALUE))
				);
		gl_selfAvatarPane.setVerticalGroup(
				gl_selfAvatarPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selfAvatarPane.createSequentialGroup()
						.addGap(20)
						.addComponent(selfAvatar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(5)
						.addComponent(selfHP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(35)
						.addComponent(timer, 200, 200, 200)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		selfAvatarPane.setLayout(gl_selfAvatarPane);

		selfHandPane = new JPanel();
		selfHandPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		selfPane.add(selfHandPane, "cell 1 0,grow");
		selfHandPane.setLayout(new GridLayout(0, 3, 0, 0));

		selfDeckPane = new JPanel();
		selfDeckPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		selfPane.add(selfDeckPane, "cell 2 0,grow");
		selfDeckPane.setLayout(new BorderLayout(0, 0));

		selfDeckBack = new JLabel("");
		selfDeckBack.setIcon(new ImageIcon(BattleField.class.getResource("/images/cards/back.png")));
		selfDeckPane.add(selfDeckBack, BorderLayout.CENTER);



		new Thread() {
			public void run() {

				List<Card> deck = selfDeck.retriveCards();
				List<Card> hand = new ArrayList<Card>();

				// Shuffle
				long seed = System.nanoTime();
				Collections.shuffle(deck, new Random(seed));

				for(int i = 0; i < 3; i++) {	// Populate my hand, 1 card at a time
					Card first = deck.remove(0);
					hand.add(first);
					JPanel cd = new CardDrawn(first);
					//					cd.setPreferredSize(new Dimension(selfHandPane.getWidth() / 3, selfHandPane.getHeight()));
					//					cd.setSize(new Dimension(selfHandPane.getWidth() / 3, selfHandPane.getHeight()));
					//					cd.setMaximumSize(new Dimension(selfHandPane.getWidth() / 3, selfHandPane.getHeight()));
					//					cd.setMinimumSize(new Dimension(selfHandPane.getWidth() / 3, selfHandPane.getHeight()));
					selfHandPane.add(cd);

					// Populate opponent's hand, 1 card at a time
					// Cover cards
					Image img = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/cards/back.png").getFile()));
					img = ImageUtils.rotate(img, 180);
					//					img = ImageUtils.resizeBetter(img, opponentHandPane.getWidth() / 3, opponentHandPane.getHeight() / 3);
					JPanel cc = new CardDrawn(img);
					//					cc.setPreferredSize(new Dimension(opponentHandPane.getWidth() / 3, opponentHandPane.getHeight()));
					//					cc.setSize(new Dimension(opponentHandPane.getWidth() / 3, opponentHandPane.getHeight()));
					//					cc.setMaximumSize(new Dimension(opponentHandPane.getWidth() / 3, opponentHandPane.getHeight()));
					//					cc.setMinimumSize(new Dimension(opponentHandPane.getWidth() / 3, opponentHandPane.getHeight()));
					opponentHandPane.add(cc);

				}

				// SEND COLOR
				String message = "COLOR" + Consts.SEPARATOR + hand.get(0).getColor().toString()
						+ Consts.SEPARATOR + hand.get(1).getColor().toString()
						+ Consts.SEPARATOR + hand.get(2).getColor().toString() + "\n";
				try {
					output.writeBytes(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				try {	// READ OPPONENT'S COLORS
					String colors = input.readLine();
					
					if(colors.startsWith("COLOR")) {
						String temp = colors.substring(colors.indexOf(Consts.SEPARATOR) + 1);
						String color1 = temp.substring(0, temp.indexOf(Consts.SEPARATOR));
						temp = temp.substring(color1.length() + 1);
						String color2 = temp.substring(0, temp.indexOf(Consts.SEPARATOR));
						temp = temp.substring(color2.length() + 1);
						String color3 = temp.substring(0, temp.length());
						
						System.out.print(color1 + "\n" + color2 + "\n" + color3);
						
						opponentColorCardPane.removeAll();
						
						// PRINT COLORS
						if(color1.equals(CardColor.RED.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/red_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color1.equals(CardColor.GREEN.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/green_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color1.equals(CardColor.BLUE.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/blue_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color1.equals(CardColor.YELLOW.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/yellow_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color1.equals(CardColor.GRAY.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/gray_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						}
						
						if(color2.equals(CardColor.RED.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/red_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color2.equals(CardColor.GREEN.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/green_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color2.equals(CardColor.BLUE.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/blue_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color2.equals(CardColor.YELLOW.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/yellow_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color2.equals(CardColor.GRAY.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/gray_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						}
						if(color3.equals(CardColor.RED.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/red_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color3.equals(CardColor.GREEN.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/green_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color3.equals(CardColor.BLUE.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/blue_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color3.equals(CardColor.YELLOW.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/yellow_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						} else if(color3.equals(CardColor.GRAY.toString())) {
							Image i = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/gray_card_miniature.png").getFile()));
							JPanel p = new ImagePanel(i);
							opponentColorCardPane.add(p);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}






			}
		}.run();

		setVisible(true);
	}


	public void populateView() {

		Image img = ImageUtils.readImage(StringUtils.changeSpaces(BattleField.class.getResource("/images/cards/back.png").getFile()));
		img = ImageUtils.resizeBetter(img, selfDeckPane.getWidth(), selfDeckPane.getHeight());
		selfDeckBack.setIcon(new ImageIcon(img));

		img = ImageUtils.resizeBetter(img, opponentDeckPane.getWidth(), opponentDeckPane.getHeight());
		img = ImageUtils.rotate(img, 180);
		opponentDeckBack.setIcon(new ImageIcon(img));

		revalidate();
		repaint();
	}
}
