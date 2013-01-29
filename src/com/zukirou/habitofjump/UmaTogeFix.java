package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class UmaTogeFix extends DynamicGameObject{
	public static final float UMA_TOGE_WIDTH = 0.3f;
	public static final float UMA_TOGE_HEIGHT = 0.2f;
	public static final float UMA_TOGE_VELOCITY = 3f;
	
	float stateTime = 0;
	
	public UmaTogeFix(float x, float y){
		super (x, y, UMA_TOGE_WIDTH, UMA_TOGE_HEIGHT);
		velocity.set(0, 0);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(UMA_TOGE_WIDTH / 2, UMA_TOGE_HEIGHT / 2);
		
		stateTime += deltaTime;
	}
}