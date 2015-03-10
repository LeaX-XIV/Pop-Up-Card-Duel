package com.github.borione.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.github.borione.gui.components.MotionPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JInternalFrame;

public class DeckSelect extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeckSelect frame = new DeckSelect();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DeckSelect() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new MotionPanel(this);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnClose = new JButton("");
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
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		
		JButton btnChoose = new JButton("Choose");
		panel_2.add(btnChoose);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnPrevious = new JButton("");
		btnPrevious.setIcon(new ImageIcon(DeckSelect.class.getResource("/images/left_arrow.png")));
		btnPrevious.setOpaque(false);
		btnPrevious.setFocusPainted(false);
		btnPrevious.setContentAreaFilled(false);
		btnPrevious.setBorderPainted(false);
		panel_3.add(btnPrevious);
		
		JLabel lblPagine = new JLabel("pagine");
		panel_3.add(lblPagine);
		
		JButton btnNext = new JButton("");
		btnNext.setIcon(new ImageIcon(DeckSelect.class.getResource("/images/right_arrow.png")));
		btnNext.setContentAreaFilled(false);
		btnNext.setFocusPainted(false);
		btnNext.setBorderPainted(false);
		btnNext.setOpaque(false);
		panel_3.add(btnNext);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(null);
	}
}
