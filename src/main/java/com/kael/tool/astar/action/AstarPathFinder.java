package com.kael.tool.astar.action;

import java.util.ArrayList;
import java.util.List;

import com.kael.tool.astar.entity.Location;

/**
 * @author Administrator
 *
 */
public class AstarPathFinder {
	
	private List<Location> openList;
	private List<Location> closeList;
	
	public AstarPathFinder(){
		openList = new ArrayList<Location>();
		closeList = new ArrayList<Location>();
	}
	
	public List<Location> findPath(Location start,Location dest,int[][] map){
		List<Location> path = new ArrayList<Location>();
		openList.add(start);
		Location current = null;
		do{
			current = getLowestFscoreLocation(openList);
			//System.out.println(current.toString());
			closeList.add(current);
			openList.remove(current);
			if(closeList.contains(dest)){
				break;
			}
			List<Location> adjacentLocations = getWalkableAdjacentLocations(current, map);
			for(Location lo : adjacentLocations){
				if(closeList.contains(lo)){
					continue;
				}
				if(!openList.contains(lo)){
					lo.setMovedSteps(current.getMovedSteps()+1);
					lo.setEvalRemainSteps(evalRemainSteps(current,dest));
					lo.setTotalEvalSteps(evalRemainSteps(current,dest)+lo.getMovedSteps());
					openList.add(lo);
				}else{
					if(current.getMovedSteps()+1 < lo.getMovedSteps()){
						lo.setMovedSteps(current.getMovedSteps()+1 );
						lo.setPrevious(current);
					}
				}
			}
		
		}while(!openList.isEmpty());
		Location destination = null;
		if(closeList.contains(dest)){
			destination = current;
			
			path.add(destination);
			while(destination.getPrevious() != null){
				destination = destination.getPrevious();
				path.add(destination);
			}
		}
		
		return path;
	}
	
	private List<Location> getWalkableAdjacentLocations(Location current,int[][] map){
		int x = current.getX();
		int y = current.getY();
		List<Location> walkableLos = new ArrayList<Location>();
		Location lo = null;
		if(x+1 < map[0].length && (map[y][x+1] == 1 || map[y][x+1] == 4)){
			lo = new Location(x+1,y);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		if(x-1>0 && (map[y][x-1] == 1 || map[y][x-1] == 4)){
			lo = new Location(x-1,y);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		if(y+1 < map.length && (map[y+1][x] == 1 || map[y+1][x] == 4)){
			lo = new Location(x,y+1);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		if(y-1>0 && (map[y-1][x] == 1 ||  map[y-1][x] == 4)){
			lo = new Location(x,y-1);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		return walkableLos;
	}
	
	/**
	 * @param openList
	 * @return
	 */
	private Location getLowestFscoreLocation(List<Location> openList){
		if(openList == null || openList.size() == 0){
			return null;
		}
		int minSteps = openList.get(0).getTotalEvalSteps();
		int tmpSteps = 0;
		Location lowestFlocation = openList.get(0);
		for(Location lo : openList){
			tmpSteps = lo.getTotalEvalSteps();
			if(tmpSteps < minSteps){
				minSteps = tmpSteps;
				lowestFlocation = lo;
			}
		}
		return lowestFlocation;
	}
	
	/**
	 * @param current
	 * @param dest
	 * @return
	 */
	private int evalRemainSteps(Location current,Location dest){ // H function
		int distanceX = dest.getX() - current.getX();
		int distanceY = dest.getY() - current.getY();
		if(distanceX < 0){
			distanceX = distanceX * -1;
		}
		if(distanceY < 0){
			distanceY = distanceY * -1;
		}
		return distanceX + distanceY;
	}
}
