package com.github.borione.gui.components;

import java.awt.BorderLayout;
import java.awt.MouseInfo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;

public class ToolTip extends JFrame {
	
	JPanel p;
	
	public ToolTip(String tip) {
		setType(Type.UTILITY);
		setResizable(false);
				
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		p = new JPanel();
		p.setOpaque(false);
		p.setBorder(null);
		setContentPane(p);
		p.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel(tip);
		lblNewLabel.setBackground(Color.DARK_GRAY);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		p.add(lblNewLabel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		p.add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(ToolTip.class.getResource("/images/tooltip-up-right.png")));
		panel.add(label_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(ToolTip.class.getResource("/images/tooltip-down-right.png")));
		panel.add(lblNewLabel_1, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		p.add(panel_4, BorderLayout.WEST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel label_2 = new JLabel("");
		panel_4.add(label_2, BorderLayout.NORTH);
		label_2.setIcon(new ImageIcon(ToolTip.class.getResource("/images/tooltip-up-left.png")));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.CENTER);
		panel_5.setBackground(Color.DARK_GRAY);
		
		JLabel label_3 = new JLabel("");
		panel_4.add(label_3, BorderLayout.SOUTH);
		label_3.setIcon(new ImageIcon(ToolTip.class.getResource("/images/tooltip-down-left.png")));
		
		pack();
	}
	
	public static void main(String[] args) {
		ToolTip t = new ToolTip("ciao");
		t.setLocation(MouseInfo.getPointerInfo().getLocation());
		t.setVisible(true);
		
	}

}
