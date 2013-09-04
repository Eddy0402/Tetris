package edu.ncku.eddy.game.component.mino;

import edu.ncku.eddy.GameEngine;
import edu.ncku.eddy.Launcher;
import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Block.BlockType;
import edu.ncku.eddy.game.component.Piece;

public class L extends Piece {

	public L(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public BlockMovingPosition[] getBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rotatePiece(RotationMethod rotationMethod) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isTouchingButtom() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveLeft() {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean moveRight() {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean canMoveLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canMoveRight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveDown() {
		return false;
	}

	@Override
	public Type getType() {
		return Type.L;
	}

}
