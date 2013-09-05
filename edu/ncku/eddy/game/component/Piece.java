package edu.ncku.eddy.game.component;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.mino.*;
import edu.ncku.eddy.util.TestOutput;

/**
 * �ާ@����Tetromino�Aposition�N��䤤�ߤ��
 * 
 * @author Eddy
 */
public abstract class Piece {
	protected int positionCol;
	protected int positionLine;
	protected RotationState rotationState = RotationState.Default;

	/**
	 * Tetromino����
	 */
	public enum Type {
		I, O, S, Z, L, J, T
	}

	/**
	 * ���બ�A
	 */
	public enum RotationState {
		Default, Right, UpsideDown, Left
	}

	/**
	 * ����ʧ@
	 */
	public enum RotationMethod {
		Right, Left, UpsideDown
	}

	/**
	 * ����P�w�I�s��
	 */
	protected enum OffsetData {
		Offset1, Offset2, Offset3, Offset4, Offset5
	}

	/**
	 * �إ߷s����eTetromino ��l��m�w�]��X:4(��5��)�BY:19(��20��)
	 * 
	 * @param type
	 *            Tetromino����
	 * @param positionX
	 *            ��l��mX
	 * @param positionY
	 *            ��l��mY
	 * @return ������Piece����
	 */
	public static Piece createPiece(Type type, int positionX, int positionY) {
		switch (type) {
		case I:
			return new I(positionY, positionY);
		case J:
			return new J(positionY, positionY);
		case L:
			return new L(positionY, positionY);
		case O:
			return new O(positionY, positionY);
		case S:
			return new S(positionY, positionY);
		case T:
			return new T(positionY, positionY);
		case Z:
			return new Z(positionY, positionY);
		default:
			return null;
		}
	}

	/**
	 * �����s�Τ�k�A���o�������
	 */
	protected Piece(int positionCol, int positionLine) {
		this.positionCol = positionCol;
		this.positionLine = positionLine;
		rotationState = RotationState.Default;
	}

	/**
	 * �^��Tetromino����
	 */
	public abstract Type getType();

	/**
	 * ��o��Tetromino�֦�������A��lock�ɥs��
	 * 
	 * @return �|�Ӯy��
	 */
	public final BlockMovingPosition[] getBlocks() {
		return getBlocks(positionLine, positionCol, rotationState);
	}

	/**
	 * �ǤJ���ߦ�m�����A��o��Tetromino�Ҧ�������A�Ǧ^BlockMovingPosition�}�C <b>��l���O��@</b>
	 * 
	 * @param positionLine
	 *            �ؼФ�����ߦ�m
	 * @param positionCol
	 *            �ؼФ�����ߦ�m
	 * @param rotationState
	 *            ���
	 * @return BlockMovingPosition�}�C
	 */
	public abstract BlockMovingPosition[] getBlocks(int positionLine, int positionCol, RotationState rotationState);

	/**
	 * ����Tetromino(�t�h�Ӵ����I) <b>��l���O��@</b>
	 * 
	 * @see {@link http://harddrop.com/wiki/SRS}
	 * @see {@link http://tetris.wikia.com/wiki/SRS}
	 * 
	 * @param rotationMethod
	 *            �����V
	 * @return <code>true</code> ������ <code>false</code> ������
	 */
	public abstract boolean rotatePiece(RotationMethod rotationMethod);

	protected boolean rotatePieceJLSTZ(RotationMethod rotationMethod) {

		// �P�_���U�Ӫ����
		RotationState nextRotationState = getNextRotationState(rotationState, rotationMethod);

		// �P�_�C���I�O�_�i��
		for (OffsetData offsetData : OffsetData.values()) {
			int[] previousOffsetData = getJLSTZOffsetData(rotationState, offsetData);
			int[] nextOffsetData = getJLSTZOffsetData(nextRotationState, offsetData);
			if (checkPoint(positionLine + previousOffsetData[0]
					- nextOffsetData[0], positionCol + previousOffsetData[1]
					- nextOffsetData[1], nextRotationState)) {
				this.rotationState = nextRotationState;
				this.positionLine = positionLine + previousOffsetData[0]
						- nextOffsetData[0];
				this.positionCol = positionCol + previousOffsetData[1]
						- nextOffsetData[1];
				return true;
			}
		}

		return false;
	}

	// �e�̬�line�B��̬�col�A�]����n��HardDrop�ۤ�
	protected int[] getJLSTZOffsetData(RotationState rotationState, OffsetData offsetData) {
		switch (rotationState) {
		case Default:
			switch (offsetData) {
			case Offset1:
				return new int[] { 0, 0 };
			case Offset2:
				return new int[] { 0, 0 };
			case Offset3:
				return new int[] { 0, 0 };
			case Offset4:
				return new int[] { 0, 0 };
			case Offset5:
				return new int[] { 0, 0 };
			}
		case Left:
			switch (offsetData) {
			case Offset1:
				return new int[] { 0, 0 };
			case Offset2:
				return new int[] { 0, -1 };
			case Offset3:
				return new int[] { -1, -1 };
			case Offset4:
				return new int[] { 2, 0 };
			case Offset5:
				return new int[] { 2, -1 };
			}
		case Right:
			switch (offsetData) {
			case Offset1:
				return new int[] { 0, 0 };
			case Offset2:
				return new int[] { 0, 1 };
			case Offset3:
				return new int[] { -1, 1 };
			case Offset4:
				return new int[] { 2, 0 };
			case Offset5:
				return new int[] { 2, 1 };
			}
		case UpsideDown:
			switch (offsetData) {
			case Offset1:
				return new int[] { 0, 0 };
			case Offset2:
				return new int[] { 0, 0 };
			case Offset3:
				return new int[] { 0, 0 };
			case Offset4:
				return new int[] { 0, 0 };
			case Offset5:
				return new int[] { 0, 0 };
			}
		}
		return null;
	}

	/**
	 * ����Tetromino
	 * 
	 * @return <code>true</code> ������ <code>false</code> ������
	 */
	public final boolean moveLeft() {
		if (canMoveLeft()) {
			positionCol--;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �k��Tetromino
	 * 
	 * @return <code>true</code> ������ <code>false</code> ������
	 */
	public final boolean moveRight() {
		if (canMoveRight()) {
			positionCol++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �U��Tetromino
	 * 
	 * @return <code>true</code> ������ <code>false</code> ������
	 */
	public final boolean moveDown() {
		if (canMoveDown()) {
			positionLine--;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �P�_�i�_����Tetromino
	 * 
	 * @return <code>true</code> �i���� <code>false</code> ���i����
	 */
	protected final boolean canMoveLeft() {
		return checkPoint(positionLine, positionCol - 1, rotationState);
	}

	/**
	 * �P�_�i�_�k��Tetromino
	 * 
	 * @return <code>true</code> �i���� <code>false</code> ���i����
	 */
	protected final boolean canMoveRight() {
		return checkPoint(positionLine, positionCol + 1, rotationState);
	}

	/**
	 * �P�_�i�_�U��Tetromino
	 * 
	 * @return <code>true</code> �i���� <code>false</code> ���i����
	 */
	public final boolean canMoveDown() {
		TestOutput.sysout(checkPoint(positionLine - 1, positionCol, rotationState));
		return checkPoint(positionLine - 1, positionCol, rotationState);
	}

	/**
	 * �P�_Tetromino�O�_�i�s�b��ؼЦ�m
	 * 
	 * @param positionLine
	 *            �ؼФ�����ߦ�m
	 * @param positionCol
	 *            �ؼФ�����ߦ�m
	 * @param rotationState
	 *            �ؼ����
	 * @return true:�Ӧ�m�ů� false:���i����
	 */
	protected final boolean checkPoint(int positionLine, int positionCol, RotationState rotationState) {

		boolean result = true;

		for (BlockMovingPosition position : getBlocks(positionLine, positionCol, rotationState)) {
			if (Launcher.gameEngine.GetField().getBlock(position) == null) {
				result = false;
				return result;
			}
			if (Launcher.gameEngine.GetField().getBlock(position).getBlockType() != BlockType.None) {
				result = false;
			}
		}

		return result;
	}

	protected final RotationState getNextRotationState(RotationState previosRotationState, RotationMethod rotationMethod) {
		switch (rotationMethod) {
		case Left:
			switch (previosRotationState) {
			case Default:
				return RotationState.Left;
			case Left:
				return RotationState.UpsideDown;
			case Right:
				return RotationState.Default;
			case UpsideDown:
				return RotationState.Right;
			}
			break;
		case Right:
			switch (previosRotationState) {
			case Default:
				return RotationState.Right;
			case Left:
				return RotationState.Default;
			case Right:
				return RotationState.UpsideDown;
			case UpsideDown:
				return RotationState.Left;
			}
			break;
		case UpsideDown:
			switch (previosRotationState) {
			case Default:
				return RotationState.UpsideDown;
			case Left:
				return RotationState.Right;
			case Right:
				return RotationState.Left;
			case UpsideDown:
				return RotationState.Default;
			}
			break;
		}
		// �W���w�g�P�_�Ҧ��i��A����O���F�q�L�sĶ
		return previosRotationState;
	}

	/**
	 * �Ω��ܳ�@Block����m
	 * 
	 * @author Eddy
	 */
	public class BlockMovingPosition {
		public int line;
		public int col;

		public BlockMovingPosition(int line, int col) {
			this.line = line;
			this.col = col;
		}
	}

}
