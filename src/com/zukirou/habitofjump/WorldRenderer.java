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
		if(world.pc.position.y > cam.position.y)
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
			batcher.drawSprite(platform.position.x, platform.position.y, 2, 2, keyFrame);
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
			TextureRegion keyFrame = Assets.umaFly.getKeyFrame(uma.stateTime, Animation.ANIMATION_LOOPING);
			float side = uma.velocity.x < 0 ? -1 : 1;
			batcher.drawSprite(uma.position.x, uma.position.y, side * 1, 1, keyFrame);
		}
	}
	
	private void renderCastel(){
		Castle castle = world.castle;
		batcher.drawSprite(castle.position.x, castle.position.y, 2, 2, Assets.castle);
	}
}