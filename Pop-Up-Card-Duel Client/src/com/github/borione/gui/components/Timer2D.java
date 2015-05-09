package com.github.borione.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Timer2D extends JComponent {

	private static final int REPEATS = 360;			// 18 seconds
	private static final int DELAY_ANIMATION = 50;	// of timer
	
	private Timer timer;
	private ActionListener task;
	private int numRepeats = 0;
//	long start;

	public Timer2D() {
		
		task = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(++numRepeats == REPEATS) {
					timer.setRepeats(false);
//					System.out.println(System.currentTimeMillis() - start);
				}
				Timer2D.this.repaint();
			}
		};
		
		timer = new Timer(DELAY_ANIMATION, task);
		
//		start = System.currentTimeMillis();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillOval(0, 0, 200, 200);

		g.setColor(new Color(240, 240, 240));
		g.fillOval(3, 3, 194, 194);
		
		g.setColor(Color.RED);
		g.fillArc(3, 3, 194, 194, 90, numRepeats);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.drawString("" + (18 - (DELAY_ANIMATION * numRepeats) / 1000), 100, 100);
	}
	
	public boolean isRunning() {
		return timer.isRunning();
	}
	
	public void clear() {
		numRepeats = 0;
		repaint();
	}
	
	public void start() {
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Timer2D timer = new Timer2D();
		frame.add(timer);
		frame.setSize(200, 200);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 255, 0, 0));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		timer.start();
	}

}
