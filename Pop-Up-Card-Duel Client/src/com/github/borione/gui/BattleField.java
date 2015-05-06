package com.github.borione.gui;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.ImageIcon;

import javax.swing.JLabel;

import com.github.borione.crud.Avatar;
import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.gui.components.ColoredProgressBar;
import com.github.borione.util.Consts;
import com.github.borione.util.ImageUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

public class BattleField extends JPanel {
	
	private Socket sk;
	private DataOutputStream out;
	private BufferedReader in;
	
	private JPanel opponentPane;
	private JPanel opponentDeckPane;
	private JPanel opponentHandPane;
	private JPanel opponentAvatarPane;
	private JPanel panel;
	private JLabel opponentAvatar;
	private ColoredProgressBar opponentHP;
	private JPanel opponentColorCardPane;
	private JLabel colorCard1Lbl;
	private JLabel colorCard2Lbl;
	private JLabel colorCard3Lbl;
	private JPanel selfPane;
	private JPanel selfAvatarPane;
	private JLabel selfAvatar;
	private ColoredProgressBar selfHP;
	private JPanel selfHandPane;
	private JPanel selfDeckPane;
	
	public static void main(String[] args) {
		MainGui mg = new MainGui();
		BattleField m1 = new BattleField(Player.factory("LeaX_XIV"), Deck.factory(1), new Socket());
		mg.getContentPane().add(m1, BorderLayout.CENTER);
		mg.setVisible(true);
		m1.populateView();
	}

	/**
	 * Create the panel.
	 */
	public BattleField(Player self, Deck selfDeck, /*Player opponent, Deck opponentDeck*/ Socket sk) {
		setLayout(new GridLayout(2, 0, 0, 0));
		
		this.sk = sk;
		try {
			this.out = new DataOutputStream(this.sk.getOutputStream());
			this.in = new BufferedReader(new InputStreamReader(this.sk.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		opponentPane = new JPanel();
		add(opponentPane);
		opponentPane.setLayout(new MigLayout("", "[275.00px][515.00,grow][275.00]", "[240.00px,grow]"));
		
		opponentDeckPane = new JPanel();
		opponentDeckPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		opponentPane.add(opponentDeckPane, "cell 0 0,grow");
		
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
			String url = "http://" + Consts.SERVER + "/" + Consts.AVATAR_PATH + "/" + "avatar.png";
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
		opponentHP.setValue(6);
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
		
		colorCard1Lbl = new JLabel("");
		opponentColorCardPane.add(colorCard1Lbl);
		
		colorCard2Lbl = new JLabel("");
		opponentColorCardPane.add(colorCard2Lbl);
		
		colorCard3Lbl = new JLabel("");
		opponentColorCardPane.add(colorCard3Lbl);
		
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
		selfHP.setValue(13);
		selfPane.add(selfAvatarPane, "cell 0 0,grow");
		GroupLayout gl_selfAvatarPane = new GroupLayout(selfAvatarPane);
		gl_selfAvatarPane.setHorizontalGroup(
			gl_selfAvatarPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selfAvatarPane.createSequentialGroup()
					.addGap((275 / 2) - (100 / 2))
					.addComponent(selfAvatar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(40, Short.MAX_VALUE))
				.addGroup(gl_selfAvatarPane.createSequentialGroup()
					.addGap((275 / 2) - (146 / 2))
					.addComponent(selfHP, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		gl_selfAvatarPane.setVerticalGroup(
			gl_selfAvatarPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selfAvatarPane.createSequentialGroup()
					.addGap(20)
					.addComponent(selfAvatar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(selfHP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
		
		setVisible(true);
	}
	
	
	public void populateView() {	
		JLabel selfDeckBack = new JLabel("");
		Image img = ImageUtils.readImage(BattleField.class.getResource("/images/cards/back.png").toString());
		img = ImageUtils.resizeBetter(img, selfDeckPane.getWidth(), selfDeckPane.getHeight());
		
		selfDeckBack.setIcon(new ImageIcon(img));
		selfDeckPane.add(selfDeckBack, BorderLayout.CENTER);
	}
}
