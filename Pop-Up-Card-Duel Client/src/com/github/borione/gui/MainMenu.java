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

import com.github.borione.crud.Avatar;
import com.github.borione.crud.Player;
import com.github.borione.util.Consts;
import com.github.borione.util.ImageUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.SwingConstants;

public class MainMenu extends JPanel implements ComponentListener {

	Player p;
	
	GroupLayout groupLayout;

	JLabel lblPhoto;
	JLabel lblName;
	JButton btnQuickBattle;
	JButton btnCollectionManager;
	JButton btnClose;
	JButton btnOptoins;

	public static void main(String[] args) {
		JFrame m = new JFrame();
		MainMenu m1 = new MainMenu(Player.factory("CapraTheBest"));
		m.setUndecorated(true);
		m.setContentPane(m1);
		m.setExtendedState(m.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		m.setResizable(false);
		m.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public MainMenu(Player p) {
		this.p = p;
		
		this.addComponentListener(this);
		
		setAlignmentX(Component.LEFT_ALIGNMENT);

		lblPhoto = new JLabel("photo");
		lblPhoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPhoto.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPhoto.setMaximumSize(new Dimension(400, 400));
		lblPhoto.setPreferredSize(new Dimension(100, 100));

		lblName = new JLabel(p.getName());
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Bleeding Cowboys", Font.PLAIN, 6));
		lblName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblName.setMaximumSize(new Dimension(400, 50));
		lblName.setPreferredSize(new Dimension(100, 20));

		btnQuickBattle = new JButton("quick battle");
		btnQuickBattle.setFocusPainted(false);

		btnCollectionManager = new JButton("Collection Manager");
		btnCollectionManager.setFocusPainted(false);
		btnCollectionManager.setPreferredSize(new Dimension(87, 23));

		btnClose = new JButton("");
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

		btnOptoins = new JButton("");
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
		groupLayout = new GroupLayout(this);
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

	public void setProfilePhoto(String path) {
		setProfilePhoto(ImageUtils.readImage(path));
	}

	public void setProfilePhoto(Image img) {
		img = ImageUtils.resizeBetter(img, lblPhoto.getWidth(), lblPhoto.getHeight());

		lblPhoto.setIcon(new ImageIcon(img));
	}
	
	public void fitNameFont() {
		Font labelFont = lblName.getFont();
		String labelText = lblName.getText();

		int stringWidth = lblName.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = lblName.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = lblName.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		lblName.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		try {
			setProfilePhoto(ImageUtils.getImageFromWeb("http://" + Consts.SERVER + "/" + Consts.AVATAR_PATH + Avatar.factory(p.getAvatar()).getPath()));
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fitNameFont();
    }

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}
}
