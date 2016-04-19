package com.kael.tool.astar.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kael.tool.astar.action.AstarPathFinder;
import com.kael.tool.astar.action.Moveable;
import com.kael.tool.astar.util.Consts;
import com.kael.tool.astar.util.ImageManager;




public class Charactor {
	
	private Location lo;
	private Moveable pathFinder;
	private Map<String,String> route = new LinkedHashMap<String,String>();
	private Image img;
	private boolean blink;
	private int count;
	private int x; //
	private int y;
	
	public Charactor(Location lo){
		this.lo = lo;
		img = ImageManager.getInstance().getArmy();
		x = lo.getX() * Consts.HCS + Consts.HCS;
		y = lo.getY() * Consts.VCS;
	}
	
	public void drasSelf(Graphics2D g2d){
		int times = 10;
		if(!blink) {
			g2d.drawImage(img,x,y, null);	
		}else{
			if(count <= times){
				g2d.drawImage(img,x,y, null);	
			}
			count++;
			if(count == times*2){
				count = 0;
			}
		}
	}
	
	public void moveForward(int[][] map,Location dest){
		AstarPathFinder apf = new AstarPathFinder();
		List<Location> paths = apf.findPath(this.getLo(), dest, map);
		int index = paths.size() - 1;
		Location loc = null;
		//System.out.println("---------");
		for(;index>=0;index--){
			loc = paths.get(index);
			System.out.println("location:"+loc);
			this.setLocationX(loc.getX());
			this.setLocationY(loc.getY());
		}
	}
	

	
	public void setLocationY(int nextY){
		lo.setY(nextY);
		int yPixel = nextY * Consts.VCS;
		if(yPixel >= y){
			while(y < yPixel){
				y += 2;
				try{
					Thread.sleep(20);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}else{
			while(y > yPixel){
				y -= 2;
				try{
					Thread.sleep(20);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setLocationX(int nextX){
		lo.setX(nextX);
		int xPixel = nextX * Consts.VCS + Consts.HCS;
		if(xPixel >= x){
			while(x < xPixel){
				x += 2;
				try{
					Thread.sleep(20);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}else{
			while(x > xPixel){
				x -= 2;
				try{
					Thread.sleep(20);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public Location getLo() {
		return lo;
	}

	public void setLo(Location lo) {
		this.lo = lo;
	}

	public Map<String, String> getRoute() {
		return route;
	}

	public void setPathFinder(Moveable pathFinder) {
		this.pathFinder = pathFinder;
	}

	public void setBlink(boolean blink) {
		this.blink = blink;
	}

	public void setRoute(Map<String, String> route) {
		this.route = route;
	}
}
