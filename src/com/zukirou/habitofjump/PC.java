package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.DynamicGameObject;

public class PC extends DynamicGameObject{
	public static final int PC_STATE_JUMP = 0;
	public static final int PC_STATE_FALL = 1;
	public static final int PC_STATE_HIT = 2;
	public static final float PC_JUMP_VELOCITY = 11;
	public static final float PC_MOVE_VELOCITY = 20;
	public static final float PC_WIDTH = 1;
	public static final float PC_HEIGHT = 1;
	
	int state;
	float stateTime;
	
	public PC(float x, float y){
		super(x, y, PC_WIDTH, PC_HEIGHT);
		state = PC_STATE_FALL;
		stateTime = 0;
	}
	
	public void update(float deltaTime){
		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		
		if(velocity.y > 0 && state != PC_STATE_HIT){
			if(state != PC_STATE_JUMP){
				state = PC_STATE_JUMP;
				stateTime = 0;
			}
		}
		
		if(velocity.y < 0 && state != PC_STATE_HIT){
			if(state != PC_STATE_FALL){
				state = PC_STATE_FALL;
				stateTime = 0;
			}
		}
		if(position.x < 0)
			position.x = World.WORLD_WIDTH;
		if(position.x > World.WORLD_WIDTH)
			position.x = 0;
		
		stateTime += deltaTime;
	}
	
	public void hitUma(){
		velocity.set(0, 0);
		state = PC_STATE_HIT;
		stateTime = 0;
	}
	
	public void hitPlatform(){
		velocity.y = PC_JUMP_VELOCITY;
		state = PC_STATE_JUMP;
		stateTime = 0;
	}
	
	public void hitSpring(){
		velocity.y = PC_JUMP_VELOCITY * 1.5f;
		state = PC_STATE_JUMP;
		stateTime = 0;
	}
}