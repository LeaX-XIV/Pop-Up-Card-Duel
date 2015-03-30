package com.github.borione.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.github.borione.crud.Deck;
import com.github.borione.crud.Player;
import com.github.borione.gui.components.DeckDescription;
import com.github.borione.gui.components.Loading;
import com.github.borione.gui.components.MotionPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout.Group;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JInternalFrame;

import java.awt.GridLayout;

import javax.swing.JLayeredPane;

public class DeckSelect extends JFrame {

	private JPanel contentPane;

	JPanel panel;
	JButton btnClose;
	JPanel panel_2;
	JButton btnChoose;
	JPanel panel_1;
	JPanel panel_3;
	JButton btnPrevious;
	JLabel lblPagine;
	JButton btnNext;

	Player p;
	private JPanel pagesPane;
	List<JPanel> pages;
	int selected;
	int sentaku;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeckSelect frame = new DeckSelect(Player.factory("LeaX_XIV"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DeckSelect(Player p) {
		
		Loading l = new Loading("Loading decks.");
//		setResizable(false);
		this.p = p;

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 629, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new MotionPanel(this);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		btnClose = new JButton("");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnClose.setPressedIcon(new ImageIcon(DeckSelect.class.getResource("/images/close_pressed.png")));
		btnClose.setIcon(new ImageIcon(DeckSelect.class.getResource("/images/close.png")));
		btnClose.setOpaque(false);
		btnClose.setFocusPainted(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setBorderPainted(false);
		btnClose.setPreferredSize(new Dimension(30, 30));
		btnClose.setSize(new Dimension(30, 30));
		panel.add(btnClose, BorderLayout.EAST);

		panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);

		btnChoose = new JButton("Choose");
		panel_2.add(btnChoose);

		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.SOUTH);

		btnPrevious = new JButton("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnPrevious.isEnabled()) {
					selected -= 1;
//					System.out.println(selected);
					pagesPane.removeAll();
					pagesPane.add(pages.get(selected), BorderLayout.CENTER);
					lblPagine.setText((selected + 1) + "/" + pages.size());
					if(selected == 0) {
						btnPrevious.setEnabled(false);
					}
					if(selected + 1 == pages.size()) {
						btnNext.setEnabled(false);
					} else {
						btnNext.setEnabled(true);
					}
					repaint();
					revalidate();
				}
			}
		});
		btnPrevious.setIcon(new ImageIcon(DeckSelect.class.getResource("/images/left_arrow.png")));
		btnPrevious.setOpaque(false);
		btnPrevious.setFocusPainted(false);
		btnPrevious.setContentAreaFilled(false);
		btnPrevious.setBorderPainted(false);
		panel_3.add(btnPrevious);

		lblPagine = new JLabel("pagine");
		panel_3.add(lblPagine);

		btnNext = new JButton("");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnNext.isEnabled()) {
					selected += 1;
//					System.out.println(selected);
					pagesPane.removeAll();
					pagesPane.add(pages.get(selected), BorderLayout.CENTER);
					lblPagine.setText((selected + 1) + "/" + pages.size());
					if(selected == 0) {
						btnPrevious.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
					}
					if(selected + 1 == pages.size()) {
						btnNext.setEnabled(false);
					}

					repaint();
					revalidate();
				}
			}
		});
		btnNext.setIcon(new ImageIcon(DeckSelect.class.getResource("/images/right_arrow.png")));
		btnNext.setContentAreaFilled(false);
		btnNext.setFocusPainted(false);
		btnNext.setBorderPainted(false);
		btnNext.setOpaque(false);
		panel_3.add(btnNext);

		pagesPane = new JPanel();
		panel_1.add(pagesPane, BorderLayout.CENTER);
		pagesPane.setLayout(new BorderLayout(0, 0));		
		
		pages = new ArrayList<JPanel>();
		populateList();
		
		l.stop();
		setVisible(true);
	}

	// FIXME: BOOM
	public void populateList() {
		List<Deck> decks = p.retriveDecks();
		JPanel p = new JPanel(new GridLayout(2, 0));
		for(int i = 0; i < decks.size(); i++) {
			if(i % 2 == 0 && i != 0) {
				pages.add(p);
				p = new JPanel(new GridLayout(2, 0));
			}
			Deck deck = decks.get(i);
			DeckDescription dd = new DeckDescription(deck, i+1);
			dd.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					sentaku = dd.getNumber();
					dd.setBorder(new LineBorder(Color.BLACK, 3, true));
				}
			});
			p.add(dd);
		}
		pages.add(p);
		
		selected = 0;
		pagesPane.add(pages.get(selected), BorderLayout.CENTER);
		lblPagine.setText((selected + 1) + "/" + pages.size());
		btnPrevious.setEnabled(false);
		repaint();
	}
}
