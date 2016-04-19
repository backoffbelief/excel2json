package com.kael.tool.astar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.kael.tool.astar.listener.InstructionListener;
import com.kael.tool.astar.ui.FireEmblePanel;


public class Main {
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel fireEmble = new FireEmblePanel();
		frame.getContentPane().add(fireEmble);
		frame.setLocation(50, 100);
		frame.setTitle("纹章外传");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		InstructionListener il = new InstructionListener(fireEmble);
		frame.addKeyListener(il);
		
	}
}
