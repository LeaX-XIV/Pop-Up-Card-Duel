package com.github.borione.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Dimension;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

import com.github.borione.connection.TypeRequest;
import com.github.borione.crud.Avatar;
import com.github.borione.crud.Card;
import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.gui.components.CardDrawn;
import com.github.borione.gui.components.ImagePanel;
import com.github.borione.gui.components.Loading;
import com.github.borione.main.Main;
import com.github.borione.util.Consts;
import com.github.borione.util.FontUtils;
import com.github.borione.util.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingConstants;

public class MainMenu extends JPanel implements ComponentListener {

	static Object lock = new Object();
	
	Player p;
	
	GroupLayout groupLayout;

	JLabel lblPhoto;
	JLabel lblName;
	JButton btnQuickBattle;
	JButton btnCollectionManager;
	JButton btnOptoins;

	public static void main(String[] args) {
		MainGui mg = new MainGui();
		MainMenu m1 = new MainMenu(Player.factory(args[0]));
		mg.getContentPane().add(m1, BorderLayout.CENTER);
		mg.setVisible(true);
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
		lblName.setFont(Main.defaultFont);
		lblName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblName.setMaximumSize(new Dimension(400, 50));
		lblName.setPreferredSize(new Dimension(100, 20));

		btnQuickBattle = new JButton("Quick Battle");
		btnQuickBattle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnQuickBattle.isEnabled()) {
					btnQuickBattle.setEnabled(false);
					btnCollectionManager.setEnabled(false);
					DeckSelect ds = new DeckSelect((JFrame) MainMenu.this.getParent().getParent().getParent().getParent(), p);				    
					 
					Deck d = ds.getSelection();
//					System.out.println(d.getName());
					
					try {
						Socket sk = new Socket("127.0.0.1", 31415);
						DataOutputStream out = new DataOutputStream(sk.getOutputStream());
						BufferedReader in = new BufferedReader(new InputStreamReader(sk.getInputStream()));
						
						out.writeBytes(TypeRequest.START_BATTLE.toString() + Consts.SEPARATOR +
								d.formatData() + "\n");
						
						String answer = in.readLine();
						
						if(answer.equals(Consts.ALL_OK)) {
							changeToBattle(d, sk);
						}
						
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					btnQuickBattle.setEnabled(true);
					btnCollectionManager.setEnabled(true);					
				}
			}
		});
		btnQuickBattle.setFocusPainted(false);

		btnCollectionManager = new JButton("Collection Manager");
		btnCollectionManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnCollectionManager.isEnabled()) {
					// JUST A TRY
					JFrame f = new JFrame();
					JPanel p = new JPanel();
					f.setContentPane(p);
					JPanel p1 = new JPanel(new GridLayout(0, 2, 10, 15));
					for(Card c : MainMenu.this.p.retriveCollection()) {
						p1.add(new CardDrawn(c));
					}
					JScrollPane sp = new JScrollPane(p1);
					// DOESN'T SHOW SCROLLBAR
					JScrollBar sb = new JScrollBar(JScrollBar.VERTICAL);
					sp.setVerticalScrollBar(sb);
					p.add(sp);
					f.setBounds(100, 100, 700, 500);
					f.setVisible(true);
				}
			}
		});
		btnCollectionManager.setFocusPainted(false);
		btnCollectionManager.setPreferredSize(new Dimension(87, 23));

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
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnCollectionManager, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnQuickBattle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
										.addComponent(btnOptoins, 30, 30, 30)
										.addComponent(lblPhoto, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(804, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblPhoto, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGap(109)
						.addComponent(btnQuickBattle)
						.addGap(65)
						.addComponent(btnCollectionManager, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
						.addComponent(btnOptoins, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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

	@Override
	public void componentResized(ComponentEvent e) {
		FontUtils.fitNameFont(lblName);
		try {
			setProfilePhoto(ImageUtils.getImageFromWeb("http://" + Consts.SERVER + "/" + Consts.AVATAR_PATH + Avatar.factory(p.getAvatar()).getPath()));
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	
	private void changeToBattle(Deck d, Socket sk) {
		JPanel content = (JPanel) getParent();
		content.remove(this);
		BattleField bf = new BattleField(p, d, sk);
		content.add(bf, BorderLayout.CENTER);
		content.revalidate();
		content.repaint();
		bf.populateView();
	}
}
