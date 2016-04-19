package com.kael.tool.astar.entity;

public class Location {
	
	private int x;
	private int y;
	private int movedSteps; //ֵ
	private int evalRemainSteps; //hֵ
	private int totalEvalSteps; //fֵ
	private Location previous;
	
	public Location(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o){
		if(o instanceof Location){
			Location l = (Location)o;
			if(l.x == this.x && l.y == this.y){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String desc = "["+x+","+y+"]";
		return desc;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMovedSteps() {
		return movedSteps;
	}

	public void setMovedSteps(int movedSteps) {
		this.movedSteps = movedSteps;
	}

	public int getEvalRemainSteps() {
		return evalRemainSteps;
	}

	public void setEvalRemainSteps(int evalRemainSteps) {
		this.evalRemainSteps = evalRemainSteps;
	}

	public int getTotalEvalSteps() {
		return totalEvalSteps;
	}

	public void setTotalEvalSteps(int totalEvalSteps) {
		this.totalEvalSteps = totalEvalSteps;
	}

	public Location getPrevious() {
		return previous;
	}

	public void setPrevious(Location previous) {
		this.previous = previous;
	}
	
	
}
