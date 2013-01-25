package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.Music;
import com.zukirou.gameFrameWork.Sound;
import com.zukirou.game.Framework.gl.Animation;
import com.zukirou.game.Framework.gl.Font;
import com.zukirou.game.Framework.gl.Texture;
import com.zukirou.game.Framework.gl.TextureRegion;
import com.zukirou.games.impl.GLGame;

public class Assets{
	public static Texture background;
	public static TextureRegion backgroundRegion;
	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static Animation coinAnim;
	public static Animation sizJump;
	public static Animation sizFall;
	public static TextureRegion sizHit;
	public static Animation umaFly;
	public static TextureRegion platform;
	public static Animation breakingPlatform;
	public static Font font;
	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	
	public static void load(GLGame game){
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);
		
		items = new Texture(game, "items.png");
		mainMenu = new TextureRegion(items, 256, 352, 224, 96);
		pauseMenu = new TextureRegion(items, 256, 256,224, 96);
		ready = new TextureRegion(items, 128, 288, 128, 32);
		gameOver = new TextureRegion(items, 0, 288, 128, 64);
		highScoresRegion = new TextureRegion(Assets.items, 256, 384, 224, 32);
		logo = new TextureRegion(items, 0, 352, 256, 128);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 256, 256, 224, 96);
		
		spring = new TextureRegion(items, 128, 0, 32, 32);
		castle = new TextureRegion(items, 128, 64, 64, 64);
		coinAnim = new Animation(0.2f,	new TextureRegion(items, 128, 32, 32, 32),
										new TextureRegion(items, 160, 32, 32, 32),
										new TextureRegion(items, 192, 32, 32, 32),
										new TextureRegion(items, 160, 32, 32, 32),
										new TextureRegion(items, 128, 32, 32, 32));
		sizJump = new Animation(0.2f,	new TextureRegion(items, 0, 128, 32, 32),
										new TextureRegion(items, 32, 128, 32, 32));
		sizFall = new Animation(0.2f,	new TextureRegion(items, 64, 128, 32, 32),
										new TextureRegion(items, 96, 128, 32, 32));
		
		sizHit = new TextureRegion(items, 128, 128, 32, 32);

		umaFly = new Animation(0.2f,	new TextureRegion(items, 0, 160, 32, 32),
										new TextureRegion(items, 32, 160, 32, 32));
		
		platform = new TextureRegion(items, 64, 160, 64, 64);
		breakingPlatform = new Animation(0.2f,	new TextureRegion(items, 0, 224, 64, 64),
												new TextureRegion(items, 64, 224, 64, 64),
												new TextureRegion(items, 128, 224, 64, 64),
												new TextureRegion(items, 196, 224, 64, 64));
		
		font = new Font(items, 224, 0, 16, 16, 20);
		
		music = game.getAudio().newMusic("bgmDrum.ogg");
		music.setLooping(true);
		music.setVolume(0.5f);
		if(Settings.soundEnabled)
			music.play();
		jumpSound = game.getAudio().newSound("jump.ogg");
		highJumpSound = game.getAudio().newSound("up.ogg");
		hitSound = game.getAudio().newSound("damage.ogg");
		coinSound = game.getAudio().newSound("goin.ogg");
		clickSound = game.getAudio().newSound("click.ogg");
	}
	
	public static void reload(){
		background.reload();
		items.reload();
		if(Settings.soundEnabled)
			music.play();
	}
	
	public static void playSound(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}
	
}
