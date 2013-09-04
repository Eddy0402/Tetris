package edu.ncku.eddy.game.component;

import java.util.ArrayList;

import edu.ncku.eddy.game.component.mino.*;


/**
 * 操作中的Tetromino，position代表其中心方塊
 * @author Eddy
 */
public abstract class Piece {
	protected int positionX;
	protected int positionY;
	protected int rotationState;

	
	/**
	 * Tetromino種類
	 */
	public enum Type {
		I, O, S, Z, L, J ,T
	}	
	
	/**
	 * 旋轉狀態
	 */
	public enum RotationState {
		Default, Right, UpsideDown, Left
	}
	
	public enum RotationMethod {
		Right, Left , UpsideDown
	}

	/**
	 * 建立新的當前Tetromino
	 * 初始位置預設為X:4(第5格)、Y:19(第20格)
	 * @param type Tetromino種類
	 * @param positionX 初始位置X
	 * @param positionY 初始位置Y
	 * @return 相應的Piece物件
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
	 * 獲得該Tetromino擁有的方塊，於lock時叫用
	 * @return 四個座標
	 */
	public abstract ArrayList<BlockMovingPosition> getBlocks();
	
	/**
	 * 旋轉Tetromino
	 * @param 旋轉方向
	 * @return TODO
	 * @return true:有旋轉 false:未旋轉
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
