package com.zukirou.habitofjump;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zukirou.game.Framework.math.OverlapTester;
import com.zukirou.game.Framework.math.Vector2;

public class World {
	public interface WorldListener{
		public void jump();
		public void highJump();
		public void hit();
		public void coin();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);
	
	public final PC pc;
	public final List<Platform> platforms;
	public final List<Spring> springs;
	public final List<Uma> umas;
	public final List<Coin> coins;
	public Castle castle;
	public final WorldListener listener;
	public final Random rand;
	
	public float heightSoFar;
	public int score;
	public int state;
	
	public World(WorldListener listener){
		this.pc = new PC(5, 0);
		this.platforms = new ArrayList<Platform>();
		this.springs = new ArrayList<Spring>();
		this.umas = new ArrayList<Uma>();
		this.coins = new ArrayList<Coin>();
		this.listener = listener;
		
		rand = new Random();
		generateLevel();
		
		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}
	
	private void generateLevel(){
		float y = Platform.PLATFORM_HEIGHT / 2;
		float maxJumpHeight = PC.PC_JUMP_VELOCITY * PC.PC_JUMP_VELOCITY / (2 * -gravity.y);
		while(y < WORLD_HEIGHT - WORLD_WIDTH / 2){
			int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
			float x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
			Platform platform = new Platform(type, x, y);
			platforms.add(platform);
			
			if(rand.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING){
				Spring spring = new Spring(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
				springs.add(spring);
			}
			
			if(y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f){
				Uma uma = new Uma(platform.position.x + rand.nextFloat(), platform.position.y + Uma.UMA_HEIGHT + rand.nextFloat() * 2);
				umas.add(uma);
			}
			
			if(rand.nextFloat() > 0.6f){
				Coin coin = new Coin(platform.position.x + rand.nextFloat(), platform.position.y + Coin.COIN_HEIGHT + rand.nextFloat() * 3);
				coins.add(coin);
			}
			
			y += (maxJumpHeight - 0.5f);
			y -= rand.nextFloat() * (maxJumpHeight / 3);
		}
		
		castle = new Castle(WORLD_WIDTH / 2, y);
	}
	
	public void update(float deltaTime, float accelX){
		updatePc(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateUmas(deltaTime);
		updateCoins(deltaTime);
		if(pc.state != PC.PC_STATE_HIT)
			checkCollisions();
		checkGameOver();
	}
	
	private void updatePc(float deltaTime, float accelX){
		if(pc.state != PC.PC_STATE_HIT && pc.position.y <= 0.5f)
			pc.hitPlatform();
		if(pc.state != PC.PC_STATE_HIT)
			pc.velocity.x = -accelX / 10 * PC.PC_MOVE_VELOCITY;
		pc.update(deltaTime);
		heightSoFar = Math.max(pc.position.y, heightSoFar);
	}
	
	private void updatePlatforms(float deltaTime){
		int len = platforms.size();
		for(int i = 0; i < len; i++){
			Platform platform = platforms.get(i);
			platform.update(deltaTime);
			if(platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME){
				platforms.remove(platform);
				len = platforms.size();
			}
		}
	}
	
	private void updateUmas(float deltaTime){
		int len = umas.size();
		for(int i = 0; i < len; i++){
			Uma uma = umas.get(i);
			uma.update(deltaTime);
		}
	}
	
	private void updateCoins(float deltaTime){
		int len = coins.size();
		for(int i = 0; i < len; i++){
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}
	
	private void checkCollisions(){
		checkPlatformCollisions();
		checkUmaCollisions();
		checkItemCollisions();
		checkCastleCollisions();
	}
	
	private void checkPlatformCollisions(){
		if(pc.velocity.y > 0)
			return;
		
		int len = platforms.size();
		for(int i = 0; i < len; i++){
			Platform platform = platforms.get(i);
			if(pc.position.y > platform.position.y){
				if(OverlapTester.overlapRectangles(pc.bounds, platform.bounds)){
					pc.hitPlatform();
					listener.jump();
					if(rand.nextFloat() > 0.5f){
						platform.pulverize();
					}
					break;
				}
			}
		}
	}
	
	private void checkUmaCollisions(){
		int len = umas.size();
		for(int i = 0; i < len; i++){
			Uma uma = umas.get(i);
			if(OverlapTester.overlapRectangles(uma.bounds, pc.bounds)){
				pc.hitUma();
				listener.hit();
			}
		}
	}
	
	private void checkItemCollisions(){
		int len = coins.size();
		for(int i = 0; i < len; i++){
			Coin coin = coins.get(i);
			if(OverlapTester.overlapRectangles(pc.bounds, coin.bounds)){
				coins.remove(coin);
				len = coins.size();
				listener.coin();
				score += Coin.COIN_SCORE;
			}
		}
		
		if(pc.velocity.y > 0)
			return;
		
		len = springs.size();
		for(int i = 0; i < len; i++){
			Spring spring = springs.get(i);
			if(pc.position.y > spring.position.y){
				if(OverlapTester.overlapRectangles(pc.bounds, spring.bounds)){
					pc.hitSpring();
					listener.highJump();
				}
			}
		}
	}
	
	private void checkCastleCollisions(){
		if(OverlapTester.overlapRectangles(castle.bounds, pc.bounds)){
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}
	
	private void checkGameOver(){
		if(heightSoFar - 7.5f > pc.position.y){
			state = WORLD_STATE_GAME_OVER;
		}
	}
}