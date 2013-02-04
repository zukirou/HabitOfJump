package com.zukirou.habitofjump;

import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.zukirou.gameFrameWork.Game;
import com.zukirou.gameFrameWork.Input.TouchEvent;

import com.zukirou.game.Framework.gl.Animation;
import com.zukirou.game.Framework.gl.Camera2D;
import com.zukirou.game.Framework.gl.FPSCounter;
import com.zukirou.game.Framework.gl.SpriteBatcher;
import com.zukirou.game.Framework.gl.TextureRegion;

import com.zukirou.games.impl.GLScreen;

import com.zukirou.game.Framework.math.OverlapTester;
import com.zukirou.game.Framework.math.Rectangle;
import com.zukirou.game.Framework.math.Vector2;
import com.zukirou.habitofjump.World.WorldListener;

public class GameScreen extends GLScreen{
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int GAME_STORY_CLEAR = 5;

	public Random rand;

	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle endBounds;
	int lastScore;
	String scoreString;
	
	FPSCounter fpsCounter;
	
	public GameScreen(Game game){
		super(game);
		state = GAME_READY;
		guiCam = new Camera2D(glGraphics, 320, 480);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 1000);
		worldListener = new WorldListener(){
			@Override
			public void jump(){
				Assets.playSound(Assets.jumpSound);
			}
			
			@Override
			public void highJump(){
				Assets.playSound(Assets.highJumpSound);
			}
			
			@Override
			public void hit(){
				Assets.playSound(Assets.hitSound);
			}
			
			@Override
			public void coin(){
				Assets.playSound(Assets.coinSound);
			}
			
			@Override
			public void hitDamage(){
				Assets.playSound(Assets.hitDamageSound00);
			}
			
			@Override
			public void bossDead(){
				Assets.playSound(Assets.bossDeadSound);
			}
			
		};
		
		world = new World(worldListener);
		renderer = new WorldRenderer(glGraphics, batcher, world);
		pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		quitBounds = new Rectangle(160 - 96, 190, 192, 36);
		endBounds = new Rectangle(160 - 96, 100, 96, 32);
		lastScore = 0;
		scoreString = "score: 0";
		
		fpsCounter = new FPSCounter();
	}
	
	@Override
	public void update(float deltaTime){
		if(deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch(state){
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		case GAME_STORY_CLEAR:
			updateGameStoryClear();
			break;
		}
	}
	
	private void updateReady(){
		if(game.getInput().getTouchEvents().size() > 0){
			state = GAME_RUNNING;
		}
	}
	
	private void updateRunning(float deltaTime){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
		}

		
		world.update(deltaTime, game.getInput().getAccelX());
		int roundLevel = World.roundLevel;
		Settings.currentRound = roundLevel;

		
		if(world.score != lastScore){
			lastScore = world.score;
			scoreString = "score:" + lastScore;
		}
		if(world.state == World.WORLD_STATE_NEXT_LEVEL){
			state = GAME_LEVEL_END;
		}
		if(world.state == World.WORLD_STATE_GAME_OVER){
			state = GAME_OVER;
			for(int i = 0; i < 5; i++){
				if(lastScore >= Settings.highscores[i])
					scoreString = "new highscore: " + lastScore;
				else
					scoreString = "score: " + lastScore;				
			}
			Settings.addScore(lastScore, roundLevel);
			Settings.save(game.getFileIO());
		}
		if(world.state == World.WORLD_STATE_GAME_STORY_CLEAR){
			state = GAME_STORY_CLEAR;
		}
	}
	
	private void updatePaused(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}
			if(OverlapTester.pointInRectangle(quitBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}
	
	private void updateLevelEnd(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			World.roundLevel ++;
			Settings.currentRound = World.roundLevel;
			game.setScreen(new StoryScreen(game));
			world = new World(worldListener);
			renderer = new WorldRenderer(glGraphics, batcher, world);
//			world.score = lastScore;
//			state = GAME_READY;
		}
	}
	
	private void updateGameOver(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			World.gravity = new Vector2(0, -12);
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	private void updateGameStoryClear(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();		
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				World.roundLevel = 0;
				Settings.currentRound = 0;
				World.camMovFlag = 0;
				World.blankGround = 0;
				game.setScreen(new MainMenuScreen(game));
			}else{
				continue;
			}
		}
	}

	
	@Override
	public void present(float deltaTime){
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		renderer.render();
		
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		
		if(Settings.currentRound == 0){
			TextureRegion keyFrameExplain;
			keyFrameExplain = Assets.explain.getKeyFrame(World.explainTime, Animation.ANIMATION_LOOPING);
			batcher.drawSprite(160, 350, 96, 64, keyFrameExplain);			
		}
				
		switch(state){
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		case GAME_STORY_CLEAR:
			presentGameStoryClear();
			break;
		}
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);

//		fpsCounter.logFrame();
		
	}
	
	private void presentReady(){
		batcher.drawSprite(160 , 150, 64, 32, 90, Assets.goArrow);
		switch(Settings.currentRound){
		case 0:
			batcher.drawSprite(160, 240, 96, 32, Assets.ready00);
			break;
		case 1:
			batcher.drawSprite(160, 240, 96, 32, Assets.ready01);
			break;
		case 2:
			batcher.drawSprite(160, 240, 96, 32, Assets.ready02);
			break;
		case 3:
			batcher.drawSprite(160, 240, 96, 32, Assets.ready03);
			break;
		case 4:
			batcher.drawSprite(160, 240, 96, 32, Assets.ready04);
			break;
		case 5:
			batcher.drawSprite(160, 240, 96, 32, Assets.ready05);
			break;
		case 6:
			batcher.drawSprite(160, 240, 96, 32, Assets.ready06);
			break;
		}
	}
	
	private void presentRunning(){

		batcher.drawSprite(320 - 10, 480, 10, 40, Assets.resume);
//		Assets.font.drawText(batcher, scoreString, 16, 480 - 20);
	}
	
	private void presentPaused(){
		batcher.drawSprite(160, 240, 113, 24, Assets.resume);
		batcher.drawSprite(160, 190, 70, 24, Assets.quit);
		
//		Assets.font.drawText(batcher, scoreString, 16, 480 - 20);
	}
	
	private void presentLevelEnd(){
		/*
		String topText = " Good Job!";		
		String bottomText = "Please Touch Screen";					
		float topWidth = Assets.font.glyphWidth * topText.length();		
		float bottomWidth = Assets.font.glyphWidth * bottomText.length();		
		Assets.font.drawText(batcher, topText, 160 - topWidth / 2, 480 - 40);		
		Assets.font.drawText(batcher, bottomText, 160 - bottomWidth / 2, 480 - 80);
		*/
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(160, 400, 64, 32, Assets.goArrow);
		batcher.endBatch();
	}
	
	private void presentGameOver(){
		batcher.drawSprite(160, 150, 96, 32, Assets.gameOver);
//		float scoreWidth = Assets.font.glyphWidth * scoreString.length();
//		Assets.font.drawText(batcher, scoreString, 160 - scoreWidth / 2, 480 - 20);
	}
	
	private void presentGameStoryClear(){
		batcher.beginBatch(Assets.bg05);
		batcher.drawSprite(160, 240, 320, 480, 180, Assets.backgroundRegion06);
		batcher.endBatch();
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(144, 100, 32, 32, Assets.girl);
		batcher.drawSprite(176, 100, 32, 32, Assets.man);
		batcher.drawSprite(160, 290, 256, 160, Assets.storyEnd);
//		String endText = "E n d";
//		String endText01 = "Thanks for your playing";
//		float endTxtWidth = Assets.font.glyphWidth * endText.length();
//		float endTxt01Width = Assets.font.glyphWidth * endText01.length();
//		Assets.font.drawText(batcher, endText, 160 - endTxtWidth / 2, 80);
//		Assets.font.drawText(batcher, endText01, 160 - endTxt01Width / 2, 40);
		batcher.endBatch();
		
	}
	
	public void pause(){
		if(state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	
}