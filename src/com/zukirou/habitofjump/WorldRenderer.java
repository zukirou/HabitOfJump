package com.zukirou.habitofjump;

import javax.microedition.khronos.opengles.GL10;

import com.zukirou.game.Framework.gl.Animation;
import com.zukirou.game.Framework.gl.Camera2D;
import com.zukirou.game.Framework.gl.SpriteBatcher;
import com.zukirou.game.Framework.gl.TextureRegion;
import com.zukirou.games.impl.GLGraphics;

public class WorldRenderer{
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;
	
	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world){
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}
	
	public void render(){
		if(world.pc.position.y > cam.position.y && world.camMovFlag == 0)//カメラ動かしたくないときに使うフラグ
			cam.position.y = world.pc.position.y;
		cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}
	
	public void renderBackground(){
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundRegion);
		batcher.endBatch();
	}
	
	public void renderObjects(){
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		renderPc();
		renderPlatforms();
		renderItems();
		renderUma();
		renderCastel();
		renderUmaFall();
		renderUmaTogeFix();
		renderBoss();		
		batcher.endBatch();			
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderPc(){
		TextureRegion keyFrame;
		
		switch(world.pc.state){
		case PC.PC_STATE_FALL:
			keyFrame = Assets.sizFall.getKeyFrame(world.pc.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case PC.PC_STATE_JUMP:
			keyFrame = Assets.sizJump.getKeyFrame(world.pc.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case PC.PC_STATE_HIT:
		default:
			keyFrame = Assets.sizHit;
		}
		float side = world.pc.velocity.x < 0 ? -1 : 1;
		batcher.drawSprite(world.pc.position.x, world.pc.position.y, side * 1, 1, keyFrame);
	}
	
	private void renderPlatforms(){
		int len = world.platforms.size();
		for(int i = 0; i < len; i++){
			Platform platform = world.platforms.get(i);
			TextureRegion keyFrame = Assets.platform;
			if(platform.state == Platform.PLATFORM_STATE_PULVERIZING){
				keyFrame = Assets.breakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
			}
			if(platform.state == Platform.PLATFORM_STATE_FIXED){
				batcher.drawSprite(platform.position.x, platform.position.y, 2, 0.5f, Assets.nonBreakPlatform);
			}else{
				batcher.drawSprite(platform.position.x, platform.position.y, 2, 2, keyFrame);								
			}
		}
	}
	
	private void renderItems(){
		int len = world.springs.size();
		for(int i = 0; i < len; i++){
			Spring spring = world.springs.get(i);
			batcher.drawSprite(spring.position.x, spring.position.y, 1, 1, Assets.spring);
		}
		len = world.coins.size();
		for(int i = 0; i < len; i++){
			Coin coin = world.coins.get(i);
			TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
			batcher.drawSprite(coin.position.x, coin.position.y, 1, 1, keyFrame);
		}
	}
	
	private void renderUma(){
		int len = world.umas.size();
		for(int i = 0; i < len; i++){
			Uma uma = world.umas.get(i);
			UmaToge umaToge = world.umaToges.get(i);
			TextureRegion keyFrame = Assets.umaFly.getKeyFrame(uma.stateTime, Animation.ANIMATION_LOOPING);
			TextureRegion togeKeyFrame = Assets.umaToge.getKeyFrame(umaToge.stateTime, Animation.ANIMATION_LOOPING);
			float side = uma.velocity.x < 0 ? -1 : 1;
			float toge_side = uma.velocity.x < 0 ? -1 : 1;
			umaToge.position.x = uma.position.x;
			umaToge.position.y = uma.position.y - 0.5f;
			batcher.drawSprite(uma.position.x, uma.position.y, side * 1, 0.5f, keyFrame);
			batcher.drawSprite(umaToge.position.x, umaToge.position.y, toge_side * 1, 0.5f, togeKeyFrame);
		}
	}
	
	private void renderBoss(){
		Boss boss = world.boss;		
		if(boss.state == Boss.BOSS_STATE_DEAD){
			TextureRegion keyFrame = Assets.bossDead.getKeyFrame(boss.stateTime, Animation.ANIMATION_NONLOOPING);			
			batcher.drawSprite(boss.position.x, boss.position.y, 3, 3, keyFrame);
		}else if(boss.state == Boss.BOSS_STATE_ALIVE){		
			TextureRegion keyFrame = Assets.Boss.getKeyFrame(boss.stateTime, Animation.ANIMATION_LOOPING);			
			float side = boss.velocity.x < 0 ? -1 : 1;						
			batcher.drawSprite(boss.position.x, boss.position.y, side * 3, 3, keyFrame);			
		}
	}

	
	private void renderUmaFall(){
		int len = world.umasFall.size();
		for(int i = 0; i < len; i++){
			UmaFall umaFall = world.umasFall.get(i);
			UmaTogeFall umaTogeFall = world.umaTogesFall.get(i);
			TextureRegion keyFrame = Assets.umaFly.getKeyFrame(umaFall.stateTime, Animation.ANIMATION_LOOPING);
			TextureRegion togeKeyFrame = Assets.umaToge.getKeyFrame(umaTogeFall.stateTime, Animation.ANIMATION_LOOPING);
			umaTogeFall.position.x = umaFall.position.x;
			umaTogeFall.position.y = umaFall.position.y - 0.5f;
			batcher.drawSprite(umaFall.position.x, umaFall.position.y, 1, 0.5f, keyFrame);
			batcher.drawSprite(umaTogeFall.position.x, umaTogeFall.position.y, 1, 0.5f, togeKeyFrame);
		}
	}
	
	private void renderUmaTogeFix(){
		int len = world.umaTogesFix.size();
		for(int i = 0; i < len; i++){
			UmaTogeFix umaTogeFix = world.umaTogesFix.get(i);
			TextureRegion togeKeyFrame = Assets.umaToge.getKeyFrame(umaTogeFix.stateTime, Animation.ANIMATION_NONLOOPING);
			batcher.drawSprite(umaTogeFix.position.x, umaTogeFix.position.y, 1, 0.5f, togeKeyFrame);
		}
	}
	
	private void renderCastel(){
		Castle castle = world.castle;
		batcher.drawSprite(castle.position.x, castle.position.y + 0.7f, 2, 2, Assets.castle);
	}
}