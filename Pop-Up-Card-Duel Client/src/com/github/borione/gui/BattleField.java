package com.github.borione.gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;

public class BattleField extends JPanel {

	/**
	 * Create the panel.
	 */
	public BattleField() {
		setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel opponentPane = new JPanel();
		add(opponentPane);
		opponentPane.setLayout(new MigLayout("", "[275px][515px,grow][275px,grow]", "[240.00,grow]"));
		
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
		panel.add(opponentAvatar);
		opponentAvatar.setSize(new Dimension(100, 100));
		opponentAvatar.setPreferredSize(new Dimension(100, 100));
		opponentAvatar.setMinimumSize(new Dimension(100, 100));
		
		JProgressBar opponentHP = new JProgressBar();
		panel.add(opponentHP);
		opponentHP.setForeground(Color.GREEN);
		
		JPanel opponentColorCardPane = new JPanel();
		opponentAvatarPane.add(opponentColorCardPane, BorderLayout.SOUTH);
		
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
		selfAvatarPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel selfAvatar = new JLabel("");
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
