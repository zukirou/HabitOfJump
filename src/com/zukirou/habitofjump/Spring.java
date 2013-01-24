package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.GameObject;

public class Spring extends GameObject{
	public static float SPRING_WIDTH = 0.3f;
	public static float SPRING_HEIGHT = 0.3f;
	
	public Spring(float x, float y){
		super(x, y, SPRING_WIDTH, SPRING_HEIGHT);
	}
}