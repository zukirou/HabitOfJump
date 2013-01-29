package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class UmaTogeFall extends DynamicGameObject{
	public static final float UMA_TOGE_WIDTH = 0.3f;
	public static final float UMA_TOGE_HEIGHT = 0.2f;
	public static final float UMA_TOGE_VELOCITY = -2f;
	
	float stateTime = 0;
	
	public UmaTogeFall(float x, float y){
		super (x, y, UMA_TOGE_WIDTH, UMA_TOGE_HEIGHT);
		velocity.set(0, UMA_TOGE_VELOCITY);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(UMA_TOGE_WIDTH / 2, UMA_TOGE_HEIGHT / 2);
		
		if(position.y < UMA_TOGE_HEIGHT){
			position.y = -1;
		}

		stateTime += deltaTime;
	}
}