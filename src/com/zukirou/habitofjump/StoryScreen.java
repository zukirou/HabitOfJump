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


public class StoryScreen extends GLScreen{
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle arrowBounds;
	int arrowTouchFlag = 0;
	Vector2 touchPoint;
	int round = Settings.currentRound;
	
	public StoryScreen(Game game){
		super(game);
		guiCam = new Camera2D(glGraphics, 320, 480);
		batcher = new SpriteBatcher(glGraphics, 100);
		arrowBounds = new Rectangle(90, 90, 100, 100);
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
				arrowTouchFlag = 1;
			}
		
			if(arrowTouchFlag == 1 && event.type == TouchEvent.TOUCH_UP && OverlapTester.pointInRectangle(arrowBounds, touchPoint)){
				arrowTouchFlag = 0;
				Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game));
				
				return;
				
			}
			
		}
	}
	
	@Override
	public void present(float deltaTime){
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
				
		gl.glEnable(GL10.GL_TEXTURE_2D);
				
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		if(arrowTouchFlag == 1){
			batcher.drawSprite(160, 150, 64, 32, Assets.goArrow);
		}
		switch(round){
		case 0:
			batcher.drawSprite(160, 480 - 192, 256, 192, Assets.storyR00);
			break;
		case 1:
			batcher.drawSprite(160, 480 - 192, 256, 192, Assets.storyR01);
			break;
		case 2:
			batcher.drawSprite(160, 480 - 192, 256, 192, Assets.storyR02);
			break;
		case 3:
			batcher.drawSprite(160, 480 - 192, 256, 192, Assets.storyR03);
			break;
		case 4:
			batcher.drawSprite(160, 480 - 192, 256, 192, Assets.storyR04);
			break;
		case 5:
			batcher.drawSprite(160, 480 - 192, 256, 192, Assets.storyR05);
			break;
		case 6:
			batcher.drawSprite(160, 480 - 192, 256, 192, Assets.storyR06);
			break;
		}
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