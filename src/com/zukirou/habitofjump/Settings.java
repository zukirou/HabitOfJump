package com.zukirou.habitofjump;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.zukirou.gameFrameWork.FileIO;

public class Settings{
	public static boolean soundEnabled = true;
	public final static int[] highscores = new int[] {100, 80, 50, 30, 10};
	public final static String file = ".hoj";
	public static int currentRound = 0;
	
	public static void load(FileIO files){
		BufferedReader in = null;
		try{
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for(int i = 0; i < 5; i++){
				highscores[i] = Integer.parseInt(in.readLine());
			}
			currentRound = Integer.parseInt(in.readLine());
			
		}catch(IOException e){
			
		}catch(NumberFormatException e){
			
		}finally{
			try{
				if(in != null)
					in.close();
			}catch(IOException e){
				
			}
		}
	}
	
	public static void save(FileIO files){
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
			out.write(Boolean.toString(soundEnabled));
			out.write("/n");
			for(int i = 0; i < 5; i++){
				out.write(Integer.toString(highscores[i]));
				out.write("/n");
			}
			out.write(Integer.toString(currentRound));
		}catch(IOException e){
			
		}finally{
			try{
				if(out != null)
					out.close();
			}catch (IOException e){
				
			}
		}
	}
	
	public static void addScore(int score, int round){
		if(currentRound <= round)
			currentRound = round;
	
		for(int i = 0; i < 5; i++){
			if(highscores[i] < score){
				for(int j = 4; j > 1; j--)
					highscores[j] = highscores[j - 1];
				highscores[i] = score;
				break;	
			}	
		}
	}
	
}