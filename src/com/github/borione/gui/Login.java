package com.github.borione.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import com.github.borione.gui.components.HintTextField;
import com.github.borione.gui.components.ImagePanel;
import com.github.borione.gui.components.MotionPanel;

import java.awt.Component;
import java.io.IOException;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.awt.Font;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtCofirmPass;
	private JTextField txtName;
	private JTextField txtMail;
	private JTextField txtConfirmMail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 269, 469);
		BufferedImage backgroungImage;
		try {
			backgroungImage = ImageIO.read(getClass().getResource("/images/login-back.png"));
			contentPane = new ImagePanel(backgroungImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			contentPane = new JPanel();
			e.printStackTrace();
		}
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new MotionPanel(this);
		panel.setOpaque(false);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton button = new JButton("");
		button.setMinimumSize(new Dimension(30, 30));
		button.setMaximumSize(new Dimension(30, 30));
		button.setPreferredSize(new Dimension(30, 30));
		panel.add(button, BorderLayout.EAST);
		button.setPressedIcon(new ImageIcon(Login.class.getResource("/images/close_pressed.png")));
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setFocusPainted(false);
		button.setFocusTraversalKeysEnabled(false);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		button.setIcon(new ImageIcon(Login.class.getResource("/images/close.png")));
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{button}));
		
		JPanel panelSignUp = new JPanel();
		panelSignUp.setOpaque(false);
		contentPane.add(panelSignUp, BorderLayout.CENTER);
		panelSignUp.setLayout(new BorderLayout(0, 0));
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setOpaque(false);
		panelSignUp.add(fieldPanel, BorderLayout.CENTER);
		
		txtUsername = new HintTextField("Username");
		txtUsername.setToolTipText("");
		txtUsername.setCaretColor(Color.BLACK);
		txtUsername.setFont(new Font("Times New Roman", Font.BOLD, 13));
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setOpaque(false);
		txtUsername.setColumns(10);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Login.class.getResource("/images/help.png")));
		
		txtPassword = new HintTextField("Password");
		txtPassword.setCaretColor(Color.BLACK);
		txtPassword.setForeground(Color.WHITE);
		txtPassword.setFont(new Font("Times New Roman", Font.BOLD, 13));
		txtPassword.setOpaque(false);
		txtPassword.setColumns(10);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Login.class.getResource("/images/help.png")));
		
		txtCofirmPass = new HintTextField("Confirm Password");
		txtCofirmPass.setCaretColor(Color.BLACK);
		txtCofirmPass.setOpaque(false);
		txtCofirmPass.setForeground(Color.WHITE);
		txtCofirmPass.setFont(new Font("Times New Roman", Font.BOLD, 13));
		txtCofirmPass.setColumns(10);
		
		txtName = new HintTextField("Name");
		txtName.setCaretColor(Color.BLACK);
		txtName.setOpaque(false);
		txtName.setForeground(Color.WHITE);
		txtName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		txtName.setColumns(10);
		
		txtMail = new HintTextField("E-Mail");
		txtMail.setCaretColor(Color.BLACK);
		txtMail.setOpaque(false);
		txtMail.setForeground(Color.WHITE);
		txtMail.setFont(new Font("Times New Roman", Font.BOLD, 13));
		txtMail.setColumns(10);
		
		txtConfirmMail = new HintTextField("Confirm E-Mail");
		txtConfirmMail.setCaretColor(Color.BLACK);
		txtConfirmMail.setOpaque(false);
		txtConfirmMail.setForeground(Color.WHITE);
		txtConfirmMail.setFont(new Font("Times New Roman", Font.BOLD, 13));
		txtConfirmMail.setColumns(10);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Login.class.getResource("/images/help.png")));
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Login.class.getResource("/images/help.png")));
		GroupLayout gl_fieldPanel = new GroupLayout(fieldPanel);
		gl_fieldPanel.setHorizontalGroup(
			gl_fieldPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_fieldPanel.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_fieldPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtConfirmMail, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_fieldPanel.createSequentialGroup()
							.addComponent(txtMail, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_fieldPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtCofirmPass, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_fieldPanel.createSequentialGroup()
							.addGroup(gl_fieldPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txtPassword, Alignment.LEADING)
								.addComponent(txtUsername, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_fieldPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		gl_fieldPanel.setVerticalGroup(
			gl_fieldPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_fieldPanel.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_fieldPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(txtUsername, Alignment.LEADING))
					.addGap(18)
					.addGroup(gl_fieldPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(txtCofirmPass, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_fieldPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_fieldPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMail, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(txtConfirmMail, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(301, Short.MAX_VALUE))
		);
		
		fieldPanel.setLayout(gl_fieldPanel);
		fieldPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsername, txtPassword, txtCofirmPass, txtName, txtMail, txtConfirmMail}));
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panelSignUp.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnSignUp = new JButton("SIGN UP");
		btnSignUp.setFocusPainted(false);
		btnSignUp.setFont(new Font("Times New Roman", Font.BOLD, 39));
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setContentAreaFilled(false);
		btnSignUp.setBorderPainted(false);
		btnSignUp.setOpaque(false);
		panel_1.add(btnSignUp, BorderLayout.CENTER);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{fieldPanel, txtUsername, panel, button, panelSignUp, txtConfirmMail, txtMail, label_3, txtName, label_2, txtCofirmPass, txtPassword, label, label_1, panel_1, btnSignUp}));
		
		JPanel panelLogin = new JPanel();
		panelLogin.setOpaque(false);
		contentPane.add(panelLogin, BorderLayout.CENTER);
		panelLogin.setLayout(new BorderLayout(0, 0));
		
		JPanel FieldPanel = new JPanel();
		FieldPanel.setOpaque(false);
		panelLogin.add(FieldPanel, BorderLayout.NORTH);
		
		HintTextField hintTextField = new HintTextField("Password");
		hintTextField.setOpaque(false);
		hintTextField.setForeground(Color.WHITE);
		hintTextField.setFont(new Font("Times New Roman", Font.BOLD, 13));
		hintTextField.setColumns(10);
		hintTextField.setCaretColor(Color.BLACK);
		
		HintTextField hintTextField_1 = new HintTextField("Username");
		hintTextField_1.setToolTipText("");
		hintTextField_1.setOpaque(false);
		hintTextField_1.setForeground(Color.WHITE);
		hintTextField_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		hintTextField_1.setColumns(10);
		hintTextField_1.setCaretColor(Color.BLACK);
		GroupLayout gl_FieldPanel = new GroupLayout(FieldPanel);
		gl_FieldPanel.setHorizontalGroup(
			gl_FieldPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_FieldPanel.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_FieldPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(hintTextField_1, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addComponent(hintTextField, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(56, Short.MAX_VALUE))
		);
		gl_FieldPanel.setVerticalGroup(
			gl_FieldPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_FieldPanel.createSequentialGroup()
					.addGap(37)
					.addComponent(hintTextField_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(hintTextField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(275, Short.MAX_VALUE))
		);
		FieldPanel.setLayout(gl_FieldPanel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panelLogin.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setFocusPainted(false);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 39));
		btnLogin.setContentAreaFilled(false);
		btnLogin.setOpaque(false);
		btnLogin.setBorderPainted(false);
		panel_2.add(btnLogin);
		
		JLabel lblSignUp = new JLabel("Sign Up");
		
		lblSignUp.setForeground(Color.CYAN);
		panel.add(lblSignUp, BorderLayout.WEST);
		
		JLabel label_4 = new JLabel("<");
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel.remove(label_4);
				panel.add(lblSignUp, BorderLayout.WEST);
				contentPane.remove(panelSignUp);
				contentPane.add(panelLogin, BorderLayout.CENTER);
				Login.this.repaint();
				Login.this.revalidate();
				btnLogin.grabFocus();
			}
		});
		lblSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel.remove(lblSignUp);
				panel.add(label_4, BorderLayout.WEST);
				contentPane.remove(panelLogin);
				contentPane.add(panelSignUp, BorderLayout.CENTER);
				Login.this.repaint();
				Login.this.revalidate();
				btnSignUp.grabFocus();
			}
		});
		label_4.setForeground(Color.CYAN);
		btnLogin.grabFocus();
	}
}
