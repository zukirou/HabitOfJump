package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class Uma extends DynamicGameObject{
	public static final float UMA_WIDTH = 1;
	public static final float UMA_HEIGHT = 0.6f;
	public static final float UMA_VELOCITY = 3f;
	
	float stateTime = 0;
	
	public Uma(float x, float y){
		super (x, y, UMA_WIDTH, UMA_HEIGHT);
		velocity.set(UMA_VELOCITY, 0);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(UMA_WIDTH / 2, UMA_HEIGHT / 2);
		
		if(position.x < UMA_WIDTH / 2){
			position.x = UMA_WIDTH / 2;
			velocity.x = UMA_VELOCITY;
		}
		if(position.x > World.WORLD_WIDTH - UMA_WIDTH / 2){
			position.x = World.WORLD_WIDTH - UMA_WIDTH / 2;
			velocity.x = -UMA_VELOCITY;
		}
		stateTime += deltaTime;
	}
}