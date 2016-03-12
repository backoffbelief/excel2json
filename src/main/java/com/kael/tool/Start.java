package com.kael.tool;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				}
				new Excel2JsonApp();
			}
		});
	}

}
