package com.zukirou.habitofjump;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;


import com.zukirou.gameFrameWork.Game;
import com.zukirou.gameFrameWork.Input.TouchEvent;

import com.zukirou.game.Framework.gl.Camera2D;
import com.zukirou.game.Framework.gl.SpriteBatcher;

import com.zukirou.games.impl.GLScreen;

import com.zukirou.game.Framework.math.OverlapTester;
import com.zukirou.game.Framework.math.Rectangle;
import com.zukirou.game.Framework.math.Vector2;


public class MainMenuScreen extends GLScreen{
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle enduranceBounds;
	Rectangle recordBounds;
	Vector2 touchPoint;
	
	public MainMenuScreen(Game game){
		super(game);
		guiCam = new Camera2D(glGraphics, 320, 480);
		batcher = new SpriteBatcher(glGraphics, 100);
		
		soundBounds = new Rectangle(0, 0, 64, 64);

		playBounds = new Rectangle(87, 185, 146, 40);

		enduranceBounds = new Rectangle(87, 135, 146, 40);
		
		recordBounds = new Rectangle(87, 85, 146, 40);
		
		touchPoint = new Vector2();
	}
	
	@Override
	public void update(float deltaTime){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				
				if(OverlapTester.pointInRectangle(playBounds, touchPoint)){
					batcher.beginBatch(Assets.items);
					batcher.drawSprite(160, 200, 300, 110, Assets.mainMenuStartPush);
					batcher.endBatch();
					Assets.playSound(Assets.clickSound);
					game.setScreen(new GameScreen(game));
					return;
				}
/*				
				if(OverlapTester.pointInRectangle(enduranceBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HighscoresScreen(game));
					return;
				}
				
				if(OverlapTester.pointInRectangle(recordBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HelpScreen1(game));
					return;
				}
*/				
				if(OverlapTester.pointInRectangle(soundBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
						Assets.music.play();
					else
						Assets.music.pause();
				}
			}
		}
	}
	
	@Override
	public void present(float deltaTime){
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
				
		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(160, 200, 315, 352, Assets.logo);
		batcher.drawSprite(160, 185, 146, 40, Assets.mainMenuStart);
		batcher.drawSprite(160, 135, 146, 40, Assets.mainMenuEndurance);
		batcher.drawSprite(160, 85, 146, 40, Assets.mainMenuScoreRecord);
		batcher.drawSprite(32, 32, 64, 64, Settings.soundEnabled ? Assets.soundOn : Assets.soundOff);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}
	
	@Override
	public void pause(){
		Settings.save(game.getFileIO());
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