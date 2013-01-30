package com.zukirou.habitofjump;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zukirou.game.Framework.gl.Animation;
import com.zukirou.game.Framework.gl.TextureRegion;
import com.zukirou.game.Framework.math.OverlapTester;
import com.zukirou.game.Framework.math.Vector2;

public class World {
	public interface WorldListener{
		public void jump();
		public void highJump();
		public void hit();
		public void coin();
		public void hitDamage();
		public void bossDead();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 5;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_STATE_GAME_STORY_CLEAR = 3;
	public static Vector2 gravity = new Vector2(0, -12);
	
	public final PC pc;
	public final List<Platform> platforms;
	public final List<Spring> springs;
	public final List<Uma> umas;
	public final List<UmaToge> umaToges;
	public final List<Coin> coins;
	public Castle castle;
	public final WorldListener listener;
	public final Random rand;
		
	public float heightSoFar;
	public int score;
	public int state;
	static int roundLevel;
	public int platformType;
	public float platformX;
	public float umaX;
	public float umaY;
	public float umaFallX;
	public float umaFallY;
	public float becomePulverizer = 0.5f;
	public Boss boss;
	public final List<UmaFall> umasFall;
	public final List<UmaTogeFall> umaTogesFall;
	public final List<UmaTogeFix> umaTogesFix;
	
	public int camMovFlag = 0;
	public int blankGround = 0;
	public int bossHp = Boss.BOSS_HP;

	
	public World(WorldListener listener){
		this.pc = new PC(5, 0);
		this.platforms = new ArrayList<Platform>();
		this.springs = new ArrayList<Spring>();
		this.umas = new ArrayList<Uma>();
		this.umaToges = new ArrayList<UmaToge>();
		this.coins = new ArrayList<Coin>();
		this.listener = listener;
		this.boss = new Boss(WORLD_WIDTH / 2, 13);			
		this.umasFall = new ArrayList<UmaFall>();
		this.umaTogesFall = new ArrayList<UmaTogeFall>();
		this.umaTogesFix = new ArrayList<UmaTogeFix>();
				
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
			switch(roundLevel){
			case 0:
				platformType = Platform.PLATFORM_TYPE_NONBREAK;
				platformX = 5;
				break;
				
			case 1:
				platformX = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
				platformType = Platform.PLATFORM_TYPE_NONBREAK;
				if(rand.nextFloat() > 0.6f){
					Spring spring = new Spring(platformX, y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
					springs.add(spring);					
				}else{
					break;					
				}
				break;

			case 2:
				platformX = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
				platformType = rand.nextFloat() > 0.5 ? Platform.PLATFORM_TYPE_STATIC : Platform.PLATFORM_TYPE_NONBREAK;
				break;

			case 3:
				platformX = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
				platformType = rand.nextFloat() > 0.5f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
				becomePulverizer = 0.3f;

				if(rand.nextFloat() > 0.8f){
					Spring spring = new Spring(platformX, y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
					springs.add(spring);					
				}else{
					break;					
				}
				break;
				
			case 4:
				platformX = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
				umaX = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2; 
				platformType = rand.nextFloat() > 0.5f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
				becomePulverizer = 0;
				if(rand.nextFloat() > 0.5f){
					Spring spring = new Spring(platformX, y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
					springs.add(spring);					
				}else{
					Uma uma = new Uma(umaX + rand.nextFloat(), y + Uma.UMA_HEIGHT + rand.nextFloat() * 2);
					umas.add(uma);
					UmaToge umaToge = new UmaToge(umaX + rand.nextFloat(), y + UmaToge.UMA_TOGE_HEIGHT + rand.nextFloat() * 2);
					umaToges.add(umaToge);
				}
				break;

			case 5:
				platformX = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
				umaX = rand.nextFloat() * (WORLD_WIDTH - Uma.UMA_WIDTH) + Uma.UMA_WIDTH / 2; 
				if(y > 0 && y < 20 || y > 30 && y < 50 || y > 60 && y < 70){
					Spring spring = new Spring(platformX, y + Platform.PLATFORM_HEIGHT / 2 + Spring.SPRING_HEIGHT / 2);
					springs.add(spring);
				}else{
					Uma uma = new Uma(umaX + (rand.nextFloat() * rand.nextFloat() > 0.5f ? 1 : -1), y + Uma.UMA_HEIGHT);// + rand.nextFloat() * 2);
					umas.add(uma);
					UmaToge umaToge = new UmaToge(umaX + rand.nextFloat(), y + UmaToge.UMA_TOGE_HEIGHT);// + rand.nextFloat() * 2);
					umaToges.add(umaToge);					
				}
				break;
				
			case 6:
				camMovFlag = 1;
				blankGround = 1;
				for(int i = 0; i < 15; i++){
					UmaTogeFix umaTogeFix = new UmaTogeFix(i, 14.9f);
					umaTogesFix.add(umaTogeFix);
				}
				break;
				
			default:
				break;
			}
			if(roundLevel < 5 ){//‚T‚©‚ç‘«ê‚Å‚È‚¢B
				Platform platform = new Platform(platformType, platformX, y);
				platforms.add(platform);
				y += (maxJumpHeight - 0.5f);
				y -= rand.nextFloat() * (maxJumpHeight / 3);
			}else{
				y += (maxJumpHeight - 0.5f);
				y -= rand.nextFloat() * (maxJumpHeight / 3);
			}
		}
			castle = new Castle(WORLD_WIDTH / 2, y);
	}

	
	public void update(float deltaTime, float accelX){
		updatePc(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateUmas(deltaTime);
		updateUmaToges(deltaTime);
		updateCoins(deltaTime);
		updateBoss(deltaTime);
		updateUmasFall(deltaTime);
		updateUmaTogesFall(deltaTime);
		
		if(pc.state != PC.PC_STATE_HIT)
			checkCollisions();
		if(blankGround == 0)
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
	
	private void updateUmaToges(float deltaTime){
		int len = umaToges.size();
		for(int i = 0; i < len; i++){
			UmaToge umaToge = umaToges.get(i);
			umaToge.update(deltaTime);
		}
	}
	
	private void updateCoins(float deltaTime){
		int len = coins.size();
		for(int i = 0; i < len; i++){
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}
	
	private void updateBoss(float deltaTime){
			boss.update(deltaTime);
			
			if(boss.position.x > 1.5 && boss.position.x < 1.5 + deltaTime){
				UmaFall umaFall= new UmaFall(2, 13);
				umasFall.add(umaFall);
				UmaTogeFall umaTogeFall = new UmaTogeFall(2, 13);
				umaTogesFall.add(umaTogeFall);																	
			}
			
			if(boss.position.x > 5 && boss.position.x < 5 + deltaTime){
				UmaFall umaFall= new UmaFall(5, 13);
				umasFall.add(umaFall);
				UmaTogeFall umaTogeFall = new UmaTogeFall(5, 13);
				umaTogesFall.add(umaTogeFall);													
			}
			
			if(boss.position.x > 8 - deltaTime && boss.position.x < 8){
				UmaFall umaFall= new UmaFall(8, 13);
				umasFall.add(umaFall);
				UmaTogeFall umaTogeFall = new UmaTogeFall(8, 13);
				umaTogesFall.add(umaTogeFall);													
			}	
			if(boss.state == Boss.BOSS_STATE_DEAD && boss.stateTime > Boss.BOSS_DEAD_TIME){			
				state = WORLD_STATE_GAME_STORY_CLEAR;
				roundLevel = 0;
			}
	}

	private void updateUmasFall(float deltaTime){
		int len = umasFall.size();
		for(int i = 0; i < len; i++){
			UmaFall umaFall = umasFall.get(i);
			umaFall.update(deltaTime);
/*
			if(umaFall.position.y < 0){
				umasFall.remove(umaFall);
				len = umasFall.size();
			}
			*/
		}
	}
	
	private void updateUmaTogesFall(float deltaTime){
		int len = umaTogesFall.size();
		for(int i = 0; i < len; i++){
			UmaTogeFall umaTogeFall = umaTogesFall.get(i);
			umaTogeFall.update(deltaTime);
			/*
			if(umaTogeFall.position.y < 0){
				umaTogesFall.remove(umaTogeFall);
				len = umaTogesFall.size();
			}
			*/
		}
	}
	
	private void checkCollisions(){
		checkPlatformCollisions();
		checkUmaCollisions();
		checkUmaTogeCollisions();
		checkItemCollisions();
		checkCastleCollisions();		
		checkBossCollisions();
		checkUmaFallCollisions();
		checkUmaTogeFallCollisions();
		checkUmaTogeFixCollisions();
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
					if(rand.nextFloat() > becomePulverizer && platform.state != Platform.PLATFORM_STATE_FIXED){
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
				pc.hitPlatform();							
				listener.jump();							
			}
		}
	}
	
	private void checkUmaTogeCollisions(){
		int len = umaToges.size();
		for(int i = 0; i < len; i++){
			UmaToge umaToge = umaToges.get(i);
			if(	OverlapTester.overlapRectangles(umaToge.bounds, pc.bounds) && pc.state == PC.PC_STATE_JUMP){
				gravity = new Vector2(0, 0);
				pc.hitUma();				
				listener.hit();
			}			
		}				
	}		
	
	private void checkUmaFallCollisions(){
		int len = umasFall.size();
		for(int i = 0; i < len; i++){
			UmaFall umaFall = umasFall.get(i);						
			if(OverlapTester.overlapRectangles(umaFall.bounds, pc.bounds)){								
				pc.hitPlatform();							
				listener.jump();							
			}
		}
	}

	private void checkUmaTogeFallCollisions(){
		int len = umaTogesFall.size();
		for(int i = 0; i < len; i++){
			UmaTogeFall umaTogeFall = umaTogesFall.get(i);
			if(	OverlapTester.overlapRectangles(umaTogeFall.bounds, pc.bounds) && pc.state == PC.PC_STATE_JUMP){
				gravity = new Vector2(0, 0);
				pc.hitUma();				
				listener.hit();
				state = WORLD_STATE_GAME_OVER;
			}			
		}				
	}		

	private void checkUmaTogeFixCollisions(){
		int len = umaTogesFix.size();
		for(int i = 0; i < len; i++){
			UmaTogeFix umaTogeFix = umaTogesFix.get(i);
			if(	OverlapTester.overlapRectangles(umaTogeFix.bounds, pc.bounds)){
				gravity = new Vector2(0, 0);
				pc.hitUma();				
				listener.hit();
				state = WORLD_STATE_GAME_OVER;
			}			
		}				
	}		

	private void checkBossCollisions(){
		if(OverlapTester.overlapRectangles(boss.bounds, pc.bounds)){										
			pc.hitBoss();			
			listener.hitDamage();			
			bossHp -= 60;			
			if(bossHp < 0){
				listener.bossDead();																				
				boss.dead();
//				camMovFlag = 0;
//				blankGround = 0;
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
		if(pc.state == PC.PC_STATE_FALL && OverlapTester.overlapRectangles(castle.bounds, pc.bounds)){
			roundLevel ++;
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}
	
	private void checkGameOver(){
		if(heightSoFar - 8.0f > pc.position.y){
			state = WORLD_STATE_GAME_OVER;
		}
	}
}