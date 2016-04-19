package com.kael.tool.astar.ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import com.kael.tool.astar.entity.Charactor;
import com.kael.tool.astar.entity.Location;
import com.kael.tool.astar.map.Maps;
import com.kael.tool.astar.util.Consts;
import com.kael.tool.astar.util.ImageManager;

public class FireEmblePanel extends JPanel  {
	
	private int offset = Consts.HCS;
	private Cursor cursors;
	private int[][] map = Maps.MAP3;
	private Charactor c;
	
	public FireEmblePanel(){
		int width = Consts.HCS * Maps.MAP1[0].length+Consts.HCS*2;
		int height = Consts.VCS * Maps.MAP1.length;
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		cursors = new Cursor(3,12);
		
		Location lo = new Location(3,11);
		c = new Charactor(lo);
		
	
		new Thread(new Runnable(){
			public void run(){
				while(true){
					try{
						Thread.sleep(50);
					}catch(Exception e){
						e.printStackTrace();
					}
					repaint();
				}
			}
		}).start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawMap(g);
		drawCharacter(g);
		drawCursor(g);
	}
	
	private void drawMap(Graphics g){
		
		int type = 0;
		for(int row=0 ; row < map.length; row++){
			for(int col=0; col<map[0].length;col++){
				type = map[row][col];
				Image img = ImageManager.getInstance().getImageByType(type);
				if(img!=null)
					g.drawImage(img, col * Consts.HCS+offset, row * Consts.VCS, this);
			}
		}
	}
	
	private void drawCursor(Graphics g){
		cursors.drawSelf((Graphics2D)g);
	}
	
	private void drawCharacter(Graphics g){
		c.drasSelf((Graphics2D)g);
	}
	
	public Cursor getCursors() {
		return cursors;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public Charactor getC() {
		return c;
	}
	
	
}
