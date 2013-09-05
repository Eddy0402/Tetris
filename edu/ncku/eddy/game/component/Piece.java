package edu.ncku.eddy.game.component;

import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.mino.*;
import edu.ncku.eddy.util.TestOutput;

/**
 * 操作中的Tetromino，position代表其中心方塊
 * 
 * @author Eddy
 */
public abstract class Piece {
	protected int positionCol;
	protected int positionLine;
	protected RotationState rotationState = RotationState.Default;

	/**
	 * Tetromino種類
	 */
	public enum Type {
		I, O, S, Z, L, J, T
	}

	/**
	 * 旋轉狀態
	 */
	public enum RotationState {
		Default, Right, UpsideDown, Left
	}

	/**
	 * 旋轉動作
	 */
	public enum RotationMethod {
		Right, Left, UpsideDown
	}

	/**
	 * 旋轉判定點編號
	 */
	protected enum OffsetData {
		Offset1, Offset2, Offset3, Offset4, Offset5
	}

	/**
	 * 建立新的當前Tetromino 初始位置預設為X:4(第5格)、Y:19(第20格)
	 * 
	 * @param type
	 *            Tetromino種類
	 * @param positionX
	 *            初始位置X
	 * @param positionY
	 *            初始位置Y
	 * @return 相應的Piece物件
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
	 * 內部叫用方法，取得方塊實體
	 */
	protected Piece(int positionCol, int positionLine) {
		this.positionCol = positionCol;
		this.positionLine = positionLine;
		rotationState = RotationState.Default;
	}

	/**
	 * 回傳Tetromino種類
	 */
	public abstract Type getType();

	/**
	 * 獲得該Tetromino擁有的方塊，於lock時叫用
	 * 
	 * @return 四個座標
	 */
	public final BlockMovingPosition[] getBlocks() {
		return getBlocks(positionLine, positionCol, rotationState);
	}

	/**
	 * 傳入中心位置及轉位，獲得該Tetromino所有的方塊，傳回BlockMovingPosition陣列 <b>於子類別實作</b>
	 * 
	 * @param positionLine
	 *            目標方塊中心位置
	 * @param positionCol
	 *            目標方塊中心位置
	 * @param rotationState
	 *            轉位
	 * @return BlockMovingPosition陣列
	 */
	public abstract BlockMovingPosition[] getBlocks(int positionLine, int positionCol, RotationState rotationState);

	/**
	 * 旋轉Tetromino(含多個測試點) <b>於子類別實作</b>
	 * 
	 * @see {@link http://harddrop.com/wiki/SRS}
	 * @see {@link http://tetris.wikia.com/wiki/SRS}
	 * 
	 * @param rotationMethod
	 *            旋轉方向
	 * @return <code>true</code> 有旋轉 <code>false</code> 未旋轉
	 */
	public abstract boolean rotatePiece(RotationMethod rotationMethod);

	protected boolean rotatePieceJLSTZ(RotationMethod rotationMethod) {

		// 判斷接下來的轉位
		RotationState nextRotationState = getNextRotationState(rotationState, rotationMethod);

		// 判斷每個點是否可用
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

	// 前者為line、後者為col，因此剛好跟HardDrop相反
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
	 * 左移Tetromino
	 * 
	 * @return <code>true</code> 有移動 <code>false</code> 未移動
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
	 * 右移Tetromino
	 * 
	 * @return <code>true</code> 有移動 <code>false</code> 未移動
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
	 * 下移Tetromino
	 * 
	 * @return <code>true</code> 有移動 <code>false</code> 未移動
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
	 * 判斷可否左移Tetromino
	 * 
	 * @return <code>true</code> 可移動 <code>false</code> 不可移動
	 */
	protected final boolean canMoveLeft() {
		return checkPoint(positionLine, positionCol - 1, rotationState);
	}

	/**
	 * 判斷可否右移Tetromino
	 * 
	 * @return <code>true</code> 可移動 <code>false</code> 不可移動
	 */
	protected final boolean canMoveRight() {
		return checkPoint(positionLine, positionCol + 1, rotationState);
	}

	/**
	 * 判斷可否下移Tetromino
	 * 
	 * @return <code>true</code> 可移動 <code>false</code> 不可移動
	 */
	public final boolean canMoveDown() {
		TestOutput.sysout(checkPoint(positionLine - 1, positionCol, rotationState));
		return checkPoint(positionLine - 1, positionCol, rotationState);
	}

	/**
	 * 判斷Tetromino是否可存在於目標位置
	 * 
	 * @param positionLine
	 *            目標方塊中心位置
	 * @param positionCol
	 *            目標方塊中心位置
	 * @param rotationState
	 *            目標轉位
	 * @return true:該位置空缺 false:不可移動
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
		// 上面已經判斷所有可能，此行是為了通過編譯
		return previosRotationState;
	}

	/**
	 * 用於表示單一Block的位置
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
