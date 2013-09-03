package edu.ncku.eddy.game.component;

/*
 * �ާ@����Tetromino�Aposition�N��䤤�ߤ��
 * Type��������A
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
