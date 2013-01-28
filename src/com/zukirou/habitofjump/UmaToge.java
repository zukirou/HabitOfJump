package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class UmaToge extends DynamicGameObject{
	public static final float UMA_TOGE_WIDTH = 0.3f;
	public static final float UMA_TOGE_HEIGHT = 0.2f;
	public static final float UMA_TOGE_VELOCITY = 1f;
	
	float stateTime = 0;
	
	public UmaToge(float x, float y){
		super (x, y, UMA_TOGE_WIDTH, UMA_TOGE_HEIGHT);
		velocity.set(UMA_TOGE_VELOCITY, 0);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(UMA_TOGE_WIDTH / 2, UMA_TOGE_HEIGHT / 2);
		
		if(position.x < UMA_TOGE_WIDTH / 2){
			position.x = UMA_TOGE_WIDTH / 2;
			velocity.x = UMA_TOGE_VELOCITY;
		}
		if(position.x > World.WORLD_WIDTH - UMA_TOGE_WIDTH / 2){
			position.x = World.WORLD_WIDTH - UMA_TOGE_WIDTH / 2;
			velocity.x = -UMA_TOGE_VELOCITY;
		}
		stateTime += deltaTime;
	}
	
	public void updateFall(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(UMA_TOGE_WIDTH / 2, UMA_TOGE_HEIGHT / 2);
		
		if(position.y < UMA_TOGE_HEIGHT / 2){
			position.y = UMA_TOGE_HEIGHT / 2;
			velocity.y = UMA_TOGE_VELOCITY;
		}
		if(position.y > World.WORLD_HEIGHT - UMA_TOGE_HEIGHT / 2){
			position.y = World.WORLD_HEIGHT - UMA_TOGE_HEIGHT / 2;
			velocity.y = -UMA_TOGE_VELOCITY;
		}
		stateTime += deltaTime;
	}

}