package com.kael.tool.astar.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import com.kael.tool.astar.entity.Charactor;
import com.kael.tool.astar.entity.Location;
import com.kael.tool.astar.ui.Cursor;
import com.kael.tool.astar.ui.FireEmblePanel;
import com.kael.tool.astar.util.Util;
public class InstructionListener extends KeyAdapter{
	
	private JPanel panel;
	
	public InstructionListener(JPanel panel){
		this.panel = panel;
	}
	
	public void keyPressed(KeyEvent e){
		int[][] map = ((FireEmblePanel)panel).getMap();
		Cursor cursor = ((FireEmblePanel)panel).getCursors();
		Charactor c = ((FireEmblePanel)panel).getC();
		int k = e.getKeyCode();
		int direction = 0;
		int x = 0;
		int y = 0;
		switch(k){
			case KeyEvent.VK_UP:
				direction = -2;
				break;
				
			case KeyEvent.VK_DOWN:
				direction = 2;
				break;
				
			case KeyEvent.VK_LEFT:
				direction = -1;
				break;
				
			case KeyEvent.VK_RIGHT:
				direction = 1;
				break;
				
			case KeyEvent.VK_S:  // create wall
			
				x = cursor.getX();
				y = cursor.getY();
				map[y][x] = 2;
				 ((FireEmblePanel)panel).setMap(map);
				break;
			
			case KeyEvent.VK_D: // create ground
				x = cursor.getX();
				y = cursor.getY();
				map[y][x] = 1;
				 ((FireEmblePanel)panel).setMap(map);
				break;
			
			case KeyEvent.VK_T: // mark
				x = cursor.getX();
				y = cursor.getY();
				map[y][x] = 4;
				 ((FireEmblePanel)panel).setMap(map);
				 
				break;
				
			case KeyEvent.VK_X: // choose & move
				x = cursor.getX();
				y = cursor.getY();
				if(cursor.isChosen()){
					System.out.println("move");
					c.setBlink(false);
					Location lo = new Location(x,y);
					Thread move = new MoveThread(c,lo,map,cursor);
					move.start();
				}else{
					
					if(c.getLo().getX()==x && c.getLo().getY() == y){
						System.out.println("chosen");
						c.setBlink(true);
						cursor.setChosen(true);
					}
				}
				break;
				
			
			case KeyEvent.VK_P: // print arrays of current map
				Util.printMap(map);
				break;
		}
		cursor.move(direction);
	}
	
	class MoveThread extends Thread{
		private Charactor c;
		private Location lo;
		private int[][] map;
		private Cursor cursor;
		public MoveThread(Charactor c,Location lo,int[][] map,Cursor cursor){
			this.c = c;
			this.lo = lo;
			this.map = map;
			this.cursor = cursor;
		}
		
		public void run(){
			c.moveForward(map, lo);
			cursor.setChosen(false);
		}
	}
}
