package edu.ncku.eddy.game.component.mino;

import java.util.ArrayList;

import edu.ncku.eddy.game.component.Piece;

public class S extends Piece {

	public S(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public ArrayList<BlockMovingPosition> getBlocks() {
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
	
	@Override
	public Type getType() {
		return Type.S;
	}


}
