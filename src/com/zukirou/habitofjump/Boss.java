package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class Boss extends DynamicGameObject{
	public static final float BOSS_WIDTH = 3;
	public static final float BOSS_HEIGHT = 2;
	public static final float BOSS_VELOCITY = 1;
	
	float stateTime = 0;
	
	public Boss(float x, float y){
		super (x, y, BOSS_WIDTH, BOSS_HEIGHT);
		velocity.set(BOSS_VELOCITY, 0);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(BOSS_WIDTH / 2, BOSS_HEIGHT / 2);
		
		if(position.x < BOSS_WIDTH / 2){
			position.x = BOSS_WIDTH / 2;
			velocity.x = BOSS_VELOCITY;
		}
		if(position.x > World.WORLD_WIDTH - BOSS_WIDTH / 2){
			position.x = World.WORLD_WIDTH - BOSS_WIDTH / 2;
			velocity.x = -BOSS_VELOCITY;
		}
		stateTime += deltaTime;
	}
}