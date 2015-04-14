package com.github.borione.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
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
import java.util.concurrent.ExecutionException;

import javax.swing.JComponent;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DeckSelect extends JDialog {

	public Deck selected = null;
	public Boolean isSelected = false;

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

	Loading l;
	Player p;
	List<Deck> decks;
	private JPanel pagesPane;
	List<JPanel> pages;
	int selectedPage;
	int sentaku = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeckSelect frame = new DeckSelect(new JFrame(), Player.factory("LeaX_XIV"));
					frame.getSelection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DeckSelect(JFrame frame, Player p) {
		super(frame, "deck");
		this.p = p;
		setModal(true);
		//		setUndecorated(true);
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
				dispose();
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
		btnChoose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isSelected = true;
				synchronized (isSelected) {
					if(sentaku != -1) {
						isSelected.notify();
					}
				}

			}
		});
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
					selectedPage -= 1;
					//					System.out.println(selected);
					pagesPane.removeAll();
					pagesPane.add(pages.get(selectedPage), BorderLayout.CENTER);
					lblPagine.setText((selectedPage + 1) + "/" + pages.size());
					if(selectedPage == 0) {
						btnPrevious.setEnabled(false);
					}
					if(selectedPage + 1 == pages.size()) {
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
					selectedPage += 1;
					//					System.out.println(selected);
					pagesPane.removeAll();
					pagesPane.add(pages.get(selectedPage), BorderLayout.CENTER);
					lblPagine.setText((selectedPage + 1) + "/" + pages.size());
					if(selectedPage == 0) {
						btnPrevious.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
					}
					if(selectedPage + 1 == pages.size()) {
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
	}

	public Deck getSelection() {
		
		TwoWorker task = new TwoWorker();
		task.addPropertyChangeListener(new PropertyChangeListener() {

	        @Override
	        public void propertyChange(PropertyChangeEvent e) {
	            if (StateValue.DONE.equals(e.getPropertyName())) {
	                task.done();
	            }
	        }
	    });
		task.execute();
		
		setVisible(true);
//		l = new Loading("Loading decks.");		
		
		synchronized (isSelected){
			while(!isSelected) {
				try {
					isSelected.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			dispose();

			return decks.get(sentaku - 1);
		}
	}

	private class TwoWorker extends SwingWorker<List<Deck>, Deck> {


		@Override
		protected List<Deck> doInBackground() throws Exception {
			decks = p.retriveDecks();
			
			return decks;
		}

		@Override
		protected void done() {
			JPanel p1 = new JPanel(new GridLayout(2, 0));
			try {
				for(int i = 0; i < get().size(); i++) {
					if(i % 2 == 0 && i != 0) {
						pages.add(p1);
						p1 = new JPanel(new GridLayout(2, 0));
					}
					Deck deck = decks.get(i);
					DeckDescription dd = new DeckDescription(deck, i+1);
					dd.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							sentaku = dd.getNumber();
							for (JPanel page : pages) {
								Component[] decki = page.getComponents();
								for (Component deck : decki) {
									((JComponent) deck).setBorder(null);
								}
							}
							dd.setBorder(new LineBorder(Color.BLACK, 3, true));
						}
					});
					p1.add(dd);
					System.out.println("Aggiunto deck " + deck.getName());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pages.add(p1);

			selectedPage = 0;
			pagesPane.add(pages.get(selectedPage), BorderLayout.CENTER);
			lblPagine.setText((selectedPage + 1) + "/" + pages.size());
			btnPrevious.setEnabled(false);

			repaint();
			revalidate();
			
//			l.stop();
		}
	}
}