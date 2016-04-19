package com.kael.tool.astar.util;


import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageManager {
	
	private Image ground;
	private Image topWall;
	private Image sideWall;
	private Image cursor;
	private Image army;
	private Image target;
	private static ImageManager instance;
	  
	public static ImageManager getInstance(){
		  if(instance == null){
			  return new ImageManager();
		  }
		  return instance;
	}
	  
	private ImageManager(){
		  ground = loadImg("fire/ground.jpg");
		  topWall = loadImg("fire/wall.jpg");
		  sideWall = loadImg("fire/sidewall.jpg");
		  cursor = loadImg("fire/select.png");
		  army = loadImg("fire/army.jpg");
		  target = loadImg("fire/target.jpg");
	}
	  
	public Image loadImg(String imgUrl){
		 ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imgUrl));
		 return icon.getImage();
	}
	
	public Image getImageByType(int type){
			Image img = null;
			switch(type) {
				case 1:
					img = ground;
					break;
					
				case 2:
					img = topWall;
					break;
					
				case 3:
					img = sideWall;
					break;	
				
				case 4:
					img = target;
					break;	
			}
			return img;
	}  
	  
	public Image getGround() {
		return ground;
	}

	public Image getTopWall() {
		return topWall;
	}

	public Image getSideWall() {
		return sideWall;
	}

	public Image getCursor() {
		return cursor;
	}

	public Image getArmy() {
		return army;
	}

	public Image getTarget() {
		return target;
	}

	public void setTarget(Image target) {
		this.target = target;
	}
}
