package com.zukirou.habitofjump;

import com.zukirou.gameFrameWork.Music;
import com.zukirou.gameFrameWork.Sound;
import com.zukirou.game.Framework.gl.Animation;
import com.zukirou.game.Framework.gl.Font;
import com.zukirou.game.Framework.gl.Texture;
import com.zukirou.game.Framework.gl.TextureRegion;
import com.zukirou.games.impl.GLGame;

public class Assets{
	public static Texture bg00;
	public static TextureRegion backgroundRegion00;
	public static Texture bg01;
	public static TextureRegion backgroundRegion01;
	public static Texture bg02;
	public static TextureRegion backgroundRegion02;
	public static Texture bg03;
	public static TextureRegion backgroundRegion03;
	public static Texture bg04;
	public static TextureRegion backgroundRegion04;
	public static Texture bg05;
	public static TextureRegion backgroundRegion05;
	public static Texture bg06;
	public static TextureRegion backgroundRegion06;

	
	
	public static Texture items;
	
	public static TextureRegion logo;
	public static TextureRegion mainMenuStart;
	public static TextureRegion mainMenuStartPush;
	public static TextureRegion mainMenuEndurance;
	public static TextureRegion mainMenuEndurancePush;
	public static TextureRegion mainMenuScoreRecord;
	public static TextureRegion mainMenuScoreRecordPush;
	
	public static Animation explain;
	
	public static TextureRegion ready00;
	public static TextureRegion ready01;
	public static TextureRegion ready02;
	public static TextureRegion ready03;
	public static TextureRegion ready04;
	public static TextureRegion ready05;
	public static TextureRegion ready06;
	public static TextureRegion storyR00;
	public static TextureRegion storyR01;
	public static TextureRegion storyR02;
	public static TextureRegion storyR03;
	public static TextureRegion storyR04;
	public static TextureRegion storyR05;
	public static TextureRegion storyR06;
	public static TextureRegion storyEnd;
	public static TextureRegion end;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion goArrow;
	public static TextureRegion resume;
	public static TextureRegion quit;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static Animation coinAnim;
	public static Animation sizJump;
	public static Animation sizFall;
	public static TextureRegion sizHit;
	public static Animation umaFly;
	public static Animation umaToge;
	public static TextureRegion platform;
	public static Animation breakingPlatform;
	public static TextureRegion nonBreakPlatform;
	public static Font font;
	public static Music music00;
	public static Music music01;
	public static Music music02;
	public static Music music03;
	public static Music music04;
	public static Music music05;
	public static Music music06;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	
	public static Sound hitDamageSound00;
	public static Sound hitDamageSound01;
	public static Sound hitDamageSound02;
	public static Sound bossDeadSound;
	
	public static Animation Boss;
	public static Animation bossDamage;
	public static Animation bossDead;
	public static TextureRegion man;
	public static TextureRegion girl;
	public static Animation girlFlame;
	
	public static void load(GLGame game){
		bg00 = new Texture(game, "bg00.png");
		backgroundRegion00 = new TextureRegion(bg00, 0, 0, 320, 480);
		bg01 = new Texture(game, "bg01.png");
		backgroundRegion01 = new TextureRegion(bg01, 0, 0, 320, 480);
		bg02 = new Texture(game, "bg02.png");
		backgroundRegion02 = new TextureRegion(bg02, 0, 0, 320, 480);
		bg03 = new Texture(game, "bg03.png");
		backgroundRegion03 = new TextureRegion(bg03, 0, 0, 320, 480);
		bg04 = new Texture(game, "bg04.png");
		backgroundRegion04 = new TextureRegion(bg04, 0, 0, 320, 480);
		bg05 = new Texture(game, "bg05.png");
		backgroundRegion05 = new TextureRegion(bg05, 0, 0, 320, 480);
		bg06 = new Texture(game, "bg06.png");
		backgroundRegion06 = new TextureRegion(bg06, 0, 0, 320, 480);

		items = new Texture(game, "items.png");
		
		logo = new TextureRegion(items, 0, 672, 320, 352);
		mainMenuStart = new TextureRegion(items, 0, 288, 146, 40);
		mainMenuStartPush = new TextureRegion(items, 147, 288, 146, 40);
		mainMenuEndurance = new TextureRegion(items, 0, 335, 182, 42);
		mainMenuEndurancePush = new TextureRegion(items, 205, 335, 182, 42);
		mainMenuScoreRecord = new TextureRegion(items, 0, 384, 155, 42);
		mainMenuScoreRecordPush = new TextureRegion(items, 173, 384, 155, 42);
				
		explain = new Animation(1.0f, 	new TextureRegion(items, 256, 224, 128, 64),
										new TextureRegion(items, 384, 224, 128, 64),
										new TextureRegion(items, 512, 224, 128, 64),
										new TextureRegion(items, 640, 224, 96, 64));
				
		ready00 = new TextureRegion(items, 192, 192, 96, 32);
		ready01 = new TextureRegion(items, 288, 192, 96, 32);
		ready02 = new TextureRegion(items, 384, 192, 96, 32);
		ready03 = new TextureRegion(items, 480, 192, 96, 32);
		ready04 = new TextureRegion(items, 576, 192, 96, 32);
		ready05 = new TextureRegion(items, 672, 192, 96, 32);
		ready06 = new TextureRegion(items, 768, 192, 96, 32);
		
		storyR00 = new TextureRegion(items, 0, 480, 256, 192);
		storyR01 = new TextureRegion(items, 256, 480, 256, 192);
		storyR02 = new TextureRegion(items, 512, 480, 256, 192);
		storyR03 = new TextureRegion(items, 768, 480, 256, 192);
		storyR04 = new TextureRegion(items, 320, 672, 256, 192);
		storyR05 = new TextureRegion(items, 576, 672, 256, 192);
		storyR06 = new TextureRegion(items, 832, 672, 192, 192);
		storyEnd = new TextureRegion(items, 320, 864, 256, 160);
		end = new TextureRegion(items, 800, 224, 96, 32);
		
		gameOver = new TextureRegion(items, 864, 192, 96, 32);
		highScoresRegion = new TextureRegion(Assets.items, 256, 384, 224, 32);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		goArrow = new TextureRegion(items,736, 224, 64, 32);
		
		resume = new TextureRegion(items, 0, 448, 113, 24);
		quit = new TextureRegion(items, 128, 448, 70, 25);
		
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

		umaFly = new Animation(0.2f,	new TextureRegion(items, 0, 160, 32, 16),
										new TextureRegion(items, 32, 160, 32, 16));
		
		umaToge = new Animation(0.2f,	new TextureRegion(items, 0, 176, 32, 16),
										new TextureRegion(items, 32, 176, 32, 16));
		
		platform = new TextureRegion(items, 64, 160, 64, 64);
		
		breakingPlatform = new Animation(0.2f,	new TextureRegion(items, 0, 224, 64, 64),
												new TextureRegion(items, 64, 224, 64, 64),
												new TextureRegion(items, 128, 224, 64, 64),
												new TextureRegion(items, 196, 224, 64, 64));
		
		nonBreakPlatform = new TextureRegion(items, 128, 160, 64, 16);
		
		Boss = new Animation(0.2f,	new TextureRegion(items, 192, 96, 96, 96),
				new TextureRegion(items, 288, 96, 96, 96),
				new TextureRegion(items, 384, 96, 96, 96),
				new TextureRegion(items, 288, 96, 96, 96));
		
		bossDamage = new Animation(0.2f,	new TextureRegion(items, 768, 96, 96, 96),
											new TextureRegion(items, 768, 96, 96, 96),
											new TextureRegion(items, 384, 96, 96, 96));
		
		bossDead = new Animation(0.2f,	new TextureRegion(items, 480, 0, 96, 96),
										new TextureRegion(items, 576, 0, 96, 96),
										new TextureRegion(items, 672, 0, 96, 96),
										new TextureRegion(items, 768, 0, 96, 96),
										new TextureRegion(items, 864, 0, 96, 96),
										new TextureRegion(items, 480, 96, 96, 96),
										new TextureRegion(items, 576, 96, 96, 96),
										new TextureRegion(items, 672, 96, 96, 96));

		man = new TextureRegion(items, 960, 0, 32, 32);
		girl = new TextureRegion(items, 992, 0, 32, 32);
		girlFlame = new Animation(0.2f,	new TextureRegion(items, 960, 32, 32, 32),
										new TextureRegion(items, 992, 32, 32, 32),
										new TextureRegion(items, 960, 64, 32, 32),
										new TextureRegion(items, 992, 64, 32, 32)); 
		
		font = new Font(items, 224, 0, 16, 16, 16);
		
		music00 = game.getAudio().newMusic("R00.ogg");
		music00.setLooping(true);
		music00.setVolume(0.5f);
		music01 = game.getAudio().newMusic("R01.ogg");
		music01.setLooping(true);
		music01.setVolume(0.5f);
		music02 = game.getAudio().newMusic("R02.ogg");
		music02.setLooping(true);
		music02.setVolume(0.5f);
		music03 = game.getAudio().newMusic("R03.ogg");
		music03.setLooping(true);
		music03.setVolume(0.5f);
		music04 = game.getAudio().newMusic("R04.ogg");
		music04.setLooping(true);
		music04.setVolume(0.5f);
		music05 = game.getAudio().newMusic("R05.ogg");
		music05.setLooping(true);
		music05.setVolume(0.5f);
		music06 = game.getAudio().newMusic("R06.ogg");
		music06.setLooping(true);
		music06.setVolume(0.5f);
		
		if(Settings.soundEnabled)
			switch(Settings.currentRound){
			case 0:
				music00.play();
				break;
			case 1:
				music01.play();
				break;
			case 2:
				music02.play();
				break;
			case 3:
				music03.play();
				break;
			case 4:
				music04.play();
				break;
			case 5:
				music05.play();
				break;
			case 6:
				music06.play();
				break;
			}
		jumpSound = game.getAudio().newSound("jump.ogg");
		highJumpSound = game.getAudio().newSound("up.ogg");
		hitSound = game.getAudio().newSound("damage.ogg");
		coinSound = game.getAudio().newSound("goin.ogg");
		hitDamageSound00 = game.getAudio().newSound("hitDamage00.ogg");
		hitDamageSound01 = game.getAudio().newSound("hitDamage01.ogg");
		hitDamageSound02 = game.getAudio().newSound("hitDamage02.ogg");
		bossDeadSound = game.getAudio().newSound("bossDead.ogg");		
		clickSound = game.getAudio().newSound("click.ogg");
		
	}
	
	public static void reload(){
		switch(Settings.currentRound){
		case 0:
			bg00.reload();
			items.reload();
			if(Settings.soundEnabled)
				music00.play();
			break;
		case 1:
			bg01.reload();
			items.reload();
			if(Settings.soundEnabled)
				music01.play();
			break;
		case 2:
			bg02.reload();
			items.reload();
			if(Settings.soundEnabled)
				music02.play();
			break;
		case 3:
			bg03.reload();
			items.reload();
			if(Settings.soundEnabled)
				music03.play();
			break;
		case 4:
			bg04.reload();
			items.reload();
			if(Settings.soundEnabled)
				music04.play();
			break;
		case 5:
			bg05.reload();
			items.reload();
			if(Settings.soundEnabled)
				music05.play();
			break;
		case 6:
			bg06.reload();
			items.reload();
			if(Settings.soundEnabled)
				music06.play();
			break;

		}
	}
	
	public static void playSound(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}
	
}
