package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.GameObject;

public class Castle extends GameObject{
	public static float CASTLE_WIDTH = 1.7f;
	public static float CASTLE_HEIGHT = 0.2f;
	
	public Castle(float x, float y){
		super(x, y, CASTLE_WIDTH, CASTLE_HEIGHT);
	}
}