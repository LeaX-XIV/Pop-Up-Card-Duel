package com.github.borione.gui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class MainMenu extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainMenu() {
		
		JLabel lblNewLabel = new JLabel("photo");
		lblNewLabel.setMaximumSize(new Dimension(400, 400));
		lblNewLabel.setPreferredSize(new Dimension(100, 100));
		
		JLabel lblName = new JLabel("name");
		lblName.setMaximumSize(new Dimension(400, 50));
		lblName.setPreferredSize(new Dimension(100, 20));
		
		JButton btnQuickBattle = new JButton("quick battle");
		
		JButton btnNewButton = new JButton("Collection Manager");
		btnNewButton.setPreferredSize(new Dimension(87, 23));
		
		JLabel lblNewLabel_1 = new JLabel("options");
		lblNewLabel_1.setMaximumSize(new Dimension(46, 46));
		lblNewLabel_1.setMinimumSize(new Dimension(23, 23));
		lblNewLabel_1.setPreferredSize(new Dimension(23, 23));
		
		JButton btnClose = new JButton("");
		btnClose.setOpaque(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setBorderPainted(false);
		btnClose.setIcon(new ImageIcon(MainMenu.class.getResource("/images/close.png")));
		btnClose.setMinimumSize(new Dimension(30, 30));
		btnClose.setMaximumSize(new Dimension(30, 30));
		btnClose.setPreferredSize(new Dimension(30, 30));
		btnClose.setSize(new Dimension(30, 30));
		btnClose.setPressedIcon(new ImageIcon(MainMenu.class.getResource("/images/close_pressed.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 488, Short.MAX_VALUE)
										.addComponent(btnClose))
									.addComponent(lblName, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(540))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnQuickBattle, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
							.addGap(540))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(116)
							.addComponent(btnQuickBattle)
							.addGap(65)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnClose)))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
}
