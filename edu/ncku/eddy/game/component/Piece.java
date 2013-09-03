package edu.ncku.eddy.game.component;

/*
 * 操作中的Tetromino，position代表其中心方塊
 * Type為其種類，
 */
public abstract class Piece {
	private int positionX;
	private int positionY;
	private int rotationState;
	public Type type;

	public enum Type {
		I, O, S, Z, L, J ,T
	}

	public enum RotationState {
		Default, Right, UpsideDown, Left
	}

	public Piece(Type type, int positionX, int positionY) {
		this.type = type;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public abstract Block[] getBlocks();
	
	public abstract void rotatePiece();

}
