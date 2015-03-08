package com.github.borione.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel {
	
	
	
	public static void main(String[] args) {
		JFrame m = new JFrame();
		m.setUndecorated(true);
		m.setContentPane(new MainMenu());
		m.pack();
		m.setExtendedState(m.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		m.setResizable(false);
		m.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public MainMenu() {
		setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lblPhoto = new JLabel("photo");
		lblPhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPhoto.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPhoto.setMaximumSize(new Dimension(400, 400));
		lblPhoto.setPreferredSize(new Dimension(100, 100));
		
		JLabel lblName = new JLabel("name");
		lblName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblName.setMaximumSize(new Dimension(400, 50));
		lblName.setPreferredSize(new Dimension(100, 20));
		
		JButton btnQuickBattle = new JButton("quick battle");
		btnQuickBattle.setFocusPainted(false);
		
		JButton btnCollectionManager = new JButton("Collection Manager");
		btnCollectionManager.setFocusPainted(false);
		btnCollectionManager.setPreferredSize(new Dimension(87, 23));
		
		JButton btnClose = new JButton("");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		btnClose.setFocusPainted(false);
		btnClose.setBorder(null);
		btnClose.setOpaque(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setBorderPainted(false);
		btnClose.setIcon(new ImageIcon(MainMenu.class.getResource("/images/close.png")));
		btnClose.setMinimumSize(new Dimension(30, 30));
		btnClose.setMaximumSize(new Dimension(30, 30));
		btnClose.setPreferredSize(new Dimension(30, 30));
		btnClose.setSize(new Dimension(30, 30));
		btnClose.setPressedIcon(new ImageIcon(MainMenu.class.getResource("/images/close_pressed.png")));
		
		JButton btnOptoins = new JButton("");
		btnOptoins.setFocusPainted(false);
		btnOptoins.setPressedIcon(new ImageIcon(MainMenu.class.getResource("/images/options_pressed.png")));
		btnOptoins.setIcon(new ImageIcon(MainMenu.class.getResource("/images/options.png")));
		btnOptoins.setOpaque(false);
		btnOptoins.setContentAreaFilled(false);
		btnOptoins.setBorderPainted(false);
		btnOptoins.setMinimumSize(new Dimension(30, 30));
		btnOptoins.setMaximumSize(new Dimension(30, 30));
		btnOptoins.setPreferredSize(new Dimension(30, 30));
		btnOptoins.setSize(new Dimension(30, 30));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnCollectionManager, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnQuickBattle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
									.addComponent(btnOptoins, 30, 30, 30))
								.addContainerGap(593, Short.MAX_VALUE))
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, true)
									.addComponent(lblPhoto, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblName, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
								.addGap(620)))
						.addComponent(btnClose, Alignment.TRAILING, 30, 30, 30)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblPhoto, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(109)
							.addComponent(btnQuickBattle)
							.addGap(65)
							.addComponent(btnCollectionManager, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
							.addComponent(btnOptoins, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
}
