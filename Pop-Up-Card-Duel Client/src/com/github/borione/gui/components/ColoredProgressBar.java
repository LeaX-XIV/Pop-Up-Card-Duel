package com.github.borione.gui.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColoredProgressBar extends JProgressBar {
	
	private HashMap<Double, Color> rangeColor;
	private List<Double> keys;

	/**
	 * It's a normal JProgressBar with the &#42;value&#42;/&#42;maximum&#42; ratio written on.
	 * You can add ranges to change color based on the ratio.<br>
	 * Example:<br>
	 * <code>
	 * // Create the bar<br>
	 * ColoredProgressBar bar = new ColoredProgressBar();<br>
	 * // You want it to be green when over the half, and turn red when below.<br>
	 * bar.addColorRange(0.0, Corol.RED);<br>
	 * bar.addColorRange(0.5, Color.GREEN);<br>
	 * // Set you maximun, minimun, values and all you want, the do your operations.
	 * </code>
	 */
	public ColoredProgressBar() {
		super();
		rangeColor = new HashMap<Double, Color>();
		keys = new ArrayList<Double>();
		setStringPainted(true);
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				for (int i = keys.size() - 1; i >= 0; i--) {
					double key = keys.get(i);
					
					int value = getValue();
					int max = getMaximum();
					double q = (double) value / max;
					if(q > key) {
						setForeground(rangeColor.get(key));
						break;
					}
					
				}				
				setString(getValue() + "/" + getMaximum());
			}
		});
	}
	
	public void addColorRange(double start, Color c) {
		rangeColor.put(start, c);
		keys.add(start);
		// Ascendent order
		keys.sort(null);
	}
	
	public boolean removeColorRange(double start) {
		rangeColor.remove(start);
		return keys.remove(start);
	}

}
