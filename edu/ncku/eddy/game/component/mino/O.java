package edu.ncku.eddy.game.component.mino;

import edu.ncku.eddy.game.component.Block;
import edu.ncku.eddy.game.component.Piece;

public class O extends Piece {

	public O(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public Block[] getBlocks() {
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
		// TODO Auto-generated method stub
		
	}

}
