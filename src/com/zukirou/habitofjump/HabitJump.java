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
			Assets.music.pause();
	}
}