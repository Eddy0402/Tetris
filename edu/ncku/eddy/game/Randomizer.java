package edu.ncku.eddy.game;

import java.util.Random;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.component.Piece;
import edu.ncku.eddy.component.mino.*;
import edu.ncku.eddy.util.TestOutput;

public class Randomizer {
	private long seed;
	private Random rnd;

	public final int NEW_BLOCK_POSITION_X = 4;
	public final int NEW_BLOCK_POSITION_Y = 20;

	/**
	 * �@���ͦ��@�U
	 */
	private int currentBagIndex = 0;

	/**
	 * �ӳU����}�C
	 */
	private int[] currentBag = new int[7];
	private int[] nextBag = new int[7];

	public Randomizer(long seed) {
		this.seed = seed;
		this.rnd = new Random(seed + currentBagIndex);

		// �ͦ��Ĥ@�U
		currentBag = generateBag(7);
	}

	private int[] generateBag(int typeCount) {

		// �ϥμ��J�P�k����0-6�H���ƦC���}�C
		// ���إߤ@�Ӱ}�C�A�M���H�������Ʀr
		int[] result = new int[] { 0, 1, 2, 3, 4, 5, 6 };
		for (int i = 0; i < 7; i++) {
			int replace = (int) Math.floor(rnd.nextFloat() * 7);
			TestOutput.sysout(replace);

			// �P��i�Ӽƥ洫
			int temp;
			temp = result[i];
			result[i] = result[replace];
			result[replace] = temp;
		}
		TestOutput.sysout(result[0] + "," + result[1] + "," + result[2] + ","
				+ result[3] + "," + result[4] + "," + result[5] + ","
				+ result[6]);

		return result;
	}

	/**
	 * �]���b��ܮɥs�Ψ�A���͸��᭱��Tetromino�A�]�����ͤ@�U����������g�b�o�̡C
	 */
	public Piece[] getNextPieces() {
		int pieceCount = Launcher.gameEngine.getPieceIndex();

		Piece[] result = new Piece[6];

		int index = 0;
		boolean generatedNewBag = false;

		for (int i = pieceCount + 1; i < pieceCount + 7; i++) {
			int indexInBagOfNextPiece = i % 7;

			int bagIndex = (i - indexInBagOfNextPiece) / 7;

			if (bagIndex != currentBagIndex) {
				rnd.setSeed(seed + currentBagIndex + 1);
				nextBag = generateBag(7);
				generatedNewBag = true;
			}

			int[] bag = currentBag;
			if (generatedNewBag) {
				bag = nextBag;
			}

			switch (bag[indexInBagOfNextPiece]) {
			case 0:
				result[index] = new I(0, 0);
				break;
			case 1:
				result[index] = new Z(0, 0);
				break;
			case 2:
				result[index] = new S(0, 0);
				break;
			case 3:
				result[index] = new J(0, 0);
				break;
			case 4:
				result[index] = new L(0, 0);
				break;
			case 5:
				result[index] = new T(0, 0);
				break;
			case 6:
				result[index] = new O(0, 0);
				break;
			}

			index++;
		}

		return result;
	}

	public Piece getNewPiece() {
		int pieceCount = Launcher.gameEngine.getPieceIndex();
		int indexInBagOfNextPiece = pieceCount % 7;
		int bagIndex = (pieceCount - indexInBagOfNextPiece) / 7;

		if (bagIndex != currentBagIndex) {
			currentBagIndex++;
			currentBag = nextBag;
		}

		switch (currentBag[indexInBagOfNextPiece]) {
		case 0:
			return new I(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);
		case 1:
			return new Z(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);
		case 2:
			return new S(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);
		case 3:
			return new J(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);
		case 4:
			return new L(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);
		case 5:
			return new T(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);
		case 6:
			return new O(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);
		}

		// �ϳq�L�sĶ
		return new I(NEW_BLOCK_POSITION_X, NEW_BLOCK_POSITION_Y);

	}
}
