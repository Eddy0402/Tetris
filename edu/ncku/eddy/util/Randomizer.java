package edu.ncku.eddy.util;

import java.util.Random;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.game.component.Piece;
import edu.ncku.eddy.game.component.mino.*;

public class Randomizer {
	private long seed;
	private Random rnd;

	/**
	 * 一次生成一袋
	 */
	private int currentBagIndex = 0;

	/**
	 * 該袋方塊陣列
	 */
	private int[] currentBag = new int[7];

	public Randomizer(long seed) {
		this.seed = seed;
		this.rnd = new Random(seed + currentBagIndex);

		// 生成第一袋
		currentBag = generateBag(7);
	}

	private int[] generateBag(int typeCount) {

		// 使用撲克牌法產生0-6隨機排列的陣列
		// 先建立一個陣列，然後隨機互換數字
		int[] result = new int[] { 0, 1, 2, 3, 4, 5, 6 };
		for (int i = 0; i < 7; i++) {
			int replace = (int) Math.floor(rnd.nextFloat() * 7);
			TestOutput.sysout(replace);

			// 與第i個數交換
			int temp;
			temp = result[i];
			result[i] = result[replace] ;
			result[replace] = temp;
		}
		TestOutput.sysout(result[0] + "," + result[1] + "," + result[2] + ","
				+ result[3] + "," + result[4] + "," + result[5] + ","
				+ result[6]);

		return result;
	}

	/*
	 * public int[] createPieceSequence(boolean[] pieceEnable, Random random,
	 * int arrayMax) { int[] pieceArray = new int[arrayMax]; int pieceKind =
	 * GeneralUtil.getNumberOfPiecesCanAppear(pieceEnable);
	 * 
	 * 
	 * for(int i = 0; i < arrayMax / pieceKind; i++) { // Flags for pieces which
	 * have already appeared boolean[] alreadyAppeared = new
	 * boolean[Piece.PIECE_COUNT];
	 * 
	 * // Create draws for(int j = 0; j < pieceKind; j++) { int id = 0;
	 * 
	 * // Draw do { id = random.nextInt(Piece.PIECE_COUNT); }
	 * while((pieceEnable[id] == false) || (alreadyAppeared[id] == true));
	 * 
	 * // Set block drawn flag to ON alreadyAppeared[id] = true;
	 * 
	 * // Add to NEXT list pieceArray[i * pieceKind + j] = id; } }
	 * 
	 * return pieceArray; }
	 */

	public Piece[] getNextPieces() {
		// TODO next功能
		return null;
	}

	public Piece getNewPiece() {
		int pieceCount = Launcher.gameEngine.getPieceCount();
		int indexInBagOfNextPiece = pieceCount % 7;
		int bagIndex = (pieceCount - indexInBagOfNextPiece) / 7;

		if (bagIndex != currentBagIndex) {
			currentBagIndex++;
			rnd.setSeed(seed + currentBagIndex);
			currentBag = generateBag(7);
		}

		switch (currentBag[indexInBagOfNextPiece]) {
		case 0:
			return new I(4, 20);
		case 1:
			return new Z(4, 20);
		case 2:
			return new S(4, 20);
		case 3:
			return new J(4, 20);
		case 4:
			return new L(4, 20);
		case 5:
			return new T(4, 20);
		case 6:
			return new O(4, 20);
		}

		// 使通過編譯
		return new I(4, 20);

	}
}
