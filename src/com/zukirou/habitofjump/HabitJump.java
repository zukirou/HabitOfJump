package com.zukirou.habitofjump;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.zukirou.gameFrameWork.Screen;
import com.zukirou.games.impl.GLGame;

public class HabitJump extends GLGame{
	boolean firstTimeCreate = true;
	
	@Override
	public Screen getStartScreen(){
		return new MainMenuScreen(this);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config){
		super.onSurfaceCreated(gl, config);
		if(firstTimeCreate){
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		}else{
			Assets.reload();
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(Settings.soundEnabled)
			switch(Settings.currentRound){
			case 0:
				Assets.music00.pause();
				break;
			case 1:
				Assets.music01.pause();
				break;
			case 2:
				Assets.music02.pause();
				break;
			case 3:
				Assets.music03.pause();
				break;
			case 4:
				Assets.music04.pause();
				break;
			case 5:
				Assets.music05.pause();
				break;
			case 6:
				Assets.music06.pause();
				break;
			}
	}
}