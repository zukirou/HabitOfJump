package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class Boss extends DynamicGameObject{
	public static final float BOSS_WIDTH = 3;
	public static final float BOSS_HEIGHT = 3;
	public static final float BOSS_VELOCITY = 1;
	public static final int BOSS_HP = 100;
	public static final int BOSS_STATE_ALIVE = 0;
	public static final int BOSS_STATE_DEAD = 1;
	public static final int BOSS_STATE_WAIT = 2;
	public static final float BOSS_DEAD_TIME = 0.2f * 8;
	
	float stateTime = 0;
	int state;
	
	public Boss(float x, float y){
		super (x, y, BOSS_WIDTH, BOSS_HEIGHT);
		velocity.set(BOSS_VELOCITY, 0);
		this.state = BOSS_STATE_WAIT;
	}

	public void update(float deltaTime){
		if(state == BOSS_STATE_ALIVE){
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
	
	public void dead(){
		stateTime = 0;
		velocity.x = 0;
		state = BOSS_STATE_DEAD;
	}
	
}