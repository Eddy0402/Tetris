package edu.ncku.eddy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Config {

	public static boolean enableGhost = true;

	// Keys
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

	// ¹CÀ¸¤â·P

	// delay auto shift
	public static int DAS;
	// auto repeat rate
	public static int ARR;
	// soft drop have no DAS,this variable is ARR setting
	public static int SOFT_DROP_ARR;

	public static boolean AR;

	static {
		if (readConfig()) {
		} else {
			// default key

			KEY_MOVE_RIGHT = 39;
			KEY_MOVE_LEFT = 37;

			KEY_SOFT_DROP = 40;
			KEY_HARD_DROP = 32;

			KEY_ROTATE_RIGHT = 88;
			KEY_ROTATE_LEFT = 90;
			KEY_ROTATE_UPSIDE_DOWN = 38;

			KEY_HOLD = 16;

			KEY_STOP_GAME = 27;
			KEY_START_GAME = 10;

			DAS = 90;
			ARR = 20;
			SOFT_DROP_ARR = 20;
			AR = false;
		}
	}

	private static boolean readConfig() {
		boolean readed = false;

		File cngFile = new File("res/config.txt");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cngFile), "UTF8"));

			String line = reader.readLine();
			String[] pars = line.split(",");

			try {
				KEY_MOVE_RIGHT = Integer.parseInt(pars[0]);
				KEY_MOVE_LEFT = Integer.parseInt(pars[1]);

				KEY_SOFT_DROP = Integer.parseInt(pars[2]);
				KEY_HARD_DROP = Integer.parseInt(pars[3]);

				KEY_ROTATE_RIGHT = Integer.parseInt(pars[4]);
				KEY_ROTATE_LEFT = Integer.parseInt(pars[5]);
				KEY_ROTATE_UPSIDE_DOWN = Integer.parseInt(pars[6]);

				KEY_HOLD = Integer.parseInt(pars[7]);

				KEY_STOP_GAME = Integer.parseInt(pars[8]);
				KEY_START_GAME = Integer.parseInt(pars[9]);

				DAS = Integer.parseInt(pars[10]);
				ARR = Integer.parseInt(pars[11]);
				SOFT_DROP_ARR = Integer.parseInt(pars[12]);
				if (pars[13].equals("yes")) {
					AR = true;
				} else {
					AR = false;
				}
			} catch (NumberFormatException e) {
				reader.close();
				return readed;
			} catch (ArrayIndexOutOfBoundsException e) {
				reader.close();
				return readed;
			}
			System.out.println("read config sucessful!");
			reader.close();
		} catch (Exception e) {
			return readed;
		}

		readed = true;

		return readed;
	}
}
