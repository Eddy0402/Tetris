package edu.ncku.eddy.game.component;

import java.util.ArrayList;

import edu.ncku.eddy.game.component.mino.*;


/**
 * �ާ@����Tetromino�Aposition�N��䤤�ߤ��
 * @author Eddy
 */
public abstract class Piece {
	protected int positionX;
	protected int positionY;
	protected int rotationState;

	
	/**
	 * Tetromino����
	 */
	public enum Type {
		I, O, S, Z, L, J ,T
	}	
	
	/**
	 * ���બ�A
	 */
	public enum RotationState {
		Default, Right, UpsideDown, Left
	}
	
	public enum RotationMethod {
		Right, Left , UpsideDown
	}

	/**
	 * �إ߷s����eTetromino
	 * ��l��m�w�]��X:4(��5��)�BY:19(��20��)
	 * @param type Tetromino����
	 * @param positionX ��l��mX
	 * @param positionY ��l��mY
	 * @return ������Piece����
	 */
	public static Piece createPiece(Type type,int positionX, int positionY)
	{
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
	
	public Piece(int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public abstract Type getType(); 

	/**
	 * ��o��Tetromino�֦�������A��lock�ɥs��
	 * @return �|�Ӯy��
	 */
	public abstract ArrayList<BlockMovingPosition> getBlocks();
	
	/**
	 * ����Tetromino
	 * @param �����V
	 * @return TODO
	 * @return true:������ false:������
	 */
	public abstract boolean rotatePiece(RotationMethod rotationMethod);
		
	public abstract boolean moveLeft();
	
	public abstract boolean moveRight();
	
	public abstract boolean moveDown();
	
	protected abstract boolean isTouchingButtom();
	
	protected abstract boolean canMoveLeft();
	
	protected abstract boolean canMoveRight();
	
	public class BlockMovingPosition{
		public int X;
		public int Y;
	}
}
