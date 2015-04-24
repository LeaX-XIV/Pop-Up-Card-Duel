package com.github.borione.gui;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.ImageIcon;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import com.github.borione.crud.Avatar;
import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.gui.components.ColoredProgressBar;
import com.github.borione.util.Consts;
import com.github.borione.util.ImageUtils;

import java.awt.Color;
import java.awt.Dimension;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

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
		opponentPane.setLayout(new MigLayout("", "[275.00px][515.00,grow][275.00]", "[240.00px,grow]"));
		
		JPanel opponentDeckPane = new JPanel();
		opponentDeckPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		opponentPane.add(opponentDeckPane, "cell 0 0,grow");
		
		JPanel opponentHandPane = new JPanel();
		opponentHandPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		opponentPane.add(opponentHandPane, "cell 1 0,grow");
		opponentHandPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel opponentAvatarPane = new JPanel();
		opponentAvatarPane.setBorder(new LineBorder(new Color(0, 0, 0)));
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
		opponentAvatar.setSize(new Dimension(100, 100));
		opponentAvatar.setPreferredSize(new Dimension(100, 100));
		opponentAvatar.setMinimumSize(new Dimension(100, 100));
		
		ColoredProgressBar opponentHP = new ColoredProgressBar();
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
		selfPane.setLayout(new MigLayout("", "[270.00px][515.00,grow,fill][275.00]", "[240.00px,grow]"));
		
		JPanel selfAvatarPane = new JPanel();
		selfAvatarPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		
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
		
		ColoredProgressBar selfHP = new ColoredProgressBar();
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
		
		JPanel selfHandPane = new JPanel();
		selfHandPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		selfPane.add(selfHandPane, "cell 1 0,grow");
		selfHandPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel selfDeckPane = new JPanel();
		selfDeckPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		selfPane.add(selfDeckPane, "cell 2 0,grow");
	}
}
