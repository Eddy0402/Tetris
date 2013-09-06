package edu.ncku.eddy.util;

public class Config {

	public static boolean enableGhost = true;
	
	public static int KEY_MOVE_RIGHT;
	public static int KEY_MOVE_LEFT;
	
	public static int KEY_SOFT_DROP;
	public static int KEY_HARD_DROP;
	
	public static int KEY_ROTATE_RIGHT;
	public static int KEY_ROTATE_LEFT;
	public static int KEY_ROTATE_UPSIDE_DOWN;
	
	public static int KEY_HOLD;
	
	public static int KEY_STOP_GAME;
	public static int KEY_START_GAME;
	
	
	static{
		if(readConfig()){
			//Initialize key setting
			
		}else{
			//default key
			
			KEY_MOVE_RIGHT=39;
			KEY_MOVE_LEFT=37;
			
			KEY_SOFT_DROP=40;
			KEY_HARD_DROP=32;
			
			KEY_ROTATE_RIGHT=88;
			KEY_ROTATE_LEFT=90;
			KEY_ROTATE_UPSIDE_DOWN=38;
			
			KEY_HOLD=16;
			
			KEY_STOP_GAME=27;
			KEY_START_GAME=10;
			
		}		
	}
	
	private static boolean readConfig(){
		return false;
	}

}
