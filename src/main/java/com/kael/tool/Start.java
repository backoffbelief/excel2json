package com.kael.tool;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new Excel2JsonApp();
			}
		});
	}

}
