package edu.ncku.eddy.util;

public class Config {

	public static boolean enableGhost = true;
	
	
	//Keys
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
	
	//¹CÀ¸¤â·P
	
	//delay auto shift
	public static int DAS;
	//auto repeat rate
	public static int ARR;
	//soft drop have no DAS,this variable is ARR setting
	public static int SOFT_DROP_ARR;
	
	static{
		if(readConfig()){
			//Initialize key setting by configuation file TODO
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
			
			DAS=90;
			ARR=5;
			SOFT_DROP_ARR=40;
			
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
			
			DAS=100;
			ARR=20;
			SOFT_DROP_ARR=50;			
		}		
	}
	
	private static boolean readConfig(){
		return false;
	}

}
