package com.github.borione.gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.github.borione.crud.Avatar;
import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.util.Consts;
import com.github.borione.util.ImageUtils;

import java.awt.Color;
import java.awt.Dimension;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BattleField extends JPanel {
	
	public static void main(String[] args) {
		MainGui mg = new MainGui();
		BattleField m1 = new BattleField(Player.factory("LeaX_XIV"), Deck.factory(1), Player.factory("CapraTheBest"), Deck.factory(2));
		mg.getContentPane().add(m1, BorderLayout.CENTER);
		mg.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public BattleField(Player self, Deck selfDeck, Player opponent, Deck opponentDeck) {
		setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel opponentPane = new JPanel();
		add(opponentPane);
		opponentPane.setLayout(new MigLayout("", "[275px][515px][275px,grow]", "[240.00,grow]"));
		
		JPanel opponentDeckPane = new JPanel();
		opponentPane.add(opponentDeckPane, "cell 0 0,grow");
		
		JPanel opponentHandPane = new JPanel();
		opponentPane.add(opponentHandPane, "cell 1 0,grow");
		opponentHandPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel opponentAvatarPane = new JPanel();
		opponentPane.add(opponentAvatarPane, "cell 2 0,grow");
		opponentAvatarPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		opponentAvatarPane.add(panel);
		
		JLabel opponentAvatar = new JLabel("");
		try {
			String url = "http://" + Consts.SERVER + "/" + Consts.AVATAR_PATH + "/" + Avatar.factory(opponent.getAvatar()).getPath();
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
		panel.add(opponentAvatar);
		opponentAvatar.setSize(new Dimension(100, 100));
		opponentAvatar.setPreferredSize(new Dimension(100, 100));
		opponentAvatar.setMinimumSize(new Dimension(100, 100));
		
		JProgressBar opponentHP = new JProgressBar();
		panel.add(opponentHP);
		opponentHP.setForeground(Color.GREEN);
		
		JPanel opponentColorCardPane = new JPanel();
		opponentColorCardPane.setSize(new Dimension(0, 50));
		opponentColorCardPane.setPreferredSize(new Dimension(10, 50));
		opponentAvatarPane.add(opponentColorCardPane, BorderLayout.SOUTH);
		opponentColorCardPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel colorCard1Lbl = new JLabel("");
		opponentColorCardPane.add(colorCard1Lbl);
		
		JLabel colorCard2Lbl = new JLabel("");
		opponentColorCardPane.add(colorCard2Lbl);
		
		JLabel colorCard3Lbl = new JLabel("");
		opponentColorCardPane.add(colorCard3Lbl);
		
		JPanel selfPane = new JPanel();
		add(selfPane);
		selfPane.setLayout(new MigLayout("", "[270.00px][515.00][275.00,grow]", "[240.00px,grow]"));
		
		JPanel selfAvatarPane = new JPanel();
		FlowLayout fl_selfAvatarPane = new FlowLayout(FlowLayout.CENTER, 5, 5);
		selfAvatarPane.setLayout(fl_selfAvatarPane);
		
		JLabel selfAvatar = new JLabel("");
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
		selfAvatarPane.add(selfAvatar);
		
		JProgressBar selfHP = new JProgressBar();
		selfHP.setForeground(Color.GREEN);
		selfAvatarPane.add(selfHP);
		selfPane.add(selfAvatarPane, "cell 0 0,grow");
		
		JPanel selfHandPane = new JPanel();
		selfPane.add(selfHandPane, "cell 1 0,grow");
		selfHandPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel selfDeckPane = new JPanel();
		selfPane.add(selfDeckPane, "cell 2 0,grow");

	}
}
