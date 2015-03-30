package com.github.borione.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Window.Type;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.ImageIcon;

public class Loading extends JDialog {

	private JPanel contentPane;
	private JLabel lblIcon;
	private JLabel lblText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loading frame = new Loading("Loading. Please wait.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Loading(String text) {
		setUndecorated(true);
		setVisible(true);
		setAlwaysOnTop(true);
//		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 88);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lblIcon = new JLabel();
		lblIcon.setIcon(new ImageIcon(Loading.class.getResource("/images/loading.gif")));
		contentPane.add(lblIcon, BorderLayout.WEST);
		
		lblText = new JLabel(text);
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblText, BorderLayout.CENTER);
		
		pack();
	}
	
	public void stop() {
		dispose();
	}

}
