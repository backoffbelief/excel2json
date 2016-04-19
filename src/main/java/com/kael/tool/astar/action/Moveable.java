package com.kael.tool.astar.action;

import com.kael.tool.astar.entity.Location;
import com.kael.tool.astar.entity.Charactor;
public interface Moveable {
	
	int fakeMove(Charactor c,Location dest,int[][] map);
}
