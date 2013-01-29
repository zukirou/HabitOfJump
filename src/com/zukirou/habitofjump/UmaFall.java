package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class UmaFall extends DynamicGameObject{
	public static final float UMA_WIDTH = 1;
	public static final float UMA_HEIGHT = 0.2f;
	public static final float UMA_VELOCITY = -2f;
	
	float stateTime = 0;
	
	public UmaFall(float x, float y){
		super (x, y, UMA_WIDTH, UMA_HEIGHT);
		velocity.set(0, UMA_VELOCITY);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(UMA_WIDTH / 2, UMA_HEIGHT / 2);
		
		if(position.y < UMA_HEIGHT){
			position.y = -1;
		}
		
		stateTime += deltaTime;
	}
}