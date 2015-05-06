package com.github.borione.gui.components;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import com.github.borione.crud.Card;
import com.github.borione.crud.CardColor;
import com.github.borione.crud.Deck;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.awt.Font;

public class DeckDescription extends JPanel {
	
	JPanel panel_1;
	JPanel panel;
	JLabel lblName;
	JLabel lblCards;
	JPanel panel_2;
	JLabel lblNumber;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setContentPane(new DeckDescription(Deck.factory(1), 1));
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
	}

	/**
	 * Create the panel.
	 */
	public DeckDescription(Deck d, int number) {
		setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(2, 0, 0, 0));
		
		panel = new JPanel();
		panel_1.add(panel);
		panel.setLayout(new BorderLayout(20, 0));
		
		lblName = new JLabel(d.getName());
		panel.add(lblName, BorderLayout.WEST);
		lblCards = new JLabel(d.retriveNumberCards() + "/15");
		panel.add(lblCards, BorderLayout.EAST);
		
		panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 0));
		
		lblNumber = new JLabel("" + number);
		lblNumber.setFont(new Font("Tahoma", Font.PLAIN, 45));
		add(lblNumber, BorderLayout.WEST);
		
		populateCrystals(d);
	}
	
	// XXX: CAN USE QUERY TO EXTRACT ONLY THE COLORS
	private void populateCrystals(Deck d) {
		List<Card> cards = d.retriveCards();
		
		for (Card card : cards) {
			JLabel crystal = new JLabel();
			if(card.getColor().equals(CardColor.RED)) {
				crystal.setIcon(new ImageIcon(DeckDescription.class.getResource("/images/red_crystal.png")));
			} else if(card.getColor().equals(CardColor.GREEN)) {
				crystal.setIcon(new ImageIcon(DeckDescription.class.getResource("/images/green_crystal.png")));
			} else if(card.getColor().equals(CardColor.BLUE)) {
				crystal.setIcon(new ImageIcon(DeckDescription.class.getResource("/images/blue_crystal.png")));
			} else if(card.getColor().equals(CardColor.YELLOW)) {
				crystal.setIcon(new ImageIcon(DeckDescription.class.getResource("/images/yellow_crystal.png")));
			} else if(card.getColor().equals(CardColor.GRAY)) {
				crystal.setIcon(new ImageIcon(DeckDescription.class.getResource("/images/gray_crystal.png")));
			}
			
			panel_2.add(crystal);
		}
	}
	
	public int getNumber() {
		return Integer.parseInt(lblNumber.getText());
	}
}
