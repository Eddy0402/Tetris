package edu.ncku.eddy.util;

public class TestOutput {
	public static void sysout(String outString){
		System.out.println(outString);
	}

	public static void sysout(int outint) {
		System.out.println(outint);
	}

	public static void sysout(boolean outboolean) {
		System.out.println(outboolean ? "true" : "false");
	}
}
